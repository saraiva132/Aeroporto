package ServerSide;

import sdaeroporto.AutocarroMain;

/**
 * Este tipo de dados define o thread agente prestador de serviço do monitor <i>Autocarro</i>
 * para a solução do problema <b>Rapsódia no Aeroporto</b>.
 * <p>
 * É responsável por ler o pedido do cliente, que lhe é passado utilizando o paradigma 
 * passagem de mensagens sobre sockets usando o protocolo TCP, delegar o seu processamento ao 
 * <i>ServerAutocarroInterface</i> e enviar uma mensagem de resposta de volta ao cliente.
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ServerAutocarroProxy extends ServerProxy{

    /**
     * Referência para o tipo de dados <i>AutocarroMain</i> que é responsável por
     * inicializar e estabelecer o lado do servidor do monitor <i>Autocarro</i>
     * 
     * @serialField autocarroMain
     */
    private final AutocarroMain autocarroMain;

    /**
     * Instanciação do interface ao Autocarro
     * 
     * @param sconi canal de comunicação
     * @param autoInter insterface ao Autocarro
     * @param autocarroMain referência para o tipo de dados <i>AutocarroMain</i>
     */
    public ServerAutocarroProxy(ServerCom sconi, ServerAutocarroInterface autoInter,AutocarroMain autocarroMain) {
        super(sconi,autoInter,"AutocarroProxy_");
        this.autocarroMain = autocarroMain;
    }      
    
    /**
     * Ciclo de vida do thread agente prestador de serviçoes do monitor <i>Autocarro</i>.
     */
    @Override
    public void run(){
        if(super.requestAndReply())
            autocarroMain.close();
    }   
}