package Interfaces;

import Estruturas.Mala;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Identifica e descreve as operações que o passageiro pode realizar sobre o
 * monitor <b>Porão</b>.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface PoraoPassageiroInterface extends Remote{
    
    /**
     * Enviar as malas para o porão do avião
     * <p>
     * Invocador: PassageiroMain
     * <p>
     * A cada ciclo da simulação, a PassageiroMain encarrega-se de enviar as malas para o porão do avião que irá aterrar no aeroporto.
     * 
     * @param malas conjunto de todas as malas dos passageiros
     * @throws RemoteException 
     */
    public void sendLuggages(Mala[] malas) throws RemoteException;
    
    /**
     * Fechar o monitor Porao.
     * <p>
     * Invocador: Passageiro
     * <p>
     * O Passageiro, após concluir o seu ciclo de vida invoca a operação para fechar o monitor <i>Porao</i>.
     * 
     * @throws RemoteException 
     */
    public void shutdownMonitor() throws RemoteException;
}
