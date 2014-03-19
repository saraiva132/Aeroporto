package Threads;

import Estruturas.AuxInfo.bagDest;
import Interfaces.ZonaDesembarqueBagageiroInterface;
import Estruturas.AuxInfo.bagState;
import Estruturas.Mala;
import static Estruturas.AuxInfo.nChegadas;
import Interfaces.LoggingBagageiroInterface;
import Interfaces.PoraoBagageiroInterface;
import Interfaces.RecolhaBagageiroInterface;

/**
 * Identifica o tipo de dados bagageiro
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Bagageiro extends Thread {

    /**
     * Identifica o estado do bagageiro
     *
     * @serialField state
     */
    private bagState state;

    /**
     * Auxilia a simulação do ciclo de vida e as operações que o bagageiro pode
     * realizar sobre a zona de desembarque
     *
     * @serialField zona
     */
    private ZonaDesembarqueBagageiroInterface zona;

    /**
     * Auxilia a simulação do ciclo de vida e as operações que o bagageiro pode
     * realizar sobre o porão do avião
     *
     * @serialField porao
     */
    private PoraoBagageiroInterface porao;

    /**
     * Auxilia a simulação do ciclo de vida e as operações que o bagageiro pode
     * realizar sobre as zonas de recolha e de armazenamento temporário de
     * bagagens
     *
     * @serialField recolha
     */
    private RecolhaBagageiroInterface recolha;

    /**
     * Auxilia no logging da evolução dos estados do problema ao longo da
     * simulação
     *
     * @serialField log
     */
    private LoggingBagageiroInterface log;
    private int nVoo;

    /**
     * Instanciação e inicialização do bagageiro
     *
     * @param zona monitor corresondente à zona de desembarque
     * @param porao monitor correspondente ao porão do avião
     * @param recolha monitor correspondente às zonas de recolha e armazenamento
     * temporário de bagagens
     * @param log monitor correspondente ao logging do problema
     */
    public Bagageiro(ZonaDesembarqueBagageiroInterface zona, PoraoBagageiroInterface porao,
            RecolhaBagageiroInterface recolha, LoggingBagageiroInterface log) {
        state = bagState.WAITING_FOR_A_PLANE_TO_LAND;
        this.zona = zona;
        this.porao = porao;
        this.recolha = recolha;
        this.log = log;
        this.nVoo = 0;
    }

    /**
     * Implementa o ciclo de vida do bagageiro
     */
    @Override
    public void run() {
        Mala mala;
        while(nVoo < nChegadas) {
            zona.takeARest();
            log.reportState(state = bagState.WAITING_FOR_A_PLANE_TO_LAND);
            mala = porao.tryToCollectABag();
            bagDest nextState;
            do {
                log.reportState(state = bagState.AT_THE_PLANES_HOLD);
                nextState = recolha.carryItToAppropriateStore(log,mala);
                if (nextState != bagDest.LOBBYCLEAN) {
                    log.bagagemPorao();
                }
                switch (nextState) {
                    case BELT:
                        log.reportState(state = bagState.AT_THE_LUGGAGE_BELT_CONVERYOR);
                        break;
                    case STOREROOM:
                        log.reportState(state = bagState.AT_THE_STOREROOM);
                        break;
                }
                mala = porao.tryToCollectABag();
            } while (nextState != bagDest.LOBBYCLEAN);
            log.reportState(state = bagState.WAITING_FOR_A_PLANE_TO_LAND);
        }
    }
    
    public void setnVoo(int voo)
    {
        this.nVoo = voo;
    }
}