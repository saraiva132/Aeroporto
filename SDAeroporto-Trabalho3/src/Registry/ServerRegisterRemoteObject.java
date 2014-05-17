/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Registry;

import static Estruturas.Globals.registryHostname;
import static Estruturas.Globals.registryPort;
import Interfaces.Register;
import genclass.GenericIO;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author rafael
 */
public class ServerRegisterRemoteObject {

    public static void main(String[] args) {

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        RegisterRemoteObject regEngine = new RegisterRemoteObject(registryHostname, registryPort);
        int listeningPort = 22000;   //define in global
        Register regEngineStub = null;
        try {
            regEngineStub = (Register) UnicastRemoteObject.exportObject(regEngine, listeningPort);
        } catch (RemoteException e) {
            GenericIO.writelnString("RegisterRemoteObject stub generation exception: " + e.getMessage());
            System.exit(1);
        }

        String nameEntry = "RegisterHandler";
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(registryHostname, registryPort);
        } catch (RemoteException e) {
            GenericIO.writelnString("RMI registry creation exception: " + e.getMessage());
            System.exit(1);
        }

        try {
            registry.rebind(nameEntry, regEngineStub);
        } catch (RemoteException e) {
            GenericIO.writelnString("RegisterRemoteObject remote exception on registration: " + e.getMessage());
            System.exit(1);
        }

    }
}