package Monitores;

import Estruturas.Globals;
import Estruturas.Globals.destination;
import static Estruturas.Globals.passMax;
import Estruturas.ShutdownException;
import Interfaces.LoggingInterface;
import Interfaces.ZonaDesembarqueInterface;
import java.rmi.RemoteException;
import sdaeroporto.ZonaDesembarqueMain;

/**
 * Monitor que simula a zona de interacção entre os passageiros e o bagageiro na
 * Zona de Desembarque do avião
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ZonaDesembarque implements ZonaDesembarqueInterface {

    /**
     * Número de passageiros que faltam passar pela zona de desembarque.
     *
     * @serialField nPass
     */
    private int nPass;
    /**
     * O bagageiro ja pode começar a recolher malas
     *
     * @serialField canGo
     */
    private boolean canGo;
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
    
    
    private ZonaDesembarqueMain zona;
    /**
     * Instanciação e inicialização do monitor <b>ZonaDesembarque</b>
     */
    public ZonaDesembarque(LoggingInterface log,ZonaDesembarqueMain zona) {
        nPass = passMax;
        canGo = false;
        three_entities_ended = 0;
        this.log = log;
        this.zona = zona;
    }

    /**
     * Descansar
     * <p>
     * Invocador: Bagageiro
     * <p>
     * O bagageiro descansa enquanto o próximo voo não chega e o último
     * passageiro não sai do avião
     */
    @Override
    public synchronized void takeARest() {
        //System.out.println("Taking a Rest guys...");
        try {
            while (!canGo) {
                wait();
            }
        } catch (InterruptedException ex) {
        }
        canGo = false;
    }

    /**
     * O que devo fazer?
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro após sair do avião reflecte sobre para onde deve ir. O
     * último passageiro deve notificar o bagageiro de que já pode começar a ir
     * buscar as malas ao porão do avião
     *
     * @param passageiroID identificador do passageiro
     * @param dest
     * <ul>
     * <li>TRUE se este aeroporto é o seu destino
     * <li>FALSE caso contrário
     * </ul>
     * @param nMalas número de malas que o passageiro contém
     * @return Qual o seu próximo passo dependendo da sua condição:
     * <ul>
     * <li> WITH_BAGAGE caso este seja o seu destino e possua bagagens
     * <li> WTHOUT_BAGAGE caso este seja o seu destino e não possua bagagens
     * <li> IN_TRANSIT caso esteja em trânsito
     * </ul>
     */
    @Override
    public synchronized destination whatShouldIDo(int passageiroID, boolean dest, int nMalas) {
        //System.out.println("What should i do!");

        nPass--;
        if (nPass == 0) {
            //reset da variavel nPass
            canGo = true;
            nPass = passMax;
            notify();
        }
        if (dest && nMalas == 0) {
            return destination.WITHOUT_BAGGAGE;
        } else if (dest) {
            try {
                log.reportState(passageiroID, Globals.passState.AT_THE_LUGGAGE_COLLECTION_POINT);
            } catch (RemoteException e) {
                System.exit(1);
            }
            return destination.WITH_BAGGAGE;

        } else {
            return destination.IN_TRANSIT;
        }

    }

    /**
     * Não existem mais malas para apanhar
     * <p>
     * Invocador: Bagageiro
     * <p>
     * O bagageiro, após verificar que o porão já se encontra vazio, dirige-se à
     * sua sala de espera
     */
    @Override
    public synchronized void noMoreBagsToCollect() {
        //System.out.println("No more bags to collect!");
        try {
            log.reportState(Globals.bagState.WAITING_FOR_A_PLANE_TO_LAND);
        } catch (RemoteException e) {
            System.exit(1);
        }
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
            zona.close();
        }
    }

}
