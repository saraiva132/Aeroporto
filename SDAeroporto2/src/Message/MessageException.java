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
public class MessageException extends Exception {

    private Request msg;

    public MessageException(String errorMessage, Request msg) {
        super(errorMessage);
        this.msg = msg;
    }

    public Request getErrorMessage() {
        return (msg);
    }
    
    
}
