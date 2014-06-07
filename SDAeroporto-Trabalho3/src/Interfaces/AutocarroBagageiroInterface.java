package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Identifica e descreve as operações que o bagageiro pode realizar sobre o monitor 
 * <b>Autocarro</b>.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface AutocarroBagageiroInterface extends Remote{
    /**
     * Fechar o monitor Autocarro.
     * <p>
     * Invocador: Bagageiro
     * <p>
     * O bagageiro após concluir o seu ciclo de vida invoca a operação para fechar o monitor <i>Autocarro</i>.
     * 
     * @throws RemoteException 
     */
    public void shutdownMonitor() throws RemoteException;
}
