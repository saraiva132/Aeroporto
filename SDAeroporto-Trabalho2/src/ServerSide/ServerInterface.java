/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerSide;

import Message.MessageRequestException;
import Message.Request;
import Message.Response;

/**
 *
 * @author Hugo
 */
public interface ServerInterface {
    public Response processAndReply(Request request) throws MessageRequestException;
}
