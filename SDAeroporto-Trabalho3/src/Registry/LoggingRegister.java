/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Registry;

import Estruturas.Globals;
import static Estruturas.Globals.portNumber;
import static Estruturas.Globals.registryHostname;
import static Estruturas.Globals.registryPort;
import Interfaces.LoggingInterface;
import Interfaces.Register;
import Monitores.Logging;
import genclass.GenericIO;
import java.io.PrintStream;
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
public class LoggingRegister {
    
    private boolean canEnd;
    private Logging log;
    private LoggingInterface logInt;
    private Registry registry;
    private PrintStream stdout;
    
    public LoggingRegister() {
        super();
        canEnd = false;
        
        stdout = System.out;
        Logging log = null;
        Registry registry = null;
        LoggingInterface logInt = null;
        log = new Logging(this);
        try {
            logInt = (LoggingInterface) UnicastRemoteObject.exportObject(log, portNumber[Globals.MON_LOGGING]);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
    }
    
    public synchronized void listening() {
        String entry = "Logging";
        String nameEntryBase = "RegisterHandler";
        Register register = null;
        try {
            registry = LocateRegistry.getRegistry(registryHostname, registryPort);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
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
            register.bind(entry, logInt);
        } catch (RemoteException e) {
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.exit(1);
        }
        stdout.println("O serviço Logging foi estabelecido!");
        stdout.println("O servidor esta em escuta.");
        
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
            UnicastRemoteObject.unexportObject(log, false);
        } catch (NoSuchObjectException ex) {
        }
    }
    
    /**
     * Terminar a execução do serviço referente ao monitor <i>Logging</i>.
     */
    public synchronized void close() {
        canEnd = true;
        notify();
        System.out.printf("Closing...");
        
    }
}
