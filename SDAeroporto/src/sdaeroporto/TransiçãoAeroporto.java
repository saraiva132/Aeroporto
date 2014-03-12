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

    public TransiçãoAeroporto() {
        nPassageiros = passMax;
    }

    /**
     * Passageiro com destino final abandona o aeroporto e vai para casa.
     *
     * @param passageiroID
     */
    @Override
    public synchronized void goHome() {
        nPassageiros--;
        if (nPassageiros == 0) {
            nPassageiros = passMax;
            notifyAll();
        }
        try {
            while (nPassageiros > 0) {
                wait();
            }
        } catch (InterruptedException ex) {
        }
    }

    /**
     * Passageiro que nao se encontra no destino final. Prepara o proximo voo.
     *
     * @param passageiroID
     */
    @Override
    public synchronized void prepareNextLeg() {
        nPassageiros--;
        if (nPassageiros == 0) {
            nPassageiros = passMax;
            notifyAll();
        }
        try {
            while (nPassageiros > 0) {
                wait();
            }
        } catch (InterruptedException ex) {
        }
    }
}
