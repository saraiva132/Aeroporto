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
    private boolean[] seat;
    private boolean hasEnded;

    public Autocarro() {
        hasEnded = false;
        nOcupantes = 0;
        seat = new boolean[lotação];
        for (int i = 0; i < lotação; i++) {
            seat[i] = false; // Autocarro inicialmente encontra-se vazio.
        }
    }

    /**
     * Passageiro entra no autocarro
     *
     * @param id
     */
    @Override
    public synchronized void enterTheBus(int id) {
        System.out.println("Entering the bus motha focka");
        nOcupantes++;
        seat[id-1] = true;
        if (nOcupantes == lotação) {
            notify();
        }
        try {
            while (!hasEnded) {
                wait();
            }
        } catch (InterruptedException ex) {
        }
    }

    /**
     * Passageiro sai do autocarro
     *
     * @param id
     */
    @Override
    public synchronized void leaveTheBus(int id) {
        System.out.println("IM OUT!Shitty bus");
        nOcupantes--;
        seat[id-1] = false;
        if (nOcupantes == 0) {
            notify();
        }
    }

    @Override
    public synchronized void announcingBusBoardingWaiting() {
        System.out.println("All Aboard V2");
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
    public void goToDepartureTerminal() {
        System.out.println("Go to next leg");
        //Estado de transição. Fazer o quê mesmo?
    }

    /**
     * Motorista leva os passageiros para o proximo aeroporto
     */
    @Override
    public void goToArrivalTerminal() {
        System.out.println("Lets get back guys");
        //Estado de transição. Fazer o quê mesmo?
    }

    /**
     * Motorista estaciona o autocarro
     */
    @Override
    public void parkTheBus() {
        //Transição
        System.out.println("Parking..");
    }

    /**
     * Motorista estaciona o autocarro e larga os passageiros.
     */
    @Override
    public synchronized void parkTheBusAndLetPassOff() {
        System.out.println("OUT OUT OUT!");
        hasEnded = true;
        notifyAll();
        try {
            while (nOcupantes > 0) {
                wait();
            }
        } catch (InterruptedException ex) {
        }
        hasEnded = false;
    }
}
