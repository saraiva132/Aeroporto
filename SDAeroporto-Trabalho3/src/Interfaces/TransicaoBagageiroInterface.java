package Interfaces;

import Estruturas.VectorCLK;
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
     * @param vc relógio vectorial do bagageiro
     * @return Relógio vectorial actualizado
     * @throws java.rmi.RemoteException
     */
    public VectorCLK bagageiroDone(VectorCLK vc) throws RemoteException;
    
    /**
     * Fechar o monitor TransicaoAeroporto.
     * <p>
     * Invocador: Bagageiro
     * <p>
     * O bagageiro, após concluir o seu ciclo de vida invoca a operação para fechar o monitor <i>TransicaoAeroporto</i>.
     * 
     * @throws RemoteException
     */
     public void shutdownMonitor() throws RemoteException;
     
}
