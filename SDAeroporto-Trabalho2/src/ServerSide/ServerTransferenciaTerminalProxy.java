/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerSide;

import Message.MessageRequestException;
import Message.Request;
import Message.Response;
import genclass.GenericIO;
import serverSide.ServerCom;

/**
 *
 * @author Hugo
 */
public class ServerTransferenciaTerminalProxy extends Thread{
    /**
     *  Contador de threads lançados
     *
     *    @serialField nProxy
     */
    private static int nProxy;
    /**
     *  Canal de comunicação
     *
     *    @serialField sconi
     */
    private serverSide.ServerCom sconi;
    
    private ServerTransferenciaTerminalInterface transferenciaInter;

    public ServerTransferenciaTerminalProxy(ServerCom sconi, ServerTransferenciaTerminalInterface transferenciaInter) {
        super("TransferenciaTerminalProxy_"+getProxyId() );
        this.sconi = sconi;
        this.transferenciaInter = transferenciaInter;
    }
    
    
    
    @Override
    public void run(){
        Request request;
        Response response = null;
        request = (Request) sconi.readObject();
        try{            
            response = transferenciaInter.processAndReply(request);
        }catch(MessageRequestException e)
        {   GenericIO.writelnString ("Thread " + getName () + ": " + e.getMessage () + "!");
            GenericIO.writelnString (e.getRequestErrorMessage().toString ());
            System.exit (1);            
        }
        sconi.writeObject(response);
        sconi.close();
    }
    
    private static int getProxyId ()
   {
        Class<ServerSide.ServerTransferenciaTerminalProxy> cl = null;             // representação do tipo de dados ServerAutocarroProxy na máquina
                                                                      //   virtual de Java
        int proxyId;                                         // identificador da instanciação

        try
        { cl = (Class<ServerSide.ServerTransferenciaTerminalProxy>) Class.forName ("ServerSide.ServerTransferenciaTerminalProxy");
        }
        catch (ClassNotFoundException e)
        {  GenericIO.writelnString ("O tipo de dados ServerTransferenciaTerminalProxy não foi encontrado!");
            e.printStackTrace ();
            System.exit (1);
        }

        synchronized (cl)
        {  proxyId = nProxy;
            nProxy += 1;
        }

        return proxyId;
   }
}
