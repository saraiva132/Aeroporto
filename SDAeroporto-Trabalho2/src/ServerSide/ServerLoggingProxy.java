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
import java.io.PrintStream;
import serverSide.ServerCom;
/**
 *
 * @author Hugo
 */
public class ServerLoggingProxy extends Thread{
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
    
    private ServerLoggingInterface logInter;
    private static PrintStream stdout;

    public ServerLoggingProxy(ServerCom sconi, ServerLoggingInterface logInter, PrintStream stdout) {
        super("LoggingProxy_"+getProxyId() );
        this.sconi = sconi;
        this.logInter = logInter;
        this.stdout=stdout;
    }
    
    
    
    @Override
    public void run(){
        Request request;
        Response response = null;
        request = (Request) sconi.readObject();
        try{            
            response = logInter.processAndReply(request);
        }catch(MessageRequestException e)
        {   stdout.println("Thread " + getName () + ": " + e.getMessage () );
            stdout.println (e.getRequestErrorMessage().toString ());
            System.exit (1);            
        }
        sconi.writeObject(response);
        sconi.close();
    }
    
    private static int getProxyId ()
    {   
        Class<ServerSide.ServerLoggingProxy> cl = null;             // representação do tipo de dados ClientProxy na máquina
                                                           //   virtual de Java
        int proxyId;                                         // identificador da instanciação

        try
        { cl = (Class<ServerSide.ServerLoggingProxy>) Class.forName ("ServerSide.ServerLoggingProxy");
        }
        catch (ClassNotFoundException e)
        {  stdout.println ("O tipo de dados ServerLoggingProxy não foi encontrado!");
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
