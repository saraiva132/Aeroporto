package sdaeroporto;

import Estruturas.AuxInfo.*;
import static Estruturas.AuxInfo.lotação;
import static Estruturas.AuxInfo.passMax;

/**
 * Monitor correspondente ao Repositório Geral de Informação. Necessário apenas
 * para efeitos de logging
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Logging {

    /**
     * Array com os estados de todos os passageiros
     *
     * @serialField pstate
     */
    private passState[] pstate;

    /**
     * Estado do bagageiro
     *
     * @serialField bstate
     */
    private bagState bstate;

    /**
     * Estado do motorista
     *
     * @serialField mstate
     */
    private motState mstate;

    /**
     * Número de voo
     */
    private int nVoo;

    /**
     * Número de malas no porão.
     */
    private int nMalasPorao;

    /**
     * Número de malas no cinto.
     *
     */
    private int nMalasBelt;

    /**
     * Número de malas na storeroom.
     */
    private int nMalasStore;

    /**
     * Fila de passageiros no passeio.
     */
    private int[] fila;

    /**
     * Estado de ocupação do Autocarro.
     */
    private int[] assentos;

    /**
     * Destino do passageiro.
     */
    private destination[] passDest;

    /**
     * Número de malas total dos passageiros.
     */
    private int[] nMalasTotal;

    /**
     * Número de malas actual dos passageiros.
     */
    private int[] nMalasActual;

    /**
     * Instanciação e inicialização do monitor <b>Logging</b>
     */
    public Logging() {
        pstate = new passState[passMax];
        bstate = bagState.WAITING_FOR_A_PLANE_TO_LAND;
        mstate = motState.PARKING_AT_THE_ARRIVAL_TERMINAL;
        fila = new int[passMax];
        for (int i = 0; i < passMax; i++) {
            pstate[i] = passState.AT_THE_DISEMBARKING_ZONE;
            nMalasTotal[i] = 0;
            nMalasActual[i] = 0;
            fila[i] = 0;
        }
        assentos = new int[lotação];
        for (int i = 0; i < lotação; i++) {
            assentos[i] = 0;
        }
        nMalasStore = 0;
        nMalasBelt = 0;
        nMalasPorao = 0;
        nVoo = 0;
        reportInitialStatus();
    }

    /**
     * Função auxiliar utilizada para inicializar o logging
     */
    private void reportInitialStatus() {
        System.out.println("A começar logging...");
        System.out.println("                Barbeiro                Motorista                  Passageiro1              Passageiro2              Passageiro3              Passageiro4              Passageiro5");
    }

    private synchronized void reportStatus() {

        System.out.printf("%25s %25s ", bstate, mstate);
        for (int i = 0; i < passMax; i++) {
            System.out.printf("%25s ", pstate[i]);
        }
        System.out.println();
    }

    /**
     * Invocador: Passageiro
     *
     * O passageiro altera o seu estado
     *
     * @param passID identificador do passageiro
     * @param state novo estado do passageiro
     */
    public synchronized void reportState(int passID, passState state) {
        pstate[passID] = state;
        reportStatus();
    }

    /**
     * Invocador: Motorista
     *
     * O motorista altera o seu estado
     *
     * @param state novo estado do motorista
     */
    public void reportState(motState state) {
        mstate = state;
        reportStatus();
    }

    /**
     * Invocador: Bagageiro
     *
     * O bagageiro altera o seu estado
     *
     * @param state novo estado do bagageiro
     */
    public void reportState(bagState state) {
        bstate = state;
        reportStatus();
    }
}
