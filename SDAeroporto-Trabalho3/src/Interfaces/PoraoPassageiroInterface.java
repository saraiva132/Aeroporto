/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import Estruturas.Mala;
import Estruturas.ShutdownException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author rafael
 */
public interface PoraoPassageiroInterface extends Remote{
    
    public void sendLuggages(Mala[] malas) throws RemoteException;
    public void shutdownMonitor() throws RemoteException;
}
