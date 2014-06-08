package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Identifica e descreve as operações que o motorista pode realizar sobre o
 * monitor <b>TransicaoAeroporto</b>
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface TransicaoMotoristaInterface extends Remote {

    /**
     * Fechar o monitor TransicaoAeroporto.
     * <p>
     * Invocador: Motorista
     * <p>
     * O motorista, após concluir o seu ciclo de vida invoca a operação para
     * fechar o monitor <i>TransicaoAeroporto</i>.
     *
     * @throws RemoteException
     */
    public void shutdownMonitor() throws RemoteException;
}
