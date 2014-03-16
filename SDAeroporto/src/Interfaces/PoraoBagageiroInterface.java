package Interfaces;

import Estruturas.Mala;

/**
 * Identifica e descreve as operações que o bagageiro pode realizar sobre o 
 * monitor <b>Porão</b>
 * 
 * @author rafael
 */
public interface PoraoBagageiroInterface {
    /**
     * Invocador: bagageiro
     * 
     * Tentar recolher uma mala
     * 
     * O bagageiro desloca-se ao porão do avião e caso este não se encontre vazio
     * recolhe uma mala
     * 
     * @return a mala que apanhou no porão, ou em caso do porão se encontrar 
     * vazio, null
     */
    public Mala tryToCollectABag();
}
