package Monitores;

import ClientSide.InterfaceMonitoresLogging;
import static Estruturas.AuxInfo.*;
import static Estruturas.AuxInfo.passState.*;
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
    private boolean bagageiroDone;
    private int three_entities_ended;
    private InterfaceMonitoresLogging log;
    
    /**
     * Instanciação e inicialização do monitor <b>TransiçãoAeroporto</b>
     */
    public TransiçãoAeroporto() {
        bagageiroDone = false;
        nPassageiros = passMax;
        canLeave = false;
        three_entities_ended=0;
        log = new InterfaceMonitoresLogging("TransicaoAeroporto");
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
     */
    @Override
    public synchronized void goHome(int passageiroId) {
        //System.out.println("GoHome!");
        nPassageiros--;
        System.out.println("passId: "+passageiroId+ " state: " +EXITING_THE_ARRIVAL_TERMINAL);
        log.reportState(passageiroId, EXITING_THE_ARRIVAL_TERMINAL);
        if (nPassageiros == 0) {
            canLeave = true;
            notifyAll();
        }
        try {
            while (!canLeave || !bagageiroDone) {
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
     */
    @Override
    public synchronized void prepareNextLeg(int passageiroId) {
        //System.out.println("Prepare next leg!");
        nPassageiros--;
        System.out.println("passId: "+passageiroId+ " state: " +ENTERING_THE_DEPARTURE_TERMINAL);
        log.reportState(passageiroId,ENTERING_THE_DEPARTURE_TERMINAL);
        if (nPassageiros == 0) {
            canLeave = true;
            notifyAll();
        }
        try {
            while (!canLeave || !bagageiroDone) {
                wait();
            }
        } catch (InterruptedException ex) {
        }
        nPassageiros++;
        if (nPassageiros == passMax) {
            canLeave = false;
            bagageiroDone = false;
        }
    }
    
    public synchronized void bagageiroTerminou()
    {
        System.out.println("Bagageiro acabou!(monitor)");
        bagageiroDone = true;
        notifyAll();
    }
    
    public synchronized boolean shutdownMonitor(){
        return (++three_entities_ended >= 3);   
    }
}
