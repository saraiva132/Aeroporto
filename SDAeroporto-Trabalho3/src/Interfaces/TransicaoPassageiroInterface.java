package Interfaces;

import Estruturas.VectorCLK;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Identifica e descreve as operações que um passageiro pode realizar sobre o
 * monitor <b>TransiçãoAeroporto</b>
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface TransicaoPassageiroInterface extends Remote {

    /**
     * Ir para casa
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro, cujo destino final é este aeroporto, abandona o aeroporto e
     * vai para casa. Espera até ao último passageiro do seu voo chegar à saída
     * do terminal de chegada ou ao terminal de partida, que tem a
     * responsabilidade de notificar os outros passageiros que podem ir embora e
     * que o bagageiro tenha recolhido todas as malas do porão.
     *
     * @param ts relógio vectorial do passageiro
     * @param passageiroId identificador do passageiro
     * @return Relógio vectorial actualizado
     * @throws java.rmi.RemoteException
     */
    public VectorCLK goHome(VectorCLK ts, int passageiroId) throws RemoteException;

    /**
     * Entrar no terminal de partida
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro, que se encontra em trânsito,prepara o próximo voo.Espera
     * até ao último passageiro do seu voo chegar à saída do terminal de chegada
     * ou ao terminal de partida, que tem a responsabilidade de notificar os
     * outros passageiros que podem ir embora e que o bagageiro tenha recolhido
     * todas as malas do porão.
     *
     * @param vc relógio vectorial do passageiro
     * @param passageiroId identificador do passageiro
     * @return Relógio vectorial actualizado
     * @throws java.rmi.RemoteException
     */
    public VectorCLK prepareNextLeg(VectorCLK vc, int passageiroId) throws RemoteException;

    /**
     * Fechar o monitor TransicaoAeroporto.
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro, após concluir o seu ciclo de vida invoca a operação para
     * fechar o monitor <i>TransicaoAeroporto</i>.
     *
     * @throws RemoteException
     */
    public void shutdownMonitor() throws RemoteException;
}
