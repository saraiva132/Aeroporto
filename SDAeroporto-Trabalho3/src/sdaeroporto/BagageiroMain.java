package sdaeroporto;

import Estruturas.Globals;
import static Estruturas.Globals.registryHostname;
import static Estruturas.Globals.registryPort;
import Interfaces.AutocarroBagageiroInterface;
import Interfaces.LoggingInterface;
import Interfaces.PoraoBagageiroInterface;
import Interfaces.RecolhaBagageiroInterface;
import Interfaces.TransferenciaBagageiroInterface;
import Interfaces.TransicaoBagageiroInterface;
import Interfaces.ZonaDesembarqueBagageiroInterface;
import Threads.Bagageiro;
import genclass.GenericIO;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Este tipo de dados simula a solução ao problema <b>Rapsódia no Aeroporto</b>
 * do lado do cliente correspondente ao <i>bagageiro</i>.
 * <p>
 * A comunicação baseia-se em Java RMI
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class BagageiroMain {

    static {
        System.setProperty("java.security.policy", "java.policy");
    }

    /**
     * Programa Principal
     */
    public static void main(String[] args) {
        GenericIO.writelnString("O cliente BagageiroMain foi estabelecido!");
        GenericIO.writelnString("A iniciar operacoes.");
        Globals.xmlParser();
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        Registry registry;
        PoraoBagageiroInterface poraoInter = null;
        RecolhaBagageiroInterface recolhaInter = null;
        TransicaoBagageiroInterface transicaoInter = null;
        ZonaDesembarqueBagageiroInterface desembarqueInter = null;
        AutocarroBagageiroInterface autoInter = null;
        TransferenciaBagageiroInterface transferenciaInter = null;
        LoggingInterface logInter = null;

        try {
            registry = LocateRegistry.getRegistry(registryHostname, registryPort);
            logInter = (LoggingInterface) registry.lookup("Logging");
            transferenciaInter = (TransferenciaBagageiroInterface) registry.lookup("TransferenciaTerminal");
            autoInter = (AutocarroBagageiroInterface) registry.lookup("Autocarro");
            poraoInter = (PoraoBagageiroInterface) registry.lookup("Porao");
            recolhaInter = (RecolhaBagageiroInterface) registry.lookup("RecolhaBagagem");
            transicaoInter = (TransicaoBagageiroInterface) registry.lookup("TransiçãoAeroporto");
            desembarqueInter = (ZonaDesembarqueBagageiroInterface) registry.lookup("ZonaDesembarque");
        } catch (RemoteException e) {
            GenericIO.writelnString("Excepção na localização do Logging: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            GenericIO.writelnString("O Logging não está registado: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        }
        Bagageiro bagageiro = new Bagageiro(desembarqueInter, poraoInter, recolhaInter, transicaoInter);
        bagageiro.start();
        try {
            bagageiro.join();
        } catch (InterruptedException ex) {
            GenericIO.writelnString("Erro a terminar bagageiro!");
            System.exit(1);
        }
        try {
            logInter.shutdownMonitor();
            transferenciaInter.shutdownMonitor();
            autoInter.shutdownMonitor();
            poraoInter.shutdownMonitor();
            recolhaInter.shutdownMonitor();
            transicaoInter.shutdownMonitor();
            desembarqueInter.shutdownMonitor();
        } catch (RemoteException e) {
            System.out.printf("Cant shutdown Monitor");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
