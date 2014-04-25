/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import static Estruturas.AuxInfo.MON_TRANSICAO_AEROPORTO;
import static Estruturas.AuxInfo.portNumber;
import Monitores.TransiçãoAeroporto;
import ServerSide.ServerTransicaoAeroportoInterface;
import ServerSide.ServerTransicaoAeroportoProxy;
import genclass.GenericIO;
import ServerSide.ServerCom;

/**
 *
 * @author Hugo
 */
public class TransicaoAeroportoMain {
    private ServerCom scon;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new TransicaoAeroportoMain().listening();
    }
    
    private void listening(){
        TransiçãoAeroporto recolha = new TransiçãoAeroporto();
        ServerTransicaoAeroportoInterface recolhaInter = new ServerTransicaoAeroportoInterface(recolha);
        ServerCom sconi;
        ServerTransicaoAeroportoProxy recolhaProxy;
        
        scon = new ServerCom(portNumber[MON_TRANSICAO_AEROPORTO]);
        scon.start();
        
        GenericIO.writelnString ("O serviço TransicaoAeroporto foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");
        
        while(true)
        {   sconi = scon.accept();
            recolhaProxy = new ServerTransicaoAeroportoProxy(sconi,recolhaInter,this);
            recolhaProxy.start();
        }
    }
    
    public void close(){
        scon.end();
        System.exit(0);
    }
    
}
