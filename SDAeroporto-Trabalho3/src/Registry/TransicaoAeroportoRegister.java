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
import Interfaces.TransicaoInterface;
import Monitores.TransiçãoAeroporto;
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
public class TransicaoAeroportoRegister {
    private boolean canEnd;
    private TransiçãoAeroporto transicao;
    private TransicaoInterface transicaoInterface ;
    private Registry registry ;
    private LoggingInterface log ;
    
    
    public TransicaoAeroportoRegister() {
        super();
        canEnd=false;
        
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
        transicao = new TransiçãoAeroporto(log,this);
        try {
            transicaoInterface = (TransicaoInterface) UnicastRemoteObject.exportObject(transicao, portNumber[Globals.MON_TRANSICAO_AEROPORTO]);
        } catch (RemoteException e) {
            System.exit(1);
        }
    }
    
    public synchronized void run() {
        String entry = "TransiçãoAeroporto";
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
            register.bind(entry, transicaoInterface);
        } catch (RemoteException e) {
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.exit(1);
        }
        //GenericIO.writelnString("O serviço TransicaoAeroporto foi estabelecido!");
        //GenericIO.writelnString("O servidor esta em escuta.");
        
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
            UnicastRemoteObject.unexportObject(transicao, false);
        } catch (NoSuchObjectException ex) {
        }
    }
    
    
    
    
    
    /**
     * Terminar a execução do serviço referente ao monitor
     * <i>TransicaoAeroporto</i>.
     */
    public synchronized void close() {
        canEnd = true;
        notify();
        System.out.printf("Closing...");
    }
}
