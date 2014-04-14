package Threads;

import Estruturas.AuxInfo.bagDest;
import Interfaces.ZonaDesembarqueBagageiroInterface;
import Estruturas.AuxInfo.bagState;
import Estruturas.Mala;
import static Estruturas.AuxInfo.nChegadas;
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
    private int nVoo;

    /**
     * Instanciação e inicialização do bagageiro
     *
     * @param zona monitor corresondente à zona de desembarque
     * @param porao monitor correspondente ao porão do avião
     * @param recolha monitor correspondente às zonas de recolha e armazenamento
     * temporário de bagagens
     */
    public Bagageiro(ZonaDesembarqueBagageiroInterface zona, PoraoBagageiroInterface porao,
            RecolhaBagageiroInterface recolha) {
        this.zona = zona;
        this.porao = porao;
        this.recolha = recolha;
        this.nVoo = 0;
    }

    /**
     * Implementa o ciclo de vida do bagageiro
     */
    @Override
    public void run() {
        Mala mala;
        while (nVoo < nChegadas) {
            zona.takeARest();
            mala = porao.tryToCollectABag();
            bagDest nextState;
            do {
                nextState = recolha.carryItToAppropriateStore(mala);
                mala = porao.tryToCollectABag();
            } while (nextState != bagDest.LOBBYCLEAN);
        }
    }

    public void setnVoo(int voo) {
        this.nVoo = voo;
    }
}
