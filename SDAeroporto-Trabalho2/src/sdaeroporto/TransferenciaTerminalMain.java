/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import static Estruturas.AuxInfo.MON_RECOLHA_BAGAGEM;
import static Estruturas.AuxInfo.MON_TRANSFERENCIA_TERMINAL;
import static Estruturas.AuxInfo.portNumber;
import Monitores.RecolhaBagagem;
import Monitores.TransferenciaTerminal;
import ServerSide.ServerRecolhaBagagemInterface;
import ServerSide.ServerRecolhaBagagemProxy;
import ServerSide.ServerTransferenciaTerminalInterface;
import ServerSide.ServerTransferenciaTerminalProxy;
import genclass.GenericIO;
import java.io.IOException;
import java.net.ServerSocket;
import serverSide.ServerCom;

/**
 *
 * @author Hugo
 */
public class TransferenciaTerminalMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TransferenciaTerminal transferencia = new TransferenciaTerminal();
        ServerTransferenciaTerminalInterface transferenciaInter = 
                                            new ServerTransferenciaTerminalInterface(transferencia);
        ServerCom scon, sconi;
        ServerTransferenciaTerminalProxy transferenciaProxy;
        
        scon = new ServerCom(portNumber[MON_TRANSFERENCIA_TERMINAL]);
        
        scon.start();
        
        GenericIO.writelnString ("O serviço TransferenciaTerminal foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");
        
        while(true)
        {   sconi = scon.accept();
            transferenciaProxy = new ServerTransferenciaTerminalProxy(sconi,transferenciaInter);
            transferenciaProxy.start();
        }
    }
    
}
