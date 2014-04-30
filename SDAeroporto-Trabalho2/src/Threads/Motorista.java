package Threads;

import ClientSide.InterfaceMotorista;
import Estruturas.Globals.motState;

/**
 * Identifica o tipo de dados motorista
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Motorista extends Thread {

    /**
     * Identifica o estado do motorista
     * 
     * @serialField state
     */
    private motState state;

    
    private InterfaceMotorista motoristaI = null;
    
    /**
     * Instanciação e inicialização do motorista
     */
    public Motorista() {
        motoristaI = new InterfaceMotorista();
    }

    /**
     * Implementa o ciclo de vida do motorista
     */
    @Override
    public void run() {
        while (!motoristaI.hasDaysWorkEnded()) {
            
                int nTickets = motoristaI.announcingBusBoardingShouting();
                motoristaI.announcingBusBoardingWaiting(nTickets);
                motoristaI.goToDepartureTerminal();
                motoristaI.parkTheBusAndLetPassOff();
                motoristaI.goToArrivalTerminal();  
                motoristaI.parkTheBus();
            
        }
    }
}
