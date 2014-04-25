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
public class ServerZonaDesembarqueProxy extends Thread{
    
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
    private ServerCom sconi;
    
    private ServerZonaDesembarqueInterface desembarqueInter;

    public ServerZonaDesembarqueProxy(ServerCom sconi, ServerZonaDesembarqueInterface desembarqueInter) {
        super("ZonaDesembarqueProxy_"+getProxyId());
        this.sconi = sconi;
        this.desembarqueInter = desembarqueInter;
    }
    
    @Override
    public void run(){
        Request request;
        Response response = null;
        request = (Request) sconi.readObject();
        try{            
            response = desembarqueInter.processAndReply(request);
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
        Class<ServerSide.ServerZonaDesembarqueProxy> cl = null;             // representação do tipo de dados ServerAutocarroProxy na máquina
                                                                      //   virtual de Java
        int proxyId;                                         // identificador da instanciação

        try
        { cl = (Class<ServerSide.ServerZonaDesembarqueProxy>) Class.forName ("ServerSide.ServerZonaDesembarqueProxy");
        }
        catch (ClassNotFoundException e)
        {  GenericIO.writelnString ("O tipo de dados ServerZonaDesembarqueProxy não foi encontrado!");
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
