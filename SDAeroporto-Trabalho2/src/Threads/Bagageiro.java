package Threads;

import ClientSide.InterfaceBagageiro;
import Estruturas.AuxInfo.bagDest;
import Estruturas.Mala;
import static Estruturas.AuxInfo.nChegadas;

/**
 * Identifica o tipo de dados bagageiro
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Bagageiro extends Thread {

    private InterfaceBagageiro bagageiroI = null;
    
    /**
     * Instanciação e inicialização do bagageiro
     */
    public Bagageiro() {
        bagageiroI = new InterfaceBagageiro();
    }

    /**
     * Implementa o ciclo de vida do bagageiro
     */
    @Override
    public void run() {
        Mala mala;
        int i =0;
        while(i < nChegadas) {
            i++;
            System.out.println("Bagageiro encontra-se no voo numero: "+i);
            bagageiroI.takeARest();
            mala = bagageiroI.tryToCollectABag();
            bagDest nextState;
            do {
                nextState = bagageiroI.carryItToAppropriateStore(mala);
                mala = bagageiroI.tryToCollectABag();
                
            } while (nextState != bagDest.LOBBYCLEAN);
            bagageiroI.bagageiroAcabou();
            bagageiroI.noMoreBagsToCollect();
        }
    }
}
