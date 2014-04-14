/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSide;

import Message.MessageException;
import Message.Request;
import Message.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rafael
 */
public class ServerProxy extends Thread {

    /**
     * Canal de comunicação
     *
     * @serialField sconi
     */
    private ServerCom channel;
    private Service service;

    public ServerProxy(ServerCom channel, Service service) {
        this.service = service;
        this.channel = channel;
    }

    /**
     * Ciclo de vida do thread agente prestador de serviço.
     */
    @Override
    public void run() {
        Request inMessage = null; // mensagem de entrada
        
        Response outMessage = null; // mensagem de saída
        

        inMessage = (Request) channel.readObject();                     // ler pedido do cliente
        try {
            try {
                outMessage = service.processAndReply(inMessage);         // processá-lo
            } catch (ClassNotFoundException ex) {

            }
        } catch (MessageException e) {
            System.out.println("Thread " + getName() + ": " + e.getMessage() + "!");
            System.out.println(e.getMessage().toString());
            System.exit(1);
        }
        channel.writeObject(outMessage);                                // enviar resposta ao cliente
        channel.close();                                                // fechar canal de comunicação
    }
}
