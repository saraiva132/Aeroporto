package Monitores;

import Estruturas.AuxInfo.*;
import static Estruturas.AuxInfo.lotação;
import static Estruturas.AuxInfo.passMax;
import Interfaces.LoggingBagageiroInterface;
import Interfaces.LoggingMotoristaInterface;
import Interfaces.LoggingPassageiroInterface;
import genclass.GenericIO;
import genclass.TextFile;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Monitor correspondente ao Repositório Geral de Informação. Necessário apenas
 * para efeitos de logging
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Logging implements LoggingBagageiroInterface, LoggingMotoristaInterface, LoggingPassageiroInterface {

    /**
     * Nome do ficheiro de logging
     *
     * @serialField fileName
     */
    private String fileName = "log.txt";

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
    
    PrintWriter out;
    /**
     * Instanciação e inicialização do monitor <b>Logging</b>
     *
     */
    public Logging() {
        try {
            out = new PrintWriter(new FileWriter("test.txt", false));
        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex) {
            
        }
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
            assentos[i] = 0;
        }
        nMalasStore = 0;
        nMalasBelt = 0;
        nMalasPorao = 0;
        this.nVoo = 0;
    }

    /**
     * Função auxiliar utilizada para inicializar o logging
     */
    public synchronized void reportInitialStatus() {

        out.println("|PLANE |   PORTER       DRIVER                                       PASSENGERS");
        out.print("|FN  BN| Stat CB SR     Stat      ");
        for(int i = 0;i<passMax;i++)
        {
            out.printf("Q%s",i);
        }
        out.print("             ");
        for(int i =0;i<lotação;i++)
        {
             out.printf("S%s",i);
        }
        out.print("     ");
        //log.writelnString("                                                         PASSENGERS");
        //System.out.print("  St1 Si1 NR1 NA1 St2 Si2 NR2 NA2 St3 Si3 NR3 NA3 St4 Si4 NR4 NA4 St5 Si5 NR5 NA5 St6 Si6 NR6 NA6");
        for(int i=0;i<passMax;i++)
        {
            out.print("St"+i+" Si"+i+" NR"+i+" NA"+i+"|");
        }
        out.println();
    }

    private synchronized void reportStatus() {
        out.printf("|%2s %3s|%4s %3s %3s | %4s fila: [", nVoo, nMalasPorao, bstate.ordinal(), nMalasBelt, nMalasStore, mstate.ordinal());
        for (int i = 0; i < fila.length; i++) {
            out.printf("%1d ", fila[i]);
        }

        out.print("] autocarro: [");
        for (int i = 0; i < assentos.length; i++) {
            out.printf("%1d ", assentos[i]);
        }
        out.print("]  ");
        for (int i = 0; i < passMax; i++) {
            out.printf("%3s %3s  %1s  %2s |", pstate[i].ordinal(), passDest[i], nMalasTotal[i], nMalasActual[i]);
        }

        out.println();
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

    public synchronized void setPorao(int set) {
        this.nMalasPorao = set;
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
    public synchronized void addfilaEspera(int id) {

        for (int i = 0; i < fila.length; i++) {
            if (fila[i] == 0) {
                fila[i] = id + 1;
                break;
            }
        }

        reportStatus();
    }

    @Override
    public synchronized void removefilaEspera() {
        fila[0] = 0;
        for (int i = 1; i < fila.length; i++) {
            fila[i - 1] = fila[i];
            if (fila[i] == 0) {
                fila[i - 1] = 0;
            }
        }
        reportStatus();
    }

    @Override
    public synchronized void autocarroState(int[] seats) {
        System.arraycopy(seats, 0, this.assentos, 0, seats.length);
        reportStatus();
    }
    
    
}
