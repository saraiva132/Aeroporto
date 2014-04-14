/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientSide;

import Estruturas.AuxInfo;
import Interfaces.AutocarroPassageiroInterface;
import Interfaces.RecolhaPassageiroInterface;
import Interfaces.TransferenciaPassageiroInterface;
import Interfaces.TransicaoPassageiroInterface;
import Interfaces.ZonaDesembarquePassageiroInterface;
import Message.Request;
import Message.Response;

/**
 *
 * @author rafael
 */
public class InterfacePassageiro implements AutocarroPassageiroInterface, RecolhaPassageiroInterface, TransferenciaPassageiroInterface, TransicaoPassageiroInterface, ZonaDesembarquePassageiroInterface {

    /**
     * @param hostName nome do sistema computacional onde está localizado o
     * servidor
     */
    private String [] hostname;
    /**
     * @param port número do port de escuta do servidor
     */
    private int [] port;

    public InterfacePassageiro(String [] hostName, int [] port) {
        this.hostname = hostName;
        this.port = port;
    }

    @Override
    public void enterTheBus(int ticketID, int passID) {
        ClientCom con = new ClientCom(hostname[0], port[0]);
        Response outMessage;
        if (!con.open()) {
            return;
        }
        outMessage = SendRequest(con,1,ticketID,passID);
        con.close();
    }

    @Override
    public void leaveTheBus(int ticketID, int passID) {
        ClientCom con = new ClientCom(hostname[0], port[0]);
        Response outMessage;
        if (!con.open()) {
            return;
        }
        outMessage = SendRequest(con,2,ticketID,passID);
        con.close();
    }

    @Override
    public AuxInfo.bagCollect goCollectABag(int bagID) {
        ClientCom con = new ClientCom(hostname[2], port[2]);
        Response outMessage;
        if (!con.open()) {
            return null;
        }
        outMessage = SendRequest(con,3,bagID);
        con.close();
        return (AuxInfo.bagCollect) outMessage.getAns();
    }

    @Override
    public void reportMissingBags(int passageiroID, int malasPerdidas) {
        ClientCom con = new ClientCom(hostname[2], port[2]);
        Response outMessage;
        if (!con.open()) {
            return;
        }
        outMessage = SendRequest(con,4,passageiroID,malasPerdidas);
        con.close();
    }

    @Override
    public int takeABus(int passageiroID) {
        ClientCom con = new ClientCom(hostname[3], port[3]);
        Response outMessage;
        if (!con.open()) {
            return 0;
        }
        outMessage = SendRequest(con,5,passageiroID);
        con.close();
        return (int) outMessage.getAns();
    }

    @Override
    public void goHome(int passID) {
        ClientCom con = new ClientCom(hostname[4], port[4]);
        Response outMessage;
        if (!con.open()) {
            return;
        }
        outMessage = SendRequest(con,6,passID);
        con.close();
    }

    @Override
    public void prepareNextLeg(int passID) {
        ClientCom con = new ClientCom(hostname[4], port[4]);
        Response outMessage;
        if (!con.open()) {
            return;
        }
        outMessage = SendRequest(con,7,passID);
        con.close();
    }

    @Override
    public AuxInfo.destination whatShouldIDo(boolean dest, int nMalas, int passID) {
        ClientCom con = new ClientCom(hostname[5], port[5]);
        Response outMessage;
        if (!con.open()) {
            return null;
        }
        outMessage = SendRequest(con,8,dest,nMalas,passID);
        con.close();
        return (AuxInfo.destination) outMessage.getAns();
    }
    
    public Response SendRequest(ClientCom con,int messID,Object... args)
    {  
        Request inMessage = new Request(messID,args);
        Response outMessage;
        con.writeObject(inMessage);
        outMessage = (Response) con.readObject();
         if (outMessage.getStatus() != 0) {
            //tratamento de codigos de erro
        }
         return outMessage;
    }
}
