package Interfaces;

import Estruturas.Reply;
import Estruturas.VectorCLK;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *Identifica e descreve as operações que o motorista pode realizar sobre o 
 * monitor <b>TransferênciaTerminal</b>
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface TransferenciaMotoristaInterface extends Remote{

    /**
     * Trabalho já acabou?
     * <p>
     * Invocador - Motorista.
     * <p>
     * Motorista verifica se o trabalho já acabou. É acordado nas seguintes condições:
     * <ul>
     * <li>Se os passageiros na fila de espera, no passeio, cobrem a lotação do autocarro
     * <li>Se a hora de partida chegou.
     * </ul>
     * 
     * O trabalho dele acabou se à hora da partida não se encontrar ninguém no passeio!
     * @param ts relógio vectorial do motorista
     * @return Relógio vectorial actualizado juntamente com
     * <ul>
     * <li>TRUE, se o dia de trabalho acabou
     * <li>FALSE, caso contrário
     * </ul>
     * @throws java.rmi.RemoteException
     */
    public Reply hasDaysWorkEnded(VectorCLK ts) throws RemoteException;
    
   /**
    * Anunciar início de viagem
    * <p>
     * Invocador - Motorista.
     * <p>
     * Motorista anuncia que a viagem vai começar, acorda um passageiro e adormece.
     * O objectivo deste método é chamar um passageiro de cada vez por ordem de chegada
     * na fila de espera. Entrada ordenada!
     * 
     * @param vc relógio vectorial do motorista
     * @return Número de passageiros que tomaram interesse em participar na viagem
     * (limitado à lotação do Autocarro) juntamente com o relógio vectorial actualizado
     * @throws java.rmi.RemoteException
     */
    public Reply announcingBusBoardingShouting(VectorCLK vc) throws RemoteException;
    
    /**
     * Fechar o monitor TransferenciaTerminal.
     * <p>
     * Invocador: Motorista
     * <p>
     * O motorista, após concluir o seu ciclo de vida invoca a operação para fechar o monitor <i>TransferenciaTerminal</i>.
     * 
     * @throws RemoteException
     */
    public void shutdownMonitor() throws RemoteException;
}
