package Interfaces;

import Estruturas.AuxInfo.bagDest;
import Estruturas.Mala;

/**
 * Identifica e descreve as operações que o bagageiro pode realizar sobre o 
 * monitor <b>RecolhaBagagem</b>
 * 
 * @author rafael
 */
public interface RecolhaBagageiroInterface {
    
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
    public bagDest carryItToAppropriateStore(Mala mala);
     
}
