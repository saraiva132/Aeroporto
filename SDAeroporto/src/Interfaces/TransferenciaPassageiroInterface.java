package Interfaces;

/**
 * Identifica e descreve as operações que um passageiro pode realizar sobre o 
 * monitor <b>TransferenciaTerminal</b>
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface TransferenciaPassageiroInterface {
    
    /**
     * Apanhar o autocarro
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro anuncia que pretende apanhar o autocarro. Coloca-se na fila,
     * sendo-lhe atribuído um ticket com a posição em que se deverá sentar no 
     * autocarro. Por fim, espera até que seja a sua vez de entrar no autocarro
     * 
     * @param log referência para o monitor de logging; utilizado para reportar a evolução do estado global do problema
     * @param passageiroID identificador do passageiro
     * @return Posição do seu assento no autocarro
     */
    public int takeABus(LoggingPassageiroInterface log,int passageiroID);
}
