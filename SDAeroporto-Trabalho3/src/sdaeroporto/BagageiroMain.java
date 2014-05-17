package sdaeroporto;

import Estruturas.Globals;
import static Estruturas.Globals.registryHostname;
import static Estruturas.Globals.registryPort;
import Interfaces.PoraoBagageiroInterface;
import Interfaces.RecolhaBagageiroInterface;
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
        Registry registry;
        PoraoBagageiroInterface porao = null;
        RecolhaBagageiroInterface recolha = null;
        TransicaoBagageiroInterface transicao = null;
        ZonaDesembarqueBagageiroInterface zona = null;
        try {
            registry = LocateRegistry.getRegistry(registryHostname, registryPort);
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
        for (int i = 0; i < Globals.hostNames.length; i++) //clientResquest.closeMonitor(i);
        {
            
        }
    }
}
