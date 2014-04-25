/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClientSide;


import static Estruturas.AuxInfo.*;
import static Estruturas.AuxInfo.OK;
import Estruturas.AuxInfo.bagCollect;
import Estruturas.AuxInfo.destination;
import static Estruturas.AuxInfo.hostName;
import static Estruturas.AuxInfo.portNumber;
import Interfaces.AutocarroPassageiroInterface;
import Interfaces.RecolhaPassageiroInterface;
import Interfaces.TransferenciaPassageiroInterface;
import Interfaces.TransicaoPassageiroInterface;
import Interfaces.ZonaDesembarquePassageiroInterface;
import Message.Request;
import Message.Response;
import genclass.GenericIO;
import static java.lang.Thread.sleep;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class InterfacePassageiro implements AutocarroPassageiroInterface, RecolhaPassageiroInterface, TransferenciaPassageiroInterface, TransicaoPassageiroInterface, ZonaDesembarquePassageiroInterface{
    private String name;

    public InterfacePassageiro(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @param ticketID
     * @param passageiroId 
     */
    @Override
    public void enterTheBus(int ticketID, int passageiroId){
        ClientCom con = new ClientCom(hostName[MON_AUTOCARRO], portNumber[MON_AUTOCARRO]);
        Request request;
        Response response;
        open(con);
        request = new Request(ENTER_THE_BUS, new Object[]{ticketID,passageiroId});
        con.writeObject(request);
        response = (Response) con.readObject();
        
        if (response.getStatus() != OK) {
            GenericIO.writelnString(name+": Status de mensagem de resposta errado!");
            System.exit(1);
        }
        
        con.close();
    }
    
    /**
     * 
     * @param passageiroId
     * @param ticketID 
     */
    @Override
    public void leaveTheBus(int passageiroId, int ticketID) {
    
        ClientCom con = new ClientCom(hostName[MON_AUTOCARRO], portNumber[MON_AUTOCARRO]);
        Request request;
        Response response;
        open(con);
        request = new Request(LEAVE_THE_BUS, new Object[]{passageiroId,ticketID});
        con.writeObject(request);
        response = (Response) con.readObject();
        
        if (response.getStatus() != OK) {
            GenericIO.writelnString(name+": Status de mensagem de resposta errado!");
            System.exit(1);
        }
        
        con.close();
    }
    
    /**
     * 
     * @param bagID
     * @return 
     */
    @Override
    public bagCollect goCollectABag(int bagID) {
        ClientCom con = new ClientCom(hostName[MON_RECOLHA_BAGAGEM], portNumber[MON_RECOLHA_BAGAGEM]);
        Request request;
        Response response;
        open(con);
        request = new Request(GO_COLLECT_A_BAG, new Object[]{bagID});
        con.writeObject(request);
        response = (Response) con.readObject();
        
        if (response.getStatus() != OK) {
            GenericIO.writelnString(name+": Status de mensagem de resposta errado!");
            System.exit(1);
        }
        
        if (!(response.getAns() instanceof bagCollect)) {
            GenericIO.writelnString(name+": Estou a espera de um bagCollect!");
            System.exit(1);
        }
        
        con.close();
        return (bagCollect) response.getAns();
    }
    
    /**
     * 
     * @param passageiroID
     * @param malasPerdidas 
     */
    @Override
    public void reportMissingBags(int passageiroID, int malasPerdidas) {
        ClientCom con = new ClientCom(hostName[MON_RECOLHA_BAGAGEM], portNumber[MON_RECOLHA_BAGAGEM]);
        Request request;
        Response response;
        open(con);
        request = new Request(REPORT_MISSING_BAGS, new Object[]{passageiroID,malasPerdidas});
        con.writeObject(request);
        response = (Response) con.readObject();
        
        if (response.getStatus() != OK) {
            GenericIO.writelnString(name+": Status de mensagem de resposta errado!");
            System.exit(1);
        }
        
        con.close();
    }
    
    /**
     * 
     * @param passageiroID
     * @return 
     */
    @Override
    public int takeABus(int passageiroID) {
        ClientCom con = new ClientCom(hostName[MON_TRANSFERENCIA_TERMINAL], portNumber[MON_TRANSFERENCIA_TERMINAL]);
        Request request;
        Response response;
        open(con);
        request = new Request(TAKE_A_BUS, new Object[]{passageiroID});
        con.writeObject(request);
        response = (Response) con.readObject();
        
        if (response.getStatus() != OK) {
            GenericIO.writelnString(name+": Status de mensagem de resposta errado!");
            System.exit(1);
        }
        if (!(response.getAns() instanceof Integer)) {
            GenericIO.writelnString(name+": Estava a espera de um int!");
            System.exit(1);
        }
        
        con.close();
        return (int) response.getAns();
    }
    
    /**
     * 
     * @param passageiroId 
     */
    @Override
    public void goHome(int passageiroId) {
        ClientCom con = new ClientCom(hostName[MON_TRANSICAO_AEROPORTO], portNumber[MON_TRANSICAO_AEROPORTO]);
        Request request;
        Response response;
        open(con);
        request = new Request(GO_HOME, new Object[]{passageiroId});
        con.writeObject(request);
        response = (Response) con.readObject();
        
        if (response.getStatus() != OK) {
            GenericIO.writelnString(name+": Status de mensagem de resposta errado!");
            System.exit(1);
        }
        
        con.close();
    }
    
    /**
     * 
     * @param passageiroId 
     */
    @Override
    public void prepareNextLeg(int passageiroId) {
        ClientCom con = new ClientCom(hostName[MON_TRANSICAO_AEROPORTO], portNumber[MON_TRANSICAO_AEROPORTO]);
        Request request;
        Response response;
        open(con);
        request = new Request(PREPARE_NEXT_LEG, new Object[]{passageiroId});
        con.writeObject(request);
        response = (Response) con.readObject();
        
        if (response.getStatus() != OK) {
            GenericIO.writelnString(name+": Status de mensagem de resposta errado!");
            System.exit(1);
        }
        
        con.close();
    }
    
    /**
     * 
     * @param passageiroID
     * @param dest
     * @param nMalas
     * @return 
     */
    @Override
    public destination whatShouldIDo(int passageiroID, boolean dest, int nMalas) {
        ClientCom con = new ClientCom(hostName[MON_ZONA_DESEMBARQUE], portNumber[MON_ZONA_DESEMBARQUE]);
        Request request;
        Response response;
        open(con);
        request = new Request(WHAT_SHOULD_I_DO, new Object[]{passageiroID,dest,nMalas});
        con.writeObject(request);
        response = (Response) con.readObject();
        
        if (response.getStatus() != OK) {
            GenericIO.writelnString(name+": Status de mensagem de resposta errado!");
            System.exit(1);
        }
        
        if (!(response.getAns() instanceof destination)) {
            GenericIO.writelnString(name+": Estava a espera de um destination!");
            System.exit(1);
        }
        con.close();
        
        return (destination) response.getAns();
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
