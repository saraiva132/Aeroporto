package Interfaces;

import Estruturas.Mala;
import Estruturas.Reply;
import Estruturas.VectorCLK;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Identifica e descreve as operações que o bagageiro pode realizar sobre o 
 * monitor <b>RecolhaBagagem</b>
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399s
 */
public interface RecolhaBagageiroInterface extends Remote{
    
    /**
     * Transportar uma mala
     * <p>
     * Invocador: Bagageiro
     * <p>
     * O bagageiro transporta uma mala para um determinado local:
     * <ul>
     * <li> para a zona de armazenamento temporário caso a mala pertença a um passageiro 
     * que esteja em trânsito
     * <li> para a passadeira rolante caso pertença a um passageiro cujo destino
     * é este aeroporto, notificando-o de seguida
     * </ul>
     * <p>
     * Caso o objecto mala seja null notifica todos os passageiros de que já 
     * não existem mais malas no porão do avião 
     * 
     * @param vc relógio vectorial do bagageiro
     * @param mala mala que o bagageiro transporta e informação do estado actual do porão
     * @return Relógio vectorial actualizado juntamente com o local para onde levou a mala:
     * <ul>
     * <li>STOREROOM, zona de armazenamento temporário de bagagens
     * <li>BELT, zona de recolha de bagagens
     * </ul>
     * Alternativamente, caso o objecto mala seja um null, a informação de que o porão já se encontra vazio:
     * <ul>
     * <li>LOBBYCLEAN
     * </ul>
     * @throws java.rmi.RemoteException
     */
    public Reply carryItToAppropriateStore(VectorCLK vc, Mala mala) throws RemoteException;
    
    /**
     * Fechar o monitor RecolhaBagagem.
     * <p>
     * Invocador: Bagageiro
     * <p>
     * O bagageiro, após concluir o seu ciclo de vida invoca a operação para fechar o monitor <i>RecolhaBagagem</i>.
     * 
     * @throws RemoteException
     */
     public void shutdownMonitor() throws RemoteException ;
}
