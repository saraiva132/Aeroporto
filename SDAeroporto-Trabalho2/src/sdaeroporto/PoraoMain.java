/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import static Estruturas.AuxInfo.MON_PORAO;
import static Estruturas.AuxInfo.portNumber;
import Monitores.Porao;
import ServerSide.ServerPoraoInterface;
import ServerSide.ServerPoraoProxy;
import genclass.GenericIO;
import ServerSide.ServerCom;

/**
 *
 * @author Hugo
 */
public class PoraoMain {
   private ServerCom scon;
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
        ServerCom sconi;
        ServerPoraoProxy poraoProxy;
        scon = new ServerCom(portNumber[MON_PORAO]);
        scon.start();
        
        porao = new Porao();
        poraoInter = new ServerPoraoInterface(porao);
        
        GenericIO.writelnString ("O serviço Porao foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");
        
        while(!canEnd)
        {   sconi = scon.accept();
            poraoProxy = new ServerPoraoProxy(sconi,poraoInter,this);
            poraoProxy.start();
        }
    }
    
    public void close(){
        canEnd=true;
        //scon.end();
        System.exit(0);
    }
    
}
