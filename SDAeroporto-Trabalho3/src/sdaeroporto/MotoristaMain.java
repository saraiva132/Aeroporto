package sdaeroporto;

import Estruturas.Globals;
import static Estruturas.Globals.registryHostname;
import static Estruturas.Globals.registryPort;
import Interfaces.AutocarroMotoristaInterface;
import Interfaces.TransferenciaMotoristaInterface;
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

    /**
     * Programa Principal
     */
    public static void main(String[] args) {
        GenericIO.writelnString("O cliente MotoristaMain foi estabelecido!");
        GenericIO.writelnString("A iniciar operacoes.");
        Globals.xmlParser();
        Registry registry;
        AutocarroMotoristaInterface auto = null;
        TransferenciaMotoristaInterface transferencia = null;
        try {
            registry = LocateRegistry.getRegistry(registryHostname, registryPort);
            auto = (AutocarroMotoristaInterface) registry.lookup("Autocarro");
            transferencia = (TransferenciaMotoristaInterface) registry.lookup("Transferencia");
        } catch (RemoteException e) {
            GenericIO.writelnString("Excepção na localização da barbearia: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            GenericIO.writelnString("A barbearia não está registada: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        }
        Motorista motorista = new Motorista(auto, transferencia);
        motorista.start();
        try {
            motorista.join();
        } catch (InterruptedException ex) {
            GenericIO.writelnString("Erro a terminar motorista!");
            System.exit(-1);
        }
        for (int i = 0; i < Globals.hostNames.length; i++) //clientResquest.closeMonitor(i);
        {
            
        }
    }

}
