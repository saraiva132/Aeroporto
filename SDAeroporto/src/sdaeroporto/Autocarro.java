/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import static Estruturas.AuxInfo.lotação;
import Interfaces.AutocarroMotoristaInterface;
import Interfaces.AutocarroPassageiroInterface;

/**
 *
 * @author rafael
 */


public class Autocarro implements AutocarroMotoristaInterface, AutocarroPassageiroInterface {

    private int nOcupantes;
    private boolean [] seat;
    
    public Autocarro()
    {
        seat = new boolean[lotação];
        for(int i = 0;i<lotação;i++)
        {
            seat[i] = false; // Autocarro inicialmente encontra-se vazio.
        }
    }
    
        /**
     * Passageiro entra no autocarro
     * @param id
     */
    @Override
    public synchronized void enterTheBus(int id)
    {
        nOcupantes++;
        seat[id] = true;
        if(nOcupantes == lotação)
            notify();
    }
    
    /**
     * Passageiro sai do autocarro
     * @param id 
     */
    @Override
    public synchronized void leaveTheBus(int id)
    {
        nOcupantes--;
        seat[id] = false;
        if(nOcupantes == 0)
            notify();
    }
    
    @Override
    public synchronized void announcingBusBoardingWaiting() {
        try {
            while (nOcupantes < lotação) {
                wait();
            }
        } catch (InterruptedException ex) {
        }
    }
    
    /**
     * Motorista conduz de volta
     */
    @Override
    public void goToDepartureTerminal()
    {
        //Estado de transição. Fazer o quê mesmo?
    }
    
    /**
     * Motorista leva os passageiros para o proximo aeroporto
     */
    @Override
    public void goToArrivalTerminal()
    {
        //Estado de transição. Fazer o quê mesmo?
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
    public synchronized void parkTheBusAndLetPassOff()
    {
        try {
            while (nOcupantes > 0) {
                wait();
            }
        } catch (InterruptedException ex) {
        }
    }
}
