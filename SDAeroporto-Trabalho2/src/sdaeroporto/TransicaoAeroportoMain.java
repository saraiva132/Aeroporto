package sdaeroporto;

import static Estruturas.Globals.MON_TRANSICAO_AEROPORTO;
import static Estruturas.Globals.portNumber;
import Monitores.TransiçãoAeroporto;
import ServerSide.ServerTransicaoAeroportoInterface;
import ServerSide.ServerTransicaoAeroportoProxy;
import genclass.GenericIO;
import ServerSide.ServerCom;

/**
 * Este tipo de dados simula a solução do lado do servidor referente ao monitor <i>TransicaoAeroporto</i> do problema
 * <b>Rapsódia no Aeroporto</b>.
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class TransicaoAeroportoMain {
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
        new TransicaoAeroportoMain().listening();
    }
    /**
     * Responsável pela inicialização e instanciação do agente prestador de serviço, do monitor e da interface ao <i>TransicaoAeroporto</i> e ainda do canal de escuta.
     * <p>
     * É responsável também pelo processo de escuta e do lançamento do agente prestador de serviço.
     */
    private void listening(){
        TransiçãoAeroporto transicao = new TransiçãoAeroporto();
        ServerTransicaoAeroportoInterface transicaoInter = new ServerTransicaoAeroportoInterface(transicao);
        ServerCom commSocket;
        ServerTransicaoAeroportoProxy transicaoProxy;
        
        listeningSocket = new ServerCom(portNumber[MON_TRANSICAO_AEROPORTO]);
        listeningSocket.start();
        
        GenericIO.writelnString ("O serviço TransicaoAeroporto foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");
        
        while(true)
        {   commSocket = listeningSocket.accept();
            transicaoProxy = new ServerTransicaoAeroportoProxy(commSocket,transicaoInter,this);
            transicaoProxy.start();
        }
    }
    /**
     * Terminar a execução do serviço referente ao monitor <i>TransicaoAeroporto</i>.
     */
    public void close(){
        System.exit(0);
    }
    
}
