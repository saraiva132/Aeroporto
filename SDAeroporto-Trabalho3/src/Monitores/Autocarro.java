package Monitores;

import Estruturas.Globals;
import static Estruturas.Globals.MON_AUTOCARRO;
import static Estruturas.Globals.lotação;
import Estruturas.VectorCLK;
import Interfaces.AutocarroInterface;
import Interfaces.LoggingInterface;
import Registry.AutocarroRegister;
import java.rmi.RemoteException;

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

    /**
     * Instância do tipo de dados AutocarroRegister
     *
     * @serialField auto
     */
    private final AutocarroRegister auto;

    /**
     * Instância do tipo de dados VectorCLK.
     *
     * @serialField v_clock
     */
    private VectorCLK v_clock;

    /**
     * Instanciação e inicialização do monitor <b>Autocarro</b>
     *
     * @param log rererência para o objecto remoto correspondente ao monitor de
     * Logging
     * @param auto referência para o tipo de dados AutocarroRegister
     */
    public Autocarro(LoggingInterface log, AutocarroRegister auto) {
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
        v_clock = new VectorCLK();
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
     * @param ts relógio vectorial do passageiro
     * @param ticketID lugar onde o passageiro se pode sentar
     * @param passageiroId identificador do passageiro
     * @return relógio vectorial actualizado
     */
    @Override
    public synchronized VectorCLK enterTheBus(VectorCLK ts, int ticketID, int passageiroId) {
        //System.out.println("Entering the bus motha focka.Bilhete: " + ticketID + " Bilhetes vendidos: " + bilhetes);
        v_clock.CompareVector(ts.getVc());
        nOcupantes++;

        seat[ticketID] = passageiroId + 1;
        try {
            log.UpdateVectorCLK(v_clock, MON_AUTOCARRO);
            log.autocarroState(seat);
            log.reportState(passageiroId, Globals.passState.TERMINAL_TRANSFER);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
        if (nOcupantes == bilhetesVendidos) {
            notifyAll();
        }
        return new VectorCLK(v_clock);
    }

    /**
     * Sair do autocarro
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro sai do autocarro e caso seja o último a sair notifica o
     * motorista de que já não há mais ninguém no autocarro.
     *
     * @param ts relógio vectorial do passageiro
     * @param passageiroId identificador do passageiro
     * @param ticketID lugar onde o passageiro estava sentado
     * @return relógio vectorial actualizado
     */
    @Override
    public synchronized VectorCLK leaveTheBus(VectorCLK ts, int passageiroId, int ticketID) {
        //System.out.println("IM OUT!Shitty bus");
        v_clock.CompareVector(ts.getVc());
        while (!hasEnded) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        nOcupantes--;
        seat[ticketID] = 0;
        try {
            log.UpdateVectorCLK(v_clock, MON_AUTOCARRO);
            log.autocarroState(seat);
            log.reportState(passageiroId, Globals.passState.AT_THE_DEPARTURE_TRANSFER_TERMINAL);
        } catch (RemoteException e) {
            System.exit(1);
        }
        if (nOcupantes == 0) {
            notify();
        }
        return new VectorCLK(v_clock);
    }

    /**
     * Esperar que passageiros entrem no autocarro
     * <p>
     * Invocador: Motorista
     * <p>
     * Motorista espera que todos os passageiros entrem no autocarro para poder
     * seguir
     *
     * @param ts relógio vectorial do motorista
     * @param bilhetesvendidos Número de bilhetes vendidos (corresponde ao
     * número de passageiros que estão à espera)
     * @return relógio vectorial actualizado
     */
    @Override
    public synchronized VectorCLK announcingBusBoardingWaiting(VectorCLK ts, int bilhetesvendidos) {

        // System.out.println("All Aboard V2: bilhetes - " + bilhetesVendidos);
        v_clock.CompareVector(ts.getVc());
        try {
            log.UpdateVectorCLK(v_clock, MON_AUTOCARRO);
        } catch (RemoteException ex) {
            System.exit(1);
        }
        this.bilhetesVendidos = bilhetesvendidos;
        while (nOcupantes < bilhetesVendidos) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        return new VectorCLK(v_clock);
    }

    /**
     * Dirigir-se ao terminal de partida
     * <p>
     * Invocador: Motorista
     * <p>
     * Motorista conduz os passageiros que se encontram em trânsito para o
     * terminal de partida.
     *
     * @param ts relógio vectorial do motorista
     * @return relógio vectorial actualizado
     */
    @Override
    public VectorCLK goToDepartureTerminal(VectorCLK ts) {
        v_clock.CompareVector(ts.getVc());
        try {
            log.UpdateVectorCLK(v_clock, MON_AUTOCARRO);
            log.reportState(Globals.motState.DRIVING_FORWARD);
        } catch (RemoteException e) {
            System.exit(1);
        }
        return new VectorCLK(v_clock);
    }

    /**
     * Dirigir-se ao terminal de chegada
     * <p>
     * Invocador: Motorista
     * <p>
     * Motorista retorna ao terminal de chegada.
     *
     * @param ts relógio vectorial do motorista
     * @return relógio vectorial actualizado
     */
    @Override
    public VectorCLK goToArrivalTerminal(VectorCLK ts) {
        v_clock.CompareVector(ts.getVc());
        try {
            log.UpdateVectorCLK(v_clock, MON_AUTOCARRO);
            log.reportState(Globals.motState.DRIVING_BACKWARD);
        } catch (RemoteException e) {
            System.exit(1);
        }
        return new VectorCLK(v_clock);
    }

    /**
     * Estacionar o autocarro no terminal de chegada
     * <p>
     * Invocador: Motorista
     * <p>
     * Motorista estaciona o autocarro no terminal de chegada.
     *
     * @param ts relógio vectorial do motorista
     * @return relógio vectorial actualizado
     */
    @Override
    public VectorCLK parkTheBus(VectorCLK ts) {
        v_clock.CompareVector(ts.getVc());
        try {
            log.UpdateVectorCLK(v_clock, MON_AUTOCARRO);
            log.reportState(Globals.motState.PARKING_AT_THE_ARRIVAL_TERMINAL);
        } catch (RemoteException e) {
            System.exit(1);
        }
        return new VectorCLK(v_clock);
    }

    /**
     * Estacionar o autocarro no terminal de partida
     * <p>
     * Invocador: Motorista
     * <p>
     * Motorista estaciona o autocarro e larga os passageiros; bloqueia até que
     * o último passageiro saia do autocarro e o acorde.
     *
     * @param ts relógio vectorial do motorista
     * @return relógio vectorial actualizado
     */
    @Override
    public synchronized VectorCLK parkTheBusAndLetPassOff(VectorCLK ts) {
        v_clock.CompareVector(ts.getVc());
        //System.out.println("OUT OUT OUT!");
        try {
            log.UpdateVectorCLK(v_clock, MON_AUTOCARRO);
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
        return new VectorCLK(v_clock);
    }

    /**
     * Fechar o monitor Autocarro.
     * <p>
     * Invocador: Bagageiro, Passageiro e Motorista
     * <p>
     * O bagageiro/passageiro/motorista após concluir o seu ciclo de vida invoca
     * a operação para fechar o monitor <i>Autocarro</i>.
     */
    @Override
    public synchronized void shutdownMonitor() {
        if (++three_entities_ended >= 3) {
            auto.close();
        }
    }

}
