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
import Monitores.ZonaDesembarque;
import genclass.GenericIO;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Este tipo de dados simula a solução do lado do servidor referente ao monitor <i>ZonaDesembarque</i> do problema
 * <b>Rapsódia no Aeroporto</b>.
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ZonaDesembarqueMain {
    /**
     * Programa Principal.
     */
    public static void main(String[] args) {
        Globals.xmlParser();
        new ZonaDesembarqueMain().listening();
    }
    /**
     * Responsável pela inicialização e instanciação do agente prestador de serviço, do monitor e da interface ao <i>ZonaDesembarques</i> e ainda do canal de escuta.
     * <p>
     * É responsável também pelo processo de escuta e do lançamento do agente prestador de serviço.
     */
    private void listening(){
        ZonaDesembarque desembarque ;
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
        desembarque = new ZonaDesembarque(log);
        try {
            desembarque = (ZonaDesembarque) UnicastRemoteObject.exportObject(desembarque, MON_PORAO);
        } catch (RemoteException e) {
            System.exit(1);
        }
        String entry = "ZonaDesembarque";

        try {
            registry = LocateRegistry.getRegistry(registryHostname, registryPort);
        } catch (RemoteException e) {
            System.exit(1);
        }

        try {
            registry.bind(entry, desembarque);
        } catch (RemoteException e) {
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.exit(1);
        }
        
        GenericIO.writelnString ("O serviço ZonaDesembarque foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");

    }
    /**
     * Terminar a execução do serviço referente ao monitor <i>ZonaDesembarque</i>.
     */
    public void close(){
        System.exit(0);
    }
    
}
