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
 * Este tipo de dados é responsável por registar o monitor <b>Logging</b>.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class LoggingRegister {
    /**
     * Identifica se já se pode terminar a execução do serviço.
     * 
     * @serialField canEnd
     */
    private boolean canEnd;
    /**
     * Instância do monitor <b>Logging</b>
     * 
     * @serialField log
     */
    private Logging log;
    
    /**
     * Instância do objecto que define as operações que podem ser realizadas sobre o monitor <b>Logging</b>
     * 
     * @serialField logInterface
     */
    private LoggingInterface logInterface;
    
    /**
     * Instância da interface que contêm os métodos para guardar e retirar referências para objectos remotos.
     * 
     * @serialField registry
     */
    private Registry registry;
    
    /**
     * 
     * @serialField stdout
     */
    private PrintStream stdout;
    
    /**
     * Instanciação e Inicialização do LoggingRegister
     */
    public LoggingRegister() {
        super();
        canEnd = false;
        stdout = System.out;
        log = new Logging(this);
        try {
            logInterface = (LoggingInterface) UnicastRemoteObject.exportObject(log, portNumber[Globals.MON_LOGGING]);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
    }
    
    /**
     * Ciclo de vida do LoggingRegister
     */
    public synchronized void run() {
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
            register.bind(entry, logInterface);
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
