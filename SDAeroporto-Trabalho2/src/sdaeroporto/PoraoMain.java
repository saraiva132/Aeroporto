package sdaeroporto;

import static Estruturas.Globals.MON_PORAO;
import static Estruturas.Globals.portNumber;
import Monitores.Porao;
import ServerSide.ServerPoraoInterface;
import ServerSide.ServerPoraoProxy;
import genclass.GenericIO;
import ServerSide.ServerCom;

/**
 * Este tipo de dados simula a solução do lado do servidor referente ao monitor <i>Porao</i> do problema
 * <b>Rapsódia no Aeroporto</b>.
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class PoraoMain {
    /**
     * Canal de escuta.
     * 
     * @serialField listeningSocket
     */
   private ServerCom listeningSocket;
    /**
     * Programa Principal.
     */
    public static void main(String[] args) {
        new PoraoMain().listening();
    }
    /**
     * Responsável pela inicialização e instanciação do agente prestador de serviço, do monitor e da interface ao <i>Porao</i> e ainda do canal de escuta.
     * <p>
     * É responsável também pelo processo de escuta e do lançamento do agente prestador de serviço.
     */
    public void listening(){
        Porao porao;
        ServerPoraoInterface poraoInter;
        ServerCom commSocket;
        ServerPoraoProxy poraoProxy;
        listeningSocket = new ServerCom(portNumber[MON_PORAO]);
        listeningSocket.start();
        
        porao = new Porao();
        poraoInter = new ServerPoraoInterface(porao);
        
        GenericIO.writelnString ("O serviço Porao foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");
        
        while(true)
        {   commSocket = listeningSocket.accept();
            poraoProxy = new ServerPoraoProxy(commSocket,poraoInter,this);
            poraoProxy.start();
        }
    }
    /**
     * Terminar a execução do serviço referente ao monitor <i>Porao</i>.
     */
    public void close(){
        System.exit(0);
    }
    
}
