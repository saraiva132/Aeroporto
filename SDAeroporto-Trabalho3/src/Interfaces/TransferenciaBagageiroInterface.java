/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author rafael
 */
public interface TransferenciaBagageiroInterface extends Remote{
    public boolean shutdownMonitor() throws RemoteException;
}
