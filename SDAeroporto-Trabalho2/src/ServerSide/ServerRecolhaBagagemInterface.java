/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerSide;

import static Estruturas.AuxInfo.CARRY_IT_TO_APPROPRIATE_STORE;
import static Estruturas.AuxInfo.CLOSE;
import static Estruturas.AuxInfo.GO_COLLECT_A_BAG;
import static Estruturas.AuxInfo.OK;
import static Estruturas.AuxInfo.REPORT_MISSING_BAGS;
import static Estruturas.AuxInfo.RESET_NOMORE_BAGS;
import static Estruturas.AuxInfo.passMax;
import Estruturas.Mala;
import Message.MessageRequestException;
import Message.Request;
import Message.Response;
import Monitores.RecolhaBagagem;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ServerRecolhaBagagemInterface implements ServerInterface{
    private RecolhaBagagem recolha;

    public ServerRecolhaBagagemInterface(RecolhaBagagem recolha) {
        this.recolha = recolha;
    }
    
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
                
                response = new Response(OK,recolha.goCollectABag(bagID));
                break;
                
            case CARRY_IT_TO_APPROPRIATE_STORE:
                if(request.getArgs().length != 1)
                    throw new MessageRequestException("Formato do request CARRY_IT_TO_APPROPRIATE_STORE inválido: \"\n" +
"                           + \"espera-se 1 parametro!",request);
                if( !(request.getArgs()[0] instanceof Mala) && request.getArgs()[0] != null )
                    throw new MessageRequestException("Leitura de tipo de objecto mala inválido!",request);
                
                Mala mala = (Mala) request.getArgs()[0];
                
                response = new Response(OK,recolha.carryItToAppropriateStore(mala));
                break;
                
            case RESET_NOMORE_BAGS:
                recolha.resetNoMoreBags();
                response = new Response(OK,null);
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
                response = new Response(OK,null);
                break;
            case CLOSE:
                response= new Response(OK,recolha.shutdownMonitor());               
                break;
            default:
                throw new MessageRequestException("Tipo de request inválido!",request);
        }        
        return response;
    }
    
}
