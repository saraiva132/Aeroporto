package sdaeroporto;

import Estruturas.Globals;
import static Estruturas.Globals.MON_TRANSFERENCIA_TERMINAL;
import static Estruturas.Globals.portNumber;
import Monitores.TransferenciaTerminal;
import ServerSide.ServerTransferenciaTerminalInterface;
import ServerSide.ServerTransferenciaTerminalProxy;
import genclass.GenericIO;
import ServerSide.ServerCom;

/**
 * Este tipo de dados simula a solução do lado do servidor referente ao monitor <i>TransferenciaTerminal</i> do problema
 * <b>Rapsódia no Aeroporto</b>.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class TransferenciaTerminalMain {
    /**
     * Canal de escuta.
     * 
     * @serialField listeningSocket
     */
    private ServerCom listenngSocket;
    /**
     * Programa Principal.
     */
    public static void main(String[] args) {
        Globals.xmlParser();
        new TransferenciaTerminalMain().listening();
    }
    /**
     * Responsável pela inicialização e instanciação do agente prestador de serviço, do monitor e da interface ao <i>TransferenciaTerminal</i> e ainda do canal de escuta.
     * <p>
     * É responsável também pelo processo de escuta e do lançamento do agente prestador de serviço.
     */
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
        
        while(true)
        {   commSocket = listenngSocket.accept();
            transferenciaProxy = new ServerTransferenciaTerminalProxy(commSocket,transferenciaInter,this);
            transferenciaProxy.start();
        }
    }
    /**
     * Terminar a execução do serviço referente ao monitor <i>TransferenciaTerminal</i>.
     */
    public void close(){
        System.exit(0);
    }
    
}
