/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message;

/**
 *
 * @author rafael
 */
public class MessageRequestException extends Exception {

    private Request msg;

    public MessageRequestException(String errorMessage, Request message) {
        super(errorMessage);
        this.msg = message;
    }

    public Request getRequestErrorMessage() {
        return (msg);
    }    
}
