/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import Interfaces.AutocarroMotoristaInterface;
import Interfaces.AutocarroPassageiroInterface;

/**
 *
 * @author rafael
 */


public class Autocarro implements AutocarroMotoristaInterface, AutocarroPassageiroInterface {

    private int nOcupantes;
    private int [] passageirosID;
    
    public Autocarro()
    {
    }
    
        /**
     * Passageiro entra no autocarro
     * @param passageiroID 
     */
    @Override
    public synchronized void enterTheBus(int passageiroID)
    {
        
    }
    
    /**
     * Passageiro sai do autocarro
     * @param passageiroID 
     */
    @Override
    public synchronized void leaveTheBus(int passageiroID)
    {
        
    }
    
    /**
     * Motorista conduz de volta
     */
    @Override
    public void goToDepartureTerminal()
    {
        
    }
    
    /**
     * Motorista leva os passageiros para o proximo aeroporto
     */
    @Override
    public void goToArrivalTerminal()
    {
        
    }
    
    /**
     * Motorista estaciona o autocarro
     */
    @Override
    public void parkTheBus()
    {
        
    }
    
    /**
     * Motorista estaciona o autocarro e larga os passageiros.
     */
    @Override
    public void parkTheBusAndLetPassOff()
    {
        
    }
}
