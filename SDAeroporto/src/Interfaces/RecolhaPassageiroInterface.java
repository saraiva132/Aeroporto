package Interfaces;

import Estruturas.AuxInfo.bagCollect;

/**
 * Identifica e descreve as operações que um passageiro pode realizar sobre o 
 * monitor <b>RecolhaBagagem</b>
 * 
 * @author rafael
 */
public interface RecolhaPassageiroInterface {
    
    /**
     * Invocador: Passageiro
     * 
     * Buscar uma mala
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
    public bagCollect goCollectABag(int bagID);
    
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
    public void reportMissingBags(int passageiroID,int malasPerdidas);

}