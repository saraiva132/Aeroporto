package sdaeroporto;

import Estruturas.Globals;
import static Estruturas.Globals.MON_AUTOCARRO;
import static Estruturas.Globals.registryHostname;
import static Estruturas.Globals.registryPort;
import Interfaces.LoggingInterface;
import Interfaces.Register;
import Monitores.Logging;
import genclass.GenericIO;
import java.io.PrintStream;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Este tipo de dados simula a solução do lado do servidor referente ao monitor <i>Logging</i> do problema
 * <b>Rapsódia no Aeroporto</b>.
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class LoggingMain {
    /**
     * Programa Principal.
     */
    public static void main(String[] args) {
        Globals.xmlParser();
        new LoggingMain().listening();
    }
    /**
     * Responsável pela inicialização e instanciação do agente prestador de serviço, do monitor e da interface ao <i>Logging</i> e ainda do canal de escuta.
     * <p>
     * É responsável também pelo processo de escuta e do lançamento do agente prestador de serviço.
     */
    public void listening(){
        if (System.getSecurityManager () == null)
        System.setSecurityManager (new RMISecurityManager ());
     GenericIO.writelnString ("Security manager was installed!");
     
        PrintStream stdout = System.out;
        Logging log = null;
        Registry registry = null;
        LoggingInterface logInt = null;
        log = new Logging();
        try {
            logInt = (LoggingInterface) UnicastRemoteObject.exportObject(log, Globals.MON_LOGGING);
        } catch (RemoteException e) {
            System.exit(1);
        }
        String entry = "Logging";
        String nameEntryBase = "RegisterHandler";
        Register register = null;
        try {
            registry = LocateRegistry.getRegistry(registryHostname, registryPort);
        } catch (RemoteException e) {
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

    }
    /**
     * Terminar a execução do serviço referente ao monitor <i>Logging</i>.
     */
    public void close(){
        System.exit(0);
    }

}
