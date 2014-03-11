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
public class TransferenciaTerminal {

    private int nPassageiros;
    private int nPassageirosMax;
    private int [] passageirosID;
    
    public TransferenciaTerminal(int nPassageirosMax)
    {
        this.nPassageirosMax = nPassageirosMax;
    }
    
    /**
     * Passageiro anuncia a intenção de entrar no autocarro
     * @param passageiroID 
     */
    
    public synchronized void takeABus(int passageiroID)
    {
        
    }
    /**
     * Passageiro entra no autocarro
     * @param passageiroID 
     */
    public synchronized void enterTheBus(int passageiroID)
    {
        
    }
    
    /**
     * Passageiro sai do autocarro
     * @param passageiroID 
     */
    public synchronized void leaveTheBus(int passageiroID)
    {
        
    }
    
    /**
     * Motorista anuncia que ja acabou o trabalho
     * @return 
     */
    public synchronized boolean hasDaysWorkEnded()
    {
        return true;
    }
    
    /**
     * Motorista anuncia que a viagem vai começar
     */
    public synchronized void announcingBusBoarding()
    {
        
    }
}
