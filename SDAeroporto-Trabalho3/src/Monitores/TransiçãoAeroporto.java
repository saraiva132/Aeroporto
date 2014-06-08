package Monitores;

import static Estruturas.Globals.MON_TRANSICAO_AEROPORTO;
import static Estruturas.Globals.passMax;
import static Estruturas.Globals.passState.*;
import Estruturas.VectorCLK;
import Interfaces.LoggingInterface;
import Interfaces.TransicaoInterface;
import Registry.TransicaoAeroportoRegister;
import java.rmi.RemoteException;

/**
 * Monitor que simula a interacção entre os passageiros à saída do aeroporto
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class TransiçãoAeroporto implements TransicaoInterface {

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
     *
     * @serialField canLeave
     *
     */
    private boolean canLeave;
    /**
     * Indica se o bagageiro já acabou de recolher as malas do porão do avião.
     *
     * @serialField bagageiroDone
     */
    private boolean bagageiroDone;
    /**
     * Identifica quantas entidades activas instanciadas (passageiro, bagageiro
     * e motorista) já terminaram o seu ciclo de vida.
     * <p>
     * Necessário para a terminação do monitor.
     *
     * @serialField three_entities_ended
     */
    private int three_entities_ended;

    /**
     * Instância da comunicação monitores.
     * <p>
     * Necessário para a comunicação com o monitor <i>Logging</i>, no âmbito da
     * actualização do estado geral do problema aquando das operações realizadas
     * sobre o monitor.
     *
     * @serialField log
     */
    private final LoggingInterface log;
    
    
    private TransicaoAeroportoRegister transicao;
    
    private VectorCLK v_clock;
    /**
     * Instanciação e inicialização do monitor <b>TransiçãoAeroporto</b>
     *
     * @param log
     */
    public TransiçãoAeroporto(LoggingInterface log,TransicaoAeroportoRegister transicao) {
        bagageiroDone = false;
        nPassageiros = passMax;
        canLeave = false;
        three_entities_ended = 0;
        this.log = log;
        this.transicao = transicao;
        v_clock = new VectorCLK();
    }

    /**
     * Ir para casa
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro, cujo destino final é este aeroporto, abandona o aeroporto e
     * vai para casa. Espera até ao último passageiro do seu voo chegar à saída
     * do terminal de chegada ou ao terminal de partida, que tem a
     * responsabilidade de notificar os outros passageiros que podem ir embora e
     * que o bagageiro tenha recolhido todas as malas do porão.
     *
     * @param passageiroId identificador do passageiro
     */
    @Override
    public synchronized VectorCLK goHome(VectorCLK ts,int passageiroId) {
        v_clock.CompareVector(ts.getVc());
        //System.out.println("GoHome!");
        nPassageiros--;
        //System.out.println("passId: "+passageiroId+ " state: " +EXITING_THE_ARRIVAL_TERMINAL);
        try {
            log.UpdateVectorCLK(v_clock, MON_TRANSICAO_AEROPORTO);
            log.reportState(passageiroId, EXITING_THE_ARRIVAL_TERMINAL);
        } catch (RemoteException e) {
            System.exit(1);
        }
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
        return new VectorCLK(v_clock.CloneVector());
    }

    /**
     * Entrar no terminal de partida
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro, que se encontra em trânsito,prepara o próximo voo.Espera
     * até ao último passageiro do seu voo chegar à saída do terminal de chegada
     * ou ao terminal de partida, que tem a responsabilidade de notificar os
     * outros passageiros que podem ir embora e que o bagageiro tenha recolhido
     * todas as malas do porão.
     *
     * @param passageiroId identificador do passageiro
     */
    @Override
    public synchronized VectorCLK prepareNextLeg(VectorCLK ts , int passageiroId) {
        v_clock.CompareVector(ts.getVc());
        //System.out.println("Prepare next leg!");
        nPassageiros--;
        //System.out.println("passId: "+passageiroId+ " state: " +ENTERING_THE_DEPARTURE_TERMINAL);
        try {
            log.UpdateVectorCLK(v_clock, MON_TRANSICAO_AEROPORTO);
            log.reportState(passageiroId, ENTERING_THE_DEPARTURE_TERMINAL);
        } catch (RemoteException e) {
            System.exit(1);
        }
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
        return new VectorCLK(v_clock.CloneVector());
    }

    /**
     * Anunciar que já recolheu todas as malas do porão do avião.
     *
     * <p>
     * Invocador: Bagageiro
     * <p>
     * Bagageiro dirige-se anuncia aos passageiros de que já acabou de recolher
     * as malas do porão do avião em que chegaram e que, assim sendo, eles podem
     * sair do aeroporto
     * @param ts
     * @return 
     */
    @Override
    public synchronized VectorCLK bagageiroDone(VectorCLK ts) {
        v_clock.CompareVector(ts.getVc());
        try {
            log.UpdateVectorCLK(v_clock, MON_TRANSICAO_AEROPORTO);
        } catch (RemoteException ex) {
            System.exit(0);
        }
        System.out.println("Bagageiro acabou!(monitor)");
        bagageiroDone = true;
        notifyAll();
        return new VectorCLK(v_clock.CloneVector());
    }

    /**
     * Terminar o monitor.
     * <p>
     * Invocadores: BagageiroMain, MotoristaMain e PassageiroMain
     * <p>
     * No final da execução da simulação, para o fechar o monitor os 3
     * lançadores das threads correspondentes ao passageiro, bagageiro e
     * motorista necessitam de fechar os monitores.
     *
     * @return Informação se pode ou não terminar o monitor.
     * <ul>
     * <li>TRUE, caso possa
     * <li>FALSE, caso contrário
     * </ul>
     */
  public synchronized void shutdownMonitor() {
        if (++three_entities_ended >= 3) {
            transicao.close();
        }
    }
}
