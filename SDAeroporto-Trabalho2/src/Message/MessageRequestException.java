package Message;

/**
 * Tipo de dados que define uma excepção lançada quando o formato da mensagem Request é inválido. 
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class MessageRequestException extends Exception {

    /**
     * Mensagem Request que originou a excepção.
     * 
     * @serialField msg
     */
    private Request msg;

    /**
     * Instanciação da MessageRequestException
     * @param errorMessage - Mensagem indicadora da condição de erro
     * @param message - Mensagem Request que originou o erro.
     */
    public MessageRequestException(String errorMessage, Request message) {
        super(errorMessage);
        this.msg = message;
    }
    
    /**
     * Obter a mensagem Request que originou o erro.
     * @return Mensagem que originou o erro
     */
    public Request getRequestErrorMessage() {
        return (msg);
    }    
}
