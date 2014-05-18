/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdaeroporto;

import Estruturas.Globals;
import static Estruturas.Globals.MON_PORAO;
import static Estruturas.Globals.portNumber;
import static Estruturas.Globals.registryHostname;
import static Estruturas.Globals.registryPort;
import Interfaces.LoggingInterface;
import Interfaces.RecolhaInterface;
import Interfaces.Register;
import Monitores.RecolhaBagagem;
import genclass.GenericIO;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class RecolhaBagagemMain {
    
    static {
        System.setProperty("java.security.policy", "java.policy");
    }
    
    private boolean canEnd = false;

    /**
     * Programa Principal.
     */
    public static void main(String[] args) {
        Globals.xmlParser();
        new RecolhaBagagemMain().listening();
    }

    /**
     * Responsável pela inicialização e instanciação do agente prestador de
     * serviço, do monitor e da interface ao <i>RecolhaBagagem</i> e ainda do
     * canal de escuta.
     * <p>
     * É responsável também pelo processo de escuta e do lançamento do agente
     * prestador de serviço.
     */
    public synchronized void listening() {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        GenericIO.writelnString("Security manager was installed!");
        RecolhaBagagem recolha = null;
        RecolhaInterface recolhaInt = null;
        Registry registry = null;
        LoggingInterface log = null;
        try {
            registry = LocateRegistry.getRegistry(registryHostname, registryPort);
            log = (LoggingInterface) registry.lookup("Logging");
        } catch (RemoteException e) {
            GenericIO.writelnString("Excepção na localização da barbearia: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            GenericIO.writelnString("A barbearia não está registada: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        }
        recolha = new RecolhaBagagem(log,this);
        try {
            recolhaInt = (RecolhaInterface) UnicastRemoteObject.exportObject(recolha, portNumber[Globals.MON_RECOLHA_BAGAGEM]);
        } catch (RemoteException e) {
            System.exit(1);
        }
        String entry = "RecolhaBagagem";
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
            register.bind(entry, recolhaInt);
        } catch (RemoteException e) {
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.exit(1);
        }

        GenericIO.writelnString("O serviço RecolhaBagagem foi estabelecido!");
        GenericIO.writelnString("O servidor esta em escuta.");
        
        try {
            wait();
        } catch (InterruptedException ex) {
        }
        if (canEnd) {
            try {
                register.unbind(entry);
            } catch (RemoteException ex) {
                System.exit(1);
            } catch (NotBoundException ex) {
                System.exit(1);
            }
            System.exit(0);
        }
    }

   public synchronized void close() {
        canEnd = true;
        notify();
        System.out.printf("Closing...");
    }

}
