package Interfaces;

import Estruturas.Mala;

/**
 * Identifica e descreve as operações que o bagageiro pode realizar sobre o
 * monitor <b>Porão</b>
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface PoraoBagageiroInterface {

    /**
     * Tentar recolher uma mala
     * <p>
     * Invocador: bagageiro
     * <p>
     * O bagageiro desloca-se ao porão do avião e caso este não se encontre
     * vazio recolhe uma mala
     *
     * @param log referência para o monitor de logging; utilizado para reportar a evolução do estado global do problema
     * @return Mala que o bagageiro apanhou do porão e informação sobre o estado do mesmo
     */
    public Mala tryToCollectABag(LoggingBagageiroInterface log);
}
