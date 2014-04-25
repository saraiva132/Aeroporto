/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import ClientSide.InterfaceMotorista;
import Estruturas.AuxInfo.motState;

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

        while (!motoristaI.hasDaysWorkEnded()) {            
            motoristaI.announcingBusBoardingWaiting(motoristaI.announcingBusBoardingShouting());
            motoristaI.goToDepartureTerminal();            
            motoristaI.parkTheBusAndLetPassOff();            
            motoristaI.goToArrivalTerminal();            
            motoristaI.parkTheBus();            
        }
    }
}
