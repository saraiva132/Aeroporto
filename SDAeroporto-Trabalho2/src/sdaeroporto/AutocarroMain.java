/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import static Estruturas.AuxInfo.MON_AUTOCARRO;
import static Estruturas.AuxInfo.portNumber;
import Monitores.Autocarro;
import ServerSide.ServerAutocarroInterface;
import ServerSide.ServerAutocarroProxy;
import genclass.GenericIO;
import serverSide.ServerCom;

/**
 *
 * @author Hugo
 */
public class AutocarroMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Autocarro auto;
        ServerAutocarroInterface autoInter;
        ServerCom scon, sconi;
        ServerAutocarroProxy autoProxy;
        
        auto = new Autocarro();
        autoInter = new ServerAutocarroInterface(auto);
        scon = new ServerCom(portNumber[MON_AUTOCARRO]);
        scon.start();
        GenericIO.writelnString ("O serviço Autocarro foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");
        
        while(true)
        {   sconi = scon.accept();
            autoProxy = new ServerAutocarroProxy(sconi,autoInter);
            autoProxy.start();
        }        
    }
    
}
