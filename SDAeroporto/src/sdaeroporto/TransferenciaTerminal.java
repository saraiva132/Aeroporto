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

    public TransferenciaTerminal() {
        fila = new LinkedList<Integer>();
    }

    /**
     * Passageiro anuncia a intenção de entrar no autocarro
     *
     * @param passageiroID
     */
    @Override
    public synchronized void takeABus(int passageiroID) {
        fila.add(passageiroID);
        if(fila.size() == lotação)
            notify();
    }

    /**
     * Motorista anuncia que ja acabou o trabalho
     *
     * @return
     */
    @Override
    public synchronized boolean hasDaysWorkEnded() {
        return true;
    }

    /**
     * Motorista anuncia que a viagem vai começar
     */
    @Override
    public synchronized void announcingBusBoarding() {
        try {
            while (fila.size() < lotação) {
                wait();
            }
        } catch (InterruptedException ex) {
        }
    } 
}
