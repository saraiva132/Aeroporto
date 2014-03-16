package sdaeroporto;

import static Estruturas.AuxInfo.lotação;
import Interfaces.AutocarroMotoristaInterface;
import Interfaces.AutocarroPassageiroInterface;

/**
 * Monitor que simula a interacção entre os passageiros e o motorista no âmbito 
 * da viagem de autocarro entre os terminais de chegada e partida
 * 
 * @author rafael
 */
public class Autocarro implements AutocarroMotoristaInterface, AutocarroPassageiroInterface {

    private int nOcupantes;
    private int bilhetes;
    private boolean[] seat;
    private boolean hasEnded;

    public Autocarro() {
        hasEnded = false;
        bilhetes = 0;
        nOcupantes = 0;
        seat = new boolean[lotação];
        for (int i = 0; i < lotação; i++) {
            seat[i] = false; // Autocarro inicialmente encontra-se vazio.
        }
    }

    /**
     * Invocador: Passageiro
     * Entrar no autocarro
     * 
     * O passageiro entra no autocarro de forma ordenada e senta-se no assento a 
     * que corresponde o seu ticket.
     * 
     * Anuncia ao motorista que já se sentou e espera que o motorista o leve até
     * à zona de transferência do terminal de partida.
     * 
     * @param ticketID lugar onde o passageiro se pode sentar
     */
    @Override
    public synchronized void enterTheBus(int ticketID) {
        //System.out.println("Entering the bus motha focka.Bilhete: " + ticketID + " Bilhetes vendidos: " + bilhetes);
        nOcupantes++;
        seat[ticketID] = true;
        
        notifyAll();

        while (!hasEnded) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
    }

    /**
     * Invocador: Passageiro
     * Sair do autocarro
     * 
     * O passageiro sai do autocarro e caso seja o último a sair notifica o 
     * motorista de que já não há mais ninguém no autocarro.
     * 
     * @param ticketID lugar onde o passageiro estava sentado 
     */
    @Override
    public synchronized void leaveTheBus(int ticketID) {
        //System.out.println("IM OUT!Shitty bus");
        nOcupantes--;
        seat[ticketID] = false;
        if (nOcupantes == 0) {
            notify();
        }
    }
    /**
     * Invocador: Motorista
     * 
     * Motorista espera que todos os passageiros entrem no autocarro para poder seguir
     * @param bilhetesVendidos - Número de bilhetes vendidos. Numero de passageiros que está à espera
     */
    @Override
    public synchronized void announcingBusBoardingWaiting(int bilhetesVendidos) {

       // System.out.println("All Aboard V2: bilhetes - " + bilhetesVendidos);

        while (nOcupantes < bilhetesVendidos) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
    }

    /**
     * Invocador: Motorista
     * 
     * Motorista conduz os passageiros para o proximo aeroporto.
     * 
     */
    @Override
    public void goToDepartureTerminal() {
        //System.out.println("Go to next leg");
        //Estado de transição. Fazer o quê mesmo?
    }

    /**
     * Invocador: Motorista
     * 
     * Motorista retorna ao aeroporto de chegada.
     */
    @Override
    public void goToArrivalTerminal() {
        //System.out.println("Lets get back guys");
        //Estado de transição. Fazer o quê mesmo?
    }

    /**
     * Invocador: Motorista
     * 
     * Motorista estaciona o autocarro no aeroporto de chegada.
     */
    @Override
    public void parkTheBus() {
        //Transição
        //System.out.println("Parking..");
    }

    /**
     * Invocador: Motorista
     * 
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
