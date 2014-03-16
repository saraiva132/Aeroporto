package sdaeroporto;

import static Estruturas.AuxInfo.*;
import Interfaces.TransferenciaMotoristaInterface;
import Interfaces.TransferenciaPassageiroInterface;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Monitor que simula a interacção entre os passageiros e o motorista na zona de 
 * transferência entre os terminais de chegada e partida
 *
 * @author rafael
 */
public class TransferenciaTerminal implements TransferenciaMotoristaInterface, TransferenciaPassageiroInterface {

    private LinkedList<Integer> fila;
    boolean timeUp, canGo, next;

    public TransferenciaTerminal() {
        fila = new LinkedList<>();
        timeUp = false;
        canGo = false;
        next = false;
    }


    private synchronized void tempoEsgotado() {
        System.out.println("ACORDA CARALHO");
        timeUp = true;
        notifyAll();
    }

    /**
     * Invocador: Passageiro
     * 
     * Apanhar o autocarro
     * 
     * O passageiro anuncia que pretende apanhar o autocarro. Coloca-se na fila,
     * sendo-lhe atribuído um ticket com a posição em que se deverá sentar no 
     * autocarro. Por fim, espera até que seja a sua vez de entrar no autocarro
     * 
     * @param passageiroID identificador do passageiro
     * @return posição do seu assento no autocarro
     */
    @Override
    public synchronized int takeABus(int passageiroID) {
        System.out.println("Take the bus");
        int ticket;

        fila.add(passageiroID);
        ticket = fila.size() % lotação;

        if (fila.size() == lotação) {
            notifyAll();
        }
        try {
            while (!canGo || fila.peek() != passageiroID) {
                wait();
            }
        } catch (InterruptedException ex) {
        }
        canGo = false;
        next = true;
        notifyAll();
        fila.remove();
        return ticket;
    }

    /**
     * Motorista anuncia que ja acabou o trabalho
     *
     * @return
     */
    @Override
    public synchronized boolean hasDaysWorkEnded() {
        System.out.println("has work ended?");
        Reminder reminder = new Reminder(3);
        try {
            while (fila.size() < lotação && !timeUp ) {
                wait();
                 if(fila.size() >= lotação)
                    reminder.timer.cancel();
            }
        } catch (InterruptedException ex) {

        }
        timeUp = false;
        return fila.isEmpty();
    }

    /**
     * Motorista anuncia que a viagem vai começar
     */
    @Override
    public synchronized int announcingBusBoardingShouting() {
        System.out.println("ALL ABOAAARD!: passageiros à espera: " + fila.size());
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
            System.out.println("Passageiro entra");
            next = false;
            pass++;
        }
        canGo = false;
        return pass;
    }

    public class Reminder {

        Timer timer;

        public Reminder(int seconds) {
            timer = new Timer();
            timer.schedule(new RemindTask(), seconds * 1000,seconds * 1000);
        }

        class RemindTask extends TimerTask {

            @Override
            public void run() {
                System.out.println("Time's up!");
                tempoEsgotado();
                timer.cancel(); //Terminate the timer thread

            }
        }
    }
}
