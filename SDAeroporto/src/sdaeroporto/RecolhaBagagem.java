/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import Interfaces.RecolhaBagageiroInterface;
import Interfaces.RecolhaPassageiroInterface;
import java.util.HashMap;



/**
 *
 * @author rafael
 */
public class RecolhaBagagem implements RecolhaBagageiroInterface,RecolhaPassageiroInterface{
    
    HashMap<Integer,Integer> cam;
    
    public RecolhaBagagem() {
    cam = new HashMap<>();  
    }
    
    /**
     * estado de bloqueio enquanto nao ha novas malas a chegar a esta zona
	(o bagageiro vai fazer notifyall cada vez que leva 1 mala para esta 
	zona e os passageiros quando 'acordam' vêm se têm alguma mala a recolher e adormecem outra  vez)
        * Invocador: Passageiro
     * @param bagID 
     */
    @Override
    public synchronized void goCollectABag(int bagID)
    {
    
    }
    /**
     * Invocador: Bagageiro
     * @param badID 
     * @return  
     */
    @Override
    public synchronized int carryItToAppropriateStore(int badID)
    {
        return 0;
    }
    
    /**
     * Estado de transição. Nao bloqueante. Os passageiros que não obtiveram todas as suas malas
     * precisam de reportar o acontecimento. Logging!
     * @param passageiroID 
     */
    @Override
    public synchronized void reportMissingBags(int passageiroID)
    {
        
    }
}
