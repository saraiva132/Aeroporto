package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Identifica e descreve as operações que o motorista pode realizar sobre o
 * monitor <b>ZonaDesembarque</b>
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface ZonaDesembarqueMotoristaInterface extends Remote{
    /**
     * Fechar o monitor ZonaDesembarque.
     * <p>
     * Invocador: Motorista
     * <p>
     * O motorista, após concluir o seu ciclo de vida invoca a operação para
     * fechar o monitor <i>ZonaDesembarque</i>.
     *
     * @throws RemoteException
     */
     public void shutdownMonitor() throws RemoteException;
}
