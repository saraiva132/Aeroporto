/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerSide;

import static Estruturas.AuxInfo.ANNOUNCING_BUS_BOARDING_SHOUTING;
import static Estruturas.AuxInfo.HAS_DAYS_WORK_ENDED;
import static Estruturas.AuxInfo.OK;
import static Estruturas.AuxInfo.SET_N_VOO;
import static Estruturas.AuxInfo.TAKE_A_BUS;
import static Estruturas.AuxInfo.nChegadas;
import static Estruturas.AuxInfo.passMax;
import Message.MessageRequestException;
import Message.Request;
import Message.Response;
import Monitores.TransferenciaTerminal;

/**
 *
 * @author Hugo
 */
public class ServerTransferenciaTerminalInterface {
    private TransferenciaTerminal transferencia;

    public ServerTransferenciaTerminalInterface(TransferenciaTerminal transferencia) {
        this.transferencia = transferencia;
    }
    
    protected Response processAndReply(Request request) throws MessageRequestException{
        Response response = null;
        switch(request.getMethodName()){
            case TAKE_A_BUS:
                if(request.getArgs().length != 1)
                    throw new MessageRequestException("Formato do request TAKE_A_BUS inválido: \"\n" +
"                           + \"espera-se 1 parametro!",request);
                if(!(request.getArgs()[0] instanceof Integer))
                    throw new MessageRequestException("Leitura de tipo de objecto passageiroId inválido!",request);
                
                int passageiroId = (int) request.getArgs()[0];
                
                if(passageiroId <0 || passageiroId>passMax)
                    throw new MessageRequestException("Id do passageiro inválido!",request);
                
                response = new Response(OK,transferencia.takeABus(passageiroId));
                break;                
            case HAS_DAYS_WORK_ENDED:
                response = new Response(OK,transferencia.hasDaysWorkEnded());
                break;                
            case ANNOUNCING_BUS_BOARDING_SHOUTING:
                response = new Response(OK,transferencia.announcingBusBoardingShouting());
                break;
                
            case SET_N_VOO:
                if(request.getArgs().length != 2)
                    throw new MessageRequestException("Formato do request SET_N_VOO inválido: "
                            + "esperam-se 2 parametros!",request);
                if(!(request.getArgs()[0] instanceof Integer))
                    throw new MessageRequestException("Leitura de tipo de objecto nVoo inválido!",request);
                
                if(!(request.getArgs()[1] instanceof Integer))
                    throw new MessageRequestException("Leitura de tipo de objecto nPass inválido!",request);
                
                int nVoo = (int) request.getArgs()[0];
                int nPass = (int) request.getArgs()[1];
                
                if(nVoo<1 || nVoo > nChegadas)
                    throw new MessageRequestException("Número de voo inválido!",request);
                
                if(nPass < 0 || nPass >= passMax)
                    throw new MessageRequestException("Número de passageiros em trânsito inválido!",request);
                transferencia.setnVoo(nVoo, nPass);
                response = new Response(OK,null);
                break;
            default:
                throw new MessageRequestException("Tipo de request inválido!",request);
        }
        return response;
    }
}
