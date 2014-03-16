package sdaeroporto;

import static Estruturas.AuxInfo.*;
import Estruturas.Mala;
import Interfaces.RecolhaBagageiroInterface;
import Interfaces.RecolhaPassageiroInterface;
import java.util.HashMap;

/**
 * Monitor que simula a interacção entre os passageiros e o bagageiro na zona
 * de recolha de bagagens e ainda o guichet de reclamação e a zona de 
 * armazenamento de bagagens
 *
 * @author rafael
 */
public class RecolhaBagagem implements RecolhaBagageiroInterface, RecolhaPassageiroInterface {

    /**
     * 
     */
    HashMap<Integer, Integer> belt;
    int nMalasStore;
    boolean noMoreBags;

    public RecolhaBagagem() {
        nMalasStore = 0;
        belt = new HashMap<>(chegadas * passMax);
        for(int i = 0; i< passMax ; i++)
            belt.put(i, 0);
        noMoreBags = false;
    }

    /**
     * Invocador: Passageiro
     * 
     * Vai buscar uma mala
     * 
     * O passageiro desloca-se à zona de recolha de bagagens para ir buscar a
     * sua mala. Espera até que aviste a sua mala na passadeira rolante ou que o
     * bagageiro anuncie que já não existem mais malas no porão do avião.
     * 
     * Simula, ainda, se o passageiro consegue ou não apanhar a sua mala de forma
     * bem sucedida.
     * 
     * @param bagID identificador da mala
     * @return a forma como conseguiu apanhar a sua mala: <b>com sucesso</b> ou 
     * <b>sem sucesso</b>. Alternativamente, a informação de que já não vale a 
     * pena continuar a espera da(s) sua(s) mala(s) que lhe falta(m) 
     */
    @Override
    public synchronized bagCollect goCollectABag(int bagID) {
       
        System.out.println("Entrei aqui a procura da mala "+bagID);
        
            while ( (belt.get(bagID) == 0) && !noMoreBags) { //Dupla condição. Se existir uma mala ou se as malas acabarem
               try {
                wait();                            //os passageiros são acordados
                } catch (InterruptedException ex) {
        }
            }
        
       System.out.println("Get a bag " + bagID);
       if(belt.get(bagID) > 0){
           belt.put(bagID, belt.get(bagID)-1);
           if(getBagChance())         
               return bagCollect.MINE;
           else 
               return bagCollect.UNSUCCESSFUL;
       }
       return bagCollect.NOMORE;
        
            
        
    }

    /**
     * Invocador: Bagageiro
     * 
     * O bagageiro transporta uma mala para um determinado local:
     * <li> para a zona de armazenamento caso a mala pertença a um passageiro 
     * que esteja em trânsito
     * <li> para a passadeira rolante caso pertença a um passageiro cujo destino
     * é este aeroporto, notificando-o de seguida
     * 
     * Caso o obejecto mala seja null notifica todos os passageiros de que já 
     * não existem mais malas no porão do avião 
     * 
     * @param mala mala que o bagageiro transporta
     * @return local para onde levou a mala, ou caso o objecto mala seja um 
     * null, a informação de que o porão já se encontra vazio
     */
    @Override
    public synchronized bagDest carryItToAppropriateStore(Mala mala) {
        if (mala == null) {
            System.out.println("MALAS ACABRAM RAPAZIADA!!!!!");
            noMoreBags = true;
            notifyAll(); // NO MORE BAGS GUYS!!
            return bagDest.LOBBYCLEAN; //Nao tem mala retorna null!
        }
        System.out.println("CarryBag "+ mala.getOwner());
        if (mala.inTransit()) {
            nMalasStore++;
            return bagDest.STOREROOM;
        } else {
            System.out.println(mala.getOwner());
            belt.put(mala.getOwner(), belt.get(mala.getOwner())+1);
            notifyAll();
            return bagDest.BELT;
        }
    }

    /**
     * Invocador: Passageiro
     * 
     * Reportar a falta de mala(s)
     * 
     * O passageiro, após não ter coleccionado todas as suas malas e após o 
     * bagageiro o ter notificado de que já não existem mais malas no porão 
     * desloca-se ao guichet de reclamação do aeroporto para reclamar a falta da(s)
     * sua(s) mala(s)
     * 
     * @param passageiroID identificador do passageiro
     * @param malasPerdidas número de malas perdidas
     */
    @Override
    public synchronized void reportMissingBags(int passageiroID, int malasPerdidas) {
        System.out.println("Report missing bags. Passageiro: "+passageiroID+" Perdidas: " + malasPerdidas);
        //Imprimir malas perdidas    
    }
    
     private boolean getBagChance() {
        return Math.random() > 0.2;
    }

}
