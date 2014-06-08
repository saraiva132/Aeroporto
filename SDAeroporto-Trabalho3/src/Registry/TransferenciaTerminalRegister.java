package Registry;

import Estruturas.Globals;
import static Estruturas.Globals.portNumber;
import static Estruturas.Globals.registryHostname;
import static Estruturas.Globals.registryPort;
import Interfaces.LoggingInterface;
import Interfaces.Register;
import Interfaces.TransferenciaInterface;
import Monitores.TransferenciaTerminal;
import genclass.GenericIO;
import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Este tipo de dados é responsável por obter uma instância do monitor de <b>Logging</b> e por registar o monitor <b>TransferenciaTerminal</b>.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class TransferenciaTerminalRegister {
    
    /**
     * Identifica se já se pode terminar a execução do serviço.
     * 
     * @serialField canEnd
     */
    private boolean canEnd;
    
    /**
     * Instância do monitor <b>TransferenciaTerminal</b>
     * 
     * @serialField transferencia
     */
    private TransferenciaTerminal transferencia;
    
    /**
     * Instância do objecto que define as operações que podem ser realizadas sobre o monitor <b>TransferenciaTerminal</b>
     * 
     * @serialField transfInterface
     */
    private TransferenciaInterface transfInterface;
    
    /**
     * Instância da interface que contêm os métodos para guardar e retirar referências para objectos remotos.
     * 
     * @serialField registry
     */
    private Registry registry;
    
    /**
     * Instância do objecto que define as operações que podem ser realizadas sobre o monitor <b>Logging</b>
     * 
     * @serialField log
     */
    private LoggingInterface log;    
    
    
    /**
     * Instanciação e Inicialização do TransferenciaTerminalRegister
     */
    public TransferenciaTerminalRegister() {
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
        transferencia = new TransferenciaTerminal(log,this);
        try {
            transfInterface = (TransferenciaInterface) UnicastRemoteObject.exportObject(transferencia, portNumber[Globals.MON_TRANSFERENCIA_TERMINAL]);
        } catch (RemoteException e) {
            System.exit(1);
        }
        
    }
    /**
     * Ciclo de vida TransferenciaTerminalRegister
     */
    public synchronized void run() {
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
            register.bind(entry, transfInterface);
        } catch (RemoteException e) {
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.exit(1);
        }

        GenericIO.writelnString("O serviço TransferenciaTerminal foi estabelecido!");
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
            UnicastRemoteObject.unexportObject(transferencia, false);
        } catch (NoSuchObjectException ex) {
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
