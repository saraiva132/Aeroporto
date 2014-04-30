package Message;

/**
 * Tipo de dados que define uma excepção lançada quando o formato da mensagem Response é inválido.
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class MessageResponseException extends Exception{

    /**
     * Mensagem Response que originou a excepção.
     * @serialField msg
     */
    private Response msg;

    /**
     * Instanciação da MessageResponseException
     * @param errorMessage - Mensagem indicadora da condição de erro
     * @param message - Mensagem Response que originou o erro.
     */
    public MessageResponseException(String errorMessage, Response message) {
        super(errorMessage);
        this.msg = message;
    }

    /**
     * Obter a mensagem Response que originou o erro.
     * @return Mensagem que originou o erro
     */
    public Response getResponseErrorMessage() {
        return (msg);
    }    
}

    

