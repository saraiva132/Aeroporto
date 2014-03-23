package Monitores;

import Estruturas.AuxInfo.*;
import static Estruturas.AuxInfo.lotação;
import static Estruturas.AuxInfo.passMax;
import Interfaces.LoggingBagageiroInterface;
import Interfaces.LoggingMotoristaInterface;
import Interfaces.LoggingPassageiroInterface;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

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
     * 
     * @serialField nVoo
     */
    private int nVoo;

    /**
     * Número de malas no porão.
     * 
     * @serialField nMalasPorao
     */
    private int nMalasPorao;

    /**
     * Número de malas na passadeira de recolha de bagagens.
     * 
     * @serialField nMalasBelt
     *
     */
    private int nMalasBelt;

    /**
     * Número de malas na storeroom.
     * 
     * @serialField nMalasStore
     */
    private int nMalasStore;

    /**
     * Fila de passageiros no passeio.
     * 
     * @serialField fila
     */
    private int[] fila;

    /**
     * Estado de ocupação do Autocarro.
     * 
     * @serialField assentos
     */
    private int[] assentos;

    /**
     * Destino do passageiro. FDT se for destino final. TRT se estiver
     * IN_TRANSIT.
     * 
     * @serialField passDest
     */
    private String[] passDest;

    /**
     * Número de malas total dos passageiros.
     * 
     * @serialField nMalasTotal
     */
    private int[] nMalasTotal;

    /**
     * Número de malas actual dos passageiros.
     * 
     * @serialField nMalasActual
     */
    private int[] nMalasActual;

    private PrintStream out;

    /**
     * Instanciação e inicialização do monitor <b>Logging</b>
     *
     */
    public Logging() {
        try {
            out = new PrintStream(new FileOutputStream(fileName, false));
        } catch (FileNotFoundException ex) {

        }
        System.setOut(out);
        pstate = new passState[passMax];
        fila = new int[passMax + 1];
        nMalasTotal = new int[passMax];
        nMalasActual = new int[passMax];
        passDest = new String[passMax];
        assentos = new int[lotação];
    }

    /**
     * Auxilia na inicialização do logging
     */
    public synchronized void reportInitialStatus() {

        out.println("|PLANE |   PORTER       DRIVER                                       PASSENGERS");
        out.print("|FN  BN| Stat CB SR     Stat      ");
        for (int i = 0; i < passMax; i++) {
            out.printf("Q%s", i);
        }
        out.print("             ");
        for (int i = 0; i < lotação; i++) {
            out.printf("S%s", i);
        }
        out.print("     ");
        for (int i = 0; i < passMax; i++) {
            out.print("St" + i + " Si" + i + " NR" + i + " NA" + i + "|");
        }
        out.println();
        bstate = bagState.WAITING_FOR_A_PLANE_TO_LAND;
        mstate = motState.PARKING_AT_THE_ARRIVAL_TERMINAL;
        for (int i = 0; i < passMax; i++) {
            pstate[i] = passState.AT_THE_DISEMBARKING_ZONE;
            nMalasTotal[i] = 0;
            nMalasActual[i] = 0;
            fila[i] = 0;
            passDest[i] = "";
        }
        for (int i = 0; i < lotação; i++) {
            assentos[i] = 0;
        }
        nMalasStore = 0;
        nMalasBelt = 0;
        nMalasPorao = 0;
    }

    /**
     * Auxilia a reportar uma actualização do estado geral do problema
     */
    private void reportStatus() {
        
        out.printf("|%2s %3s|%4s %3s %3s | %4s fila: [", nVoo, nMalasPorao, bstate.toString(), nMalasBelt, nMalasStore, mstate.toString());
        for (int i = 0; i < fila.length; i++) {
            out.printf("%1d ", fila[i]);
        }

        out.print("] autocarro: [");
        for (int i = 0; i < assentos.length; i++) {
            out.printf("%1d ", assentos[i]);
        }
        out.print("]  ");
        for (int i = 0; i < passMax; i++) {
            out.printf("%3s %3s  %1s  %2s |", pstate[i].toString(), passDest[i], nMalasTotal[i], nMalasActual[i]);
        }

        out.println();
    }
    
    /**
     * Reportar mudança de estado.
     * 
     * <p>Invocador: Passageiro
     * 
     * <p>O passageiro reporta a mudança do seu estado.
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
     * Reportar mudança de estado.
     * <p>
     * Invocador: Motorista
     * 
     * <p>Motorista reporta mudança do seu estado.
     * 
     * @param state novo estado do motorista
     */
    @Override
    public synchronized void reportState(motState state) {
        mstate = state;
        reportStatus();
    }

    /**
     * Reportar mudança de estado.
     * 
     * <p>Invocador: Bagageiro
     * 
     * <p>Bagageiro reporta mudança do seu estado
     * 
     * @param state novo estado do bagageiro
     */
    @Override
    public synchronized void reportState(bagState state) {
        bstate = state;
        reportStatus();
    }

    /**
     * Actualizar o número de voo
     *
     * @param voo número de voo
     */
    public synchronized void nVoo(int voo) {
        this.nVoo = voo;
        reportStatus();
    }

    /**
     * Definir o número de malas que vem no porão do avião que acabou de aterrar
     * 
     * @param set número de malas no porão
     */
    public synchronized void setPorao(int set) {
        this.nMalasPorao = set;
        reportStatus();
    }

    /**
     * Reportar retirada de uma mala do porão.
     * 
     * <p>Invocador: Bagageiro
     * 
     * <p>Bagageiro reporta que retirou uma mala do porão do avião.
     */
    @Override
    public synchronized void bagagemPorao() {
        this.nMalasPorao--;
        reportStatus();
    }

    /**
     * Reportar retiro de bagagem.
     * 
     * <p>Invocador: Passageiro, Bagageiro
     * 
     * <p> Passageiro reporta que tirou uma bagagem sua da passadeira de recolha de bagagens.
     * <p> Bagageiro reporta que colocou uma bagagem na passadeira de recolha de bagagens.
     * @param take 
     * <ul>
     * <li>TRUE caso tenha sido retirada da passadeira
     * <li>FALSE caso tenha sido depositada na passadeira
     * </ul>
     */
    @Override
    public synchronized void bagagemBelt(boolean take) {
        if (take) {
            this.nMalasBelt--;
        } else {
            this.nMalasBelt++;
        }
        reportStatus();
    }

    /**
     * Reportar depósito de bagagem.
     * 
     * <p>Invocador: Bagageiro
     * 
     * <p> Bagageiro reporta que colocou uma bagagem na zona de armazenamento temporário de bagagens.
     */
    @Override
    public synchronized void bagagemStore() {
        this.nMalasStore++;
        reportStatus();
    }

    /**
     * Reportar número de malas em posse.
     * 
     * <p>Invocador: Passageiro
     * 
     * <p>Passageiro reporta quantas malas tem em sua posse.
     * 
     * @param passID identificador do passageiro
     * @param nMalas número de malas que passageiro tem em sua posse
     */
    @Override
    public synchronized void malasActual(int passID, int nMalas) {
        this.nMalasActual[passID] = nMalas;
        reportStatus();
    }

    /**
     * Reportar número de malas total.
     * 
     * <p>Invocador: Passageiro
     * 
     * <p>Passageiro reporta quantas malas tem no total.
     * 
     * @param passID identificador do passageiro
     * @param nMalas número de malas totais que petencem ao passageiro
     */
    @Override
    public synchronized void malasInicial(int passID, int nMalas) {
        this.nMalasTotal[passID] = nMalas;
        reportStatus();
    }

    /**
     * Reportar tipo de passageiro.
     * 
     * <p>Invocador: Passageiro
     * 
     * <p>Passageiro reporta se está em trânsito  ou se este aeroporto corresponde ao seu destino.
     * 
     * @param passID identificador do passageiro
     * @param destino 
     * <ul>
     * <li>FALSE caso esteja em trânsito
     * <li>TRUE caso contrário
     * </ul>
     */
    @Override
    public synchronized void destino(int passID, Boolean destino) {
        if (destino) {
            this.passDest[passID] = "FDT";
        } else {
            this.passDest[passID] = "TRT";
        }
        reportStatus();
    }

    /**
     * Reportar entrada para a fila de espera.
     * 
     * <p>Invocador: Passageiro
     * 
     * <p>Passageiro reporta que se encontra na fila na zona de transferência 
     * entre terminais à espera de entrar para o autocarro.
     * 
     * @param id identificador do passageiro
     */
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

    /**
     * Reportar saída da fila de espera.
     * 
     * <p>Invocador: Passageiro
     * 
     * <p>Passageiro reporta que saiu da fila de espera para entrar no autocarro.
     */
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

    /**
     * Reportar o estado dos assentos do autocarro.
     * 
     * Invocador: Passageiro
     * 
     * <p>Passageiro, após sair do autocarro no terminal de partida, reporta o estado
     * do mesmo.
     * @param seats assentos do autocarro
     */
    @Override
    public synchronized void autocarroState(int[] seats) {
        System.arraycopy(seats, 0, this.assentos, 0, seats.length);
        reportStatus();
    }
    
    /**
     * Fechar a stream
     */
    public synchronized void close(){
        out.close();
    }

}
