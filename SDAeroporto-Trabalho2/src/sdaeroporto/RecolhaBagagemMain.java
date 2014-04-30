/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import static Estruturas.Globals.MON_RECOLHA_BAGAGEM;
import static Estruturas.Globals.portNumber;
import Monitores.RecolhaBagagem;
import ServerSide.ServerRecolhaBagagemInterface;
import ServerSide.ServerRecolhaBagagemProxy;
import genclass.GenericIO;
import ServerSide.ServerCom;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class RecolhaBagagemMain {
   private ServerCom listeningSocket;
   private boolean canEnd=false;
    /**
     * Programa Principal.
     */
    public static void main(String[] args) {
        new RecolhaBagagemMain().listening();
    }
    /**
     * Responsável pela inicialização e instanciação do agente prestador de serviço, do monitor e da interface ao <i>RecolhaBagagem</i> e ainda do canal de escuta.
     * <p>
     * É responsável também pelo processo de escuta e do lançamento do agente prestador de serviço.
     */
    public void listening(){
        RecolhaBagagem recolha = new RecolhaBagagem();
        ServerRecolhaBagagemInterface recolhaInter = new ServerRecolhaBagagemInterface(recolha);
        ServerCom commSocket;
        ServerRecolhaBagagemProxy recolhaProxy;
        
        listeningSocket = new ServerCom(portNumber[MON_RECOLHA_BAGAGEM]);
        listeningSocket.start();
        
        GenericIO.writelnString ("O serviço RecolhaBagagem foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");
        
        while(!canEnd)
        {   commSocket = listeningSocket.accept();
            recolhaProxy = new ServerRecolhaBagagemProxy(commSocket,recolhaInter,this);
            recolhaProxy.start();
        }
    }
    
    public void close() {
        canEnd=true;
       // scon.end();
        System.exit(0);
    }
    
}
