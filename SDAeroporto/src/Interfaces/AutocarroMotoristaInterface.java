package Interfaces;

/**
 * Identifica e descreve as operações que o motorista pode realizar sobre o monitor 
 * <b>Autocarro</b>
 *
 * @author rafael
 */
public interface AutocarroMotoristaInterface {
    
    /**
     * Invocador: Motorista
     * 
     * Motorista espera que todos os passageiros entrem no autocarro para poder seguir
     * @param bilhetesvendidos - Número de bilhetes vendidos. Numero de passageiros que está à espera
     */

    public void announcingBusBoardingWaiting(int bilhetesvendidos);

    /**
     * Invocador: Motorista
     * 
     * Motorista conduz os passageiros para o proximo aeroporto.
     * 
     */
    public void goToDepartureTerminal();

    /**
     * Invocador: Motorista
     * 
     * Motorista retorna ao aeroporto de chegada.
     */
    public void goToArrivalTerminal();

    /**
     * Invocador: Motorista
     * 
     * Motorista estaciona o autocarro no aeroporto de chegada.
     */
    public void parkTheBus();

     /**
     * Invocador: Motorista
     * 
     * Motorista estaciona o autocarro e larga os passageiros, ele bloqueia
     * até que o ultimo passageiro saia do Autocarro e o acorde.
     */
    public void parkTheBusAndLetPassOff();
}
