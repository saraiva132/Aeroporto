/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import static Estruturas.Globals.MON_TRANSFERENCIA_TERMINAL;
import static Estruturas.Globals.portNumber;
import Monitores.TransferenciaTerminal;
import ServerSide.ServerTransferenciaTerminalInterface;
import ServerSide.ServerTransferenciaTerminalProxy;
import genclass.GenericIO;
import ServerSide.ServerCom;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class TransferenciaTerminalMain {
    private ServerCom listenngSocket;
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
        ServerCom  commSocket;
        ServerTransferenciaTerminalProxy transferenciaProxy;
        
        listenngSocket = new ServerCom(portNumber[MON_TRANSFERENCIA_TERMINAL]);
        
        listenngSocket.start();
        
        GenericIO.writelnString ("O serviço TransferenciaTerminal foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");
        
        while(!canEnd)
        {   commSocket = listenngSocket.accept();
            transferenciaProxy = new ServerTransferenciaTerminalProxy(commSocket,transferenciaInter,this);
            transferenciaProxy.start();
        }
    }
    
    public void close(){
        canEnd=true;
        //scon.end();
        System.exit(0);
    }
    
}
