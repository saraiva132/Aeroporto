/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import static Estruturas.AuxInfo.MON_RECOLHA_BAGAGEM;
import static Estruturas.AuxInfo.portNumber;
import Monitores.RecolhaBagagem;
import ServerSide.ServerRecolhaBagagemInterface;
import ServerSide.ServerRecolhaBagagemProxy;
import genclass.GenericIO;
import serverSide.ServerCom;

/**
 *
 * @author Hugo
 */
public class RecolhaBagagemMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RecolhaBagagem recolha = new RecolhaBagagem();
        ServerRecolhaBagagemInterface recolhaInter = new ServerRecolhaBagagemInterface(recolha);
        ServerCom scon, sconi;
        ServerRecolhaBagagemProxy recolhaProxy;
        
        scon = new ServerCom(portNumber[MON_RECOLHA_BAGAGEM]);
        scon.start();
        
        GenericIO.writelnString ("O serviço RecolhaBagagem foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");
        
        while(true)
        {   sconi = scon.accept();
            recolhaProxy = new ServerRecolhaBagagemProxy(sconi,recolhaInter);
            recolhaProxy.start();
        }
    }
    
}
