package Registry;

import static Estruturas.Globals.MON_PORAO;
import static Estruturas.Globals.portNumber;
import static Estruturas.Globals.registryHostname;
import static Estruturas.Globals.registryPort;
import Interfaces.LoggingInterface;
import Interfaces.PoraoInterface;
import Interfaces.Register;
import Monitores.Porao;
import genclass.GenericIO;
import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Este tipo de dados é responsável por obter uma instância do monitor de <b>Logging</b> e por registar o monitor <b>Porao</b>.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class PoraoRegister {
    /**
     * Identifica se já se pode terminar a execução do serviço.
     * 
     * @serialField canEnd
     */
    private boolean canEnd;
    
    /**
     * Instância do monitor <b>Porao</b>
     * 
     * @serialField porao
     */
    private Porao porao;
    
    /**
     * Instância do objecto que define as operações que podem ser realizadas sobre o monitor <b>Porao</b>
     * 
     * @serialField poraoInterface
     */
    private PoraoInterface poraoInterface ;
    
    /**
     * Instância da interface que contêm os métodos para guardar e retirar referências para objectos remotos.
     * 
     * @serialField registry
     */
    private Registry registry ;
    
    /**
     * Instância do objecto que define as operações que podem ser realizadas sobre o monitor <b>Logging</b>
     * 
     * @serialField log
     */
    private LoggingInterface log ;

    
    /**
     * Instanciação e Inicialização do PoraoRegister
     */
    public PoraoRegister() {
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
        porao = new Porao(log,this);
        try {
            poraoInterface = (PoraoInterface) UnicastRemoteObject.exportObject(porao, portNumber[MON_PORAO]);
        } catch (RemoteException e) {
            System.exit(1);
        }
    }
    
    /**
     * Ciclo de vida do PoraoRegister
     */
    public synchronized void run() {
        String entry = "Porao";
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
            register.bind(entry, poraoInterface);
        } catch (RemoteException e) {
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.exit(1);
        }
        GenericIO.writelnString("O serviço Porao foi estabelecido!");
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
            UnicastRemoteObject.unexportObject(porao, false);
        } catch (NoSuchObjectException ex) {
        }
    }
    
    /**
     * Terminar a execução do serviço referente ao monitor <i>Porao</i>.
     */
    public synchronized void close() {
        canEnd = true;
        notify();
        System.out.printf("Closing...");
    }    
}
