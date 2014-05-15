/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Estruturas.Globals.motState;
import Interfaces.AutocarroMotoristaInterface;
import Interfaces.TransferenciaMotoristaInterface;
import java.rmi.RemoteException;

/**
 * Identifica o tipo de dados motorista
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Motorista extends Thread {

    /**
     * Auxilia a simulação do ciclo de vida e as operações que o motorista pode 
     * realizar sobre o autocarro
     * 
     * @serialField auto
     */
    private AutocarroMotoristaInterface auto;
    
    /**
     * Auxilia a simulação do ciclo de vida e as operações que o motorista pode 
     * realizar sobre a zona de transferência entre os terminais de chegade e 
     * partida
     * 
     * @serialField transferencia
     */
    private TransferenciaMotoristaInterface transferencia;
    
    /**
     * Instanciação e inicialização do motorista
     */
    public Motorista(AutocarroMotoristaInterface auto,TransferenciaMotoristaInterface transferencia) {
        this.auto = auto;
        this.transferencia = transferencia;
    }

    /**
     * Implementa o ciclo de vida do motorista
     */
    @Override
    public void run() {
        try
        {
        while (!transferencia.hasDaysWorkEnded()) {
            
                int nTickets = transferencia.announcingBusBoardingShouting();
                auto.announcingBusBoardingWaiting(nTickets);
                auto.goToDepartureTerminal();
                auto.parkTheBusAndLetPassOff();
                auto.goToArrivalTerminal();  
                auto.parkTheBus();  
        }
        }catch(RemoteException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}
