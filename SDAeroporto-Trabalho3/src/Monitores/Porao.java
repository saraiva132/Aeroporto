package Monitores;

import Estruturas.Globals;
import static Estruturas.Globals.MON_PORAO;
import Estruturas.Mala;
import Estruturas.Reply;
import Estruturas.VectorCLK;
import Interfaces.LoggingInterface;
import Interfaces.PoraoInterface;
import Registry.PoraoRegister;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * Monitor que simula a interacção do bassageiro com o porão de um avião
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Porao implements PoraoInterface {

    /**
     * Conjunto de malas que estão no porão do avião
     *
     * @serialField malas
     */
    ArrayList<Mala> malas;
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

    /**
     * Instância do tipo de dados VectorCLK.
     *
     * @serialField v_clock
     */
    private VectorCLK v_clock;

    /**
     * Referência para o tipo de dados PoraoRegister
     *
     * @serialField porao
     */
    private PoraoRegister porao;

    /**
     * Instanciação e inicialização do monitor <b>Porao</b>
     *
     * @param log rererência para o objecto remoto correspondente ao monitor de
     * Logging
     * @param porao referência para o tipo de dados PoraoRegister
     */
    public Porao(LoggingInterface log, PoraoRegister porao) {
        this.malas = new ArrayList<>();
        three_entities_ended = 0;
        this.log = log;
        this.porao = porao;
        v_clock = new VectorCLK();
    }

    /**
     * Tentar recolher uma mala
     * <p>
     * Invocador: bagageiro
     * <p>
     * O bagageiro desloca-se ao porão do avião e caso este não se encontre
     * vazio recolhe uma mala
     *
     * @param ts relógio vectorial do bagageiro
     * @return Mala que apanhou no porão, ou <i>null</i> caso o porão se
     * encontrar vazio juntamente com relógio vectorial actualizado
     */
    @Override
    public synchronized Reply tryToCollectABag(VectorCLK ts) {

        v_clock.CompareVector(ts.getVc());
        if (malas.isEmpty()) {
            Reply rep = new Reply(new VectorCLK(v_clock), null);
            return rep;
        } else {
            System.out.println("vim recolher mala");
            try {
                log.UpdateVectorCLK(v_clock, MON_PORAO);
                log.reportState(Globals.bagState.AT_THE_PLANES_HOLD);
                log.bagagemPorao();
            } catch (RemoteException e) {
                System.exit(1);
            }
            Reply rep = new Reply(new VectorCLK(v_clock), (Object) malas.remove(0));
            return rep;
        }
    }

    public void sendLuggages(Mala[] malas) {
        this.malas.addAll(Arrays.asList(malas));
        System.out.println("adicionei " + malas.length + " malas.");
    }

    /**
     * Fechar o monitor Porao.
     * <p>
     * Invocador: Bagageiro/Passageiro/Motorista
     * <p>
     * O bagageiro/passageiro/motorista, após concluir o seu ciclo de vida
     * invoca a operação para fechar o monitor <i>Porao</i>.
     *
     */
    @Override
    public synchronized void shutdownMonitor() {
        if (++three_entities_ended >= 3) {
            porao.close();
        }
    }
}
