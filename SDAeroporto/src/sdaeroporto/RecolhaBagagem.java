/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdaeroporto;

import Estruturas.AuxInfo.bagCollect;
import Estruturas.AuxInfo.bagDest;
import Estruturas.Mala;
import Interfaces.RecolhaBagageiroInterface;
import Interfaces.RecolhaPassageiroInterface;
import java.util.HashMap;

/**
 *
 * @author rafael
 */
public class RecolhaBagagem implements RecolhaBagageiroInterface, RecolhaPassageiroInterface {

    HashMap<Integer, Mala> belt;
    int nMalasStore;
    boolean noMoreBags;

    public RecolhaBagagem() {
        nMalasStore = 0;
        belt = new HashMap<>();
        noMoreBags = false;
    }

    /**
     * estado de bloqueio enquanto nao ha novas malas a chegar a esta zona (o
     * bagageiro vai fazer notifyall cada vez que leva 1 mala para esta zona e
     * os passageiros quando 'acordam' vêm se têm alguma mala a recolher e
     * adormecem outra vez) Invocador: Passageiro
     *
     * @param bagID
     * @return
     */
    @Override
    public synchronized bagCollect goCollectABag(int bagID) {
        System.out.println("Try to collect a bag");
        
        try {
            while (!belt.containsKey(bagID) && !noMoreBags) { //Dupla condição. Se existir uma mala ou se as malas acabarem
                wait();                             //os passageiros são acordados
            }
        } catch (InterruptedException ex) {
        }
        if (belt.containsKey(bagID)) {        
            belt.remove(bagID);
            return bagCollect.MINE;
        }
        
        return bagCollect.NOMORE;
        
    }

    /**
     * Invocador: Bagageiro
     *
     * @param bag
     * @return
     */
    @Override
    public synchronized bagDest carryItToAppropriateStore(Mala bag) {
        System.out.println("CarryBag");
        if (bag == null) {
            noMoreBags = true;
            notifyAll(); // NO MORE BAGS GUYS!!
            return null; //Nao tem mala retorna null!
        }

        if (bag.inTransit()) {
            nMalasStore++;
            return bagDest.STOREROOM;
        } else {
            System.out.println(bag.getOwner());
            belt.put(bag.getOwner(), bag);
            notifyAll();
            return bagDest.BELT;
        }
    }

    /**
     * Estado de transição. Nao bloqueante. Os passageiros que não obtiveram
     * todas as suas malas precisam de reportar o acontecimento. Logging!
     *
     * @param passageiroID
     * @param malasPerdidas
     */
    @Override
    public synchronized void reportMissingBags(int passageiroID, int malasPerdidas) {
        System.out.println("Report missing bags. Passageiro: "+passageiroID+" Perdidas: " + malasPerdidas);
        //Imprimir malas perdidas    
    }

}
