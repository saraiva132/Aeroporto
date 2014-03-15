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
public interface TransferenciaMotoristaInterface {

     /**
     * Motorista anuncia que ja acabou o trabalho
     * @return 
     */
    public boolean hasDaysWorkEnded();
    
    /**
     * Motorista anuncia que a viagem vai começar
     * @return 
     */
    public int announcingBusBoardingShouting();
    
}
