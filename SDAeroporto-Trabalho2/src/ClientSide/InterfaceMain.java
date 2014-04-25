/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClientSide;

import static Estruturas.AuxInfo.*;
import Estruturas.Mala;
import Message.Request;
import Message.Response;
import genclass.GenericIO;
import static java.lang.Thread.sleep;
import java.util.ArrayList;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class InterfaceMain {
    

    public InterfaceMain() {
    }
    
    /**
     * Actualização do número de voo no repositório geral
     * @param nVoo 
     */
    public void nVoo(int nVoo){
        ClientCom con = new ClientCom(hostName[MON_LOGGING], portNumber[MON_LOGGING]);
        Request request;
        Response response;
        open(con);
        request = new Request(N_VOO, new Object[]{nVoo});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(response);
        con.close();
    }
    
    /**
     * Actualização do número de malas no porão para o repositório geral
     * (actualizado no inicio de cada voo)
     * @param nMalas 
     */
    public void setPorao(int nMalas){
        ClientCom con = new ClientCom(hostName[MON_LOGGING], portNumber[MON_LOGGING]);
        Request request;
        Response response;
        open(con);
        request = new Request(SET_PORAO, new Object[]{nMalas});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(response);
        con.close();
    }
    
    /**
     * Actualização do número de malas iniciais de cada passageiro para o repositório geral
     * (actualizado no inicio de cada voo)
     * @param nMalasPass 
     */
    public void malasInicial(int[] nMalasPass){
        ClientCom con = new ClientCom(hostName[MON_LOGGING], portNumber[MON_LOGGING]);
        Request request;
        Response response;
        open(con);
        Object [] obj = new Object[nMalasPass.length];
        for (int i = 0; i < nMalasPass.length; i++) {   obj[i] = nMalasPass[i]; }
        request = new Request(MALAS_INICIAL, obj);
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(response);
        con.close();
    }
    
    /**
     * Actualização do destino de cada passageiro para o repositório geral
     * (actualizado no inicio de cada voo)
     * @param dest 
     */
    public void destino(boolean [] dest){
        ClientCom con = new ClientCom(hostName[MON_LOGGING], portNumber[MON_LOGGING]);
        Request request;
        Response response;
        open(con);
        Object [] obj = new Object[dest.length];
        for (int i = 0; i < dest.length; i++) {   obj[i] = dest[i]; }
        request = new Request(DESTINO, obj);
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(response);
        con.close();
    }
    
    /**
     * Método que indica ao repositório geral o ínicio de um novo voo.
     * Limpa os valores das variáveis.
     */
    public void reportInitialStatus(){
        ClientCom con = new ClientCom(hostName[MON_LOGGING], portNumber[MON_LOGGING]);
        Request request;
        Response response;
        open(con);
        request = new Request(REPORT_INITIAL_STATUS, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(response);
        con.close();
    }
    
    /**
     * Método que indica ao repositório geral o fim de um voo.
     */
    public void reportFinalStatus(){
        ClientCom con = new ClientCom(hostName[MON_LOGGING], portNumber[MON_LOGGING]);
        Request request;
        Response response;
        open(con);
        request = new Request(REPORT_FINAL_STATUS, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(response);
        con.close();
    }
    
    /**
     * Indica ao repositório geral que a simulação terminou.
     */
    public void close(){
        ClientCom con = new ClientCom(hostName[MON_LOGGING], portNumber[MON_LOGGING]);
        Request request;
        Response response;
        open(con);
        request = new Request(CLOSE, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(response);
        con.close();
    }  
    
    /** 
     * Actualiza, por cada voo, o monitor de transferencia de terminal com o número de voo 
     * e os passageiros em transito.
     * @param nVoo
     * @param passTRT 
     */
    public void setnVoo(int nVoo,int passTRT){
        ClientCom coni = new ClientCom(hostName[MON_TRANSFERENCIA_TERMINAL], portNumber[MON_TRANSFERENCIA_TERMINAL]);
        Request request;
        Response response;
        open(coni);
        request = new Request(SET_N_VOO, new Object[]{nVoo,passTRT});
        coni.writeObject(request);
        response = (Response) coni.readObject();
        checkStatus(response);
        coni.close();
    }
    
    /**
     * ???
     */
    public void resetNoMoreBags(){
        ClientCom coni = new ClientCom(hostName[MON_RECOLHA_BAGAGEM], portNumber[MON_RECOLHA_BAGAGEM]);
        Request request;
        Response response;
        open(coni);
        request = new Request(RESET_NOMORE_BAGS, new Object[]{});
        coni.writeObject(request);
        response = (Response) coni.readObject();
        checkStatus(response);
        coni.close();
    }
    
    /**
     * Metodo que preenche o porao com as malas de cada voo.
     * @param malas 
     */
    public void sendLuggages(ArrayList<Mala> malas){
        ClientCom coni = new ClientCom(hostName[MON_PORAO], portNumber[MON_PORAO]);
        Request request;
        Response response;
        open(coni);
        Object [] obj = new Object[malas.size()];
        
        for(int i = 0; i<malas.size();i++){
                obj[i] = malas.get(i);
        }
        
        request = new Request(SEND_LUGAGES, obj);
        coni.writeObject(request);
        response = (Response) coni.readObject();
        checkStatus(response);
        coni.close();
    }
    
    /**
     * Verifica se uma mensagem de resposta não devolveu uma mensagem de erro.
     * @param response 
     */

    public void closeMonitor(int monitorId){
        ClientCom coni = new ClientCom(hostName[monitorId], portNumber[monitorId]);
        Request request;
        Response response;
        open(coni);        
        request = new Request(CLOSE, new Object[]{});
        coni.writeObject(request);
        response = (Response) coni.readObject();
        checkStatus(response);
        coni.close();
    }
    
    private void checkStatus(Response response) {
        if (response.getStatus() != OK) {
            GenericIO.writelnString("PassageiroMain: Status de mensagem de resposta errado!");
            System.exit(1);
        }
    }
    
    /**
     * Metodo que bloqueia o programa enquanto espera que a comunicação seja estabelecida
     * @param con 
     */
    private void open(ClientCom coni){
        while (!coni.open()) // aguarda ligação
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
    }
    
}
