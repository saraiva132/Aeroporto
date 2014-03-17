package sdaeroporto;

import static Estruturas.AuxInfo.lotação;
import Interfaces.AutocarroMotoristaInterface;
import Interfaces.AutocarroPassageiroInterface;
import Interfaces.LoggingPassageiroInterface;

/**
 * Monitor que simula a interacção entre os passageiros e o motorista no âmbito
 * da viagem de autocarro entre os terminais de chegada e partida
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Autocarro implements AutocarroMotoristaInterface, AutocarroPassageiroInterface {

    /**
     * Número de passageiros correntemente no autocarro
     * 
     * @serialField nOcupantes
     */
    private int nOcupantes;
    
    /**
     * Conjunto de bancos do autocarro. 
     * <p>Número do passageiro se ocupado. -1 se vazio.
     * 
     * @serialField seat
     */
    private int[] seat;
    
    /**
     * Identifica se a viagem até ao terminal de partida já acabou ou não
     * 
     * <p>TRUE caso tenha acabado e os passageiros possam sair do autocarro, 
     * FALSE caso contrário
     * 
     * @serialField hasEnded
     */
    private boolean hasEnded;
    
    /**
     * Identifica quantos passageiros o autocarro irá levar na viagem do terminal 
     * de chegada para o terminal de partida
     * 
     * @serialField bilhetesVendidos
     */
    private int bilhetesVendidos;

    /**
     * Instanciação e inicialização do monitor <b>Autocarro</b>
     */
    public Autocarro() {
        hasEnded = false;
        bilhetesVendidos = 0;
        nOcupantes = 0;
        seat = new int[lotação];
        for (int i = 0; i < lotação; i++) {
            seat[i] = -1; // Autocarro inicialmente encontra-se vazio.
        }
    }

    /**
     * Entrar no autocarro
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro entra no autocarro de forma ordenada e senta-se no assento a 
     * que corresponde o seu ticket.
     * O último passageiro a entrar anuncia ao motorista que já se sentou e 
     * espera que o motorista o leve até à zona de transferência do terminal de 
     * partida.
     * 
     * @param log
     * @param ticketID lugar onde o passageiro se pode sentar
     * @param passID
     */
    @Override
    public synchronized void enterTheBus(Logging log,int ticketID,int passID) {
        //System.out.println("Entering the bus motha focka.Bilhete: " + ticketID + " Bilhetes vendidos: " + bilhetes);
        nOcupantes++;
        seat[ticketID] = passID+1;
        log.autocarroState(seat);
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
     * @param log
     * @param ticketID lugar onde o passageiro estava sentado 
     */
    @Override
    public synchronized void leaveTheBus(Logging log,int ticketID) {
        //System.out.println("IM OUT!Shitty bus");
        while (!hasEnded) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        nOcupantes--;
        seat[ticketID] = -1;
        log.autocarroState(seat);
        if (nOcupantes == 0) {
            notify();
        }
    }

    /**
     * Esperar que passageiros entrem no autocarro
     * <p>
     * Invocador: Motorista
     *<p>
     * Motorista espera que todos os passageiros entrem no autocarro para poder
     * seguir
     *
     * @param bilhetesvendidos - Número de bilhetes vendidos
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
        //System.out.println("Go to next leg");
        //Estado de transição. Fazer o quê mesmo?
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
        //System.out.println("Lets get back guys");
        //Estado de transição. Fazer o quê mesmo?
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
        //Transição
        //System.out.println("Parking..");
    }

    /**
      * Estacionar o autocarro no terminal de partida
      * <p>
     * Invocador: Motorista
     * <p>
     * Motorista estaciona o autocarro e larga os passageiros, ele bloqueia
     * até que o ultimo passageiro saia do Autocarro e o acorde.
     */
    @Override
    public synchronized void parkTheBusAndLetPassOff() {
        //System.out.println("OUT OUT OUT!");
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

}
