/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas;

import Interfaces.ZonaDesembarqueBagageiroInterface;
import Estruturas.AuxInfo.bagState;
import Interfaces.PoraoBagageiroInterface;
import Interfaces.RecolhaBagageiroInterface;

/**
 *
 * @author rafael
 */
public class Bagageiro extends Thread {

    private bagState state;
    ZonaDesembarqueBagageiroInterface zona;
    PoraoBagageiroInterface porao;
    RecolhaBagageiroInterface recolha;

    public Bagageiro(ZonaDesembarqueBagageiroInterface zona, PoraoBagageiroInterface porao,
            RecolhaBagageiroInterface recolha) {
        state = bagState.WAITING_FOR_A_PLANE_TO_LAND;
        this.zona = zona;
        this.porao = porao;
        this.recolha = recolha;
    }

    @Override
    public void run() {
        int bagID;
        int nextState;
        zona.takeARest();
        while ((bagID = porao.tryToCollectABag()) != 0) {
            state = bagState.AT_THE_PLANES_HOLD;
            nextState = recolha.carryItToAppropriateStore(bagID);
            switch (nextState) {
                case 0:
                    state = bagState.AT_THE_LUGGAGE_BELT_CONVERYOR;
                    break;
                case 1:
                    state = bagState.AT_THE_STOREROOM;
                    break;
            }
        }
    }
    
}
