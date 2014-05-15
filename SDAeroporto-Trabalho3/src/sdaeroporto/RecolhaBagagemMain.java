/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import Estruturas.Globals;
import static Estruturas.Globals.MON_PORAO;
import static Estruturas.Globals.registryHostname;
import static Estruturas.Globals.registryPort;
import Interfaces.LoggingInterface;
import Monitores.RecolhaBagagem;
import genclass.GenericIO;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class RecolhaBagagemMain {
   private boolean canEnd=false;
    /**
     * Programa Principal.
     */
    public static void main(String[] args) {
        Globals.xmlParser();
        new RecolhaBagagemMain().listening();
    }
    /**
     * Responsável pela inicialização e instanciação do agente prestador de serviço, do monitor e da interface ao <i>RecolhaBagagem</i> e ainda do canal de escuta.
     * <p>
     * É responsável também pelo processo de escuta e do lançamento do agente prestador de serviço.
     */
    public void listening(){
        RecolhaBagagem recolha = null;
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
        recolha = new RecolhaBagagem(log);
        try {
            recolha = (RecolhaBagagem) UnicastRemoteObject.exportObject(recolha, MON_PORAO);
        } catch (RemoteException e) {
            System.exit(1);
        }
        String entry = "RecolhaBagagem";
        
        try {
            registry = LocateRegistry.getRegistry(registryHostname, registryPort);
        } catch (RemoteException e) {
            System.exit(1);
        }

        try {
            registry.bind(entry, recolha);
        } catch (RemoteException e) {
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.exit(1);
        }
        
        GenericIO.writelnString ("O serviço RecolhaBagagem foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");
    }
    
    public void close() {
        canEnd=true;
       // scon.end();
        System.exit(0);
    }
    
}
