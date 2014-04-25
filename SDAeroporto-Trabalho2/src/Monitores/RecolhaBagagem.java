package Monitores;

import ClientSide.InterfaceMonitoresLogging;
import static Estruturas.AuxInfo.*;
import Estruturas.Mala;
import Interfaces.RecolhaBagageiroInterface;
import Interfaces.RecolhaPassageiroInterface;
import java.util.HashMap;

/**
 * Monitor que simula a interacção entre os passageiros e o bagageiro na zona de
 * recolha de bagagens e ainda o guichet de reclamação e a zona de armazenamento
 * de bagagens
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class RecolhaBagagem implements RecolhaBagageiroInterface, RecolhaPassageiroInterface {

    /**
     * Passadeira de bagagens na zona de recolha de bagagens. Cada key do
     * hashmap está associada a um passageiro e o valor que está associado a
     * essa key corresponde ao número de malas que estão na passadeira que
     * pertencem ao passageiro
     *
     * @serialField belt
     */
    private HashMap<Integer, Integer> belt;

    /**
     * Número de malas que estão na zona de armazenamento temporário de bagagens
     *
     * @serialField nMalasStore
     */
    private int nMalasStore;

    /**
     * Identifica se existem mais malas no porão ou não.
     * <ul>
     * <li>TRUE caso não existam
     * <li>FALSE caso contrário
     * </ul>
     *
     * @serialField noMoreBags
     */
    private boolean noMoreBags;

    private InterfaceMonitoresLogging log;
    /**
     * Instanciação e inicialização do monitor <b>RecolhaBagagem</b>
     */
    public RecolhaBagagem() {
        nMalasStore = 0;
        belt = new HashMap<>(nChegadas * passMax);
        for (int i = 0; i < passMax; i++) {
            belt.put(i, 0);
        }
        noMoreBags = false;
        log = new InterfaceMonitoresLogging("RecolhaBagagem");
    }

    /**
     * Buscar uma mala
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro desloca-se à zona de recolha de bagagens para ir buscar a
     * sua mala. Espera até que aviste a sua mala na passadeira rolante ou que o
     * bagageiro anuncie que já não existem mais malas no porão do avião.
     * <p>
     * Simula, ainda, se o passageiro consegue ou não apanhar a sua mala de forma
     * bem sucedida.
     * 
     * @param bagID identificador da mala
     * @return Forma como conseguiu apanhar a sua mala: 
     * <ul>
     * <li>MINE, com sucesso 
     * <li>UNSUCCESSFUL, sem sucesso 
     * </ul> 
     * <p>Alternativamente, a informação de que já não vale a 
     * pena continuar a espera da(s) sua(s) mala(s) que lhe falta(m):
     * <ul>
     * <li>NOMORE
     * </ul>
     */
    @Override
    public synchronized bagCollect goCollectABag(int bagID) {

        while ((belt.get(bagID) == 0) && !noMoreBags) { //Dupla condição. Se existir uma mala ou se as malas acabarem
            try {
                wait();                            //os passageiros são acordados
            } catch (InterruptedException ex) {
            }
        }
        if (belt.get(bagID) > 0) {
            belt.put(bagID, belt.get(bagID) - 1);
            if (getBagChance()) {
                log.bagagemBelt(true);
                log.malasActual(bagID);
                return bagCollect.MINE;
            } else {
                return bagCollect.UNSUCCESSFUL;
            }
        }
        return bagCollect.NOMORE;

    }

    /**
     * Transportar uma mala
     * <p>
     * Invocador: Bagageiro
     * <p>
     * O bagageiro transporta uma mala para um determinado local:
     * <ul>
     * <li> para a zona de armazenamento temporário caso a mala pertença a um passageiro 
     * que esteja em trânsito
     * <li> para a passadeira rolante caso pertença a um passageiro cujo destino
     * é este aeroporto, notificando-o de seguida
     * </ul>
     * <p>
     * Caso o objecto mala seja null notifica todos os passageiros de que já 
     * não existem mais malas no porão do avião 
     * 
     * @param mala mala que o bagageiro transporta
     * @return Local para onde levou a mala:
     * <ul>
     * <li>STOREROOM, zona de armazenamento temporário de bagagens
     * <li>BELT, zona de recolha de bagagens
     * </ul>
     * Alternativamente, caso o objecto mala seja um null, a informação de que o porão já se encontra vazio:
     * <ul>
     * <li>LOBBYCLEAN
     * </ul>
     */
    @Override
    public synchronized bagDest carryItToAppropriateStore( Mala mala) {
        
        if (mala == null) {
            //System.out.println("MALAS ACABRAM RAPAZIADA!!!!!");
            noMoreBags = true;
            notifyAll(); // NO MORE BAGS GUYS!!
            return bagDest.LOBBYCLEAN; //Nao tem mala retorna null!
        }
        //System.out.println("CarryBag "+ mala.getOwner());
        if (mala.inTransit()) {
            nMalasStore++;
            log.reportState(bagState.AT_THE_STOREROOM);
            log.bagagemStore();
            return bagDest.STOREROOM;
        } else {
            //System.out.println(mala.getOwner());
            belt.put(mala.getOwner(), belt.get(mala.getOwner()) + 1);
            log.reportState(bagState.AT_THE_LUGGAGE_BELT_CONVERYOR);
            log.bagagemBelt(false);
            notifyAll();
            return bagDest.BELT;
        }
    }

    /**
     * Função auxiliar utilizada a cada iteração da simulação
     */
    public synchronized void resetNoMoreBags() {
        this.noMoreBags = false;
    }

   /**
     * Reportar a falta de mala(s)
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro, após não ter coleccionado todas as suas malas e após o
     * bagageiro o ter notificado de que já não existem mais malas no porão
     * desloca-se ao guichet de reclamação do aeroporto para reclamar a falta
     * da(s) sua(s) mala(s)
     *
     * @param passageiroID identificador do passageiro
     * @param malasPerdidas número de malas perdidas
     */

    @Override
    public synchronized void reportMissingBags(int passageiroID, int malasPerdidas) {
        log.missingBags(malasPerdidas);    
        log.reportState(passageiroID, passState.AT_THE_BAGGAGE_RECLAIM_OFFICE);
    }

    /**
     * Função auxiliar que simula a probabilidade de uma mala ser ou não perdida
     *
     * @return 
     * <ul>
     * <li>TRUE caso a mala não tenha sido perdida
     * <li>FALSE caso contrário
     * </ul>
     */
    private boolean getBagChance() {
        return Math.random() > 0.2;
    }
    
}