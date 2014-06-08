package Threads;

import Estruturas.Globals.bagDest;
import static Estruturas.Globals.nChegadas;
import Estruturas.Mala;
import Estruturas.Reply;
import Estruturas.VectorCLK;
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

    /**
     * Auxilia a simulação do ciclo de vida e as operações que o bagageiro pode
     * realizar sobre a zona de transição do aeroporto
     * 
     * @serialField transicao
     */
    private TransicaoBagageiroInterface transicao;

    /**
     * Relógio Vectorial do bagageiro.
     * 
     * @serialField vc
     */
    private VectorCLK vc;

    /**
     * Instanciação e inicialização do bagageiro
     *
     * @param zona referência para o objecto remoto correspondente ao monitor ZonaDesembarque
     * @param porao referência para o objecto remoto correspondente ao monitor Porao 
     * @param recolha referência para o objecto remoto correspondente ao monitor RecolhaBagagem
     * @param transicao referência para o objecto remoto correspondente ao monitor TransicaoAeroporto
     */
    public Bagageiro(ZonaDesembarqueBagageiroInterface zona, PoraoBagageiroInterface porao,
            RecolhaBagageiroInterface recolha, TransicaoBagageiroInterface transicao) {
        this.zona = zona;
        this.porao = porao;
        this.recolha = recolha;
        this.transicao = transicao;
        vc = new VectorCLK();
    }

    /**
     * Implementa o ciclo de vida do bagageiro
     */
    @Override
    public void run() {
        Mala mala;
        Reply response;
        int i = 0;
        try {
            while (i < nChegadas) {
                i++;
                System.out.println("Bagageiro encontra-se no voo numero: " + i);
                vc.Add(0);
                vc = zona.takeARest(vc);
                vc.Add(0);
                response = porao.tryToCollectABag(vc);
                mala = (Mala) response.getRetorno();
                vc = response.getTimestamp();
                bagDest nextState;
                do {
                    vc.Add(0);
                    response = recolha.carryItToAppropriateStore(vc, mala);
                    nextState = (bagDest) response.getRetorno();
                    vc = response.getTimestamp();
                    vc.Add(0);
                    response = porao.tryToCollectABag(vc);
                    mala = (Mala) response.getRetorno();
                    vc = response.getTimestamp();

                } while (nextState != bagDest.LOBBYCLEAN);
                vc.Add(0);
                vc = transicao.bagageiroDone(vc);
                vc.Add(0);
                vc = zona.noMoreBagsToCollect(vc);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
