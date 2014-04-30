package ServerSide;

import java.io.PrintStream;
import sdaeroporto.LoggingMain;
/**
 * Este tipo de dados define o thread agente prestador de serviço do monitor <i>Logging</i>
 * para a solução do problema <b>Rapsódia no Aeroporto</b>.
 * <p>
 * É responsável por ler o pedido do cliente, que lhe é passado utilizando o paradigma 
 * passagem de mensagens sobre sockets usando o protocolo TCP, delegar o seu processamento ao 
 * <i>ServerLoggingInterface</i> e enviar uma mensagem de resposta de volta ao cliente.
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ServerLoggingProxy extends ServerProxy{

    /**
     * Referencia para um Printstream da consola
     * 
     * @serialField stdout
     */
    private static PrintStream stdout;
    /**
     * Referência para o tipo de dados <i>LoggingMain</i> que é responsável por
     * inicializar e estabelecer o lado do servidor do monitor <i>Logging</i>
     * 
     * @serialField logMain
     */
    private final LoggingMain logMain;

    /**
     * Instanciação do interface ao monitor Logging
     *
     * @param sconi canal de comunicação
     * @param logInter insterface ao Logging
     * @param stdout referencia para um printstream da consola
     */
    public ServerLoggingProxy(ServerCom sconi, ServerLoggingInterface logInter, PrintStream stdout,LoggingMain logMain) {
        super(sconi,logInter,"LoggingProxy");
        this.logMain = logMain;
        this.stdout = stdout;
    }
     /**
     * Ciclo de vida do thread agente prestador de serviços do monitor <i>Logging</i>.
     */
    @Override
    public void run(){
        if(super.requestAndReply())
            logMain.close();
    }
    
}
