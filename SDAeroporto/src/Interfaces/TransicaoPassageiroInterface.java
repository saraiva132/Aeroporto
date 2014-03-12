/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

/**
 *
 * @author rafael
 */
public interface TransicaoPassageiroInterface {

    /**
     * Passageiro com destino final abandona o aeroporto e vai para casa.
     *
     * @param passageiroID
     */
    
    public void goHome();

    /**
     * Passageiro que nao se encontra no destino final. Prepara o proximo voo.
     *
     * @param passageiroID
     */
    public void prepareNextLeg();

}
