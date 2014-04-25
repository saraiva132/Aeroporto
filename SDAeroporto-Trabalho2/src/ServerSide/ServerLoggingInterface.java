/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerSide;

import Estruturas.AuxInfo;
import static Estruturas.AuxInfo.*;
import Message.MessageRequestException;
import Message.Request;
import Message.Response;
import Monitores.Logging;

/**
 *
 * @author Hugo
 */
public class ServerLoggingInterface {
    private Logging log;

    public ServerLoggingInterface(Logging log) {
        this.log = log;
    }
    
    protected Response processAndReply(Request request) throws MessageRequestException{
        Response response;
        int passId;
        
        switch(request.getMethodName()){
            case REPORT_INITIAL_STATUS:
                log.reportInitialStatus();
                break;
            case REPORT_STATE_PASSAGEIRO:
                if(request.getArgs().length != 2)
                   throw new MessageRequestException("Formato do request REPORT_STATE_PASSAGEIRO inválido: "
                           + "esperam-se 2 parametros!",request);
                if(!(request.getArgs()[0] instanceof Integer))
                    throw new MessageRequestException("Leitura de tipo de objecto pState inválido!",request);
                if(!(request.getArgs()[1] instanceof AuxInfo.passState))
                    throw new MessageRequestException("Leitura de tipo de objecto passId inválido!",request);

                AuxInfo.passState pState = (AuxInfo.passState) request.getArgs()[1];
                passId = (int) request.getArgs()[0];
                log.reportState(passId, pState);
                
                break;
            case REPORT_STATE_MOTORISTA:
                if(request.getArgs().length != 1)
                   throw new MessageRequestException("Formato do request REPORT_STATE_MOTORISTA inválido: "
                           + "espera-se 1 parametro!",request);
                if(!(request.getArgs()[0] instanceof AuxInfo.motState))
                    throw new MessageRequestException("Leitura de tipo de objecto mState inválido!",request);
                AuxInfo.motState mState = (AuxInfo.motState) request.getArgs()[0];
                log.reportState(mState);
                break;
                
            case REPORT_STATE_BAGAGEIRO:
                if(request.getArgs().length != 1)
                   throw new MessageRequestException("Formato do request REPORT_STATE_BAGAGEIRO inválido: "
                           + "espera-se 1 parametro!",request);
                if(!(request.getArgs()[0] instanceof AuxInfo.bagState))
                    throw new MessageRequestException("Leitura de tipo de objecto bState inválido!",request);
                AuxInfo.bagState bState = (AuxInfo.bagState) request.getArgs()[0];
                log.reportState(bState);
                break;
                
            case N_VOO:
                if(request.getArgs().length != 1)
                   throw new MessageRequestException("Formato do request N_VOO inválido: "
                           + "espera-se 1 parametro!",request);
                if(!(request.getArgs()[0] instanceof Integer))
                    throw new MessageRequestException("Leitura de tipo de objecto nVoo inválido!",request);
                int nVoo = (int) request.getArgs()[0];
                log.nVoo(nVoo);                
                break;
                
            case SET_PORAO:
                if(request.getArgs().length != 1)
                   throw new MessageRequestException("Formato do request SET_PORAO inválido: "
                           + "espera-se 1 parametro!",request);
                if(!(request.getArgs()[0] instanceof Integer))
                    throw new MessageRequestException("Leitura de tipo de objecto nMalasPorao inválido!",request);
                int nMalasPorao = (int) request.getArgs()[0];
                log.setPorao(nMalasPorao);
                break;
                
            case BAGAGEM_PORAO:
                log.bagagemPorao();
                break;
            case BAGAGEM_BELT:
                if(request.getArgs().length != 1)
                   throw new MessageRequestException("Formato do request BAGAGEM_BELT inválido: "
                           + "espera-se 1 parametro!",request);
                if(!(request.getArgs()[0] instanceof Boolean))
                    throw new MessageRequestException("Leitura de tipo de objecto take inválido!",request);
                boolean take = (boolean) request.getArgs()[0];
                log.bagagemBelt(take);
                break;
                
            case BAGAGEM_STORE:
                log.bagagemStore();
                break;
            case MALAS_ACTUAL:
                if(request.getArgs().length != 1)
                   throw new MessageRequestException("Formato do request MALAS_ACTUAL inválido: "
                           + "espera-se 1 parametro!",request);
                if(!(request.getArgs()[0] instanceof Integer))
                    throw new MessageRequestException("Leitura de tipo de objecto passId inválido!",request);
                passId = (int) request.getArgs()[0];
                log.malasActual(passId);
                break;
                
            case MALAS_INICIAL:
                int [] malasInicial = new int[request.getArgs().length];
                for(int i = 0;i<malasInicial.length;i++)
                {
                    if(!(request.getArgs()[i] instanceof Integer))
                        throw new MessageRequestException("Leitura de tipo de objecto malasInicial["+i+"] inválido!",request);
                    malasInicial[i] = (int) request.getArgs()[i];
                }
                log.malasInicial(malasInicial);                               
                break;
                
            case DESTINO:
                boolean [] destino = new boolean[request.getArgs().length];
                for(int i = 0;i<destino.length;i++)
                {
                    if(!(request.getArgs()[i] instanceof Boolean))
                        throw new MessageRequestException("Leitura de tipo de objecto destino["+i+"] inválido!",request);
                    destino[i] = (boolean) request.getArgs()[i];
                }
                log.destino(destino);
                break;
                
            case MISSING_BAGS:
                if(request.getArgs().length != 1)
                   throw new MessageRequestException("Formato do request MISSING_BAGS inválido: "
                           + "espera-se 1 parametro!",request);
                if(!(request.getArgs()[0] instanceof Integer))
                        throw new MessageRequestException("Leitura de tipo de objecto malasPerdidas inválido!",request);
                int malasPerdidas = (int) request.getArgs()[0];
                log.missingBags(malasPerdidas);
                
            case ADD_FILA_ESPERA:
                if(request.getArgs().length != 1)
                   throw new MessageRequestException("Formato do request ADD_FILA_ESPERA inválido: "
                           + "espera-se 1 parametro!",request);
                if(!(request.getArgs()[0] instanceof Integer))
                        throw new MessageRequestException("Leitura de tipo de objecto id inválido!",request);
                int id = (int) request.getArgs()[0];
                log.addfilaEspera(id);
                break;
                
            case REMOVE_FILA_ESPERA:
                log.removefilaEspera();
                break;
            case AUTOCARRO_STATE:
               
                int [] seats = new int[request.getArgs().length];
                for(int i = 0;i<seats.length;i++)
                {
                    if(!(request.getArgs()[i] instanceof Integer))
                        throw new MessageRequestException("Leitura de tipo de objecto seats["+i+"] inválido!",request);
                    seats[i] = (int) request.getArgs()[i];
                }
                log.autocarroState(seats);                
                break;
                
            case CLOSE:
                log.close();
                break;
            case REPORT_FINAL_STATUS:
                log.reportFinalStatus();
                break;     
            default:
                throw new MessageRequestException("Tipo de request inválido!",request);
        }
        response = new Response(OK,null);
        return response;
    }
}
