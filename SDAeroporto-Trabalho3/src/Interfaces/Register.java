/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

/**
 *
 * @author rafael
 */

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Register extends Remote
{
  /**
   *  Binds a remote reference to the specified name in this registry.
   *
   *    @param name the name to associate with the remote reference
   *    @param ref reference to the remote object
   *
   *    @throws RemoteException if either the invocation of the remote method, or the communication with the registry
   *                            service fails
   *    @throws AlreadyBoundException if the name is already in use
   */

   public void bind (String name, Remote ref) throws RemoteException, AlreadyBoundException;

 /**
   *  Removes the binding for the specified name in this registry.
   *
   *    @param name the name associated with the remote reference
   *
   *    @throws RemoteException if either the invocation of the remote method, or the communication with the registry
   *                            service fails
   *    @throws NotBoundException if the name is not in use
   */

   public void unbind (String name) throws RemoteException, NotBoundException;

  /**
   *  Replaces the binding for the specified name in this registry with the supplied remote reference.
   *  If a previous binding for the specified name exists, it is discarded.
   *
   *    @param name the name to associate with the remote reference
   *    @param ref reference to the remote object
   *
   *    @throws RemoteException if either the invocation of the remote method, or the communication with the registry
   *                            service fails
   */

   public void rebind (String name, Remote ref) throws RemoteException;
}
