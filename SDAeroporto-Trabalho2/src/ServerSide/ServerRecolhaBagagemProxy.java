package ServerSide;

import sdaeroporto.RecolhaBagagemMain;

/**
 * Este tipo de dados define o thread agente prestador de serviço do monitor <i>RecolhaBagagem</i>
 * para a solução do problema <b>Rapsódia no Aeroporto</b>.
 * <p>
 * É responsável por ler o pedido do cliente, que lhe é passado utilizando o paradigma 
 * passagem de mensagens sobre sockets usando o protocolo TCP, delegar o seu processamento ao 
 * <i>ServerRecolhaBagagemInterface</i> e enviar uma mensagem de resposta de volta ao cliente.
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ServerRecolhaBagagemProxy extends ServerProxy{
    /**
     * Referência para o tipo de dados <i>RecolhaBagagemMain</i> que é responsável por
     * inicializar e estabelecer o lado do servidor do monitor <i>RecolhaBagagem</i>
     * 
     * @serialField recolhaMain
     */
    private final RecolhaBagagemMain recolhaMain;
    
    /**
     * Instanciação do interface ao monitor RecolhaBagagem
     * 
     * @param sconi canal de comunicação
     * @param recolhaInter interface ao RecolhaBagagem
     * @param recolhaMain referência para o tipo de dados <i>RecolhaBagagemMain</i>
     */
    public ServerRecolhaBagagemProxy(ServerCom sconi, ServerRecolhaBagagemInterface recolhaInter,RecolhaBagagemMain recolhaMain) {
        super(sconi,recolhaInter,"RecolhaBagagemProxy" );
        this.recolhaMain=recolhaMain;
    }  
     /**
     * Ciclo de vida do thread agente prestador de serviços do monitor <i>RecolhaBagagem</i>.
     */    
    @Override
    public void run(){
        if(super.requestAndReply())
            recolhaMain.close();
    }
    
    
}
