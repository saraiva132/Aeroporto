/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import static Estruturas.Globals.MON_ZONA_DESEMBARQUE;
import static Estruturas.Globals.portNumber;
import Monitores.ZonaDesembarque;
import ServerSide.ServerZonaDesembarqueInterface;
import ServerSide.ServerZonaDesembarqueProxy;
import genclass.GenericIO;
import ServerSide.ServerCom;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ZonaDesembarqueMain {
    private ServerCom listeningSocket;
    private boolean canEnd=false;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new ZonaDesembarqueMain().listening();
    }
    
    private void listening(){
        ZonaDesembarque desembarque = new ZonaDesembarque();
        ServerZonaDesembarqueInterface desembarqueInter = new ServerZonaDesembarqueInterface(desembarque);
        ServerCom commSocket;
        ServerZonaDesembarqueProxy desembarqueProxy;
        
        listeningSocket = new ServerCom(portNumber[MON_ZONA_DESEMBARQUE]);
        listeningSocket.start();
        
        GenericIO.writelnString ("O serviço ZonaDesembarque foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");
        
        while(!canEnd)
        {   commSocket = listeningSocket.accept();
            desembarqueProxy = new ServerZonaDesembarqueProxy(commSocket,desembarqueInter,this);
            desembarqueProxy.start();
        }
    }
    
    public void close(){
        canEnd=true;
        //scon.end();
        System.exit(0);
    }
    
}
