package sdaeroporto;

import Estruturas.Globals;
import static Estruturas.Globals.registryHostname;
import static Estruturas.Globals.registryPort;
import Interfaces.AutocarroMotoristaInterface;
import Interfaces.LoggingInterface;
import Interfaces.PoraoMotoristaInterface;
import Interfaces.RecolhaMotoristaInterface;
import Interfaces.TransferenciaMotoristaInterface;
import Interfaces.TransicaoMotoristaInterface;
import Interfaces.ZonaDesembarqueMotoristaInterface;
import Threads.Motorista;
import genclass.GenericIO;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Este tipo de dados simula a solução ao problema <b>Rapsódia no Aeroporto</b>
 * do lado do cliente correspondente ao <i>motorista</i>.
 * <p>
 * A comunicação baseia-se em passagem de mensagens sobre sockets usando o
 * protocolo TCP.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class MotoristaMain {

    static {
        System.setProperty("java.security.policy", "java.policy");
    }
    
    
    /**
     * Programa Principal
     */
    public static void main(String[] args) {
        GenericIO.writelnString("O cliente MotoristaMain foi estabelecido!");
        GenericIO.writelnString("A iniciar operacoes.");
        Globals.xmlParser();
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        Registry registry;
        AutocarroMotoristaInterface autoInter = null;
        TransferenciaMotoristaInterface transferenciaInter = null;
        PoraoMotoristaInterface poraoInter = null;
        RecolhaMotoristaInterface recolhaInter = null;
        TransicaoMotoristaInterface transicaoInter = null;
        ZonaDesembarqueMotoristaInterface desembarqueInter = null;
        LoggingInterface logInter = null;
        try {
            registry = LocateRegistry.getRegistry(registryHostname, registryPort);
            logInter = (LoggingInterface) registry.lookup("Logging");
            transferenciaInter = (TransferenciaMotoristaInterface) registry.lookup("TransferenciaTerminal");
            autoInter = (AutocarroMotoristaInterface) registry.lookup("Autocarro");
            poraoInter = (PoraoMotoristaInterface) registry.lookup("Porao");
            recolhaInter = (RecolhaMotoristaInterface) registry.lookup("RecolhaBagagem");
            transicaoInter = (TransicaoMotoristaInterface) registry.lookup("TransiçãoAeroporto");
            desembarqueInter = (ZonaDesembarqueMotoristaInterface) registry.lookup("ZonaDesembarque");
        } catch (RemoteException e) {
            GenericIO.writelnString("Excepção na localização do monitor: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            GenericIO.writelnString("Um monitor não está registado: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        }
        Motorista motorista = new Motorista(autoInter, transferenciaInter);
        motorista.start();
        try {
            motorista.join();
        } catch (InterruptedException ex) {
            GenericIO.writelnString("Erro a terminar motorista!");
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
            System.out.print("Cant shutdown Monitor");
            e.printStackTrace();
            System.exit(1);
        }
    }

}
