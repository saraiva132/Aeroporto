package ServerSide;

import static Estruturas.Globals.ANNOUNCING_BUS_BOARDING_SHOUTING;
import static Estruturas.Globals.SHUTDOWN_MONITOR;
import static Estruturas.Globals.HAS_DAYS_WORK_ENDED;
import static Estruturas.Globals.OK;
import static Estruturas.Globals.SET_N_VOO;
import static Estruturas.Globals.TAKE_A_BUS;
import static Estruturas.Globals.nChegadas;
import static Estruturas.Globals.passMax;
import Message.MessageRequestException;
import Message.Request;
import Message.Response;
import Monitores.TransferenciaTerminal;

/**
 * Este tipo de dados define o interface ao monitor <i>TranferenciaTerminal</i> do problema <b>Rapsódia no Aeroporto</b>.
 * <p>
 * Está encarregue de para cada mensagem do tipo <i>Request</i> validá-la e realizar a operação pedida na mesma
 * sobre o montior <i>TransferenciaTerminal</i> devolvendo uma mensagem do tipo <i>Response</i> que que contém (no caso de haver) 
 * o objecto devolvido pela operação que foi invocada no monitor.  
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ServerTransferenciaTerminalInterface implements ServerInterface{
    /**
     * Monitor TransferenciaTerminal (representa o serviço a ser prestado)
     * 
     * @serialField transferencia
     */
    private final TransferenciaTerminal transferencia;

    /**
     * Instanciação do interface ao monitor TransferenciaTerminal
     * 
     * @param transferencia Monitor TransferenciaTerminal
     */
    public ServerTransferenciaTerminalInterface(TransferenciaTerminal transferencia) {
        this.transferencia = transferencia;
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
        Response response = null;
        switch(request.getMethodName()){
            case TAKE_A_BUS:
                if(request.getArgs().length != 1)
                    throw new MessageRequestException("Formato do request TAKE_A_BUS inválido: \"\n" +
"                           + \"espera-se 1 parametro!",request);
                if(!(request.getArgs()[0] instanceof Integer))
                    throw new MessageRequestException("Leitura de tipo de objecto passageiroId inválido!",request);
                
                int passageiroId = (int) request.getArgs()[0];
                
                if(passageiroId < 0 || passageiroId>passMax)
                    throw new MessageRequestException("Id do passageiro inválido!",request);
                
                response = new Response(OK,transferencia.takeABus(passageiroId));
                break;                
            case HAS_DAYS_WORK_ENDED:
                response = new Response(OK,transferencia.hasDaysWorkEnded());
                break;                
            case ANNOUNCING_BUS_BOARDING_SHOUTING:
                response = new Response(OK,transferencia.announcingBusBoardingShouting());
                break;
                
            case SET_N_VOO:
                if(request.getArgs().length != 2)
                    throw new MessageRequestException("Formato do request SET_N_VOO inválido: "
                            + "esperam-se 2 parametros!",request);
                if(!(request.getArgs()[0] instanceof Integer))
                    throw new MessageRequestException("Leitura de tipo de objecto nVoo inválido!",request);
                
                if(!(request.getArgs()[1] instanceof Integer))
                    throw new MessageRequestException("Leitura de tipo de objecto nPass inválido!",request);
                
                int nVoo = (int) request.getArgs()[0];
                int nPass = (int) request.getArgs()[1];
                
                if(nVoo<1 || nVoo > nChegadas)
                    throw new MessageRequestException("Número de voo inválido!",request);
                if(nPass < 0 || nPass > passMax)
                    throw new MessageRequestException("Número de passageiros em trânsito inválido!",request);
                transferencia.setnVoo(nVoo, nPass);
                response = new Response(OK,null);
                break;
            case SHUTDOWN_MONITOR:
                response= new Response(OK,transferencia.shutdownMonitor());
                break;
            default:
                throw new MessageRequestException("Tipo de request inválido!",request);
        }
        return response;
    }
}
