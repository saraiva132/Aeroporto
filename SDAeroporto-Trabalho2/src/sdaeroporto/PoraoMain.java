/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import static Estruturas.AuxInfo.MON_LOGGING;
import static Estruturas.AuxInfo.MON_PORAO;
import static Estruturas.AuxInfo.portNumber;
import Monitores.Porao;
import ServerSide.ServerPoraoInterface;
import ServerSide.ServerPoraoProxy;
import genclass.GenericIO;
import serverSide.ServerCom;

/**
 *
 * @author Hugo
 */
public class PoraoMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Porao porao;
        ServerPoraoInterface poraoInter;
        ServerCom scon, sconi;
        ServerPoraoProxy poraoProxy;
        scon = new ServerCom(portNumber[MON_PORAO]);
        scon.start();
        
        porao = new Porao();
        poraoInter = new ServerPoraoInterface(porao);
        
        GenericIO.writelnString ("O serviço Porao foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");
        
        while(true)
        {   sconi = scon.accept();
            poraoProxy = new ServerPoraoProxy(sconi,poraoInter);
            poraoProxy.start();
        }
    }
    
}
