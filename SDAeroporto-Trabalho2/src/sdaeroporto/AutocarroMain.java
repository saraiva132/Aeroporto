package sdaeroporto;

import Estruturas.Globals;
import static Estruturas.Globals.MON_AUTOCARRO;
import static Estruturas.Globals.portNumber;
import Monitores.Autocarro;
import ServerSide.ServerAutocarroInterface;
import ServerSide.ServerAutocarroProxy;
import ServerSide.ServerCom;
import genclass.GenericIO;

/**
 * Este tipo de dados simula a solução do lado do servidor referente ao monitor <i>Autocarro</i> do problema
 * <b>Rapsódia no Aeroporto</b>.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class AutocarroMain {
    
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
        Globals.xmlParser();
        new AutocarroMain().listening();
    }
    
    /**
     * Responsável pela inicialização e instanciação do agente prestador de serviço, do monitor e da interface ao <i>Autocarro</i> e ainda do canal de escuta.
     * <p>
     * É responsável também pelo processo de escuta e do lançamento do agente prestador de serviço.
     */
    public void listening(){
        
        Autocarro auto;
        ServerAutocarroInterface autoInter;
        ServerCom commSocket;
        ServerAutocarroProxy autoProxy;
        auto = new Autocarro();
        autoInter = new ServerAutocarroInterface(auto);
        listeningSocket = new ServerCom(portNumber[MON_AUTOCARRO]);
        listeningSocket.start();
        GenericIO.writelnString ("O serviço Autocarro foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");
        
        while(true)
        {   commSocket = listeningSocket.accept();
            autoProxy = new ServerAutocarroProxy(commSocket,autoInter,this);
            autoProxy.start();
        }        
    }
    
    /**
     * Terminar a execução do serviço referente ao monitor <i>Autocarro</i>.
     */
    public void close(){
        System.exit(0);
    }
    
}
