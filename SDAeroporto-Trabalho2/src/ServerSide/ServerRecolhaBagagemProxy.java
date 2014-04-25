/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerSide;

import static Estruturas.AuxInfo.CARRY_IT_TO_APPROPRIATE_STORE;
import static Estruturas.AuxInfo.GO_COLLECT_A_BAG;
import static Estruturas.AuxInfo.OK;
import static Estruturas.AuxInfo.REPORT_MISSING_BAGS;
import static Estruturas.AuxInfo.RESET_NOMORE_BAGS;
import Estruturas.Mala;
import Message.MessageRequestException;
import Message.Request;
import Message.Response;
import Monitores.RecolhaBagagem;
import genclass.GenericIO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import serverSide.ServerCom;

/**
 *
 * @author Hugo
 */
public class ServerRecolhaBagagemProxy extends Thread{
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
    
    private ServerRecolhaBagagemInterface recolhaInter;

    public ServerRecolhaBagagemProxy(ServerCom sconi, ServerRecolhaBagagemInterface recolhaInter) {
        super("RecolhaBagagemProxy_"+getProxyId() );
        this.sconi = sconi;
        this.recolhaInter = recolhaInter;
    }  
        
    @Override
    public void run(){
        Request request;
        Response response = null;
        request = (Request) sconi.readObject();
        try{            
            response = recolhaInter.processAndReply(request);
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
        Class<ServerSide.ServerRecolhaBagagemProxy> cl = null;             // representação do tipo de dados ServerAutocarroProxy na máquina
                                                                      //   virtual de Java
        int proxyId;                                         // identificador da instanciação

        try
        { cl = (Class<ServerSide.ServerRecolhaBagagemProxy>) Class.forName ("ServerSide.ServerRecolhaBagagemProxy");
        }
        catch (ClassNotFoundException e)
        {  GenericIO.writelnString ("O tipo de dados ServerRecolhaBagagemProxy não foi encontrado!");
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
