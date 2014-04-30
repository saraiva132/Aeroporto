package ServerSide;

import static Estruturas.Globals.CARRY_IT_TO_APPROPRIATE_STORE;
import static Estruturas.Globals.SHUTDOWN_MONITOR;
import static Estruturas.Globals.GO_COLLECT_A_BAG;
import static Estruturas.Globals.OK;
import static Estruturas.Globals.REPORT_MISSING_BAGS;
import static Estruturas.Globals.RESET_NOMORE_BAGS;
import static Estruturas.Globals.passMax;
import Estruturas.Mala;
import Message.MessageRequestException;
import Message.Request;
import Message.Response;
import Monitores.RecolhaBagagem;

/**
 * Este tipo de dados define o interface ao monitor <i>RecolhaBagagem</i> do problema <b>Rapsódia no Aeroporto</b>.
 * <p>
 * Está encarregue de para cada mensagem do tipo <i>Request</i> validá-la e realizar a operação pedida na mesma
 * sobre o montior <i>RecolhaBagagem</i> devolvendo uma mensagem do tipo <i>Response</i> que que contém (no caso de haver) 
 * o objecto devolvido pela operação que foi invocada no monitor. 
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ServerRecolhaBagagemInterface implements ServerInterface{
    /**
     * Monitor RecolhaBagagem (representa o serviço a ser prestado)
     * 
     * @serialField recolha
     */
    private final RecolhaBagagem recolha;

    /**
     * Instanciação do interface ao monitor RecolhaBagagem
     * 
     * @param recolha Monitor RecolhaBagagem
     */
    public ServerRecolhaBagagemInterface(RecolhaBagagem recolha) {
        this.recolha = recolha;
    }
    /**
     * Processamento das mensagens através da execução da operação correspondente.
     * Geração de uma mensagem de resposta.
     * 
     * @param request mensagem com o pedido e (eventualmente) os parâmetros necessários para a realização da operação requerida sobre o monitor
     * @return mensagem de resposta
     * @throws MessageRequestException 
     */
    @Override
    public Response processAndReply(Request request) throws MessageRequestException{
        Response response = null;
        
        switch(request.getMethodName()){
            case GO_COLLECT_A_BAG:
                if(request.getArgs().length != 1)
                    throw new MessageRequestException("Formato do request GO_COLLECT_A_BAG inválido: \"\n" +
"                           + \"espera-se 1 parametro!",request);
                if(!(request.getArgs()[0] instanceof Integer))
                    throw new MessageRequestException("Leitura de tipo de objecto bagID inválido!",request);
                int bagID = (int) request.getArgs()[0];
                
                if(bagID <0 || bagID>passMax)
                    throw new MessageRequestException("Id da bag inválido!",request);
                
                response = new Response(OK,request.getSerial(),recolha.goCollectABag(bagID));
                break;
                
            case CARRY_IT_TO_APPROPRIATE_STORE:
                if(request.getArgs().length != 1)
                    throw new MessageRequestException("Formato do request CARRY_IT_TO_APPROPRIATE_STORE inválido: \"\n" +
"                           + \"espera-se 1 parametro!",request);
                if( !(request.getArgs()[0] instanceof Mala) && request.getArgs()[0] != null )
                    throw new MessageRequestException("Leitura de tipo de objecto mala inválido!",request);
                
                Mala mala = (Mala) request.getArgs()[0];
                
                response = new Response(OK,request.getSerial(),recolha.carryItToAppropriateStore(mala));
                break;
                
            case RESET_NOMORE_BAGS:
                recolha.resetNoMoreBags();
                response = new Response(OK,request.getSerial(),null);
                break;
                
            case REPORT_MISSING_BAGS:
                if(request.getArgs().length != 2)
                    throw new MessageRequestException("Formato do request REPORT_MISSING_BAGS inválido: \"\n" +
"                           + \"esperam-se 2 parametros!",request);
                if(!(request.getArgs()[0] instanceof Integer))
                    throw new MessageRequestException("Leitura de tipo de objecto passId inválido!",request);
                if(!(request.getArgs()[1] instanceof Integer))
                    throw new MessageRequestException("Leitura de tipo de objecto nMalasPerdidas inválido!",request);
                int passId = (int) request.getArgs()[0];
                int nMalasPerdidas = (int) request.getArgs()[1];
                if(passId < 0 || passId >= passMax)
                    throw new MessageRequestException("Id de passageiro inválido!",request);
                recolha.reportMissingBags(passId, nMalasPerdidas);
                response = new Response(OK,request.getSerial(),null);
                break;
            case SHUTDOWN_MONITOR:
                response= new Response(OK,request.getSerial(),recolha.shutdownMonitor());               
                break;
            default:
                throw new MessageRequestException("Tipo de request inválido!",request);
        }        
        return response;
    }
    
}
