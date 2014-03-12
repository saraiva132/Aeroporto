/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas;

import Estruturas.AuxInfo.motState;
import Interfaces.AutocarroMotoristaInterface;
import Interfaces.TransferenciaMotoristaInterface;

/**
 *
 * @author rafael
 */
public class Motorista extends Thread {

    private motState state;

    private AutocarroMotoristaInterface auto;
    private TransferenciaMotoristaInterface transferencia;

    public Motorista(AutocarroMotoristaInterface auto,TransferenciaMotoristaInterface transferencia) {
        this.auto = auto;
        this.transferencia = transferencia;
        state = motState.PARKING_AT_THE_ARRIVAL_TERMINAL;
    }

    @Override
    public void run() {
        
    transferencia.announcingBusBoarding();
    
    auto.goToDepartureTerminal();
    state = motState.DRIVING_FORWARD;
    
    auto.parkTheBusAndLetPassOff();
    state = motState.PARKING_AT_THE_DEPARTURE_TERMINAL;
    
    auto.goToArrivalTerminal();
    state = motState.DRIVING_BACKWARD;
    
    auto.parkTheBus();
    state = motState.PARKING_AT_THE_ARRIVAL_TERMINAL;
    
    transferencia.hasDaysWorkEnded();
    
    }
}
