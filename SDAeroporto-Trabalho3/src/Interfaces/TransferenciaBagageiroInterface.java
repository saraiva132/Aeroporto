/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import Estruturas.ShutdownException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author rafael
 */
public interface TransferenciaBagageiroInterface extends Remote{
    public void shutdownMonitor() throws RemoteException;
}
