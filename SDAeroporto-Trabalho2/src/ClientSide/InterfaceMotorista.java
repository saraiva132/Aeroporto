/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClientSide;

import static Estruturas.AuxInfo.*;
import static Estruturas.AuxInfo.OK;
import static Estruturas.AuxInfo.hostName;
import static Estruturas.AuxInfo.portNumber;
import Interfaces.AutocarroMotoristaInterface;
import Interfaces.TransferenciaMotoristaInterface;
import Message.Request;
import Message.Response;
import genclass.GenericIO;
import static java.lang.Thread.sleep;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class InterfaceMotorista implements AutocarroMotoristaInterface,TransferenciaMotoristaInterface{
    
    /**
     * 
     * @param bilhetesvendidos 
     */
    @Override
    public void announcingBusBoardingWaiting(int bilhetesvendidos) {
        ClientCom con = new ClientCom(hostName[MON_AUTOCARRO], portNumber[MON_AUTOCARRO]);
        Request request;
        Response response;
        open(con);
        request = new Request(ANNOUNCING_BUS_BOARDING_WAITING, new Object[]{bilhetesvendidos});
        con.writeObject(request);
        response = (Response) con.readObject();
        con.close();
        if (response.getStatus() != OK) {
            GenericIO.writelnString("Motorista: Status de mensagem de resposta errado!");
            System.exit(1);
        }
    }
    
    /**
     * 
     */
    @Override
    public void goToDepartureTerminal() {
        ClientCom con = new ClientCom(hostName[MON_AUTOCARRO], portNumber[MON_AUTOCARRO]);
        Request request;
        Response response;
        open(con);
        request = new Request(GO_TO_DEPARTURE_TERMINAL, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        con.close();
        
        if (response.getStatus() != OK) {
            GenericIO.writelnString("Motorista: Status de mensagem de resposta errado!");
            System.exit(1);
        }
    }
    
    /**
     * 
     */
    @Override
    public void goToArrivalTerminal() {
        ClientCom con = new ClientCom(hostName[MON_AUTOCARRO], portNumber[MON_AUTOCARRO]);
        Request request;
        Response response;
        open(con);
        request = new Request(GO_TO_ARRIVAL_TERMINAL, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        con.close();
        
        if (response.getStatus() != OK) {
            GenericIO.writelnString("Motorista: Status de mensagem de resposta errado!");
            System.exit(1);
        }
    }
    
    /**
     * 
     */
    @Override
    public void parkTheBus() {
        ClientCom con = new ClientCom(hostName[MON_AUTOCARRO], portNumber[MON_AUTOCARRO]);
        Request request;
        Response response;
        open(con);
        request = new Request(PARK_THE_BUS, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        con.close();
        
        if (response.getStatus() != OK) {
            GenericIO.writelnString("Motorista: Status de mensagem de resposta errado!");
            System.exit(1);
        }
    }
    
    /**
     * 
     */
    @Override
    public void parkTheBusAndLetPassOff() {
        ClientCom con = new ClientCom(hostName[MON_AUTOCARRO], portNumber[MON_AUTOCARRO]);
        Request request;
        Response response;
        open(con);
        request = new Request(PARK_THE_BUS_AND_LET_PASS_OFF, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        con.close();
        
        if (response.getStatus() != OK) {
            GenericIO.writelnString("Motorista: Status de mensagem de resposta errado!");
            System.exit(1);
        }
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public boolean hasDaysWorkEnded() {
        ClientCom con = new ClientCom(hostName[MON_TRANSFERENCIA_TERMINAL], portNumber[MON_TRANSFERENCIA_TERMINAL]);
        Request request;
        Response response;
        open(con);
        request = new Request(HAS_DAYS_WORK_ENDED, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        con.close();
        
        if (response.getStatus() != OK) {
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
     * 
     * @return 
     */
    @Override
    public int announcingBusBoardingShouting() {
        ClientCom con = new ClientCom(hostName[MON_TRANSFERENCIA_TERMINAL], portNumber[MON_TRANSFERENCIA_TERMINAL]);
        Request request;
        Response response;
        open(con);
        request = new Request(ANNOUNCING_BUS_BOARDING_SHOUTING, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        con.close();
        
        if (response.getStatus() != OK) {
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
