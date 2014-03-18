/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Estruturas.AuxInfo.motState;
import Interfaces.AutocarroMotoristaInterface;
import Interfaces.LoggingMotoristaInterface;
import Interfaces.TransferenciaMotoristaInterface;
import Monitores.Logging;

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
     * Auxilia no logging da evolução dos estados do problema ao longo da simulação
     * 
     * @serialField log
     */
    private LoggingMotoristaInterface log;
    
    /**
     * Instanciação e inicialização do motorista
     * 
     * @param auto monitor correspondente ao autorcarro
     * @param transferencia monitor correspondente à zona de transferência entre 
     * terminais
     * @param log monitor correspondente ao logging do problema
     */
    public Motorista(AutocarroMotoristaInterface auto, TransferenciaMotoristaInterface transferencia,LoggingMotoristaInterface log) {
        this.auto = auto;
        this.transferencia = transferencia;
        log.reportState(state = motState.PARKING_AT_THE_ARRIVAL_TERMINAL);
        this.log = log;
    }

    /**
     * Implementa o ciclo de vida do motorista
     */
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
