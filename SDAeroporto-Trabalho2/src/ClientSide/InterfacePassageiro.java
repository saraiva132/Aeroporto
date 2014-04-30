package ClientSide;


import static Estruturas.Globals.*;
import static Estruturas.Globals.OK;
import Estruturas.Globals.bagCollect;
import Estruturas.Globals.destination;
import static Estruturas.Globals.hostNames;
import static Estruturas.Globals.portNumber;
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
 * Comunicação Passageiro.
 * <p>  
 * Responsável pela comunicação entre o passageiro e os monitores com os quais interage.
 * Cada operação que um passageiro necessita de realizar sobre cada monitor corresponde ao 
 * estabelecimento de uma conexão e respectiva comunicação entre o Sistema Computacional onde
 * está a correr a thread <i>Passageiro</i> e o Sistema Computacional onde corre o respectivo monitor.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class InterfacePassageiro implements AutocarroPassageiroInterface, RecolhaPassageiroInterface, TransferenciaPassageiroInterface, TransicaoPassageiroInterface, ZonaDesembarquePassageiroInterface{
    /**
     * Identificador do passageiro
     * 
     * @serialField name
     */
    private final String name;

    /**
     * Instanciação da <i>InterfacePassageiro</i>
     * @param name identificador do passageiro
     */
    public InterfacePassageiro(String name) {
        this.name = name;
    }
    
    /**
     * Chamada remota ao monitor <b>Autocarro</b> no âmbito da operação <i>enterTheBus</i>
     * @param ticketID lugar onde o passageiro se pode sentar
     * @param passageiroId identificador do passageiro
     */
    @Override
    public void enterTheBus(int ticketID, int passageiroId){
        ClientCom con = new ClientCom(hostNames[MON_AUTOCARRO], portNumber[MON_AUTOCARRO]);
        Request request;
        Response response;
        open(con);
        request = new Request(ENTER_THE_BUS, new Object[]{ticketID,passageiroId});
        con.writeObject(request);
        response = (Response) con.readObject();
        
        if (response.getStatus() != OK || request.getSerial() != response.getSerial()) {
            GenericIO.writelnString(name+": Status de mensagem de resposta errado!");
            System.exit(1);
        }
        
        con.close();
    }
    
    /**
     * Chamada remota ao monitor <b>Autocarro</b> no âmbito da operação <i>leaveTheBus</i>
     * @param passageiroId identificador do passageiro
     * @param ticketID lugar onde o passageiro estava sentado
     */
    @Override
    public void leaveTheBus(int passageiroId, int ticketID) {
    
        ClientCom con = new ClientCom(hostNames[MON_AUTOCARRO], portNumber[MON_AUTOCARRO]);
        Request request;
        Response response;
        open(con);
        request = new Request(LEAVE_THE_BUS, new Object[]{passageiroId,ticketID});
        con.writeObject(request);
        response = (Response) con.readObject();
        
        if (response.getStatus() != OK || request.getSerial() != response.getSerial()) {
            GenericIO.writelnString(name+": Status de mensagem de resposta errado!");
            System.exit(1);
        }
        
        con.close();
    }
    
    /**
     * Chamada remota ao monitor <b>RecolhaBagagem</b> no âmbito da operação <i>goToCollectABag</i>
     * 
     * @param bagID identificador da mala
     * @return Forma como conseguiu apanhar a sua mala: 
     * <ul>
     * <li>MINE, com sucesso 
     * <li>UNSUCCESSFUL, sem sucesso 
     * </ul> 
     * <p>Alternativamente, a informação de que já não vale a 
     * pena continuar a espera da(s) sua(s) mala(s) que lhe falta(m):
     * <ul>
     * <li>NOMORE
     * </ul>
     */
    @Override
    public bagCollect goCollectABag(int bagID) {
        ClientCom con = new ClientCom(hostNames[MON_RECOLHA_BAGAGEM], portNumber[MON_RECOLHA_BAGAGEM]);
        Request request;
        Response response;
        open(con);
        request = new Request(GO_COLLECT_A_BAG, new Object[]{bagID});
        con.writeObject(request);
        response = (Response) con.readObject();
        
        if (response.getStatus() != OK || request.getSerial() != response.getSerial()) {
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
     * Chamada remota ao monitor <b>RecolhaBagagem</b> no âmbito da operação <i>reportMissingBags</i>
     * @param passageiroID identificador do passageiro
     * @param malasPerdidas número de malas perdidas
     */
    @Override
    public void reportMissingBags(int passageiroID, int malasPerdidas) {
        ClientCom con = new ClientCom(hostNames[MON_RECOLHA_BAGAGEM], portNumber[MON_RECOLHA_BAGAGEM]);
        Request request;
        Response response;
        open(con);
        request = new Request(REPORT_MISSING_BAGS, new Object[]{passageiroID,malasPerdidas});
        con.writeObject(request);
        response = (Response) con.readObject();
        
        if (response.getStatus() != OK || request.getSerial() != response.getSerial()) {
            GenericIO.writelnString(name+": Status de mensagem de resposta errado!");
            System.exit(1);
        }
        
        con.close();
    }
    
    /**
     * Chamada remota ao monitor <b>TransferenciaTerminal</b> no âmbito da operação <i>takeABus</i>
     * @param passageiroID identificador do passageiro
     * @return Posição do seu assento no autocarro
     */
    @Override
    public int takeABus(int passageiroID) {
        ClientCom con = new ClientCom(hostNames[MON_TRANSFERENCIA_TERMINAL], portNumber[MON_TRANSFERENCIA_TERMINAL]);
        Request request;
        Response response;
        open(con);
        request = new Request(TAKE_A_BUS, new Object[]{passageiroID});
        con.writeObject(request);
        response = (Response) con.readObject();
        
        if (response.getStatus() != OK || request.getSerial() != response.getSerial()) {
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
     * Chamada remota ao monitor <b>TransicaoAeroporto</b> no âmbito da operação <i>goHome</i>
     * 
     * @param passageiroId identificador do passageiro
     */
    @Override
    public void goHome(int passageiroId) {
        ClientCom con = new ClientCom(hostNames[MON_TRANSICAO_AEROPORTO], portNumber[MON_TRANSICAO_AEROPORTO]);
        Request request;
        Response response;
        open(con);
        request = new Request(GO_HOME, new Object[]{passageiroId});
        con.writeObject(request);
        response = (Response) con.readObject();
        
        if (response.getStatus() != OK || request.getSerial() != response.getSerial()) {
            GenericIO.writelnString(name+": Status de mensagem de resposta errado!");
            System.exit(1);
        }
        
        con.close();
    }
    
    /**
     * Chamada remota ao monitor <b>TransicaoAeroporto</b> no âmbito da operação <i>prepareNextLeg</i>
     * 
     * @param passageiroId identificador do passageiro
     */
    @Override
    public void prepareNextLeg(int passageiroId) {
        ClientCom con = new ClientCom(hostNames[MON_TRANSICAO_AEROPORTO], portNumber[MON_TRANSICAO_AEROPORTO]);
        Request request;
        Response response;
        open(con);
        request = new Request(PREPARE_NEXT_LEG, new Object[]{passageiroId});
        con.writeObject(request);
        response = (Response) con.readObject();
        
        if (response.getStatus() != OK || request.getSerial() != response.getSerial()) {
            GenericIO.writelnString(name+": Status de mensagem de resposta errado!");
            System.exit(1);
        }
        
        con.close();
    }
    
    /**
     * Chamada remota ao monitor <b>ZonaDesembarque</b> no âmbito da operação <i>whatShouldIDo</i>
     * 
     * @param passageiroID identificador do passageiro
     * @param dest 
     * <ul>
     * <li>TRUE se este aeroporto é o seu destino
     * <li>FALSE caso contrário
     * </ul>
     * @param nMalas número de malas que o passageiro contém
     * @return  Qual o seu próximo passo dependendo da sua condição:
     * <ul>
     * <li> WITH_BAGAGE caso este seja o seu destino e possua bagagens
     * <li> WTHOUT_BAGAGE caso este seja o seu destino e não possua bagagens
     * <li> IN_TRANSIT caso esteja em trânsito
     * </ul>
     */
    @Override
    public destination whatShouldIDo(int passageiroID, boolean dest, int nMalas) {
        ClientCom con = new ClientCom(hostNames[MON_ZONA_DESEMBARQUE], portNumber[MON_ZONA_DESEMBARQUE]);
        Request request;
        Response response;
        open(con);
        request = new Request(WHAT_SHOULD_I_DO, new Object[]{passageiroID,dest,nMalas});
        con.writeObject(request);
        response = (Response) con.readObject();
        
        if (response.getStatus() != OK || request.getSerial() != response.getSerial()) {
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
