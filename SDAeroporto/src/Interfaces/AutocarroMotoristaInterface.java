package Interfaces;

/**
 * Identifica e descreve as operações que o motorista pode realizar sobre o monitor 
 * <b>Autocarro</b>
 *
 * @author rafael
 */
public interface AutocarroMotoristaInterface {
    /**
     * Announcing bus boarding
     * 
     * 
     * 
     * @param bilhetesVendidos 
     */

    public void announcingBusBoardingWaiting(int bilhetesVendidos);

    /**
     * Motorista leva os passageiros para terminal de partida
     */
    public void goToDepartureTerminal();

    /**
     * Motorista volta ao terminal de chegadas
     */
    public void goToArrivalTerminal();

    /**
     * Motorista estaciona o autocarro
     */
    public void parkTheBus();

    /**
     * Motorista estaciona o autocarro e larga os passageiros
     */
    public void parkTheBusAndLetPassOff();
}
