package sdaeroporto;

import Estruturas.Globals;
import static Estruturas.Globals.MON_AUTOCARRO;
import static Estruturas.Globals.portNumber;
import static Estruturas.Globals.registryHostname;
import static Estruturas.Globals.registryPort;
import Estruturas.ShutdownException;
import Interfaces.AutocarroInterface;
import Interfaces.LoggingInterface;
import Interfaces.Register;
import Monitores.Autocarro;
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
 * <i>Autocarro</i> do problema
 * <b>Rapsódia no Aeroporto</b>.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class AutocarroMain {

    static {
        System.setProperty("java.security.policy", "java.policy");
    }

    /**
     * Programa Principal.
     */
    public static void main(String[] args) {
        Globals.xmlParser();
        new AutocarroMain().listening();
    }

    /**
     * Responsável pela inicialização e instanciação do agente prestador de
     * serviço, do monitor e da interface ao <i>Autocarro</i> e ainda do canal
     * de escuta.
     * <p>
     * É responsável também pelo processo de escuta e do lançamento do agente
     * prestador de serviço.
     */
    public void listening() {

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        GenericIO.writelnString("Security manager was installed!");

        Autocarro auto = null;
        AutocarroInterface autoInt = null;
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
        auto = new Autocarro(log,this);
        try {
            autoInt = (AutocarroInterface) UnicastRemoteObject.exportObject(auto, portNumber[MON_AUTOCARRO]);
        } catch (RemoteException e) {
            System.exit(1);
        }
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
    }

    /**
     * Terminar a execução do serviço referente ao monitor <i>Autocarro</i>.
     */
    public void close() {
        
        System.exit(0);
    }

}
