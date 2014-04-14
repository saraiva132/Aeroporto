/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSide;

import Message.MessageException;
import Message.Request;
import Message.Response;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author rafael
 */
public class Service {

    String monitor;

    public Service(String mon) {
        
        this.monitor = mon;
    }

    public Response processAndReply(Request inMessage) throws MessageException, ClassNotFoundException {
        
      
        return null;
    }
}
