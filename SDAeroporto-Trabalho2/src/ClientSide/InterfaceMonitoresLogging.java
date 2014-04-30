package ClientSide;

import Estruturas.Globals;
import static Estruturas.Globals.*;
import Interfaces.LoggingBagageiroInterface;
import Interfaces.LoggingMotoristaInterface;
import Interfaces.LoggingPassageiroInterface;
import Message.Request;
import Message.Response;
import genclass.GenericIO;
import static java.lang.Thread.sleep;

/**
 * Comunicação monitores.
 * <p>
 * Responsável pela comunicaçao entre os monitores <i>Autocarro</i>, <i>Porao</i>, 
 * <i>RecolhaBagagem</i>, <i>TransferenciaTerminal</i>, <i>TransicaoAeroporto</i> 
 * e <i>ZonaDesembarque</i> com o monitor <i>Logging</i> (corrrespondente ao estado
 * geral do problema <b>Rapsódia no Aeroporto</b>) no âmbito das operações realizadas neles.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class InterfaceMonitoresLogging implements LoggingPassageiroInterface, LoggingBagageiroInterface, LoggingMotoristaInterface {

    /**
     * Identificador do monitor
     * 
     * @serialField name
     */
    private String name;
    
    /**
     * Canal de comunicação, do lado do cliente.
     * 
     * @serialField con
     */
    ClientCom con;
    
    /**
     * Instanciação da <i>InterfaceMonitoresLogging</i>.
     * 
     * @param name identificiador do monitor
     */

    public InterfaceMonitoresLogging(String name) {
        this.name = name;
        con = new ClientCom(hostNames[MON_LOGGING], portNumber[MON_LOGGING]);
    }
    
    /**
     * Chamada remota ao monitor <b>Logging</b> no âmbito da operação <i>reportState</i>
     * 
     * @param passID identificador do passageiro
     * @param state estado do passageiro
     */
    @Override
    public void reportState(int passID, Globals.passState state) {
        Request request;
        Response response;
        open();
        request = new Request(REPORT_STATE_PASSAGEIRO, new Object[]{passID,state});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(request,response);
        con.close();
    }
    
    /**
     * Chamada remota ao monitor <b>Logging</b> no âmbito da operação <i>bagagemBelt</i>
     * 
     * @param take 
     * <ul>
     * <li>TRUE caso tenha sido retirada da passadeira
     * <li>FALSE caso tenha sido depositada na passadeira
     * </ul>
     */
    @Override
    public void bagagemBelt(boolean take) {

        Request request;
        Response response;
        open();
        request = new Request(BAGAGEM_BELT, new Object[]{take});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(request,response);
        con.close();
    }
    
    /**
     * Chamada remota ao monitor <b>Logging</b> no âmbito da operação <i>malasActual</i>
     * 
     * @param passID identificador do passageiro
     */
    @Override
    public void malasActual(int passID) {
        Request request;
        Response response;
        open();
        request = new Request(MALAS_ACTUAL, new Object[]{passID});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(request,response);
        con.close();
    }
    
    /**
     * Chamada remota ao monitor <b>Logging</b> no âmbito da operação <i>addFilaEspera</i>
     * 
     * @param id identificador do passageiro
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
        checkStatus(request,response);
        con.close();
    }
    
    /**
     * Chamada remota ao monitor <b>Logging</b> no âmbito da operação <i>removeFilaEspera</i>
     */
    @Override
    public void removefilaEspera() {
        Request request;
        Response response;
        open();
        request = new Request(REMOVE_FILA_ESPERA, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(request,response);
        con.close();
    }
    
    /**
     * Chamada remota ao monitor <b>Logging</b> no âmbito da operação <i>autocarroState</i>
     * 
     * @param seats assentos do autocarro
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
        checkStatus(request,response);
        con.close();
    }
    
    /**
     * Chamada remota ao monitor <b>Logging</b> no âmbito da operação <i>missingBags</i>
     * 
     * @param malasPerdidas número de malas perdidas
     */
    @Override
    public void missingBags(int malasPerdidas) {
        Request request;
        Response response;
        open();
        request = new Request(MISSING_BAGS, new Object[]{malasPerdidas});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(request,response);
        con.close();

    }
    
    /**
     * Chamada remota ao monitor <b>Logging</b> no âmbito da operação <i>reportState</i>
     * 
     * @param state estado do bagageiro
     */
    @Override
    public void reportState(Globals.bagState state) {
        Request request;
        Response response;
        open();
        request = new Request(REPORT_STATE_BAGAGEIRO, new Object[]{state});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(request,response);
        con.close();
    }
    
    /**
     * Chamada remota ao monitor <b>Logging</b> no âmbito da operação <i>bagagemPorao</i>
     */
    @Override
    public void bagagemPorao() {
        Request request;
        Response response;
        open();
        request = new Request(BAGAGEM_PORAO, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(request,response);
        con.close();

    }
    
    /**
     * Chamada remota ao monitor <b>Logging</b> no âmbito da operação <i>bagagemStore</i>
     */
    @Override
    public void bagagemStore() {
        Request request;
        Response response;
        open();
        request = new Request(BAGAGEM_STORE, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(request,response);
        con.close();

    }
    
    /**
     * Chamada remota ao monitor <b>Logging</b> no âmbito da operação <i>reportState</i>
     * @param state estado do motorista
     */
    @Override
    public void reportState(Globals.motState state) {
        Request request;
        Response response;
        open();
        request = new Request(REPORT_STATE_MOTORISTA, new Object[]{state});
        con.writeObject(request);
        response = (Response) con.readObject();
        checkStatus(request,response);
        con.close();
    }
    
    /**
     * Verificar o estado da mensagem resposta do servidor.
     * 
     * @param response mensagem de resposta do servidor
     */
    private void checkStatus(Request request, Response response) {
        if (response.getStatus() != OK || request.getSerial() != response.getSerial()) {
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
