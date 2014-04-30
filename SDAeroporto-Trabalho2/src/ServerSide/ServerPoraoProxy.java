package ServerSide;

import sdaeroporto.PoraoMain;

/**
 * Este tipo de dados define o thread agente prestador de serviço do monitor <i>Poroa</i>
 * para a solução do problema <b>Rapsódia no Aeroporto</b>.
 * <p>
 * É responsável por ler o pedido do cliente, que lhe é passado utilizando o paradigma 
 * passagem de mensagens sobre sockets usando o protocolo TCP, delegar o seu processamento ao 
 * <i>ServerPoraoInterface</i> e enviar uma mensagem de resposta de volta ao cliente.
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ServerPoraoProxy extends ServerProxy{
    /**
     * Referência para o tipo de dados <i>PoraoMain</i> que é responsável por
     * inicializar e estabelecer o lado do servidor do monitor <i>Porao</i>
     * 
     * @serialField poraoMain
     */
    private final PoraoMain poraoMain;
    
    /**
     * Instanciação do interface ao monitor Porao
     * 
     * @param sconi canal de comunicação
     * @param poraoInter interface ao Porao
     * @param poraoMain referência para o tipo de dados <i>PoraoMain</i>
     */
    public ServerPoraoProxy(ServerCom sconi, ServerPoraoInterface poraoInter, PoraoMain poraoMain) {
        super(sconi,poraoInter,"PoraoProxy");
        this.poraoMain=poraoMain;
    }
     /**
     * Ciclo de vida do thread agente prestador de serviços do monitor <i>Porao</i>.
     */
    @Override
    public void run(){
        if( super.requestAndReply() )
            poraoMain.close();
    }
    
}
