package Interfaces;

import Estruturas.Globals;
import Estruturas.VectorCLK;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Identifica as operações que as três entidades activas bagageiro, motorista e passageiro podem realizar sobre o monitor <b>Logging</b>
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
     * @throws java.rmi.RemoteException
     */
    public void reportState(Globals.bagState state) throws RemoteException;

    /**
     * Reportar retirada de uma mala do porão.
     * 
     * <p>Invocador: Bagageiro
     * 
     * <p>Bagageiro reporta que retirou uma mala do porão do avião.
     * @throws java.rmi.RemoteException
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
     * @throws java.rmi.RemoteException 
     */
    public void bagagemBelt(boolean take) throws RemoteException;

    /**
     * Reportar depósito de bagagem.
     * 
     * <p>Invocador: Bagageiro
     * 
     * <p> Bagageiro reporta que colocou uma bagagem na zona de armazenamento temporário de bagagens.
     * @throws java.rmi.RemoteException
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
     * @throws java.rmi.RemoteException
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
     * @throws java.rmi.RemoteException
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
     * @throws java.rmi.RemoteException
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
     * @throws java.rmi.RemoteException
     */
    public void addfilaEspera(int id) throws RemoteException;
    
    /**
     * Reportar saída da fila de espera.
     * 
     * <p>Invocador: Passageiro
     * 
     * <p>Passageiro reporta que saiu da fila de espera para entrar no autocarro.
     * @throws java.rmi.RemoteException
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
     * @throws java.rmi.RemoteException
     */
    public void autocarroState(int[] seats) throws RemoteException;
    
    /**
     *  Reportar falta de malas
     * 
     * <p>Invocador: Passageiro
     * 
     * <p> Passageiro antes de sair do aeroporto reporta que perdeu malas
     * @param malasPerdidas número de malas perdidas
     * @throws java.rmi.RemoteException
     */
    public void missingBags(int malasPerdidas) throws RemoteException;
    
    /**
     * Actualizar o número de voo
     * <p>
     * Invocador: PassageiroMain
     * <p>
     * A cada ciclo da simulação, a PassageiroMain encarrega-se de actualizar o número de voo no monitor Logging 
     * @param nvoo número de voo
     * @throws RemoteException
     */
    public void nVoo(int nvoo) throws RemoteException;
    
    /**
     * Definir o número de malas que vem no porão do avião que acabou de aterrar
     * <p>
     * Invocador: PassageiroMain
     * <p>
     * A cada ciclo da simulação, a PassageiroMain encarrega-se de definir o número de malas que o novo voo traz no seu Porão
     * @param nMalas número de malas
     * @throws RemoteException 
     */
    public void setPorao(int nMalas) throws RemoteException;
    
    /**
     * Reportar número de malas total de cada passageiro.
     * <p>
     * Invocador: PassageiroMain
     * <p>
     * A cada ciclo da simulação, a PassageiroMain encarrega-se reportar o número de malas total de cada passageiro
     * 
     * @param nMalas número de malas totais que petencem aos passageiros
     * @throws RemoteException 
     */
    public void malasInicial(int[] nMalas) throws RemoteException;
    
    /**
     * Reportar tipos de passageiros.
     * <p>
     * Invocador: PassageiroMain
     * <p>
     * A cada ciclo da simulação, a PassageiroMain encarrega-se de reportar o estado dos
     * passageiros: se estão em trânsito ou se este aeroporto corresponde ao seu
     * destino
     * 
     * @param dest
     * <ul>
     * <li>FALSE caso esteja em trânsito
     * <li>TRUE caso contrário
     * </ul>
     * @throws RemoteException 
     */
    public void destino(boolean[] dest) throws RemoteException;
    
    /**
     * Auxilia a inicialização do Logging.
     * <p>
     * Invocador: PassageiroMain
     * <p>
     * A cada ciclo da simulação, a PassageiroMain encarrega-se de reportar o estado inicial.
     * @throws RemoteException 
     */
    public void reportInitialStatus() throws RemoteException;
    
    /**
     * Terminar o monitor.
     * <p>
     * Invocadores: BagageiroMain, MotoristaMain e PassageiroMain
     * <p>
     * No final da execução da simulação, para o fechar o monitor os 3
     * lançadores das threads correspondentes ao passageiro, bagageiro e
     * motorista necessitam de fechar os monitores.
     * @throws RemoteException 
     */
    public void shutdownMonitor() throws RemoteException;
    
    /**
     * Actualizar o relógio vectorial do monitor Logging
     * 
     * @param ts relógio vectorial actualizado da entidade
     * @param id identificador da entidade
     * @throws RemoteException 
     */
    public void UpdateVectorCLK(VectorCLK ts, int id) throws RemoteException;
}
