package sdaeroporto;

import static Estruturas.Globals.MON_LOGGING;
import static Estruturas.Globals.portNumber;
import Monitores.Logging;
import ServerSide.ServerLoggingInterface;
import ServerSide.ServerLoggingProxy;
import java.io.PrintStream;
import ServerSide.ServerCom;

/**
 * Este tipo de dados simula a solução do lado do servidor referente ao monitor <i>Logging</i> do problema
 * <b>Rapsódia no Aeroporto</b>.
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class LoggingMain {
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
        new LoggingMain().listening();
    }
    /**
     * Responsável pela inicialização e instanciação do agente prestador de serviço, do monitor e da interface ao <i>Logging</i> e ainda do canal de escuta.
     * <p>
     * É responsável também pelo processo de escuta e do lançamento do agente prestador de serviço.
     */
    public void listening(){
        
        PrintStream stdout = System.out;
        Logging log = new Logging();
        ServerLoggingInterface logInter = new ServerLoggingInterface(log);
        ServerCom commSocket;
        ServerLoggingProxy logProxy;
        listeningSocket = new ServerCom(portNumber[MON_LOGGING]);
        listeningSocket.start();
        stdout.println("O serviço Logging foi estabelecido!");
        stdout.println("O servidor esta em escuta.");

        while (true) {
            commSocket = listeningSocket.accept();
            logProxy = new ServerLoggingProxy(commSocket, logInter,stdout,this);
            logProxy.start();
        }
    }
    /**
     * Terminar a execução do serviço referente ao monitor <i>Logging</i>.
     */
    public void close(){
        System.exit(0);
    }

}
