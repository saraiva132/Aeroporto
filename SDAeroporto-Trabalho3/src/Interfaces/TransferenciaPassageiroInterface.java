package Interfaces;

import Estruturas.Reply;
import Estruturas.VectorCLK;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Identifica e descreve as operações que um passageiro pode realizar sobre o 
 * monitor <b>TransferenciaTerminal</b>
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface TransferenciaPassageiroInterface extends Remote{
    
    /**
     * Apanhar o autocarro
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro anuncia que pretende apanhar o autocarro. Coloca-se na fila,
     * sendo-lhe atribuído um ticket com a posição em que se deverá sentar no 
     * autocarro. Por fim, espera até que seja a sua vez de entrar no autocarro
     * 
     * @param passageiroID identificador do passageiro
     * @return Posição do seu assento no autocarro
     * @throws java.rmi.RemoteException
     */
    public Reply takeABus(VectorCLK vc, int passageiroID) throws RemoteException;
    
    public void setnVoo(int nvoo, int transit) throws RemoteException;
    
    public void shutdownMonitor() throws RemoteException;
}
