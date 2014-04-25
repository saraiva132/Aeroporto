/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerSide;

import static Estruturas.AuxInfo.*;
import Estruturas.Mala;
import Message.MessageRequestException;
import Message.Request;
import Message.Response;
import Monitores.Porao;
import genclass.GenericIO;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hugo
 */
public class ServerPoraoInterface {
    private Porao porao;

    public ServerPoraoInterface(Porao porao) {
        this.porao = porao;
    }
    
    protected Response processAndReply(Request request) throws MessageRequestException{
       Response response=null;
        if(request.getMethodName() == TRY_TO_COLLECT_A_BAG)
        {   if(request.getArgs().length != 0)
                throw new MessageRequestException("Formato do request TRY_TO_COLLECT_A_BAG inválido: "
                           + "esperam-se 0 parametros!",request);
            Mala mala;
            try
            {   mala = porao.tryToCollectABag();
            }catch(NullPointerException e)
            {   mala = null;
            }
            response = new Response(OK,mala);
        }else if(request.getMethodName() == SEND_LUGAGES)
        {
            ArrayList<Mala> malas = new ArrayList<>();
            for(int i = 0; i < request.getArgs().length;i++){
                if(!(request.getArgs()[i] instanceof Mala))
                    throw new MessageRequestException("Leitura de tipo de objecto mala["+i+"] inválido!",request);
                else{
                    try {
                        malas.add( ((Mala) request.getArgs()[i]).clone() );
                    } catch (CloneNotSupportedException ex) {
                        GenericIO.writelnString ("Erro a enviar as malas para o porão!");
                        GenericIO.writelnString ("A terminar operações!");
                        System.exit(0);
                    }
                    
                }
            }
            
        }else
            throw new MessageRequestException("Tipo de request inválido!",request);
        return response;
    }
}
