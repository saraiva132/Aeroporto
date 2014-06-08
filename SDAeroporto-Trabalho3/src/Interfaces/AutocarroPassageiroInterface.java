package Interfaces;

import Estruturas.VectorCLK;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Identifica e descreve as operações que um passageiro pode realizar sobre o monitor 
 * <b>Autocarro</b>
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface AutocarroPassageiroInterface extends Remote{
    
    /**
     * Entrar no autocarro
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro entra no autocarro de forma ordenada e senta-se no assento a 
     * que corresponde o seu ticket.
     * O último passageiro a entrar anuncia ao motorista que já se sentou e 
     * espera que o motorista o leve até à zona de transferência do terminal de 
     * partida.
     * 
     * @param ts relógio vectorial do passageiro
     * @param ticketID lugar onde o passageiro se pode sentar
     * @param passageiroId identificador do passageiro
     * @return relógio vectorial actualizado
     * @throws java.rmi.RemoteException 
     */
    public VectorCLK enterTheBus(VectorCLK ts, int ticketID,int passageiroId) throws RemoteException;
    
    /**
     * Sair do autocarro
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro sai do autocarro e caso seja o último a sair notifica o 
     * motorista de que já não há mais ninguém no autocarro.
     * 
     * @param ts relógio vectorial do passageiro
     * @param passageiroId identificador do passageiro
     * @param ticketID lugar onde o passageiro estava sentado 
     * @return  relógio vectorial actualizado
     * @throws java.rmi.RemoteException  
     */
    
    public VectorCLK leaveTheBus(VectorCLK ts, int passageiroId, int ticketID) throws RemoteException;

    /**
     * Fechar o monitor Autocarro.
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro, após concluir o seu ciclo de vida invoca a operação para fechar o monitor <i>Autocarro</i>.
     * 
     * @throws RemoteException
     */
    public void shutdownMonitor() throws RemoteException;        
}