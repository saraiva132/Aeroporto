package ServerSide;

import static Estruturas.Globals.SHUTDOWN_MONITOR;
import static Estruturas.Globals.NO_MORE_BAGS_TO_COLLECT;
import static Estruturas.Globals.OK;
import static Estruturas.Globals.TAKE_A_REST;
import static Estruturas.Globals.WHAT_SHOULD_I_DO;
import static Estruturas.Globals.bagMax;
import static Estruturas.Globals.passMax;
import Message.MessageRequestException;
import Message.Request;
import Message.Response;
import Monitores.ZonaDesembarque;

/**
 * Este tipo de dados define o interface ao monitor <i>ZonaDesembarque</i> do problema <b>Rapsódia no Aeroporto</b>.
 * <p>
 * Está encarregue de para cada mensagem do tipo <i>Request</i> validá-la e realizar a operação pedida na mesma
 * sobre o montior <i>ZonaDesembarque</i> devolvendo uma mensagem do tipo <i>Response</i> que que contém (no caso de haver) 
 * o objecto devolvido pela operação que foi invocada no monitor.  
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ServerZonaDesembarqueInterface implements ServerInterface{
    /**
     * Monitor ZonaDesembarque (representa o serviço a ser prestado)
     * 
     * @serialField desembarque
     */
    private final ZonaDesembarque desembarque;

    /**
     * Instanciação do interface ao monitor ZonaDesembarque
     * 
     * @param zona Monitor ZonaDesembarque
     */
    public ServerZonaDesembarqueInterface(ZonaDesembarque zona) {
        this.desembarque = zona;
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
    public Response processAndReply(Request request) throws MessageRequestException {
        Response response = null;
        switch(request.getMethodName()){
            case TAKE_A_REST:
                desembarque.takeARest();
                response = new Response(OK,null);
                break;
                
            case WHAT_SHOULD_I_DO:
                if(request.getArgs().length != 3)
                    throw new MessageRequestException("Formato do request REPORT_MISSING_BAGS inválido: \"\n" +
"                           + \"esperam-se 3 parametros!",request);
                if(!(request.getArgs()[0] instanceof Integer))
                    throw new MessageRequestException("Leitura de tipo de objecto passId inválido!",request);
                if(!(request.getArgs()[1] instanceof Boolean))
                    throw new MessageRequestException("Leitura de tipo de objecto dest inválido!",request);
                if(!(request.getArgs()[2] instanceof Integer))
                    throw new MessageRequestException("Leitura de tipo de nMalas passId inválido!",request);
                
                int passId=  (int) request.getArgs()[0];
                boolean dest = (boolean) request.getArgs()[1];
                int nMalas = (int) request.getArgs()[2];
                if(passId<0 || passId>= passMax)
                    throw new MessageRequestException("Id do passageiro inválido",request);
                if(nMalas<0 || nMalas>bagMax)
                    throw new MessageRequestException("Número de malas inválido",request);
                response = new Response(OK,desembarque.whatShouldIDo(passId, dest, nMalas));
                break;
            case NO_MORE_BAGS_TO_COLLECT:
                desembarque.noMoreBagsToCollect();
                response = new Response(OK,null);
                break;
            case SHUTDOWN_MONITOR:
                response= new Response(OK,desembarque.shutdownMonitor());
                break;
            default:
                throw new MessageRequestException("Tipo de request inválido!",request);
                
        }
        return response;
    }
    
}
