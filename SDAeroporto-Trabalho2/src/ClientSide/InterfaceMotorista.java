/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClientSide;

import static Estruturas.Globals.*;
import static Estruturas.Globals.OK;
import static Estruturas.Globals.hostNames;
import static Estruturas.Globals.portNumber;
import Interfaces.AutocarroMotoristaInterface;
import Interfaces.TransferenciaMotoristaInterface;
import Message.Request;
import Message.Response;
import genclass.GenericIO;
import static java.lang.Thread.sleep;

/**
 * Comunicação Motorista.
 * <p>
 * Responsável pela comunicação entre o motorista e os monitores com os quais interage.
 * Cada operação que o motorista necessita de realizar sobre cada monitor corresponde ao 
 * estabelecimento de uma conexão e respectiva comunicação entre o Sistema Computacional onde
 * está a correr a thread <i>Motorista</i> e o Sistema Computacional onde corre o respectivo monitor.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class InterfaceMotorista implements AutocarroMotoristaInterface,TransferenciaMotoristaInterface{
    
    /**
     * Chamada remota ao monitor <b>Autocarro</b> no âmbito da operação <i>announcingBusBoardingWaiting</i>
     * 
     * @param bilhetesvendidos Número de bilhetes vendidos (corresponde ao número de
     * passageiros que estão à espera)
     */
    @Override
    public void announcingBusBoardingWaiting(int bilhetesvendidos) {
        ClientCom con = new ClientCom(hostNames[MON_AUTOCARRO], portNumber[MON_AUTOCARRO]);
        Request request;
        Response response;
        open(con);
        request = new Request(ANNOUNCING_BUS_BOARDING_WAITING, new Object[]{bilhetesvendidos});
        con.writeObject(request);
        response = (Response) con.readObject();
        con.close();
        if (response.getStatus() != OK || request.getSerial() != response.getSerial()) {
            GenericIO.writelnString("Motorista: Status de mensagem de resposta errado!");
            System.exit(1);
        }
    }
    
    /**
     * Chamada remota ao monitor <b>Autocarro</b> no âmbito da operação <i>goToDepartureTerminal</i>
     */
    @Override
    public void goToDepartureTerminal() {
        ClientCom con = new ClientCom(hostNames[MON_AUTOCARRO], portNumber[MON_AUTOCARRO]);
        Request request;
        Response response;
        open(con);
        request = new Request(GO_TO_DEPARTURE_TERMINAL, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        con.close();
        
        if (response.getStatus() != OK || request.getSerial() != response.getSerial()) {
            GenericIO.writelnString("Motorista: Status de mensagem de resposta errado!");
            System.exit(1);
        }
    }
    
    /**
     * Chamada remota ao monitor <b>Autocarro</b> no âmbito da operação <i>goToArrivalTerminal</i>
     */
    @Override
    public void goToArrivalTerminal() {
        ClientCom con = new ClientCom(hostNames[MON_AUTOCARRO], portNumber[MON_AUTOCARRO]);
        Request request;
        Response response;
        open(con);
        request = new Request(GO_TO_ARRIVAL_TERMINAL, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        con.close();
        
        if (response.getStatus() != OK || request.getSerial() != response.getSerial()) {
            GenericIO.writelnString("Motorista: Status de mensagem de resposta errado!");
            System.exit(1);
        }
    }
    
    /**
     * Chamada remota ao monitor <b>Autocarro</b> no âmbito da operação <i>parkTheBus</i>
     */
    @Override
    public void parkTheBus() {
        ClientCom con = new ClientCom(hostNames[MON_AUTOCARRO], portNumber[MON_AUTOCARRO]);
        Request request;
        Response response;
        open(con);
        request = new Request(PARK_THE_BUS, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        con.close();
        
        if (response.getStatus() != OK || request.getSerial() != response.getSerial()) {
            GenericIO.writelnString("Motorista: Status de mensagem de resposta errado!");
            System.exit(1);
        }
    }
    
    /**
     * Chamada remota ao monitor <b>Autocarro</b> no âmbito da operação <i>parkTheBusAndLetPassOff</i>
     */
    @Override
    public void parkTheBusAndLetPassOff() {
        ClientCom con = new ClientCom(hostNames[MON_AUTOCARRO], portNumber[MON_AUTOCARRO]);
        Request request;
        Response response;
        open(con);
        request = new Request(PARK_THE_BUS_AND_LET_PASS_OFF, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        con.close();
        
        if (response.getStatus() != OK || request.getSerial() != response.getSerial()) {
            GenericIO.writelnString("Motorista: Status de mensagem de resposta errado!");
            System.exit(1);
        }
    }
    
    /**
     * Chamada remota ao monitor <b>TransferenciaTerminal</b> no âmbito da operação <i>hasDaysWorkEnded</i>
     * @return 
     * <ul>
     * <li>TRUE, se o dia de trabalho acabou
     * <li>FALSE, caso contrário
     * </ul>
     */
    @Override
    public boolean hasDaysWorkEnded() {
        ClientCom con = new ClientCom(hostNames[MON_TRANSFERENCIA_TERMINAL], portNumber[MON_TRANSFERENCIA_TERMINAL]);
        Request request;
        Response response;
        open(con);
        request = new Request(HAS_DAYS_WORK_ENDED, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        con.close();
        
        if (response.getStatus() != OK || request.getSerial() != response.getSerial()) {
            GenericIO.writelnString("Motorista: Status de mensagem de resposta errado!");
            System.exit(1);
        }
        
        if (!(response.getAns() instanceof Boolean)) {
            GenericIO.writelnString("Motorista: Estava a espera de um boolean!");
            System.exit(1);
        }
        
        return (boolean) response.getAns();
    }
    
    /**
     * Chamada remota ao monitor <b>Autocarro</b> no âmbito da operação <i>announcingBusBoardingShouting</i>
     * @return 
     * Número de passageiros que tomaram interesse em participar na viagem
     * (limitado à lotação do Autocarro)
     */
    @Override
    public int announcingBusBoardingShouting() {
        ClientCom con = new ClientCom(hostNames[MON_TRANSFERENCIA_TERMINAL], portNumber[MON_TRANSFERENCIA_TERMINAL]);
        Request request;
        Response response;
        open(con);
        request = new Request(ANNOUNCING_BUS_BOARDING_SHOUTING, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        con.close();
        
        if (response.getStatus() != OK || request.getSerial() != response.getSerial()) {
            GenericIO.writelnString("Motorista: Status de mensagem de resposta errado!");
            System.exit(1);
        }
        
        if (!(response.getAns() instanceof Integer)) {
            GenericIO.writelnString("Motorista: Estava a espera de um int!");
            System.exit(1);
        }
        
        return (int) response.getAns();
    }
    
    /**
     * Metodo que bloqueia o programa enquanto espera que a comunicação seja estabelecida
     * @param con 
     */
    private void open(ClientCom con) {
        while (!con.open()) // aguarda ligação
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
    }
}
