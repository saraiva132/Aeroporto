package Interfaces;

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
     * @return 
     * <ul>
     * <li>TRUE, se o dia de trabalho acabou
     * <li>FALSE, caso contrário
     * </ul>
     */
    public boolean hasDaysWorkEnded() throws RemoteException;
    
   /**
    * Anunciar início de viagem
    * <p>
     * Invocador - Motorista.
     * <p>
     * Motorista anuncia que a viagem vai começar, acorda um passageiro e adormece.
     * O objectivo deste método é chamar um passageiro de cada vez por ordem de chegada
     * na fila de espera. Entrada ordenada!
     * 
     * @return Número de passageiros que tomaram interesse em participar na viagem
     * (limitado à lotação do Autocarro)
     */
    public int announcingBusBoardingShouting() throws RemoteException;
    
    public boolean shutdownMonitor() throws RemoteException;
}
