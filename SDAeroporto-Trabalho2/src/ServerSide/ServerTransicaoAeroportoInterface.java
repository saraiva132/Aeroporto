package ServerSide;

import static Estruturas.Globals.BAGAGEIRO_DONE;
import static Estruturas.Globals.SHUTDOWN_MONITOR;
import static Estruturas.Globals.GO_HOME;
import static Estruturas.Globals.OK;
import static Estruturas.Globals.PREPARE_NEXT_LEG;
import static Estruturas.Globals.passMax;
import Message.MessageRequestException;
import Message.Request;
import Message.Response;
import Monitores.TransiçãoAeroporto;

/**
 * Este tipo de dados define o interface ao monitor <i>TransicaoAeroporto</i> do problema <b>Rapsódia no Aeroporto</b>.
 * <p>
 * Está encarregue de para cada mensagem do tipo <i>Request</i> validá-la e realizar a operação pedida na mesma
 * sobre o montior <i>TransicaoAeroporto</i> devolvendo uma mensagem do tipo <i>Response</i> que que contém (no caso de haver) 
 * o objecto devolvido pela operação que foi invocada no monitor.  
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ServerTransicaoAeroportoInterface implements ServerInterface{
    /**
     * Monitor TransicaoAeroporto (representa o serviço a ser prestado)
     * 
     * @serialField transicao
     */
    private final TransiçãoAeroporto transicao;

    /**
     * Instanciação do interface ao monitor TransicaoAeroporto
     * 
     * @param transicao Monitor TransicaoAeroporto
     */
    public ServerTransicaoAeroportoInterface(TransiçãoAeroporto transicao) {
        this.transicao = transicao;
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
            case BAGAGEIRO_DONE:
                System.out.println("Bagageiro acabou!(ServerInterface)");
                transicao.bagageiroDone();
                break;
            case SHUTDOWN_MONITOR:
                return new Response(OK,transicao.shutdownMonitor());
            default:
                throw new MessageRequestException("Tipo de request inválido!",request);
                
        }
        return new Response(OK,null);
    }
}
