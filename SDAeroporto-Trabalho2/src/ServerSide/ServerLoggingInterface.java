package ServerSide;

import Estruturas.Globals;
import static Estruturas.Globals.*;
import Message.MessageRequestException;
import Message.Request;
import Message.Response;
import Monitores.Logging;

/**
 * Este tipo de dados define o interface ao monitor <i>Logging</i> do problema <b>Rapsódia no Aeroporto</b>.
 * <p>
 * Está encarregue de para cada mensagem do tipo <i>Request</i> validá-la e realizar a operação pedida na mesma
 * sobre o montior <i>Autocarro</i> devolvendo uma mensagem do tipo <i>Response</i> que que contém (no caso de haver) 
 * o objecto devolvido pela operação que foi invocada no monitor.
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ServerLoggingInterface implements ServerInterface{
    /**
     * Monitor Logging (representa o serviço a ser prestado)
     * 
     * @serialField log
     */
    private final Logging log;

    /**
     * Instanciação do interface ao monitor Logging
     * 
     * @param log Monitor Logging
     */
    public ServerLoggingInterface(Logging log) {
        this.log = log;
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
                if(!(request.getArgs()[1] instanceof Globals.passState))
                    throw new MessageRequestException("Leitura de tipo de objecto passId inválido!",request);

                Globals.passState pState = (Globals.passState) request.getArgs()[1];
                passId = (int) request.getArgs()[0];
                log.reportState(passId, pState);
                
                break;
            case REPORT_STATE_MOTORISTA:
                if(request.getArgs().length != 1)
                   throw new MessageRequestException("Formato do request REPORT_STATE_MOTORISTA inválido: "
                           + "espera-se 1 parametro!",request);
                if(!(request.getArgs()[0] instanceof Globals.motState))
                    throw new MessageRequestException("Leitura de tipo de objecto mState inválido!",request);
                Globals.motState mState = (Globals.motState) request.getArgs()[0];
                log.reportState(mState);
                break;
                
            case REPORT_STATE_BAGAGEIRO:
                if(request.getArgs().length != 1)
                   throw new MessageRequestException("Formato do request REPORT_STATE_BAGAGEIRO inválido: "
                           + "espera-se 1 parametro!",request);
                if(!(request.getArgs()[0] instanceof Globals.bagState))
                    throw new MessageRequestException("Leitura de tipo de objecto bState inválido!",request);
                Globals.bagState bState = (Globals.bagState) request.getArgs()[0];
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
                break;
                
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
            case SHUTDOWN_MONITOR:
                return new Response(OK,log.shutdownMonitor());
                 
            default:
                throw new MessageRequestException("Tipo de request inválido!",request);
        }
        return new Response(OK,null);
    }
}
