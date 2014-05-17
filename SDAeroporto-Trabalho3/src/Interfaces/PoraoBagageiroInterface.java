package Interfaces;

import Estruturas.Mala;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Identifica e descreve as operações que o bagageiro pode realizar sobre o
 * monitor <b>Porão</b>
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface PoraoBagageiroInterface extends Remote{

    /**
     * Tentar recolher uma mala
     * <p>
     * Invocador: bagageiro
     * <p>
     * O bagageiro desloca-se ao porão do avião e caso este não se encontre
     * vazio recolhe uma mala
     *
     * @return Mala que apanhou no porão, ou <i>null</i> caso o porão se encontrar vazio
     */
    public Mala tryToCollectABag() throws RemoteException;
    public boolean shutdownMonitor() throws RemoteException;
}
