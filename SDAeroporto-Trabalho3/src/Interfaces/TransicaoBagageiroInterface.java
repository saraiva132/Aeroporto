package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Identifica e descreve as operações que o bagageiro pode realizar sobre o monitor 
 * <b>TransicaoAeroporto</b>
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface TransicaoBagageiroInterface extends Remote{
    /**
     * Anunciar que já recolheu todas as malas do porão do avião.     * 
     * <p>
     * Invocador: Bagageiro
     * <p>
     * Bagageiro dirige-se anuncia aos passageiros de que já acabou de recolher 
     * as malas do porão do avião em que chegaram e que, assim sendo, eles podem sair do aeroporto
     */
    public void bagageiroDone() throws RemoteException;
    
     public boolean shutdownMonitor() throws RemoteException;
     
}
