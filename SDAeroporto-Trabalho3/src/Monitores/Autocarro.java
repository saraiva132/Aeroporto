package Monitores;

import Estruturas.Globals;
import static Estruturas.Globals.*;
import Estruturas.ShutdownException;
import Interfaces.AutocarroInterface;
import Interfaces.LoggingInterface;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdaeroporto.AutocarroMain;

/**
 * Monitor que simula a interacção entre os passageiros e o motorista no âmbito
 * da viagem de autocarro entre os terminais de chegada e partida
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Autocarro implements AutocarroInterface {

    /**
     * Número de passageiros correntemente no autocarro
     *
     * @serialField nOcupantes
     */
    private int nOcupantes;

    /**
     * Conjunto de bancos do autocarro.
     * <p>
     * Número do passageiro se ocupado. 0 se vazio.
     *
     * @serialField seat
     */
    private int[] seat;

    /**
     * Identifica se a viagem até ao terminal de partida já acabou ou não
     * <ul>
     * <li>TRUE caso tenha acabado e os passageiros possam sair do autocarro,
     * <li>FALSE caso contrário
     * </ul>
     *
     * @serialField hasEnded
     */
    private boolean hasEnded;

    /**
     * Identifica quantos passageiros o autocarro irá levar na viagem do
     * terminal de chegada para o terminal de partida
     *
     * @serialField bilhetesVendidos
     */
    private int bilhetesVendidos;

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

    private final AutocarroMain auto;
    /**
     * Instanciação e inicialização do monitor <b>Autocarro</b>
     */
    public Autocarro(LoggingInterface log,AutocarroMain auto) {
        hasEnded = false;
        bilhetesVendidos = 0;
        nOcupantes = 0;
        seat = new int[lotação];
        for (int i = 0; i < lotação; i++) {
            seat[i] = 0; // Autocarro inicialmente encontra-se vazio.
        }
        three_entities_ended = 0;
        this.log = log;
        this.auto = auto;
    }

    /**
     * Entrar no autocarro
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro entra no autocarro de forma ordenada e senta-se no assento a
     * que corresponde o seu ticket. O último passageiro a entrar anuncia ao
     * motorista que já se sentou e espera que o motorista o leve até à zona de
     * transferência do terminal de partida.
     *
     * @param ticketID lugar onde o passageiro se pode sentar
     * @param passageiroId identificador do passageiro
     */
    @Override
    public synchronized void enterTheBus(int ticketID, int passageiroId) {
        //System.out.println("Entering the bus motha focka.Bilhete: " + ticketID + " Bilhetes vendidos: " + bilhetes);
        nOcupantes++;

        seat[ticketID] = passageiroId + 1;
        try {
            log.autocarroState(seat);
            log.reportState(passageiroId, Globals.passState.TERMINAL_TRANSFER);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
        if (nOcupantes == bilhetesVendidos) {
            notifyAll();
        }
    }

    /**
     * Sair do autocarro
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro sai do autocarro e caso seja o último a sair notifica o
     * motorista de que já não há mais ninguém no autocarro.
     *
     * @param ticketID lugar onde o passageiro estava sentado
     */
    @Override
    public synchronized void leaveTheBus(int passageiroId, int ticketID) {
        //System.out.println("IM OUT!Shitty bus");
        while (!hasEnded) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        nOcupantes--;
        seat[ticketID] = 0;
        try {
            log.autocarroState(seat);
            log.reportState(passageiroId, Globals.passState.AT_THE_DEPARTURE_TRANSFER_TERMINAL);
        } catch (RemoteException e) {
            System.exit(1);
        }
        if (nOcupantes == 0) {
            notify();
        }
    }

    /**
     * Esperar que passageiros entrem no autocarro
     * <p>
     * Invocador: Motorista
     * <p>
     * Motorista espera que todos os passageiros entrem no autocarro para poder
     * seguir
     *
     * @param bilhetesvendidos - Número de bilhetes vendidos (corresponde ao
     * número de passageiros que estão à espera)
     */
    @Override
    public synchronized void announcingBusBoardingWaiting(int bilhetesvendidos) {

        // System.out.println("All Aboard V2: bilhetes - " + bilhetesVendidos);
        this.bilhetesVendidos = bilhetesvendidos;
        while (nOcupantes < bilhetesVendidos) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
    }

    /**
     * Dirigir-se ao terminal de partida
     * <p>
     * Invocador: Motorista
     * <p>
     * Motorista conduz os passageiros para o proximo terminal.
     *
     */
    @Override
    public void goToDepartureTerminal() {
        try {
            log.reportState(Globals.motState.DRIVING_FORWARD);
        } catch (RemoteException e) {
            System.exit(1);
        }
    }

    /**
     * Dirigir-se ao terminal de chegada
     * <p>
     * Invocador: Motorista
     * <p>
     * Motorista retorna ao terminal de chegada.
     */
    @Override
    public void goToArrivalTerminal() {
        try {
            log.reportState(Globals.motState.DRIVING_BACKWARD);
        } catch (RemoteException e) {
            System.exit(1);
        }
    }

    /**
     * Estacionar o autocarro no terminal de chegada
     * <p>
     * Invocador: Motorista
     * <p>
     * Motorista estaciona o autocarro no terminal de chegada.
     */
    @Override
    public void parkTheBus() {
        try {
            log.reportState(Globals.motState.PARKING_AT_THE_ARRIVAL_TERMINAL);
        } catch (RemoteException e) {
            System.exit(1);
        }
    }

    /**
     * Estacionar o autocarro no terminal de partida
     * <p>
     * Invocador: Motorista
     * <p>
     * Motorista estaciona o autocarro e larga os passageiros, ele bloqueia até
     * que o ultimo passageiro saia do Autocarro e o acorde.
     */
    @Override
    public synchronized void parkTheBusAndLetPassOff() {
        //System.out.println("OUT OUT OUT!");
        try {
            log.reportState(Globals.motState.PARKING_AT_THE_DEPARTURE_TERMINAL);
        } catch (RemoteException e) {
            System.exit(1);
        }
        hasEnded = true;
        notifyAll();
        try {
            while (nOcupantes > 0) {
                wait();
            }
        } catch (InterruptedException ex) {
        }
        hasEnded = false;
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
     * @throws Estruturas.ShutdownException
     */
    public synchronized void shutdownMonitor() {
        if (++three_entities_ended >= 3) {
            auto.close();
        }
    }

}
