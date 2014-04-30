/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerSide;

import sdaeroporto.ZonaDesembarqueMain;

/**
 * Este tipo de dados define o thread agente prestador de serviço do monitor <i>ZonaDesembarque</i>
 * para a solução do problema <b>Rapsódia no Aeroporto</b>.
 * <p>
 * É responsável por ler o pedido do cliente, que lhe é passado utilizando o paradigma 
 * passagem de mensagens sobre sockets usando o protocolo TCP, delegar o seu processamento ao 
 * <i>ServerZonaDesembarqueInterface</i> e enviar uma mensagem de resposta de volta ao cliente.
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ServerZonaDesembarqueProxy extends ServerProxy{
    /**
     * Referência para o tipo de dados <i>ZonaDesembarqueMain</i> que é responsável por
     * inicializar e estabelecer o lado do servidor do monitor <i>ZonaDesembarque</i>
     * 
     * @serialField desembarqueMain
     */
    private final ZonaDesembarqueMain desembarqueMain;
    
    /**
     * Instanciação do interface ao monitor ZonaDesembarque.
     * 
     * @param sconi canal de comunicação
     * @param desembarqueInter interface ao ZonaDesembarque
     * @param deesembarqueMain referência para o tipo de dados <i>ZonaDesembarqueMain</i>
     */
    public ServerZonaDesembarqueProxy(ServerCom sconi, ServerZonaDesembarqueInterface desembarqueInter,ZonaDesembarqueMain deesembarqueMain) {
        super(sconi,desembarqueInter,"ZonaDesembarqueProxy");
        this.desembarqueMain=deesembarqueMain;
    }
     /**
     * Ciclo de vida do thread agente prestador de serviços do monitor <i>ZonaDesembarque</i>.
     */
    @Override
    public void run(){
       if(super.requestAndReply())
           desembarqueMain.close();
    }
    
    
    
}
