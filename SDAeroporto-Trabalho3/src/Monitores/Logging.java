package Monitores;

import Estruturas.Globals.*;
import static Estruturas.Globals.fileName;
import static Estruturas.Globals.lotação;
import static Estruturas.Globals.nChegadas;
import static Estruturas.Globals.passMax;
import Interfaces.LoggingInterface;
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
public class Logging implements LoggingInterface {

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

    /**
     * Número de total de passageiros em trânsito
     *
     * @serialField nTotalPassageirosTransito
     */
    private int nTotalPassageirosTransito;

    /**
     * Número de total de passageiros cujo destino final corresponde a este
     * aeroporto
     *
     * @serialField nTotalPassageirosFinal
     */
    private int nTotalPassageirosFinal;

    /**
     * Número total de malas carregadas pelo bagageiro para a sala de
     * armazenamento temporário
     *
     * @serialField nTotalMalasStoreroom
     */
    private int nTotalMalasStoreroom;

    /**
     * Número total de malas carregadas pelo bagageiro para a passadeira de
     * recolha de bagagens
     *
     * @serialField nTotalMalasBelt
     */
    private int nTotalMalasBelt;

    /**
     * Número total de malas perdidas pelos passageiros
     *
     * @serialField nTotalMalasPerdidas
     */
    private int nTotalMalasPerdidas;

    /**
     * Identifica quantas entidades activas instanciadas (passageiro, bagageiro e motorista)
     * já terminaram o seu ciclo de vida.
     * <p>
     * Necessário para a terminação do monitor.
     * 
     * @serialField three_entities_ended
     */
    private int three_entities_ended;

    private PrintStream fic;

    /**
     * Instanciação e inicialização do monitor <b>Logging</b>
     *
     */
    public Logging() {
        try {
            fic = new PrintStream(new FileOutputStream(fileName, false));
        } catch (FileNotFoundException ex) {

        }
        System.setOut(fic);
        pstate = new passState[passMax];
        fila = new int[passMax];
        nMalasTotal = new int[passMax];
        nMalasActual = new int[passMax];
        passDest = new String[passMax];
        assentos = new int[lotação];
        nTotalPassageirosTransito = 0;
        nTotalPassageirosFinal = 0;
        nTotalMalasStoreroom = 0;
        nTotalMalasBelt = 0;
        nTotalMalasPerdidas = 0;
        bstate = bagState.WAITING_FOR_A_PLANE_TO_LAND;
        mstate = motState.PARKING_AT_THE_ARRIVAL_TERMINAL;
        three_entities_ended = 3;
    }

    /**
     * Auxilia na inicialização do logging
     */
    public synchronized void reportInitialStatus() {

        fic.println("|PLANE |   PORTER       DRIVER                                       PASSENGERS");
        fic.print("|FN  BN| Stat CB SR     Stat      ");
        for (int i = 0; i < passMax; i++) {
            fic.printf("Q%s", i);
        }
        fic.print("             ");
        for (int i = 0; i < lotação; i++) {
            fic.printf("S%s", i);
        }
        fic.print("     ");
        for (int i = 0; i < passMax; i++) {
            fic.print("St" + i + " Si" + i + " NR" + i + " NA" + i + "|");
        }
        fic.println();

        for (int i = 0; i < passMax; i++) {
            pstate[i] = passState.AT_THE_DISEMBARKING_ZONE;
            nMalasActual[i] = 0;
            fila[i] = 0;
        }
        for (int i = 0; i < lotação; i++) {
            assentos[i] = 0;
        }
        nMalasStore = 0;
        nMalasBelt = 0;
        reportStatus();
    }

    /**
     * Auxilia a reportar uma actualização do estado geral do problema
     */
    private synchronized void reportStatus() {

        fic.printf("|%2s %3s|%4s %3s %3s | %4s fila: [", nVoo, nMalasPorao, bstate.toString(), nMalasBelt, nMalasStore, mstate.toString());
        for (int i = 0; i < fila.length; i++) {
            fic.printf("%1d ", fila[i]);
        }

        fic.print("] autocarro: [");
        for (int i = 0; i < assentos.length; i++) {
            fic.printf("%1d ", assentos[i]);
        }
        fic.print("]  ");
        for (int i = 0; i < passMax; i++) {
            fic.printf("%3s %3s  %1s  %2s |", pstate[i].toString(), passDest[i], nMalasTotal[i], nMalasActual[i]);
        }

        fic.println();
    }

    /**
     * Reportar mudança de estado.
     *
     * <p>
     * Invocador: Passageiro
     *
     * <p>
     * O passageiro reporta a mudança do seu estado.
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
     * <p>
     * Motorista reporta mudança do seu estado.
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
     * <p>
     * Invocador: Bagageiro
     *
     * <p>
     * Bagageiro reporta mudança do seu estado
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
    }

    /**
     * Definir o número de malas que vem no porão do avião que acabou de aterrar
     *
     * @param nMalasPorao número de malas no porão
     */
    public synchronized void setPorao(int nMalasPorao) {
        this.nMalasPorao = nMalasPorao;
    }

    /**
     * Reportar retirada de uma mala do porão.
     *
     * <p>
     * Invocador: Bagageiro
     *
     * <p>
     * Bagageiro reporta que retirou uma mala do porão do avião.
     */
    @Override
    public synchronized void bagagemPorao() {
        this.nMalasPorao--;
        reportStatus();
    }

    /**
     * Reportar retiro de bagagem.
     *
     * <p>
     * Invocador: Passageiro, Bagageiro
     *
     * <p>
     * Passageiro reporta que tirou uma bagagem sua da passadeira de recolha de
     * bagagens.
     * <p>
     * Bagageiro reporta que colocou uma bagagem na passadeira de recolha de
     * bagagens.
     *
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
            nTotalMalasBelt++;
        }
        reportStatus();
    }

    /**
     * Reportar depósito de bagagem.
     *
     * <p>
     * Invocador: Bagageiro
     *
     * <p>
     * Bagageiro reporta que colocou uma bagagem na zona de armazenamento
     * temporário de bagagens.
     */
    @Override
    public synchronized void bagagemStore() {
        this.nMalasStore++;
        nTotalMalasStoreroom++;
        reportStatus();
    }

    /**
     * Reportar número de malas em posse.
     *
     * <p>
     * Invocador: Passageiro
     *
     * <p>
     * Passageiro reporta quantas malas tem em sua posse.
     *
     * @param passID identificador do passageiro
     */
    @Override
    public synchronized void malasActual(int passID) {
        this.nMalasActual[passID]++;
        reportStatus();
    }

    /**
     * Reportar número de malas total.
     *
     * <p>
     * No início de cada simulação é necessário reportar o número de malas total
     * de cada passageiro.
     *
     * @param nMalas número de malas totais que petencem aos passageiros
     */
    public synchronized void malasInicial(int[] nMalas) {
        System.arraycopy(nMalas, 0, nMalasTotal, 0, passMax);
    }

    /**
     * Reportar tipo de passageiro.
     *
     * <p>
     * No início de cada simulação é necessário reportar o estado dos
     * passageiros: se estão em trânsito ou se este aeroporto corresponde ao seu
     * destino
     *
     * @param destino
     * <ul>
     * <li>FALSE caso esteja em trânsito
     * <li>TRUE caso contrário
     * </ul>
     */
    public synchronized void destino(boolean[] destino) {
        for (int i = 0; i < passMax; i++) {
            if (destino[i]) {
                this.passDest[i] = "FDT";
                nTotalPassageirosFinal++;
            } else {
                this.passDest[i] = "TRT";
                nTotalPassageirosTransito++;
            }
        }
    }

    /**
     * Reportar falta de malas
     *
     * <p>
     * Invocador: Passageiro
     *
     * <p>
     * Passageiro antes de sair do aeroporto reporta que perdeu malas
     *
     * @param malasPerdidas número de malas perdidas
     */
    @Override
    public synchronized void missingBags(int malasPerdidas) {
        nTotalMalasPerdidas += malasPerdidas;
    }

    /**
     * Reportar entrada para a fila de espera.
     *
     * <p>
     * Invocador: Passageiro
     *
     * <p>
     * Passageiro reporta que se encontra na fila na zona de transferência entre
     * terminais à espera de entrar para o autocarro.
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
     * <p>
     * Invocador: Passageiro
     *
     * <p>
     * Passageiro reporta que saiu da fila de espera para entrar no autocarro.
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
     * <p>
     * Passageiro, após sair do autocarro no terminal de partida, reporta o
     * estado do mesmo.
     *
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
    private synchronized void close() {
        fic.close();
    }

    /**
     * Terminar o monitor.
     * <p>
     * Invocadores: BagageiroMain, MotoristaMain e PassageiroMain
     * <p>
     * No final da execução da simulação, para o fechar o monitor os 3 lançadores 
     * das threads correspondentes ao passageiro, bagageiro e motorista necessitam de 
     * fechar os monitores.
     * 
     * @return Informação se pode ou não terminar o monitor.
     * <ul>
     * <li>TRUE, caso possa
     * <li>FALSE, caso contrário
     * </ul>
     */
    public synchronized boolean shutdownMonitor() {
        three_entities_ended--;
        if (three_entities_ended == 0) 
        {   fic.println("Número total de chegadas de aviões: " + nChegadas);
            fic.println("\nNúmero total de passageiros: " + (nChegadas * passMax));
            fic.println("\n\tNúmero total de passageiros em trânsito: " + nTotalPassageirosTransito);
            fic.println("\n\tNúmero total de passageiros finais: " + nTotalPassageirosFinal);
            fic.println("\nNúmero total de malas carregadas pelo bagageiro:" + (nTotalMalasStoreroom + nTotalMalasBelt));
            fic.println("\n\tNúmero total de malas carregadas pelo bagageiro para a storeroom: " + nTotalMalasStoreroom);
            fic.println("\n\tNúmero total de malas carregadas pelo bagageiro para a passadeira: " + nTotalMalasBelt);
            fic.println("\n\tNúmero total de malas perdidas pelos passageiros: " + nTotalMalasPerdidas);
            close();
            return true;
        }
        return false;
        
    }
}
