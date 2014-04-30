/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import Estruturas.Globals;
import static Estruturas.Globals.MON_ZONA_DESEMBARQUE;
import static Estruturas.Globals.portNumber;
import Monitores.ZonaDesembarque;
import ServerSide.ServerZonaDesembarqueInterface;
import ServerSide.ServerZonaDesembarqueProxy;
import genclass.GenericIO;
import ServerSide.ServerCom;

/**
 * Este tipo de dados simula a solução do lado do servidor referente ao monitor <i>ZonaDesembarque</i> do problema
 * <b>Rapsódia no Aeroporto</b>.
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ZonaDesembarqueMain {
    /**
     * Canal de escuta.
     * 
     * @serialField listeningSocket
     */
    private ServerCom listeningSocket;
    /**
     * Programa Principal.
     */
    public static void main(String[] args) {
        Globals.xmlParser();
        new ZonaDesembarqueMain().listening();
    }
    /**
     * Responsável pela inicialização e instanciação do agente prestador de serviço, do monitor e da interface ao <i>ZonaDesembarques</i> e ainda do canal de escuta.
     * <p>
     * É responsável também pelo processo de escuta e do lançamento do agente prestador de serviço.
     */
    private void listening(){
        ZonaDesembarque desembarque = new ZonaDesembarque();
        ServerZonaDesembarqueInterface desembarqueInter = new ServerZonaDesembarqueInterface(desembarque);
        ServerCom commSocket;
        ServerZonaDesembarqueProxy desembarqueProxy;
        
        listeningSocket = new ServerCom(portNumber[MON_ZONA_DESEMBARQUE]);
        listeningSocket.start();
        
        GenericIO.writelnString ("O serviço ZonaDesembarque foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");
        
        while(true)
        {   commSocket = listeningSocket.accept();
            desembarqueProxy = new ServerZonaDesembarqueProxy(commSocket,desembarqueInter,this);
            desembarqueProxy.start();
        }
    }
    /**
     * Terminar a execução do serviço referente ao monitor <i>ZonaDesembarque</i>.
     */
    public void close(){
        System.exit(0);
    }
    
}
