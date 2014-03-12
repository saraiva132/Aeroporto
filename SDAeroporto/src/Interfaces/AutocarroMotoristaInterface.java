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
public interface AutocarroMotoristaInterface {
    /**
     * Motorista leva os passageiros para o proximo aeroporto
     */
    public void goToDepartureTerminal();
    /**
     * Motorista leva os passageiros para o proximo aeroporto
     */
    public void goToArrivalTerminal();
    /**
     * Motorista estaciona o autocarro
     */
    public void parkTheBus();  
    /**
     * Motorista estaciona o autocarro e larga os passageiros.
     */
    public void parkTheBusAndLetPassOff();  
}
