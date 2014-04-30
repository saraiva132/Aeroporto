package ServerSide;

import sdaeroporto.TransicaoAeroportoMain;

/**
 * Este tipo de dados define o thread agente prestador de serviço do monitor <i>TransicaoAeroporto</i>
 * para a solução do problema <b>Rapsódia no Aeroporto</b>.
 * <p>
 * É responsável por ler o pedido do cliente, que lhe é passado utilizando o paradigma 
 * passagem de mensagens sobre sockets usando o protocolo TCP, delegar o seu processamento ao 
 * <i>ServerTransicaoAeroportoInterface</i> e enviar uma mensagem de resposta de volta ao cliente.
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ServerTransicaoAeroportoProxy extends ServerProxy{
    /**
     * Referência para o tipo de dados <i>TransicaoAeroportoMain</i> que é responsável por
     * inicializar e estabelecer o lado do servidor do monitor <i>TransicaoAeroporto</i>
     * 
     * @serialField transicaoMain
     */
    private final TransicaoAeroportoMain transicaoMain;

    /**
     * Instanciação do interface ao monitor TransicaoAeroporto
     * 
     * @param sconi canal de comunicação
     * @param transicaoInter interface ao TransicaoAeroporto
     * @param aeroportoMain referência para o tipo de dados <i>TransicaoAeroportoMain</i>
     */
    public ServerTransicaoAeroportoProxy(ServerCom sconi, ServerTransicaoAeroportoInterface transicaoInter,TransicaoAeroportoMain aeroportoMain) {
        super(sconi,transicaoInter,"TransicaoAeroportoProxy" );
        this.transicaoMain=aeroportoMain;
    }
     /**
     * Ciclo de vida do thread agente prestador de serviços do monitor <i>TransicaoAeroporto</i>.
     */
    @Override
    public void run(){
        if(super.requestAndReply())
            transicaoMain.close();
    }
    
   
}
