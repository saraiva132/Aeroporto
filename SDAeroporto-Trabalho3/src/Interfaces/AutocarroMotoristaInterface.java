package Interfaces;

import Estruturas.VectorCLK;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Identifica e descreve as operações que o motorista pode realizar sobre o monitor 
 * <b>Autocarro</b>
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface AutocarroMotoristaInterface extends Remote {
    
    /**
     * Esperar que passageiros entrem no autocarro
     * <p>
     * Invocador: Motorista
     *<p>
     * Motorista espera que todos os passageiros entrem no autocarro para poder
     * seguir
     *
     * @param vc relógio vectorial do motorista
     * @param bilhetesvendidos Número de bilhetes vendidos (corresponde ao número de
     * passageiros que estão à espera)
     * @return relógio vectorial actualizado
     * @throws java.rmi.RemoteException
     */
    public VectorCLK announcingBusBoardingWaiting(VectorCLK vc, int bilhetesvendidos) throws RemoteException;

    /**
     * Dirigir-se ao terminal de partida
     * <p>
     * Invocador: Motorista
     * <p>
     * Motorista conduz os passageiros que se encontram em trânsito para o terminal de partida.
     * 
     * @param vc relógio vectorial do motorista
     * @return relógio vectorial actualizado
     * @throws java.rmi.RemoteException 
     */
    public VectorCLK goToDepartureTerminal(VectorCLK vc) throws RemoteException;

    /**
     * Dirigir-se ao terminal de chegada
     * <p>
     * Invocador: Motorista
     * <p>
     * Motorista retorna ao terminal de chegada.
     * 
     * @param vc relógio vectorial do motorista
     * @return relógio vectorial actualizado
     * @throws java.rmi.RemoteException 
     */
    public VectorCLK goToArrivalTerminal(VectorCLK vc) throws RemoteException;

    /**
     * Estacionar o autocarro no terminal de chegada
     * <p>
     * Invocador: Motorista
     * <p>
     * Motorista estaciona o autocarro no terminal de chegada.
     * 
     * @param vc relógio vectorial do motorista
     * @return relógio vectorial actualizado
     * @throws java.rmi.RemoteException 
     */
    public VectorCLK parkTheBus(VectorCLK vc) throws RemoteException;

     /**
      * Estacionar o autocarro no terminal de partida
      * <p>
     * Invocador: Motorista
     * <p>
     * Motorista estaciona o autocarro e larga os passageiros; bloqueia
     * até que o último passageiro saia do autocarro e o acorde.
     * 
     * @param vc relógio vectorial do motorista
     * @return relógio vectorial actualizado
     * @throws java.rmi.RemoteException
     */
    public VectorCLK parkTheBusAndLetPassOff(VectorCLK vc) throws RemoteException;

    /**
     * Fechar o monitor Autocarro.
     * <p>
     * Invocador: Motorista
     * <p>
     * O motorista, após concluir o seu ciclo de vida invoca a operação para fechar o monitor <i>Autocarro</i>.
     * 
     * @throws RemoteException
     */
    public void shutdownMonitor() throws RemoteException;
}
