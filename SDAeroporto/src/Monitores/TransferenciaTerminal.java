package Monitores;

import static Estruturas.AuxInfo.*;
import Interfaces.LoggingPassageiroInterface;
import Interfaces.TransferenciaMotoristaInterface;
import Interfaces.TransferenciaPassageiroInterface;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Monitor que simula a interacção entre os passageiros e o motorista na zona de
 * transferência entre os terminais de chegada e partida
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class TransferenciaTerminal implements TransferenciaMotoristaInterface, TransferenciaPassageiroInterface {

    /**
     * Fila de espera que se forma à entrada do autocarro. cada posição é
     * ocupada pela identificação de um passageiro.
     *
     * @serialField fila
     */
    private Queue<Integer> fila;
    private int nVoo;                   /*indica o número de voo para o motorista saber se já acabou*/

    private boolean timeUp, /*indica se já é altura de realizar a viagem*/
            canGo, /*indica ao passageiro se ele pode entrar no autocarro*/
            next;                       /*indica se o motorista pode deixar o próximo passageiro na fila entrar no autocarro*/


    public TransferenciaTerminal() {
        nVoo = 1;
        fila = new LinkedList<Integer>();
        timeUp = false;
        canGo = false;
        next = false;
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
     * @param log
     * @param passageiroID identificador do passageiro
     * @param voo identificador do número de Voo
     * @return Posição do seu assento no autocarro
     */
    @Override
    public synchronized int takeABus(LoggingPassageiroInterface log, int passageiroID) {
        //System.out.println("Take the bus");
        int ticket;
        fila.add(passageiroID + 1);
        log.addfilaEspera(passageiroID);
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
        log.removefilaEspera();
        notifyAll();
        return ticket;
    }

    /**
     * Trabalho já acabou?
     * <p>
     * Invocador - Motorista.
     * <p>
     * Motorista verifica se o trabalho já acabou. É acordado nas seguintes
     * condições: Se os passageiros na fila de espera, no passeio, cobrem a
     * lotação do autocarro Se a hora de partida chegou.
     *
     * O trabalho dele acabou se à hora da partida não se encontrar ninguém no
     * passeio!
     *
     * @return True se acabou. False se não acabou ainda.
     */
    @Override
    public synchronized boolean hasDaysWorkEnded() {
        //System.out.println("has work ended?");
        Reminder reminder = new Reminder(1);
        try {
            while (fila.size() < lotação && !timeUp) {
                wait();
                reminder.timer.cancel();
            }
        } catch (InterruptedException ex) {
        }
        timeUp = false;
        return (fila.isEmpty() && nVoo >= nChegadas);
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
     * viagem. Limitado à lotação do Autocarro.
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

    public void setnVoo(int nvoo) {
        this.nVoo = nvoo;
    }
}
