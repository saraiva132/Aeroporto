/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import static Estruturas.Globals.MON_PORAO;
import static Estruturas.Globals.portNumber;
import Monitores.Porao;
import ServerSide.ServerPoraoInterface;
import ServerSide.ServerPoraoProxy;
import genclass.GenericIO;
import ServerSide.ServerCom;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class PoraoMain {
   private ServerCom listeningSocket;
   private boolean canEnd=false;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new PoraoMain().listening();
    }
    
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
        
        while(!canEnd)
        {   commSocket = listeningSocket.accept();
            poraoProxy = new ServerPoraoProxy(commSocket,poraoInter,this);
            poraoProxy.start();
        }
    }
    
    public void close(){
        canEnd=true;
        //scon.end();
        System.exit(0);
    }
    
}
