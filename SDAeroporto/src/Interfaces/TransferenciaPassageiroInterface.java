package Interfaces;

/**
 * Identifica e descreve as operações que um passageiro pode realizar sobre o 
 * monitor <b>TransferenciaTerminal</b>
 * 
 * @author rafael
 */
public interface TransferenciaPassageiroInterface {
    
    /**
     * Invocador: Passageiro
     * 
     * Apanhar o autocarro
     * 
     * O passageiro anuncia que pretende apanhar o autocarro. Coloca-se na fila,
     * sendo-lhe atribuído um ticket com a posição em que se deverá sentar no 
     * autocarro. Por fim, espera até que seja a sua vez de entrar no autocarro
     * 
     * @param passageiroID identificador do passageiro
     * @return posição do seu assento no autocarro
     */
    public int takeABus(int passageiroID);
}
