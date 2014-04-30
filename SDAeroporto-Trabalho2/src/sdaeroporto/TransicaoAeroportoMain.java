/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import static Estruturas.Globals.MON_TRANSICAO_AEROPORTO;
import static Estruturas.Globals.portNumber;
import Monitores.TransiçãoAeroporto;
import ServerSide.ServerTransicaoAeroportoInterface;
import ServerSide.ServerTransicaoAeroportoProxy;
import genclass.GenericIO;
import ServerSide.ServerCom;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class TransicaoAeroportoMain {
    private ServerCom listeningSocket;
    private boolean canEnd=false;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new TransicaoAeroportoMain().listening();
    }
    
    private void listening(){
        TransiçãoAeroporto transicao = new TransiçãoAeroporto();
        ServerTransicaoAeroportoInterface transicaoInter = new ServerTransicaoAeroportoInterface(transicao);
        ServerCom commSocket;
        ServerTransicaoAeroportoProxy transicaoProxy;
        
        listeningSocket = new ServerCom(portNumber[MON_TRANSICAO_AEROPORTO]);
        listeningSocket.start();
        
        GenericIO.writelnString ("O serviço TransicaoAeroporto foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");
        
        while(!canEnd)
        {   commSocket = listeningSocket.accept();
            transicaoProxy = new ServerTransicaoAeroportoProxy(commSocket,transicaoInter,this);
            transicaoProxy.start();
        }
    }
    
    public void close(){
        canEnd=true;
        //scon.end();
        System.exit(0);
    }
    
}
