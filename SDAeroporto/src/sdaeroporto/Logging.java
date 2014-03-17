package sdaeroporto;

import Estruturas.AuxInfo.*;
import static Estruturas.AuxInfo.lotação;
import static Estruturas.AuxInfo.passMax;
import Interfaces.LoggingBagageiroInterface;
import Interfaces.LoggingMotoristaInterface;
import Interfaces.LoggingPassageiroInterface;
import java.util.LinkedList;

/**
 * Monitor correspondente ao Repositório Geral de Informação. Necessário apenas
 * para efeitos de logging
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Logging implements LoggingBagageiroInterface, LoggingMotoristaInterface, LoggingPassageiroInterface {

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
     * Destino do passageiro. FDT se for destino final. TRT se estiver
     * IN_TRANSIT.
     */
    private String[] passDest;

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
     *
     */
    public Logging() {
        pstate = new passState[passMax];
        bstate = bagState.WAITING_FOR_A_PLANE_TO_LAND;
        mstate = motState.PARKING_AT_THE_ARRIVAL_TERMINAL;
        fila = new int[passMax];
        nMalasTotal = new int[passMax];
        nMalasActual = new int[passMax];
        passDest = new String[passMax];
        for (int i = 0; i < passMax; i++) {
            pstate[i] = passState.AT_THE_DISEMBARKING_ZONE;
            nMalasTotal[i] = 0;
            nMalasActual[i] = 0;
            fila[i] = 0;
            passDest[i] = "";
        }
        assentos = new int[lotação];
        for (int i = 0; i < lotação; i++) {
            assentos[i] = -1;
        }
        nMalasStore = 0;
        nMalasBelt = 0;
        nMalasPorao = 0;
        this.nVoo = 0;
        reportInitialStatus();
    }

    /**
     * Função auxiliar utilizada para inicializar o logging
     */
    private void reportInitialStatus() {
        System.out.println("A começar logging...");
        System.out.println("PLANE     PORTER                   DRIVER");
        System.out.println("FN BN  Stat CB SR   Stat  Q1 Q2 Q3 Q4 Q5 Q6  S1 S2 S3");
        System.out.println("                                                         PASSENGERS");
        System.out.println("St1 Si1 NR1 NA1 St2 Si2 NR2 NA2 St3 Si3 NR3 NA3 St4 Si4 NR4 NA4 St5 Si5 NR5 NA5 St6 Si6 NR6 NA6");
    }

    private synchronized void reportStatus() {

        /*System.out.print(nVoo + " ");
        System.out.print(nMalasPorao + " ");
        System.out.print(bstate.toString() + " ");
        System.out.print(nMalasBelt + " ");
        System.out.print(nMalasStore + "   ");*/
        System.out.printf("%2s %2s %10s %2s %2s %10s  Fila de espera: [",nVoo,nMalasPorao,bstate.toString(),nMalasBelt,nMalasStore,mstate.toString());
        
        for (int i = 0; i < fila.length; i++) {
            System.out.printf("%1d,",fila[i]);
        }
        
        System.out.print("]  Estado do autocarro: [");
        for (int i = 0; i < assentos.length; i++) {
            System.out.printf("%1d,",assentos[i]);
        }
        
        System.out.println("]");

        for (int i = 0; i < passMax; i++) {
            System.out.printf("%12s %2s %2s %2s",pstate[i].toString(),passDest[i],nMalasTotal[i],nMalasActual[i]);
        }
        
        System.out.println();
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
    @Override
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
    @Override
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
    @Override
    public void reportState(bagState state) {
        bstate = state;
        reportStatus();
    }

    /**
     * Invocador: main
     *
     * Actualiza o número de voo
     *
     * @param voo - número de voo
     */
    public synchronized void nVoo(int voo) {
        this.nVoo = voo;
        reportStatus();
    }

    public synchronized void setPorao(int nMalas) {
        this.nMalasPorao = nMalas;
        reportStatus();
    }

    @Override
    public void bagagemPorao() {
        this.nMalasPorao--;
        reportStatus();
    }

    @Override
    public synchronized void bagagemBelt(boolean take) {
        if (take) {
            this.nMalasBelt--;
        } else {
            this.nMalasBelt++;
        }
        reportStatus();
    }

    @Override
    public void bagagemStore() {
        this.nMalasStore++;
        reportStatus();
    }

    @Override
    public synchronized void malasActual(int passID, int nMalas) {
        this.nMalasActual[passID] = nMalas;
        reportStatus();
    }

    @Override
    public synchronized void malasInicial(int passID, int nMalas) {
        this.nMalasTotal[passID] = nMalas;
        reportStatus();
    }

    @Override
    public synchronized void destino(int passID, Boolean destino) {
        if (destino) {
            this.passDest[passID] = "FDT";
        } else {
            this.passDest[passID] = "TRT";
        }
        reportStatus();
    }
    
    @Override
      public synchronized void filaEspera(Object [] oi) {
        for(int i= 0;i<oi.length;i++)
        {
            this.fila[i] = (int)oi[i];
        }
        for(int i =1;i<fila.length;i++)
        {
            if(fila[i] == fila[i-1])
                fila[i] = 0;
        }
        reportStatus();
    }
        public synchronized void filaEsperas(Object [] oi) {   
        for(int i= 0;i<oi.length;i++)
        {
            this.fila[i] = (int)oi[i];
        }
        for(int i =1;i<fila.length;i++)
        {
            if(fila[i] == fila[i-1])
                fila[i] = 0;
        }
        if(oi.length == 1)
            this.fila[0] = 0;
        reportStatus();
    }
    @Override
    public synchronized void autocarroState(int[] seats) {
        System.arraycopy(seats, 0, this.assentos, 0, seats.length);
        reportStatus();
    }
}
