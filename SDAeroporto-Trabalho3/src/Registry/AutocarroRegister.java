/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Registry;

import static Estruturas.Globals.MON_AUTOCARRO;
import static Estruturas.Globals.portNumber;
import static Estruturas.Globals.registryHostname;
import static Estruturas.Globals.registryPort;
import Interfaces.AutocarroInterface;
import Interfaces.LoggingInterface;
import Interfaces.Register;
import Monitores.Autocarro;
import genclass.GenericIO;
import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Hugo
 */
public class AutocarroRegister {
    private boolean canEnd;
    private Autocarro auto;
    private AutocarroInterface autoInt;
    private Registry registry;
    private LoggingInterface log;

    public AutocarroRegister() {
        super();
        canEnd = false;
        try {
            registry = LocateRegistry.getRegistry(registryHostname, registryPort);
            log = (LoggingInterface) registry.lookup("Logging");
        } catch (RemoteException e) {
            GenericIO.writelnString("Excepção na localização do Logging: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            GenericIO.writelnString("O Logging não está registado: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        }
        auto = new Autocarro(log, this);
        try {
            autoInt = (AutocarroInterface) UnicastRemoteObject.exportObject(auto, portNumber[MON_AUTOCARRO]);
        } catch (RemoteException e) {
            System.exit(1);
        }
    }
    
    public synchronized void listening() {
        String entry = "Autocarro";
        String nameEntryBase = "RegisterHandler";
        Register register = null;

        try {
            register = (Register) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            GenericIO.writelnString("RegisterRemoteObject lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            GenericIO.writelnString("RegisterRemoteObject not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try {
            register.bind(entry, autoInt);
        } catch (RemoteException e) {
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.exit(1);
        }
        GenericIO.writelnString("O serviço Autocarro foi estabelecido!");
        GenericIO.writelnString("O servidor esta em escuta.");

        try {
            while (!canEnd) {
                wait();
            }
        } catch (InterruptedException ex) {

        }
        try {
            register.unbind(entry);
        } catch (RemoteException ex) {
            System.exit(1);
        } catch (NotBoundException ex) {
            System.exit(1);
        }
        try {
            UnicastRemoteObject.unexportObject(auto, false);
        } catch (NoSuchObjectException ex) {
        }
    }
    
    /**
     * Terminar a execução do serviço referente ao monitor <i>Autocarro</i>.
     */
    public synchronized void close() {
        canEnd = true;
        notify();
        System.out.printf("Closing...");
        
    }
    
    
    
}
