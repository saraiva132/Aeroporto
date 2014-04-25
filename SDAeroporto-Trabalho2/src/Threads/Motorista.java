/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import ClientSide.InterfaceMotorista;
import Estruturas.AuxInfo.motState;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Identifica o tipo de dados motorista
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Motorista extends Thread {

    /**
     * Identifica o estado do motorista
     * 
     * @serialField state
     */
    private motState state;

    
    private InterfaceMotorista motoristaI = null;
    
    /**
     * Instanciação e inicialização do motorista
     */
    public Motorista() {
        motoristaI = new InterfaceMotorista();
    }

    /**
     * Implementa o ciclo de vida do motorista
     */
    @Override
    public void run() {
        Random r = new Random();
        while (!motoristaI.hasDaysWorkEnded()) {
            try{
                Thread.sleep(r.nextInt(100));
                int nTickets = motoristaI.announcingBusBoardingShouting();
                Thread.sleep(r.nextInt(100));
                motoristaI.announcingBusBoardingWaiting(nTickets);
                Thread.sleep(r.nextInt(100));
                motoristaI.goToDepartureTerminal();
                Thread.sleep(r.nextInt(100));
                motoristaI.parkTheBusAndLetPassOff();
                Thread.sleep(r.nextInt(100));
                motoristaI.goToArrivalTerminal();  
                Thread.sleep(r.nextInt(100));
                motoristaI.parkTheBus();
                Thread.sleep(r.nextInt(100));
            } catch (InterruptedException ex) {}
        }
    }
}
