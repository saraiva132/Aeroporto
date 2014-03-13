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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rafael
 */
public class TransferenciaTerminal implements TransferenciaMotoristaInterface, TransferenciaPassageiroInterface {

    private Queue<Integer> fila;
    private int nPassageiros;

    public TransferenciaTerminal() {
        fila = new LinkedList<>();
    }

    /**
     * Passageiro anuncia a intenção de entrar no autocarro
     *
     * @param passageiroID
     */
    @Override
    public synchronized int takeABus(int passageiroID) {
        int ticket;
        fila.add(fila.size());
        ticket = fila.size();
        if (fila.size() == lotação) {
            notify();
        }
        return fila.remove();
    }

    /**
     * Motorista anuncia que ja acabou o trabalho
     *
     * @return
     */
    @Override
    public synchronized boolean hasDaysWorkEnded() {
        try {
            while (fila.size() < lotação) {
                wait();
            }
        } catch (InterruptedException ex) {

        }

        if (fila.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Motorista anuncia que a viagem vai começar
     */
    @Override
    public synchronized void announcingBusBoardingShouting() {
        notifyAll();
    }
}
