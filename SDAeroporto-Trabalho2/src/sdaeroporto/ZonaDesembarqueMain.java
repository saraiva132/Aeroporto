/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import static Estruturas.AuxInfo.MON_ZONA_DESEMBARQUE;
import static Estruturas.AuxInfo.portNumber;
import Monitores.ZonaDesembarque;
import ServerSide.ServerZonaDesembarqueInterface;
import ServerSide.ServerZonaDesembarqueProxy;
import genclass.GenericIO;
import ServerSide.ServerCom;

/**
 *
 * @author Hugo
 */
public class ZonaDesembarqueMain {
    private ServerCom scon;
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
        ServerCom sconi;
        ServerZonaDesembarqueProxy desembarqueProxy;
        
        scon = new ServerCom(portNumber[MON_ZONA_DESEMBARQUE]);
        scon.start();
        
        GenericIO.writelnString ("O serviço ZonaDesembarque foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");
        
        while(!canEnd)
        {   sconi = scon.accept();
            desembarqueProxy = new ServerZonaDesembarqueProxy(sconi,desembarqueInter,this);
            desembarqueProxy.start();
        }
    }
    
    public void close(){
        canEnd=true;
        //scon.end();
        System.exit(0);
    }
    
}
