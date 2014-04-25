/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientSide;

import Estruturas.AuxInfo;
import static Estruturas.AuxInfo.*;
import Interfaces.LoggingBagageiroInterface;
import Interfaces.LoggingMotoristaInterface;
import Interfaces.LoggingPassageiroInterface;
import Message.Request;
import Message.Response;
import genclass.GenericIO;
import static java.lang.Thread.sleep;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class InterfaceMonitoresLogging implements LoggingPassageiroInterface, LoggingBagageiroInterface, LoggingMotoristaInterface {

    private String name;
    ClientCom con;

    public InterfaceMonitoresLogging(String name) {
        this.name = name;
        con = new ClientCom(hostName[MON_LOGGING], portNumber[MON_LOGGING]);
    }
    
    /**
     * 
     * @param passID
     * @param state 
     */
    @Override
    public void reportState(int passID, AuxInfo.passState state) {
        Request request;
        Response response;
        open();
        request = new Request(REPORT_STATE_PASSAGEIRO, new Object[]{passID,state});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(response);
        con.close();
    }
    
    /**
     * 
     * @param take 
     */
    @Override
    public void bagagemBelt(boolean take) {

        Request request;
        Response response;
        open();
        request = new Request(BAGAGEM_BELT, new Object[]{take});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(response);
        con.close();
    }
    
    /**
     * 
     * @param passID 
     */
    @Override
    public void malasActual(int passID) {
        Request request;
        Response response;
        open();
        request = new Request(MALAS_ACTUAL, new Object[]{passID});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(response);
        con.close();
    }
    
    /**
     * 
     * @param id 
     */
    @Override
    public void addfilaEspera(int id) {
        Request request;
        Response response;
        open();
        System.out.println("vou apanhar o bus: "+id);
        request = new Request(ADD_FILA_ESPERA, new Object[]{id});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(response);
        con.close();
    }
    
    /**
     * 
     */
    @Override
    public void removefilaEspera() {
        Request request;
        Response response;
        open();
        request = new Request(REMOVE_FILA_ESPERA, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(response);
        con.close();
    }
    
    /**
     * 
     * @param seats 
     */
    @Override
    public void autocarroState(int[] seats) {
        Request request;
        Response response;
        open();
        Object [] obj = new Object[seats.length];
        for(int i = 0; i< seats.length;i++)
        {
            obj[i] = seats[i];
        }
        request = new Request(AUTOCARRO_STATE, obj);
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(response);
        con.close();
    }
    
    /**
     * 
     * @param malasPerdidas 
     */
    @Override
    public void missingBags(int malasPerdidas) {
        Request request;
        Response response;
        open();
        request = new Request(MISSING_BAGS, new Object[]{malasPerdidas});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(response);
        con.close();

    }
    
    /**
     * 
     * @param state 
     */
    @Override
    public void reportState(AuxInfo.bagState state) {
        Request request;
        Response response;
        open();
        request = new Request(REPORT_STATE_BAGAGEIRO, new Object[]{state});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(response);
        con.close();
    }
    
    /**
     * 
     */
    @Override
    public void bagagemPorao() {
        Request request;
        Response response;
        open();
        request = new Request(BAGAGEM_PORAO, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(response);
        con.close();

    }
    
    /**
     * 
     */
    @Override
    public void bagagemStore() {
        Request request;
        Response response;
        open();
        request = new Request(BAGAGEM_STORE, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(response);
        con.close();

    }
    
    /**
     * 
     * @param state 
     */
    @Override
    public void reportState(AuxInfo.motState state) {
        Request request;
        Response response;
        open();
        request = new Request(REPORT_STATE_MOTORISTA, new Object[]{state});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(response);
        con.close();
    }
    
    /**
     * 
     * @param response 
     */
    private void checkStatus(Response response) {
        if (response.getStatus() != OK) {
            GenericIO.writelnString(name + ": Status de mensagem de resposta errado!");
            System.exit(1);
        }
    }
    
    /**
     * Metodo que bloqueia o programa enquanto espera que a comunicação seja estabelecida
     * @param con 
     */
    private void open(){
        while (!con.open()) // aguarda ligação
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
    }
}
