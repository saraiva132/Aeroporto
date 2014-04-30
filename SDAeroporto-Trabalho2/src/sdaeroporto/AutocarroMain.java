package sdaeroporto;

import static Estruturas.Globals.MON_AUTOCARRO;
import static Estruturas.Globals.portNumber;
import Monitores.Autocarro;
import ServerSide.ServerAutocarroInterface;
import ServerSide.ServerAutocarroProxy;
import ServerSide.ServerCom;
import genclass.GenericIO;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class AutocarroMain {
    private boolean canEnd=false;
   private ServerCom listeningSocket;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new AutocarroMain().listening();
    }
    
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
        
        while(!canEnd)
        {   commSocket = listeningSocket.accept();
            autoProxy = new ServerAutocarroProxy(commSocket,autoInter,this);
            autoProxy.start();
        }        
    }
    
    public void close(){
        canEnd=true;
        //scon.end();
        System.exit(0);
    }
    
}
