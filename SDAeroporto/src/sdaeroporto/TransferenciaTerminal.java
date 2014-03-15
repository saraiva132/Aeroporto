/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdaeroporto;

import static Estruturas.AuxInfo.*;
import Interfaces.TransferenciaMotoristaInterface;
import Interfaces.TransferenciaPassageiroInterface;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author rafael
 */
public class TransferenciaTerminal implements TransferenciaMotoristaInterface, TransferenciaPassageiroInterface {

    private Queue<Integer> fila;
    private int nPassageiros;
    boolean timeUp;

    public TransferenciaTerminal() {
        nPassageiros = 0;
        fila = new LinkedList<>();
        timeUp = false;
        run();
    }

    private void run() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.format("Time's up!%n");
                runn();
                timer.cancel(); //Terminate the timer thread
            }
        }, 5000);
    }

    private synchronized void runn() {
        System.out.println("ACORDA CARALHO");
        timeUp = true;
        notifyAll();
    }

    /**
     * Passageiro anuncia a intenção de entrar no autocarro
     *
     * @param passageiroID
     */
    @Override
    public synchronized int takeABus(int passageiroID) {
        System.out.println("Take the bus");
        int ticket;
        fila.add(passageiroID);
        nPassageiros++;
        ticket = fila.size();
        System.out.println(nPassageiros);
        if (fila.size() == lotação) {
            notify();
        }
        try {
            while (nPassageiros != 0) {
                wait();
            }
        } catch (InterruptedException ex) {
        }
         System.out.println(nPassageiros);
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

        try {
            while (fila.size() < lotação && timeUp == false) {
                wait();
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
    public synchronized void announcingBusBoardingShouting() {
        System.out.println("ALL ABOAAARD!");
        for (int i = 0; i < nPassageiros; i++) {
            fila.remove();
        }
        notifyAll();
        nPassageiros = 0;
    }
}
