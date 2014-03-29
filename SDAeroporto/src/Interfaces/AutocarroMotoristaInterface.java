package Interfaces;

/**
 * Identifica e descreve as operações que o motorista pode realizar sobre o monitor 
 * <b>Autocarro</b>
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface AutocarroMotoristaInterface {
    
    /**
     * Esperar que passageiros entrem no autocarro
     * <p>
     * Invocador: Motorista
     *<p>
     * Motorista espera que todos os passageiros entrem no autocarro para poder
     * seguir
     *
     * @param bilhetesvendidos Número de bilhetes vendidos (corresponde ao número de
     * passageiros que estão à espera)
     */
    public void announcingBusBoardingWaiting(int bilhetesvendidos);

    /**
     * Dirigir-se ao terminal de partida
     * <p>
     * Invocador: Motorista
     * <p>
     * Motorista conduz os passageiros que se encontram em trânsito para o terminal de partida.
     * 
     * @param log referência para o monitor de logging; utilizado para reportar a evolução do estado global do problema
     */
    public void goToDepartureTerminal(LoggingMotoristaInterface log);

    /**
     * Dirigir-se ao terminal de chegada
     * <p>
     * Invocador: Motorista
     * <p>
     * Motorista retorna ao terminal de chegada.
     * @param log referência para o monitor de logging; utilizado para reportar a evolução do estado global do problema
     */
    public void goToArrivalTerminal(LoggingMotoristaInterface log);

    /**
     * Estacionar o autocarro no terminal de chegada
     * <p>
     * Invocador: Motorista
     * <p>
     * Motorista estaciona o autocarro no terminal de chegada.
     * @param log referência para o monitor de logging; utilizado para reportar a evolução do estado global do problema
     */
    public void parkTheBus(LoggingMotoristaInterface log);

     /**
      * Estacionar o autocarro no terminal de partida
      * <p>
     * Invocador: Motorista
     * <p>
     * Motorista estaciona o autocarro e larga os passageiros; bloqueia
     * até que o último passageiro saia do autocarro e o acorde.
     * @param log referência para o monitor de logging; utilizado para reportar a evolução do estado global do problema
     */
    public void parkTheBusAndLetPassOff(LoggingMotoristaInterface log);
}
