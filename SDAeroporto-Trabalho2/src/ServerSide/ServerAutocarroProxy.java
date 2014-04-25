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
public class ServerAutocarroProxy extends Thread{
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
    
    private ServerAutocarroInterface autoInter;

    public ServerAutocarroProxy(ServerCom sconi, ServerAutocarroInterface autoInter) {
        super("AutocarroProxy_"+getProxyId() );
        
        this.sconi = sconi;
        this.autoInter = autoInter;
    }
    
    
    
    @Override
    public void run(){
        Request request;
        Response response = null;
        request = (Request) sconi.readObject();
        try{            
            response = autoInter.processAndReply(request);
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
        Class<ServerSide.ServerAutocarroProxy> cl = null;             // representação do tipo de dados ServerAutocarroProxy na máquina
                                                                      //   virtual de Java
        int proxyId;                                         // identificador da instanciação

        try
        { cl = (Class<ServerSide.ServerAutocarroProxy>) Class.forName ("ServerSide.ServerAutocarroProxy");
        }
        catch (ClassNotFoundException e)
        {  GenericIO.writelnString ("O tipo de dados ServerAutocarroProxy não foi encontrado!");
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
