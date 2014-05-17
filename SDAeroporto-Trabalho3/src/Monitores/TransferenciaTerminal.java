package Monitores;

import static Estruturas.Globals.*;
import Estruturas.Globals.passState;
import Interfaces.LoggingInterface;
import Interfaces.TransferenciaInterface;
import Interfaces.TransferenciaMotoristaInterface;
import Interfaces.TransferenciaPassageiroInterface;
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
public class TransferenciaTerminal implements  TransferenciaInterface {

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
     * Instanciação e inicialização do monitor TransferenciaTerminal
     */
    public TransferenciaTerminal(LoggingInterface log) {
        nVoo = 1;
        passTRT = 99;
        fila = new LinkedList<>();
        three_entities_ended = 0;
        timeUp = false;
        canGo = false;
        next = false;
        this.log = log;
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
     * @param passageiroID identificador do passageiro
     * @return Posição do seu assento no autocarro
     */
    @Override
    public synchronized int takeABus(int passageiroID) {
        //System.out.println("Take the bus");
        int ticket;
        try {
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
        return ticket;
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
     * O trabalho dele acabou se à hora da partida não se encontrar ninguém no
     * passeio!
     *
     * @return
     * <ul>
     * <li>TRUE, se o dia de trabalho acabou
     * <li>FALSE, caso contrário
     * </ul>
     */
    @Override
    public synchronized boolean hasDaysWorkEnded() {

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
        return (fila.isEmpty() && nVoo >= nChegadas && passTRT == 0);
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
     * @return Número de passageiros que tomaram interesse em participar na
     * viagem (limitado à lotação do Autocarro)
     */
    @Override
    public synchronized int announcingBusBoardingShouting() {
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
        return pass;
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
    public synchronized void setnVoo(int nvoo, int nPassageiros) {
        this.nVoo = nvoo;
        this.passTRT = nPassageiros;
    }

    /**
     * Terminar o monitor.
     * <p>
     * Invocadores: BagageiroMain, MotoristaMain e PassageiroMain
     * <p>
     * No final da execução da simulação, para o fechar o monitor os 3
     * lançadores das threads correspondentes ao passageiro, bagageiro e
     * motorista necessitam de fechar os monitores.
     *
     * @return Informação se pode ou não terminar o monitor.
     * <ul>
     * <li>TRUE, caso possa
     * <li>FALSE, caso contrário
     * </ul>
     */
    public synchronized boolean shutdownMonitor() {
        return (++three_entities_ended >= 3);
    }
}
