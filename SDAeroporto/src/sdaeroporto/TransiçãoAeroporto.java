/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

/**
 *
 * @author rafael
 */
public class TransiçãoAeroporto {
    
    private int nPassageiros;
    private int nPassageirosMax;
    
    public TransiçãoAeroporto(int nPassageirosMax)
    {
        this.nPassageirosMax = nPassageirosMax;
    } 
    
    /**
     * Passageiro com destino final abandona o aeroporto e vai para casa.
     * @param passageiroID 
     */
    public synchronized void goHome(int passageiroID)
    {
        
    }
    /**
     * Passageiro que nao se encontra no destino final. Prepara o proximo voo.
     * @param passageiroID 
     */ 
    public synchronized void prepareNextLeg(int passageiroID)
    {
        
    }
}
