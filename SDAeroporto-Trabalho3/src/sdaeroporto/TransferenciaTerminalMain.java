package sdaeroporto;

import Estruturas.Globals;
import static Estruturas.Globals.MON_PORAO;
import static Estruturas.Globals.portNumber;
import static Estruturas.Globals.registryHostname;
import static Estruturas.Globals.registryPort;
import Interfaces.LoggingInterface;
import Interfaces.Register;
import Interfaces.TransferenciaInterface;
import Monitores.TransferenciaTerminal;
import genclass.GenericIO;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Este tipo de dados simula a solução do lado do servidor referente ao monitor
 * <i>TransferenciaTerminal</i> do problema
 * <b>Rapsódia no Aeroporto</b>.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class TransferenciaTerminalMain {

    static {
        System.setProperty("java.security.policy", "java.policy");
    }
    
    boolean canEnd = false;
    
    /**
     * Programa Principal.
     */
    public static void main(String[] args) {
        Globals.xmlParser();
        new TransferenciaTerminalMain().listening();
    }

    /**
     * Responsável pela inicialização e instanciação do agente prestador de
     * serviço, do monitor e da interface ao <i>TransferenciaTerminal</i> e
     * ainda do canal de escuta.
     * <p>
     * É responsável também pelo processo de escuta e do lançamento do agente
     * prestador de serviço.
     */
    private synchronized void listening() {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        GenericIO.writelnString("Security manager was installed!");
        TransferenciaTerminal transferencia;
        TransferenciaInterface transfInt = null;
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
        transferencia = new TransferenciaTerminal(log,this);
        try {
            transfInt = (TransferenciaInterface) UnicastRemoteObject.exportObject(transferencia, portNumber[Globals.MON_TRANSFERENCIA_TERMINAL]);
        } catch (RemoteException e) {
            System.exit(1);
        }
        String entry = "TransferenciaTerminal";
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
            register.bind(entry, transfInt);
        } catch (RemoteException e) {
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.exit(1);
        }

        GenericIO.writelnString("O serviço TransferenciaTerminal foi estabelecido!");
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

    /**
     * Terminar a execução do serviço referente ao monitor
     * <i>TransferenciaTerminal</i>.
     */
   public synchronized void close() {
        canEnd = true;
        notify();
        System.out.printf("Closing...");
    }

}
