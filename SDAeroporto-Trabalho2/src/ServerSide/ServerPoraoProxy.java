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
public class ServerPoraoProxy extends Thread{

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
    
    private ServerPoraoInterface poraoInter;

    public ServerPoraoProxy(ServerCom sconi, ServerPoraoInterface poraoInter) {
        super("PoraoProxy_"+getProxyId() );
        this.sconi = sconi;
        this.poraoInter = poraoInter;
    }
    
    @Override
    public void run(){
        Request request;
        Response response = null;
        request = (Request) sconi.readObject();
        try{            
            response = poraoInter.processAndReply(request);
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
        Class<ServerSide.ServerPoraoProxy> cl = null;             // representação do tipo de dados ServerAutocarroProxy na máquina
                                                                      //   virtual de Java
        int proxyId;                                         // identificador da instanciação

        try
        { cl = (Class<ServerSide.ServerPoraoProxy>) Class.forName ("ServerSide.ServerPoraoProxy");
        }
        catch (ClassNotFoundException e)
        {  GenericIO.writelnString ("O tipo de dados ServerPoraoProxy não foi encontrado!");
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
