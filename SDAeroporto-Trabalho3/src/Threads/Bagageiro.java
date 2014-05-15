package Threads;

import Estruturas.Globals.bagDest;
import static Estruturas.Globals.nChegadas;
import Estruturas.Mala;
import Interfaces.PoraoBagageiroInterface;
import Interfaces.RecolhaBagageiroInterface;
import Interfaces.TransicaoBagageiroInterface;
import Interfaces.ZonaDesembarqueBagageiroInterface;
import java.rmi.RemoteException;

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

    private TransicaoBagageiroInterface transicao;

    /**
     * Instanciação e inicialização do bagageiro
     *
     * @param zona
     * @param porao
     * @param recolha
     * @param transicao
     */
    public Bagageiro(ZonaDesembarqueBagageiroInterface zona, PoraoBagageiroInterface porao,
            RecolhaBagageiroInterface recolha, TransicaoBagageiroInterface transicao) {
        this.zona = zona;
        this.porao = porao;
        this.recolha = recolha;
        this.transicao = transicao;
    }

    /**
     * Implementa o ciclo de vida do bagageiro
     */
    @Override
    public void run() {
        Mala mala;
        int i = 0;
        try {
            while (i < nChegadas) {
                i++;
                System.out.println("Bagageiro encontra-se no voo numero: " + i);
                zona.takeARest();
                mala = porao.tryToCollectABag();
                bagDest nextState;
                do {
                    nextState = recolha.carryItToAppropriateStore(mala);
                    mala = porao.tryToCollectABag();

                } while (nextState != bagDest.LOBBYCLEAN);
                transicao.bagageiroDone();
                zona.noMoreBagsToCollect();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
