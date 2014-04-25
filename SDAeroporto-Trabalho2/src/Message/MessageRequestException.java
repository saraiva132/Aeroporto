/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class MessageRequestException extends Exception {

    private Request msg;

    /**
     * 
     * @param errorMessage - Mensagem de erro
     * @param message - Mensagem que originou o erro.
     */
    public MessageRequestException(String errorMessage, Request message) {
        super(errorMessage);
        this.msg = message;
    }
    
    /**
     * Retorna a mensagem que originou o erro.
     * @return 
     */
    public Request getRequestErrorMessage() {
        return (msg);
    }    
}
