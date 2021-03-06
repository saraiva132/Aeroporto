package Interfaces;

import Estruturas.AuxInfo.bagCollect;

/**
 * Identifica e descreve as operações que um passageiro pode realizar sobre o 
 * monitor <b>RecolhaBagagem</b>
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface RecolhaPassageiroInterface {
    
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
     * @param log referência para o monitor de logging; utilizado para reportar a evolução do estado global do problema
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
    public bagCollect goCollectABag(int bagID,LoggingPassageiroInterface log);
    
    /**
     * Reportar a falta de mala(s)
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro, após não ter coleccionado todas as suas malas e após o 
     * bagageiro o ter notificado de que já não existem mais malas no porão 
     * desloca-se ao guichet de reclamação do aeroporto para reclamar a falta da(s)
     * sua(s) mala(s)
     * 
     * @param passageiroID identificador do passageiro
     * @param malasPerdidas número de malas perdidas
     * @param log referência para o monitor de logging; utilizado para reportar a evolução do estado global do problema
     */
    public void reportMissingBags(int passageiroID,int malasPerdidas, LoggingPassageiroInterface log);

}
