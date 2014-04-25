/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerSide;

import static Estruturas.AuxInfo.*;
import Message.MessageRequestException;
import Message.Request;
import Message.Response;
import Monitores.Autocarro;

/**
 *
 * @author Hugo
 */
public class ServerAutocarroInterface {
    private Autocarro auto;

    public ServerAutocarroInterface(Autocarro auto) {
        this.auto = auto;
    }
    
    protected Response processAndReply(Request request) throws MessageRequestException{
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
               break;
           case GO_TO_DEPARTURE_TERMINAL:
           case GO_TO_ARRIVAL_TERMINAL:
           case PARK_THE_BUS:
           case PARK_THE_BUS_AND_LET_PASS_OFF:
               break;
           default:
               throw new MessageRequestException("Tipo de request inválido!",request);
               
       }
       
       switch(request.getMethodName()){
            case ENTER_THE_BUS:
                auto.enterTheBus(ticketID, passageiroId);                
                break;
                
            case LEAVE_THE_BUS:
                auto.leaveTheBus(passageiroId, ticketID);   
                break;
                
            case ANNOUNCING_BUS_BOARDING_WAITING:
                auto.announcingBusBoardingWaiting(bilhetesVendidos);                  
                break;
                
            case GO_TO_DEPARTURE_TERMINAL:
                auto.goToDepartureTerminal();
                break;
                
            case GO_TO_ARRIVAL_TERMINAL:
                auto.goToArrivalTerminal();
                break;
                
            case PARK_THE_BUS:
                auto.parkTheBus();
           case PARK_THE_BUS_AND_LET_PASS_OFF:
               auto.parkTheBusAndLetPassOff();
                    
        }
        Response response = new Response(OK,null);
        return response;
    }
    
}
