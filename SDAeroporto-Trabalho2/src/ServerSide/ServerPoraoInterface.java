package ServerSide;

import static Estruturas.Globals.*;
import Estruturas.Mala;
import Message.MessageRequestException;
import Message.Request;
import Message.Response;
import Monitores.Porao;

/**
 * Este tipo de dados define o interface ao monitor <i>Porao</i> do problema <b>Rapsódia no Aeroporto</b>.
 * <p>
 * Está encarregue de para cada mensagem do tipo <i>Request</i> validá-la e realizar a operação pedida na mesma
 * sobre o montior <i>Porao</i> devolvendo uma mensagem do tipo <i>Response</i> que que contém (no caso de haver) 
 * o objecto devolvido pela operação que foi invocada no monitor.  
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ServerPoraoInterface implements ServerInterface{
    /**
     * Monitor Porao (representa o serviço a ser prestado)
     * 
     * @serialField porao
     */
    private final Porao porao;

    /**
     * Instanciação do interface ao monitor Porao
     * 
     * @param porao Monitor Porao
     */
    public ServerPoraoInterface(Porao porao) {
        this.porao = porao;
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
       Response response=null;
       switch(request.getMethodName()){
           case TRY_TO_COLLECT_A_BAG:
               if(request.getArgs().length != 0)
                   throw new MessageRequestException("Formato do request TRY_TO_COLLECT_A_BAG inválido: "
                           + "esperam-se 0 parametros!",request);
                response = new Response(OK,request.getSerial(),porao.tryToCollectABag());
               break;
           case SEND_LUGAGES:
               Mala [] malas = new Mala[request.getArgs().length];
            for(int i = 0; i < request.getArgs().length;i++){
                if(!(request.getArgs()[i] instanceof Mala))
                    throw new MessageRequestException("Leitura de tipo de objecto mala["+i+"] inválido!",request);
                else{
                    malas[i] = ((Mala) request.getArgs()[i]);                                      
                }
            }
            porao.sendLuggages(malas);
            response = new Response(OK,request.getSerial(),null);
               break;
           case SHUTDOWN_MONITOR:
               response= new Response(OK,request.getSerial(),porao.shutdownMonitor());
               break;
           default:
               throw new MessageRequestException("Tipo de request inválido!",request);
       }
        return response;
    }
}
