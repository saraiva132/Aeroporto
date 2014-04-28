package ClientSide;

import static Estruturas.Globals.*;
import Estruturas.Mala;
import Message.Request;
import Message.Response;
import genclass.GenericIO;
import static java.lang.Thread.sleep;
import java.util.ArrayList;

/**
 * Comunicação Main's.
 * <p>
 * Responsável pela comunicação entre as mains <i>PassageiroMain</i>, <i>BagageiroMain</i> e <i>MotoristaMain</i> 
 * e os servidores encarregues pela comunicação dos respectivos monitores com Sistemas 
 * Computacionais distintos.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class InterfaceMain {
    /**
     * Instanciação da <b>InterfaceMain</b>
     */
    public InterfaceMain() {
    }
    
    /**
     * Chamada remota ao monitor <b>Logging</b> no âmbito da operação <i>nVoo</i>
     * <ul>
     * <li> Estabelece a ligação com o respectivo monitor
     * <li> Envia uma mensagem que representa a chamada à função <i>nVoo</i>; 
     * a mensagem contém os argumentos necessários para a chamada à função
     * <li> Espera pela resposta do servidor
     * </ul>
     * @param nVoo número de voo em que a simulação se encontra
     */
    public void nVoo(int nVoo){
        ClientCom con = new ClientCom(hostNames[MON_LOGGING], portNumber[MON_LOGGING]);
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
     * Chamada remota ao monitor <b>Logging</b> no âmbito da operação <i>setPorao</i>
     * <ul>
     * <li> Estabelece a ligação com o respectivo monitor
     * <li> Envia uma mensagem que representa a chamada à função <i>setPorao</i>; 
     * a mensagem contém os argumentos necessários para a chamada à função
     * <li> Espera pela resposta do servidor
     * </ul>
     * @param nMalas número de malas no porão
     */
    public void setPorao(int nMalas){
        ClientCom con = new ClientCom(hostNames[MON_LOGGING], portNumber[MON_LOGGING]);
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
     * Chamada remota ao monitor <b>Logging</b> no âmbito da operação <i>malasInicial</i>
     * <ul>
     * <li> Estabelece a ligação com o respectivo monitor
     * <li> Envia uma mensagem que representa a chamada à função <i>malasInicial</i>; 
     * a mensagem contém os argumentos necessários para a chamada à função
     * <li> Espera pela resposta do servidor
     * </ul>
     * @param nMalasPass número de malas totais que petencem aos passageiros
     */
    public void malasInicial(int[] nMalasPass){
        ClientCom con = new ClientCom(hostNames[MON_LOGGING], portNumber[MON_LOGGING]);
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
     * Chamada remota ao monitor <b>Logging</b> no âmbito da operação <i>destino</i>
     * <ul>
     * <li> Estabelece a ligação com o respectivo monitor
     * <li> Envia uma mensagem que representa a chamada à função <i>destino</i>; 
     * a mensagem contém os argumentos necessários para a chamada à função
     * <li> Espera pela resposta do servidor
     * </ul>
     * @param dest 
     * <ul>
     * <li>FALSE caso esteja em trânsito
     * <li>TRUE caso contrário
     * </ul>
     */
    public void destino(boolean [] dest){
        ClientCom con = new ClientCom(hostNames[MON_LOGGING], portNumber[MON_LOGGING]);
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
     * Chamada remota ao monitor <b>Logging</b> no âmbito da operação <i>reportInitialStatus</i>
     * <ul>
     * <li> Estabelece a ligação com o respectivo monitor
     * <li> Envia uma mensagem que representa a chamada à função <i>reportInitialStatus</i>
     * <li> Espera pela resposta do servidor
     * </ul>
     */
    public void reportInitialStatus(){
        ClientCom con = new ClientCom(hostNames[MON_LOGGING], portNumber[MON_LOGGING]);
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
     * Chamada remota ao monitor <b>Logging</b> no âmbito da operação <i>reportFinalStatus</i>
     * <ul>
     * <li> Estabelece a ligação com o respectivo monitor
     * <li> Envia uma mensagem que representa a chamada à função <i>reportFinalStatus</i>
     * <li> Espera pela resposta do servidor
     * </ul>
     */
    public void reportFinalStatus(){
        ClientCom con = new ClientCom(hostNames[MON_LOGGING], portNumber[MON_LOGGING]);
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
     * Chamada remota ao monitor <b>Logging</b> no âmbito da operação <i>shutdownMonitor</i>
     * <ul>
     * <li> Estabelece a ligação com o respectivo monitor
     * <li> Envia uma mensagem que representa a chamada à função <i>close</i>
     * <li> Espera pela resposta do servidor
     * </ul>
     */
    public void close(){
        ClientCom con = new ClientCom(hostNames[MON_LOGGING], portNumber[MON_LOGGING]);
        Request request;
        Response response;
        open(con);
        request = new Request(SHUTDOWN_MONITOR, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(response);
        con.close();
    }  
    
    /** 
     * Chamada remota ao monitor <b>TransferenciaTerminal</b> no âmbito da operação <i>setnVoo</i>
     * <ul>
     * <li> Estabelece a ligação com o respectivo monitor
     * <li> Envia uma mensagem que representa a chamada à função <i>setnVoo</i>; 
     * a mensagem contém os argumentos necessários para a chamada à função
     * <li> Espera pela resposta do servidor
     * </ul>
     * @param nVoo número de  voo
     * @param passTRT número de passageiros em trânsito neste voo
     */
    public void setnVoo(int nVoo,int passTRT){
        ClientCom coni = new ClientCom(hostNames[MON_TRANSFERENCIA_TERMINAL], portNumber[MON_TRANSFERENCIA_TERMINAL]);
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
     * Chamada remota ao monitor <b>RecolhaBagagem</b> no âmbito da operação <i>resetNoMoreBags</i>
     * <ul>
     * <li> Estabelece a ligação com o respectivo monitor
     * <li> Envia uma mensagem que representa a chamada à função <i>reseltNoMoreBags</i>
     * <li> Espera pela resposta do servidor
     * </ul>
     */
    public void resetNoMoreBags(){
        ClientCom coni = new ClientCom(hostNames[MON_RECOLHA_BAGAGEM], portNumber[MON_RECOLHA_BAGAGEM]);
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
     * Chamada remota ao monitor <b>Porao</b> no âmbito da operação <i>sendLuggages</i>
     * <ul>
     * <li> Estabelece a ligação com o respectivo monitor
     * <li> Envia uma mensagem que representa a chamada à função <i>sendLuggages</i>; 
     * a mensagem contém os argumentos necessários para a chamada à função
     * <li> Espera pela resposta do servidor
     * </ul>
     * @param malas malas dos passageiros que acabaram de aterrar
     */
    public void sendLuggages(ArrayList<Mala> malas){
        ClientCom coni = new ClientCom(hostNames[MON_PORAO], portNumber[MON_PORAO]);
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
     * Chamada remota ao monitor com o objectivo de o terminar
     * <ul>
     * <li> Estabelece a ligação com o respectivo monitor
     * <li> Envia uma mensagem que representa a chamada à função <i>shutdownMonitor</i>; 
     * a mensagem contém os argumentos necessários para a chamada à função
     * <li> Espera pela resposta do servidor
     * </ul>
     * @param monitorId identificador do monitor
     */
    public void closeMonitor(int monitorId){
        ClientCom coni = new ClientCom(hostNames[monitorId], portNumber[monitorId]);
        Request request;
        Response response;
        open(coni);        
        request = new Request(SHUTDOWN_MONITOR, new Object[]{});
        coni.writeObject(request);
        response = (Response) coni.readObject();
        checkStatus(response);
        coni.close();
    }
    /**
     * Verificar o estado da mensagem resposta do servidor.
     * 
     * @param response mensagem de resposta do servidor
     */
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
