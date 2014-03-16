/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas;

import Estruturas.AuxInfo.motState;
import Interfaces.AutocarroMotoristaInterface;
import Interfaces.TransferenciaMotoristaInterface;
import sdaeroporto.Logging;

/**
 *
 * @author rafael
 */
public class Motorista extends Thread {

    private motState state;

    private AutocarroMotoristaInterface auto;
    private TransferenciaMotoristaInterface transferencia;
    private Logging log;
    
    public Motorista(AutocarroMotoristaInterface auto, TransferenciaMotoristaInterface transferencia,Logging log) {
        this.auto = auto;
        this.transferencia = transferencia;
        log.reportState(state = motState.PARKING_AT_THE_ARRIVAL_TERMINAL);
        this.log = log;
    }

    @Override
    public void run() {

        while (!transferencia.hasDaysWorkEnded()) {
            
            auto.announcingBusBoardingWaiting(transferencia.announcingBusBoardingShouting());
            
            auto.goToDepartureTerminal();
            log.reportState(state = motState.DRIVING_FORWARD);

            auto.parkTheBusAndLetPassOff();
            log.reportState(state = motState.PARKING_AT_THE_DEPARTURE_TERMINAL);

            auto.goToArrivalTerminal();
            log.reportState(state = motState.DRIVING_BACKWARD);

            auto.parkTheBus();
            log.reportState(state = motState.PARKING_AT_THE_ARRIVAL_TERMINAL);
        }
    }
}
