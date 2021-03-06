
package ServerSide;

import static Estruturas.Globals.*;
import Message.MessageRequestException;
import Message.Request;
import Message.Response;
import Monitores.Autocarro;

/**
 * Este tipo de dados define o interface ao monitor <i>Autocarro</i> do problema <b>Rapsódia no Aeroporto</b>.
 * <p>
 * Está encarregue de para cada mensagem do tipo <i>Request</i> validá-la e realizar a operação pedida na mesma
 * sobre o montior <i>Autocarro</i> devolvendo uma mensagem do tipo <i>Response</i> que que contém (no caso de haver) 
 * o objecto devolvido pela operação que foi invocada no monitor.  
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ServerAutocarroInterface implements ServerInterface {
    /**
     * Monitor Autocarro (representa o serviço a ser prestado)
     * 
     * @serialField auto
     */
    private final Autocarro auto;

    /**
     * Instanciação do interface ao monitor Autocarro
     * 
     * @param auto Monitor Autocarro
     */
    public ServerAutocarroInterface(Autocarro auto) {
        this.auto = auto;
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
       int ticketID=0,passageiroId=0,bilhetesVendidos=0;
        
       /*validacao da mensagem recebida*/       
       switch(request.getMethodName()){
           case ENTER_THE_BUS:
               if(request.getArgs().length != 2)
                   throw new MessageRequestException("Formato do request ENTER_THE_BUS inválido: "
                           + "esperam-se 2 parametros!",request);
               
               if(! (request.getArgs()[0] instanceof Integer))
                   throw new MessageRequestException("Leitura de tipo de objecto ticketID inválido!",request);
               if(! (request.getArgs()[1] instanceof Integer))
                   throw new MessageRequestException("Leitura de tipo de objecto passageiroId inválido!",request);
               
               ticketID = (int) request.getArgs()[0];
               passageiroId = (int) request.getArgs()[1];
               
               if(ticketID<0 || ticketID >= lotação)
                   throw new MessageRequestException("Id do ticket inválido!",request);
               if(passageiroId<0 || passageiroId>=passMax)
                   throw new MessageRequestException("Id do passageiro inválido!",request);
               auto.enterTheBus(ticketID, passageiroId);
               break;
           case LEAVE_THE_BUS: 
               if(request.getArgs().length != 2)
                   throw new MessageRequestException("Formato do request LEAVE_THE_BUS inválido: "
                           + "esperam-se 2 parametros!",request);
               
               if(! (request.getArgs()[0] instanceof Integer))
                   throw new MessageRequestException("Leitura de tipo de objecto passageiroId inválido!",request);
               if(! (request.getArgs()[1] instanceof Integer))
                   throw new MessageRequestException("Leitura de tipo de objecto tickedID inválido!",request);
               
               passageiroId = (int) request.getArgs()[0];
               ticketID = (int) request.getArgs()[1];
               
               if(ticketID<0 || ticketID >= lotação)
                   throw new MessageRequestException("Id do ticket inválido!",request);
               if(passageiroId<0 || passageiroId>=passMax)
                   throw new MessageRequestException("Id do passageiro inválido!",request);
               auto.leaveTheBus(passageiroId, ticketID); 
               break;
           case ANNOUNCING_BUS_BOARDING_WAITING:
               if(request.getArgs().length != 1)
                   throw new MessageRequestException("Formato do request ANNOUNCING_BUS_BOARDING_WAITING inválido: "
                           + "espera-se 1 parametro!",request);
               
               if(! (request.getArgs()[0] instanceof Integer))
                   throw new MessageRequestException("Leitura de tipo de objecto bilhetesVendidos inválido!",request);
               
               bilhetesVendidos = (int) request.getArgs()[0];
               if(bilhetesVendidos<0 || bilhetesVendidos>=passMax)
                   throw new MessageRequestException("Número de bilhetes vendidos inválido!",request);
               auto.announcingBusBoardingWaiting(bilhetesVendidos); 
               break;
           case GO_TO_DEPARTURE_TERMINAL:
               if(request.getArgs().length != 0)
                   throw new MessageRequestException("Formato do request ENTER_THE_BUS inválido: "
                           + "esperam-se 0 parametros!",request);
               auto.goToDepartureTerminal();
               break;
           case GO_TO_ARRIVAL_TERMINAL:
               if(request.getArgs().length != 0)
                   throw new MessageRequestException("Formato do request ENTER_THE_BUS inválido: "
                           + "esperam-se 0 parametros!",request);
               auto.goToArrivalTerminal();
               break;
           case PARK_THE_BUS:
               if(request.getArgs().length != 0)
                   throw new MessageRequestException("Formato do request ENTER_THE_BUS inválido: "
                           + "esperam-se 0 parametros!",request);
               auto.parkTheBus();
               break;
           case PARK_THE_BUS_AND_LET_PASS_OFF:
               if(request.getArgs().length != 0)
                   throw new MessageRequestException("Formato do request ENTER_THE_BUS inválido: "
                           + "esperam-se 0 parametros!",request);
               auto.parkTheBusAndLetPassOff();
               break;
           case SHUTDOWN_MONITOR:
               return new Response(OK,request.getSerial(),auto.shutdownMonitor());
           default:
               throw new MessageRequestException("Tipo de request inválido!",request);
       }
        return new Response(OK,request.getSerial(),null);
    }
    
}
