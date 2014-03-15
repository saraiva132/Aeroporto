/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdaeroporto;

import static Estruturas.AuxInfo.*;
import Interfaces.TransicaoPassageiroInterface;

/**
 *
 * @author rafael
 */
public class TransiçãoAeroporto implements TransicaoPassageiroInterface {

    private int nPassageiros;
    boolean canLeave;   //Passageiros podem sair!

    public TransiçãoAeroporto() {
        nPassageiros = passMax;
        canLeave = false;
    }

    /**
     * Passageiro com destino final abandona o aeroporto e vai para casa.
     *
     */
    @Override
    public synchronized void goHome() {
        System.out.println("GoHome!");
        nPassageiros--;
        if (nPassageiros == 0) {
            canLeave = true;
            notifyAll();
        }
        try {
            while (!canLeave) {
                wait();
            }
        } catch (InterruptedException ex) {
        }
        nPassageiros++;
        if (nPassageiros == passMax) {
            canLeave = false;
        }
    }

    /**
     * Passageiro que nao se encontra no destino final. Prepara o proximo voo.
     *
     */
    @Override
    public synchronized void prepareNextLeg() {
        System.out.println("Prepare next leg!");
        nPassageiros--;
        if (nPassageiros == 0) {
            canLeave = true;
            notifyAll();
        }
        try {
            while (!canLeave) {
                wait();
            }
        } catch (InterruptedException ex) {
        }
        nPassageiros++;
        if (nPassageiros == passMax) {
            canLeave = false;
        }
    }
}
