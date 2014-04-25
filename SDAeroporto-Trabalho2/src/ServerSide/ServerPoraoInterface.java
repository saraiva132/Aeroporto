/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerSide;

import static Estruturas.AuxInfo.*;
import Estruturas.Mala;
import Message.MessageRequestException;
import Message.Request;
import Message.Response;
import Monitores.Porao;

/**
 *
 * @author Hugo
 */
public class ServerPoraoInterface implements ServerInterface{
    private Porao porao;

    public ServerPoraoInterface(Porao porao) {
        this.porao = porao;
    }
    
    /**
     *
     * @param request
     * @return
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
                response = new Response(OK,porao.tryToCollectABag());
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
            response = new Response(OK,null);
               break;
           case CLOSE:
               response= new Response(OK,porao.shutdownMonitor());
               break;
           default:
               throw new MessageRequestException("Tipo de request inválido!",request);
       }
        return response;
    }
}
