package Monitores;

import static Estruturas.Globals.MON_TRANSFERENCIA_TERMINAL;
import static Estruturas.Globals.lotação;
import static Estruturas.Globals.nChegadas;
import Estruturas.Globals.passState;
import Estruturas.Reply;
import Estruturas.VectorCLK;
import Interfaces.LoggingInterface;
import Interfaces.TransferenciaInterface;
import Registry.TransferenciaTerminalRegister;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * Monitor que simula a interacção entre os passageiros e o motorista na zona de
 * transferência entre os terminais de chegada e partida
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class TransferenciaTerminal implements TransferenciaInterface {

    /**
     * Fila de espera que se forma à entrada do autocarro. cada posição é
     * ocupada pela identificação de um passageiro.
     *
     * @serialField fila
     */
    private Queue<Integer> fila;

    /**
     * Número de passageiros que faltam transportar do terminal de chegada para
     * o terminal de saída do voo que acabou de aterrar
     *
     * @serialField passTRT
     */
    private int passTRT;

    /**
     * indica o número de voo (para o motorista saber se já acabou)
     *
     * @serialField nVoo
     */
    private int nVoo;

    /**
     * indica se já é altura de realizar a viagem
     * <ul>
     * <li>TRUE caso já for altura
     * <li>FALSE, caso contrário
     * </ul>
     *
     * @serialField timeUp
     */
    private boolean timeUp;

    /**
     * indica ao passageiro se ele pode entrar no autocarro
     * <ul>
     * <li>TRUE caso possa
     * <li>FALSE, caso contrário
     * </ul>
     *
     * @serialField canGo
     */
    private boolean canGo;
    /**
     * indica se o motorista pode deixar o próximo passageiro na fila entrar no
     * autocarro
     * <ul>
     * <li>TRUE caso possa
     * <li>FALSE, caso contrário
     * </ul>
     *
     * @serialField next
     */
    private boolean next;
    /**
     * Identifica quantas entidades activas instanciadas (passageiro, bagageiro
     * e motorista) já terminaram o seu ciclo de vida.
     * <p>
     * Necessário para a terminação do monitor.
     *
     * @serialField three_entities_ended
     */
    private int three_entities_ended;

    /**
     * Instância da comunicação monitores.
     * <p>
     * Necessário para a comunicação com o monitor <i>Logging</i>, no âmbito da
     * actualização do estado geral do problema aquando das operações realizadas
     * sobre o monitor.
     *
     * @serialField log
     */
    private final LoggingInterface log;

    /**
     * Instância do tipo de dados TransferenciaTerminalRegister
     *
     * @serialField transf
     */
    private TransferenciaTerminalRegister transf;

    /**
     * Instância do tipo de dados VectorCLK.
     *
     * @serialField v_clock
     */
    private VectorCLK v_clock;

    /**
     * Instanciação e inicialização do monitor TransferenciaTerminal
     *
     * @param log rererência para o objecto remoto correspondente ao monitor de
     * Logging
     * @param transf referência para o tipo de dados
     * TransferenciaTerminalRegister
     */
    public TransferenciaTerminal(LoggingInterface log, TransferenciaTerminalRegister transf) {
        nVoo = 1;
        passTRT = 99;
        fila = new LinkedList<>();
        three_entities_ended = 0;
        timeUp = false;
        canGo = false;
        next = false;
        this.log = log;
        this.transf = transf;
        v_clock = new VectorCLK();
    }

    /**
     * Utilizado pelo motorista para 'acordar' regularmente
     */
    private synchronized void tempoEsgotado() {
        //System.out.println("ACORDA MOTORISTA");
        timeUp = true;
        notifyAll();
    }

    /**
     * Apanhar o autocarro
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro anuncia que pretende apanhar o autocarro. Coloca-se na fila,
     * sendo-lhe atribuído um ticket com a posição em que se deverá sentar no
     * autocarro. Por fim, espera até que seja a sua vez de entrar no autocarro
     *
     * @param ts relógio vectorial do motorista
     * @param passageiroID identificador do passageiro
     * @return Posição do seu assento no autocarro juntamente com o relógio
     * vectorial actualizado
     */
    @Override
    public synchronized Reply takeABus(VectorCLK ts, int passageiroID) {
        v_clock.CompareVector(ts.getVc());
        //System.out.println("Take the bus");
        int ticket;
        try {
            log.UpdateVectorCLK(v_clock, MON_TRANSFERENCIA_TERMINAL);
            log.reportState(passageiroID, passState.AT_THE_ARRIVAL_TRANSFER_TERMINAL);
            fila.add(passageiroID + 1);

            log.addfilaEspera(passageiroID);
        } catch (RemoteException e) {
            System.exit(1);
        }
        ticket = fila.size() % lotação;

        if (fila.size() == lotação) {
            notifyAll();
        }
        try {
            while (!canGo || fila.peek() != passageiroID + 1) {
                wait();
            }
        } catch (InterruptedException ex) {
        }
        canGo = false;
        next = true;
        fila.remove();
        try {
            log.removefilaEspera();
        } catch (RemoteException e) {
            System.exit(1);
        }
        notifyAll();
        passTRT--;
        return new Reply(new VectorCLK(v_clock), (Object) ticket);
    }

    /**
     * Trabalho já acabou?
     * <p>
     * Invocador - Motorista.
     * <p>
     * Motorista verifica se o trabalho já acabou. É acordado nas seguintes
     * condições:
     * <ul>
     * <li>Se os passageiros na fila de espera, no passeio, cobrem a lotação do
     * autocarro
     * <li>Se a hora de partida chegou.
     * </ul>
     *
     * O trabalho dele acabou se à hora da partida não se encontrar ninguém no
     * passeio!
     *
     * @param ts relógio vectorial do motorista
     * @return Relógio vectorial actualizado juntamente com
     * <ul>
     * <li>TRUE, se o dia de trabalho acabou
     * <li>FALSE, caso contrário
     * </ul>
     */
    @Override
    public synchronized Reply hasDaysWorkEnded(VectorCLK ts) {
        v_clock.CompareVector(ts.getVc());
        try {
            log.UpdateVectorCLK(v_clock, MON_TRANSFERENCIA_TERMINAL);
        } catch (RemoteException ex) {
            System.exit(0);
        }
        while (true) {
            try {
                Reminder reminder = new Reminder(1);
                wait();
                reminder.timer.cancel();
            } catch (InterruptedException ex) {
            }
            if (fila.size() >= lotação || (timeUp && fila.size() > 0) || (nVoo >= nChegadas && passTRT == 0)) {
                break;
            }
        }

        timeUp = false;
        return new Reply(new VectorCLK(v_clock), (Object) (fila.isEmpty() && nVoo >= nChegadas && passTRT == 0));
    }

    /**
     * Anunciar início de viagem
     * <p>
     * Invocador - Motorista.
     * <p>
     * Motorista anuncia que a viagem vai começar, acorda um passageiro e
     * adormece. O objectivo deste método é chamar um passageiro de cada vez por
     * ordem de chegada na fila de espera. Entrada ordenada!
     *
     * @param ts relógio vectorial do motorista
     * @return Número de passageiros que tomaram interesse em participar na
     * viagem (limitado à lotação do Autocarro) juntamente com o relógio
     * vectorial actualizado
     */
    @Override
    public synchronized Reply announcingBusBoardingShouting(VectorCLK ts) {
        v_clock.CompareVector(ts.getVc());
        try {
            log.UpdateVectorCLK(v_clock, MON_TRANSFERENCIA_TERMINAL);
        } catch (RemoteException ex) {
            System.exit(0);
        }
        //System.out.println("ALL ABOAAARD!: passageiros à espera: " + fila.size());
        int pass = 0;
        int npass = fila.size();
        for (int i = 0; i < npass && i < lotação; i++) {
            canGo = true;
            notifyAll();
            while (!next) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                }
            }
            //System.out.println("Passageiro entra");
            next = false;
            pass++;
        }
        canGo = false;
        return new Reply(new VectorCLK(v_clock), (Object) pass);
    }

    /**
     * Task criada em cada simulação que simula o horario do autocarro. Após 5
     * segundos de uma instância desta classe for criada vai chamar o metodo
     * tempoEsgotado que coloca a flag timeUp a true.
     */
    public class Reminder {

        Timer timer;

        public Reminder(int seconds) {
            timer = new Timer();
            timer.schedule(new RemindTask(), seconds * 30, seconds * 30);
        }

        class RemindTask extends TimerTask {

            @Override
            public void run() {
                tempoEsgotado();
                timer.cancel(); //Terminate the timer thread
            }
        }
    }

    /**
     * Actualizar o voo que acabou de aterrar e os passageiros que vêm nele e se
     * encontram em trânsito.
     *
     * @param nvoo número de voo
     * @param nPassageiros número de passageiros em trânsito neste voo
     */
    @Override
    public synchronized void setnVoo(int nvoo, int nPassageiros) {
        this.nVoo = nvoo;
        this.passTRT = nPassageiros;
    }

    /**
     * Fechar o monitor TransferenciaTerminal.
     * <p>
     * Invocador: Bagageiro, Passageiro e Motorista
     * <p>
     * O bagageiro/passageiro/motorista após concluir o seu ciclo de vida invoca
     * a operação para fechar o monitor <i>TransferenciaTerminal</i>.
     */
    @Override
    public synchronized void shutdownMonitor() {
        if (++three_entities_ended >= 3) {
            transf.close();
        }
    }
}
