package Monitores;

import static Estruturas.AuxInfo.*;
import Interfaces.LoggingPassageiroInterface;
import Interfaces.TransicaoPassageiroInterface;

/**
 * Monitor que simula a interacção entre os passageiros à saída do aeroporto 
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class TransiçãoAeroporto implements TransicaoPassageiroInterface {
    
    /**
     * Número de passageiros que faltam chegar à saída do aeroporto
     * 
     * @serialField nPassageiros
     */
    private int nPassageiros;
    
    /**
     * Indica se os passageiros já podem sair ou ainda não
     * <ul>
     * <li>TRUE caso possam
     * <li>FALSE caso contrário
     * </ul>
     * @serialField canLeave
     * 
     */
    private boolean canLeave;   
    
    /**
     * Instanciação e inicialização do monitor <b>TransiçãoAeroporto</b>
     */
    public TransiçãoAeroporto() {
        nPassageiros = passMax;
        canLeave = false;
    }

    /**
     * Ir para casa
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro, cujo destino final é este aeroporto, abandona o aeroporto e
     * vai para casa. Espera até ao último passageiro do seu voo chegar à saída 
     * do terminal de chegada ou ao terminal de partida, que tem a responsabilidade
     * de notificar os outros passageiros que podem ir embora.
     * @param passageiroId identificador do passageiro
     * @param log referência para o monitor de logging; utilizado para reportar a evolução do estado global do problema
     */
    @Override
    public synchronized void goHome(int passageiroId, LoggingPassageiroInterface log) {
        //System.out.println("GoHome!");
        nPassageiros--;
        log.reportState(passageiroId, passState.EXITING_THE_ARRIVAL_TERMINAL);
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
     * Entrar no terminal de partida
     * <p>
     * Invocador: Passageiro
     * <p> 
     * O passageiro, que se encontra em trânsito,prepara o próximo voo.Espera 
     * até ao último passageiro do seu voo chegar à saída do terminal de chegada 
     * ou ao terminal de partida, que tem a responsabilidade de notificar os 
     * outros passageiros que podem ir embora.
     * @param passageiroId identificador do passageiro
     * @param log referência para o monitor de logging; utilizado para reportar a evolução do estado global do problema
     */
    @Override
    public synchronized void prepareNextLeg(int passageiroId, LoggingPassageiroInterface log) {
        //System.out.println("Prepare next leg!");
        nPassageiros--;
        log.reportState(passageiroId, passState.ENTERING_THE_DEPARTURE_TERMINAL);
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
