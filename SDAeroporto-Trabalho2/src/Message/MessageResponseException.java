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
public class MessageResponseException extends Exception{

    private Response msg;

    public MessageResponseException(String errorMessage, Response message) {
        super(errorMessage);
        this.msg = message;
    }

    public Response getRequestErrorMessage() {
        return (msg);
    }    
}

    

