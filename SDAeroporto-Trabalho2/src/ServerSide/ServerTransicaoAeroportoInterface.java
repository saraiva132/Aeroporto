/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerSide;

import static Estruturas.AuxInfo.CLOSE;
import static Estruturas.AuxInfo.GO_HOME;
import static Estruturas.AuxInfo.OK;
import static Estruturas.AuxInfo.PREPARE_NEXT_LEG;
import static Estruturas.AuxInfo.passMax;
import Message.MessageRequestException;
import Message.Request;
import Message.Response;
import Monitores.TransiçãoAeroporto;

/**
 *
 * @author Hugo
 */
public class ServerTransicaoAeroportoInterface implements ServerInterface{
    private TransiçãoAeroporto transicao;

    public ServerTransicaoAeroportoInterface(TransiçãoAeroporto transicao) {
        this.transicao = transicao;
    }
    
    /**
     *
     * @param request
     * @return
     * @throws MessageRequestException
     */
    @Override
    public Response processAndReply(Request request) throws MessageRequestException {
        Response response;
        int passageiroId=0;
        switch(request.getMethodName()){
            case GO_HOME:
                if(request.getArgs().length != 1)
                   throw new MessageRequestException("Formato do request GO_HOME inválido: "
                           + "espera-se 1 parametro!",request);
                passageiroId = (int) request.getArgs()[0];
                if(passageiroId <0 || passageiroId >=passMax)
                    throw new MessageRequestException("Id do passageiro inválido!",request);
                transicao.goHome(passageiroId);
                break;
                
            case PREPARE_NEXT_LEG:
                if(request.getArgs().length != 1)
                   throw new MessageRequestException("Formato do request PREPARE_NEXT_LEG inválido: "
                           + "espera-se 1 parametro!",request);
                passageiroId = (int) request.getArgs()[0];
                if(passageiroId <0 || passageiroId >=passMax)
                    throw new MessageRequestException("Id do passageiro inválido!",request);
                transicao.prepareNextLeg(passageiroId);
                break;
            case CLOSE:
                return new Response(OK,transicao.shutdownMonitor());
            default:
                throw new MessageRequestException("Tipo de request inválido!",request);
                
        }
        return new Response(OK,null);
    }
}
