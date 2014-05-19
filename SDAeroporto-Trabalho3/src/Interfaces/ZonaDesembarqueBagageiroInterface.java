package Interfaces;

import Estruturas.VectorCLK;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Identifica e descreve as operações que o bagageiro pode realizar sobre o 
 * monitor <b>ZonaDesembarque</b>
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface ZonaDesembarqueBagageiroInterface extends Remote{

    /**
     * Descansar
     * <p>
     * Invocador: Bagageiro
     * <p>
     * O bagageiro descansa enquanto o próximo voo não chega e o último 
     * passageiro não sai do avião
     */
    public VectorCLK takeARest(VectorCLK ts) throws RemoteException;
    
    /**
     * Não existem mais malas para apanhar
     * <p>
     * Invocador: Bagageiro
     * <p>
     * O bagageiro, após verificar que o porão já se encontra vazio, dirige-se à
     * sua sala de espera
     */
    public VectorCLK noMoreBagsToCollect(VectorCLK vc) throws RemoteException;
    
    public void shutdownMonitor() throws RemoteException;
}
