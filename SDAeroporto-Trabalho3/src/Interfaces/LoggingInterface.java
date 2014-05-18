package Interfaces;

import Estruturas.Globals;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Identifica e descreve as operações que o bagageiro pode realizar sobre o monitor <b>Logging</b>
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface LoggingInterface extends Remote{

    /**
     * Reportar mudança de estado.
     * 
     * <p>Invocador: Bagageiro
     * 
     * <p>Bagageiro reporta mudança do seu estado
     * 
     * @param state novo estado do bagageiro
     */
    public void reportState(Globals.bagState state) throws RemoteException;

    /**
     * Reportar retirada de uma mala do porão.
     * 
     * <p>Invocador: Bagageiro
     * 
     * <p>Bagageiro reporta que retirou uma mala do porão do avião.
     */
    public void bagagemPorao() throws RemoteException;

    /**
     * Reportar depósito de bagagem.
     * 
     * <p>Invocador: Bagageiro,Passageiro
     * 
     * <p> Bagageiro reporta que colocou uma bagagem na passadeira de recolha de bagagens.
     * 
     * <p> Passageiro reporta que tirou uma bagagem sua da passadeira de recolha de bagagens.
     * 
     * 
     * @param take True, representa que retirou a mala com sucesso.
     * @param take False, representa que colocou a mala com sucesso. 
     */
    public void bagagemBelt(boolean take) throws RemoteException;

    /**
     * Reportar depósito de bagagem.
     * 
     * <p>Invocador: Bagageiro
     * 
     * <p> Bagageiro reporta que colocou uma bagagem na zona de armazenamento temporário de bagagens.
     */
    public void bagagemStore() throws RemoteException;
    
    /**
     * Reportar mudança de estado.
     * <p>
     * Invocador: Motorista
     * 
     * Motorista reporta mudança do seu estado.
     * 
     * @param state novo estado do motorista
     */
    public void reportState(Globals.motState state) throws RemoteException;
    
    /**
     * Reportar mudança de estado.
     * 
     * <p>Invocador: Passageiro
     * 
     * <p>O passageiro reporta a mudança do seu estado.
     * 
     * @param passID identificador do passageiro
     * @param state novo estado do passageiro
     */
    public void reportState(int passID, Globals.passState state) throws RemoteException;

    /**
     * Reportar número de malas em posse.
     * 
     * <p>Invocador: Passageiro
     * 
     * <p>Passageiro reporta quantas malas tem em sua posse.
     * 
     * @param passID identificador do passageiro
     */
    public void malasActual(int passID) throws RemoteException;

    /**
     * Reportar entrada para a fila de espera.
     * 
     * <p>Invocador: Passageiro
     * 
     * <p>Passageiro reporta que se encontra na fila na zona de transferência 
     * entre terminais à espera de entrar para o autocarro.
     * 
     * @param id identificador do passageiro
     */
    public void addfilaEspera(int id) throws RemoteException;
    
    /**
     * Reportar saída da fila de espera.
     * 
     * <p>Invocador: Passageiro
     * 
     * <p>Passageiro reporta que saiu da fila de espera para entrar no autocarro.
     */
    public void removefilaEspera() throws RemoteException;

    /**
     * Reportar o estado dos assentos do autocarro.
     * 
     * <p>Invocador: Passageiro
     * 
     * <p>Passageiro, após sair do autocarro no terminal de partida, reporta o estado
     * do mesmo.
     * @param seats assentos do autocarro
     */
    public void autocarroState(int[] seats) throws RemoteException;
    
    /**
     *  Reportar falta de malas
     * 
     * <p>Invocador: Passageiro
     * 
     * <p> Passageiro antes de sair do aeroporto reporta que perdeu malas
     * @param malasPerdidas número de malas perdidas
     */
    public void missingBags(int malasPerdidas) throws RemoteException;
    
    public void nVoo(int nvoo) throws RemoteException;
    
    public void setPorao(int nMalas) throws RemoteException;
    
    public void malasInicial(int[] nMalas) throws RemoteException;
    
    public void destino(boolean[] dest) throws RemoteException;
    
    public void reportInitialStatus() throws RemoteException;
    
    public void shutdownMonitor() throws RemoteException;
}
