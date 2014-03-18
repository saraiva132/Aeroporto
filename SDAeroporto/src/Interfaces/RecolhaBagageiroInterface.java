package Interfaces;

import Estruturas.AuxInfo.bagDest;
import Estruturas.Mala;
import Monitores.Logging;

/**
 * Identifica e descreve as operações que o bagageiro pode realizar sobre o 
 * monitor <b>RecolhaBagagem</b>
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399s
 */
public interface RecolhaBagageiroInterface {
    
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
     * @param log
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
    public bagDest carryItToAppropriateStore(LoggingBagageiroInterface log,Mala mala);
     
}
