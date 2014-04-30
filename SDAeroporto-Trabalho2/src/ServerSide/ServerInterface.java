package ServerSide;

import Message.MessageRequestException;
import Message.Request;
import Message.Response;

/**
 * Interface que define a operação que cada tipo de dados <i>Server*Monitor_Name*Interface</i> 
 * necessita de implementar. Tornou-se necessária a sua definição e utilização no 
 * âmbito da definição da classe mãe de todos os tipos de dados threads prestadores de serviços.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface ServerInterface {
    /**
     * Processamento das mensagens através da execução da operação correspondente.
     * Geração de uma mensagem de resposta.
     * 
     * @param request mensagem com o pedido e (eventualmente) os parâmetros necessários para a realização da operação requerida sobre o monitor
     * @return mensagem de resposta
     * @throws MessageRequestException 
     */
    public Response processAndReply(Request request) throws MessageRequestException;
}
