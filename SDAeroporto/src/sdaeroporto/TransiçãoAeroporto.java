package sdaeroporto;

import static Estruturas.AuxInfo.*;
import Interfaces.TransicaoPassageiroInterface;

/**
 * Monitor que simula a interacção entre os passageiros à saída do aeroporto 
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
     * Invocador: Passageiro
     * 
     * Vai para casa
     * 
     * O passageiro, cujo destino final é este aeroporto, abandona o aeroporto e
     * vai para casa. Espera até ao último passageiro do seu voo chegar à saída 
     * do terminal de chegada ou ao terminal de partida, que tem a responsabilidade
     * de notificar os outros passageiros que podem ir embora.
     *
     * @param passageiroID identifica o passageiro
     */
    @Override
    public synchronized void goHome() {
        //System.out.println("GoHome!");
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
     * Invocador: Passageiro
     * 
     * Entrar no terminal de partida
     * 
     * O passageiro, que se encontra em trânsito,prepara o próximo voo.Espera 
     * até ao último passageiro do seu voo chegar à saída do terminal de chegada 
     * ou ao terminal de partida, que tem a responsabilidade de notificar os 
     * outros passageiros que podem ir embora.
     * 
     */
    @Override
    public synchronized void prepareNextLeg() {
        //System.out.println("Prepare next leg!");
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
