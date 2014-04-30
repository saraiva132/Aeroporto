package ServerSide;

import sdaeroporto.TransferenciaTerminalMain;


/**
 * Este tipo de dados define o thread agente prestador de serviço do monitor <i>TransferenciaTerminal</i>
 * para a solução do problema <b>Rapsódia no Aeroporto</b>.
 * <p>
 * É responsável por ler o pedido do cliente, que lhe é passado utilizando o paradigma 
 * passagem de mensagens sobre sockets usando o protocolo TCP, delegar o seu processamento ao 
 * <i>ServerTransferenciaTerminalInterface</i> e enviar uma mensagem de resposta de volta ao cliente.
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ServerTransferenciaTerminalProxy extends ServerProxy{
    /**
     * Referência para o tipo de dados <i>TransferenciaTerminalMain</i> que é responsável por
     * inicializar e estabelecer o lado do servidor do monitor <i>TransferenciaTerminal</i>
     * 
     * @serialField transferenciaMain
     */
    private final TransferenciaTerminalMain transferenciaMain;
    
    /**
     * Instancação do interface ao monitor TransferenciaTerminal
     * 
     * @param sconi canal de comunicação
     * @param transferenciaInter interface ao TransferenciaTerminal
     * @param transferenciaMain referência para o tipo de dados <i>TransferenciaTerminalMain</i>
     */
    public ServerTransferenciaTerminalProxy(ServerCom sconi, ServerTransferenciaTerminalInterface transferenciaInter,TransferenciaTerminalMain transferenciaMain) {
        super(sconi,transferenciaInter,"TransferenciaTerminalProxy" );
        this.transferenciaMain=transferenciaMain;
    }
    
    
     /**
     * Ciclo de vida do thread agente prestador de serviços do monitor <i>TransferenciaTerminal</i>.
     */
    @Override
    public void run(){
        if(super.requestAndReply())
            transferenciaMain.close();
    }
   
}
