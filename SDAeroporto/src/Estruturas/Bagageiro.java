/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas;

import Estruturas.AuxInfo.bagDest;
import Interfaces.ZonaDesembarqueBagageiroInterface;
import Estruturas.AuxInfo.bagState;
import static Estruturas.AuxInfo.chegadas;
import Interfaces.PoraoBagageiroInterface;
import Interfaces.RecolhaBagageiroInterface;
import sdaeroporto.Logging;

/**
 * Identifica o tipo de dados bagageiro
 *
 * @author rafael
 */
public class Bagageiro extends Thread {

    /**
     * Identifica o estado do bagageiro
     *
     * @serialField state
     */
    private bagState state;
    
    /**
     * 
     */

    private ZonaDesembarqueBagageiroInterface zona;
    private PoraoBagageiroInterface porao;
    private RecolhaBagageiroInterface recolha;
    private Logging log;

    public Bagageiro(ZonaDesembarqueBagageiroInterface zona, PoraoBagageiroInterface porao,
            RecolhaBagageiroInterface recolha,Logging log) {
        state = bagState.WAITING_FOR_A_PLANE_TO_LAND;
        this.zona = zona;
        this.porao = porao;
        this.recolha = recolha;
        this.log = log;
    }

    @Override
    public void run() {
        Mala mala;
        for (int i = 0; i < chegadas; i++) {
            System.out.println("Here we go again...");
            zona.takeARest();
            log.reportState(state = bagState.WAITING_FOR_A_PLANE_TO_LAND);      
            mala = porao.tryToCollectABag();
            bagDest nextState;
            do {
                log.reportState(state = bagState.AT_THE_PLANES_HOLD);
                nextState = recolha.carryItToAppropriateStore(mala);
                switch (nextState) {
                    case BELT:
                        log.reportState(state = bagState.AT_THE_LUGGAGE_BELT_CONVERYOR);
                        break;
                    case STOREROOM:
                        log.reportState(state = bagState.AT_THE_STOREROOM);
                        break;
                }
                mala = porao.tryToCollectABag();
            } while (nextState != bagDest.LOBBYCLEAN);
            log.reportState(state = bagState.WAITING_FOR_A_PLANE_TO_LAND); 
        }
    }
    
    
    
    

}
