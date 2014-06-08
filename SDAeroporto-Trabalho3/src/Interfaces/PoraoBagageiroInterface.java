package Interfaces;

import Estruturas.Reply;
import Estruturas.VectorCLK;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Identifica e descreve as operações que o bagageiro pode realizar sobre o
 * monitor <b>Porão</b>
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface PoraoBagageiroInterface extends Remote{

    /**
     * Tentar recolher uma mala
     * <p>
     * Invocador: bagageiro
     * <p>
     * O bagageiro desloca-se ao porão do avião e caso este não se encontre
     * vazio recolhe uma mala
     *
     * @param vc relógio vectorial do bagageiro
     * @return Mala que apanhou no porão, ou <i>null</i> caso o porão se encontrar vazio juntamente com relógio vectorial actualizado
     * @throws java.rmi.RemoteException
     */
    public Reply tryToCollectABag(VectorCLK vc) throws RemoteException;
    
    /**
     * Fechar o monitor Porao.
     * <p>
     * Invocador: Bagageiro
     * <p>
     * O Bagageiro, após concluir o seu ciclo de vida invoca a operação para fechar o monitor <i>Porao</i>.
     * 
     * @throws RemoteException 
     */
    public void shutdownMonitor() throws RemoteException ;
}
