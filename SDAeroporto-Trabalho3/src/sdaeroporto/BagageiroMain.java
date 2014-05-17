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
import Monitores.Logging;
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
 * A comunicação baseia-se em passagem de mensagens sobre sockets usando o
 * protocolo TCP.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class BagageiroMain {

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
        PoraoBagageiroInterface porao = null;
        RecolhaBagageiroInterface recolha = null;
        TransicaoBagageiroInterface transicao = null;
        ZonaDesembarqueBagageiroInterface zona = null;
        AutocarroBagageiroInterface auto = null;
        TransferenciaBagageiroInterface transferencia = null;
        LoggingInterface log = null;

        try {
            registry = LocateRegistry.getRegistry(registryHostname, registryPort);
            log = (LoggingInterface) registry.lookup("Logging");
            transferencia = (TransferenciaBagageiroInterface) registry.lookup("TransferenciaTerminal");
            auto = (AutocarroBagageiroInterface) registry.lookup("Autocarro");
            porao = (PoraoBagageiroInterface) registry.lookup("Porao");
            recolha = (RecolhaBagageiroInterface) registry.lookup("RecolhaBagagem");
            transicao = (TransicaoBagageiroInterface) registry.lookup("TransiçãoAeroporto");
            zona = (ZonaDesembarqueBagageiroInterface) registry.lookup("ZonaDesembarque");
        } catch (RemoteException e) {
            GenericIO.writelnString("Excepção na localização da barbearia: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            GenericIO.writelnString("A barbearia não está registada: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        }
        Bagageiro bagageiro = new Bagageiro(zona, porao, recolha, transicao);
        bagageiro.start();
        try {
            bagageiro.join();
        } catch (InterruptedException ex) {
            GenericIO.writelnString("Erro a terminar bagageiro!");
            System.exit(-1);
        }
        try {
            log.shutdownMonitor();
            transferencia.shutdownMonitor();
            auto.shutdownMonitor();
            porao.shutdownMonitor();
            recolha.shutdownMonitor();
            transicao.shutdownMonitor();
            zona.shutdownMonitor();
        } catch (RemoteException e) {
            System.exit(1);
        }
    }
}
