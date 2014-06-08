package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Identifica e descreve as operações que o motorista pode realizar sobre o
 * monitor <b>Porão</b>.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface PoraoMotoristaInterface extends Remote{
    
    
    /**
     * Fechar o monitor Porao.
     * <p>
     * Invocador: Motorista
     * <p>
     * O Motorista, após concluir o seu ciclo de vida invoca a operação para fechar o monitor <i>Porao</i>.
     * 
     * @throws RemoteException 
     */
    public void shutdownMonitor() throws RemoteException;
}
