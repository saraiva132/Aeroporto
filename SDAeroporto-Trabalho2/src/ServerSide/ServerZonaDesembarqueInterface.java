/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerSide;

import static Estruturas.AuxInfo.CLOSE;
import static Estruturas.AuxInfo.NO_MORE_BAGS_TO_COLLECT;
import static Estruturas.AuxInfo.OK;
import static Estruturas.AuxInfo.TAKE_A_REST;
import static Estruturas.AuxInfo.WHAT_SHOULD_I_DO;
import static Estruturas.AuxInfo.bagMax;
import static Estruturas.AuxInfo.passMax;
import Message.MessageRequestException;
import Message.Request;
import Message.Response;
import Monitores.ZonaDesembarque;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ServerZonaDesembarqueInterface implements ServerInterface{
    private ZonaDesembarque desembarque;

    public ServerZonaDesembarqueInterface(ZonaDesembarque zona) {
        this.desembarque = zona;
    }
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
            case CLOSE:
                response= new Response(OK,desembarque.shutdownMonitor());
                break;
            default:
                throw new MessageRequestException("Tipo de request inválido!",request);
                
        }
        return response;
    }
    
}
