/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSide;

import static Estruturas.AuxInfo.CLOSE;
import Message.MessageRequestException;
import Message.Request;
import Message.Response;
import genclass.GenericIO;

/**
 *
 * @author Hugo
 */
public class ServerProxy extends Thread {

    /**
     * Contador de threads lançados
     *
     * @serialField nProxy
     */
    private static int nProxy=0;
    
    private ServerInterface monInterface=null;
    private String name=null;
    private ServerSide.ServerCom sconi=null;
    
    public ServerProxy(ServerCom sconi, ServerInterface monInterface, String name) {
        this.name =name+getProxyId(); 
        this.monInterface = monInterface;
        this.sconi = sconi;
    }
    
    
    private static int getProxyId() {
        Class<ServerSide.ServerAutocarroProxy> cl = null;             // representação do tipo de dados ServerAutocarroProxy na máquina
        //   virtual de Java
        int proxyId;                                         // identificador da instanciação

        try {
            cl = (Class<ServerSide.ServerAutocarroProxy>) Class.forName("ServerSide.ServerAutocarroProxy");
        } catch (ClassNotFoundException e) {
            GenericIO.writelnString("O tipo de dados ServerAutocarroProxy não foi encontrado!");
            e.printStackTrace();
            System.exit(1);
        }

        synchronized (cl) {
            proxyId = nProxy;
            nProxy += 1;
        }

        return proxyId;
    }
    
    
    protected boolean requestAndReply(){
        Request request;
        Response response = null;
        request = (Request) sconi.readObject();
        boolean canEnd=false;
        try {
            response = monInterface.processAndReply(request);
            if(request.getMethodName() == CLOSE)
                canEnd=(Boolean) response.getAns();
        } catch (MessageRequestException e) {
            GenericIO.writelnString("Thread " + name + ": " + e.getMessage() + "!");
            GenericIO.writelnString(e.getRequestErrorMessage().toString());
            System.exit(1);
        }catch(Exception e){
            GenericIO.writelnString("Thread " + name + ": " + request.toString() + "!");
            System.exit(1);
        }
        sconi.writeObject(response);
        sconi.close();              
        return canEnd;
    }

}
