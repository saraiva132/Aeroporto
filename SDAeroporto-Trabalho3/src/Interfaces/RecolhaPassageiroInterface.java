package Interfaces;

import Estruturas.Reply;
import Estruturas.VectorCLK;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Identifica e descreve as operações que um passageiro pode realizar sobre o 
 * monitor <b>RecolhaBagagem</b>
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface RecolhaPassageiroInterface extends Remote{
    
    /**
     * Buscar uma mala
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro desloca-se à zona de recolha de bagagens para ir buscar a
     * sua mala. Espera até que aviste a sua mala na passadeira rolante ou que o
     * bagageiro anuncie que já não existem mais malas no porão do avião.
     * <p>
     * Simula, ainda, se o passageiro consegue ou não apanhar a sua mala de forma
     * bem sucedida.
     * 
     * @param ts relógio vectorial do passageiro
     * @param bagID identificador da mala
     * @return Relógio vectorial actualizado juntamente com a forma como conseguiu apanhar a sua mala: 
     * <ul>
     * <li>MINE, com sucesso 
     * <li>UNSUCCESSFUL, sem sucesso 
     * </ul> 
     * <p>Alternativamente, a informação de que já não vale a 
     * pena continuar a espera da(s) sua(s) mala(s) que lhe falta(m):
     * <ul>
     * <li>NOMORE
     * </ul>
     * @throws java.rmi.RemoteException
     */
    public Reply goCollectABag(VectorCLK ts, int bagID) throws RemoteException;
    
    /**
     * Reportar a falta de mala(s)
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro, após não ter coleccionado todas as suas malas e após o 
     * bagageiro o ter notificado de que já não existem mais malas no porão 
     * desloca-se ao guichet de reclamação do aeroporto para reclamar a falta da(s)
     * sua(s) mala(s)
     * 
     * @param vc relógio vectorial do passageiro
     * @param passageiroID identificador do passageiro
     * @param malasPerdidas número de malas perdidas
     * @return Relógio vectorial actualizado
     * @throws java.rmi.RemoteException
     */
    public VectorCLK reportMissingBags(VectorCLK vc, int passageiroID,int malasPerdidas)throws RemoteException;
    
    /**
     * Função auxiliar utilizada a cada iteração da simulação.
     * <p>
     * Invocador: PassageiroMain
     * <p>
     * A cada ciclo da simulação, o PassageiroMain encarrega-se de fazer reset à variável que identifica que não existem mais malas para recolher na passadeira.
     * @throws RemoteException 
     */
    public void resetNoMoreBags() throws RemoteException;
    
    /**
     * Fechar o monitor RecolhaBagagem.
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro, após concluir o seu ciclo de vida invoca a operação para fechar o monitor <i>RecolhaBagagem</i>.
     * 
     * @throws RemoteException
     */
    public void shutdownMonitor() throws RemoteException;
}
