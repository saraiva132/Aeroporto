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

    private VectorCLK v_clock;

    private PoraoRegister porao;

    /**
     * Instanciação e inicialização do monitor <b>Porao</b>
     *
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
     * @return Mala que apanhou no porão, ou <i>null</i> caso o porão se
     * encontrar vazio
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
            porao.close();
        }
    }
}
