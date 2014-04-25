/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import static Estruturas.AuxInfo.MON_TRANSFERENCIA_TERMINAL;
import static Estruturas.AuxInfo.portNumber;
import Monitores.TransferenciaTerminal;
import ServerSide.ServerTransferenciaTerminalInterface;
import ServerSide.ServerTransferenciaTerminalProxy;
import genclass.GenericIO;
import ServerSide.ServerCom;

/**
 *
 * @author Hugo
 */
public class TransferenciaTerminalMain {
    private ServerCom scon;
    private boolean canEnd=false;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new TransferenciaTerminalMain().listening();
    }
    
    private void listening(){
        TransferenciaTerminal transferencia = new TransferenciaTerminal();
        ServerTransferenciaTerminalInterface transferenciaInter = 
                                            new ServerTransferenciaTerminalInterface(transferencia);
        ServerCom  sconi;
        ServerTransferenciaTerminalProxy transferenciaProxy;
        
        scon = new ServerCom(portNumber[MON_TRANSFERENCIA_TERMINAL]);
        
        scon.start();
        
        GenericIO.writelnString ("O serviço TransferenciaTerminal foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");
        
        while(!canEnd)
        {   sconi = scon.accept();
            transferenciaProxy = new ServerTransferenciaTerminalProxy(sconi,transferenciaInter,this);
            transferenciaProxy.start();
        }
    }
    
    public void close(){
        canEnd=true;
        //scon.end();
        System.exit(0);
    }
    
}
