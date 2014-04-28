package ClientSide;

import static Estruturas.Globals.*;
import Estruturas.Mala;
import Interfaces.PoraoBagageiroInterface;
import Interfaces.RecolhaBagageiroInterface;
import Interfaces.TransicaoBagageiroInterface;
import Interfaces.ZonaDesembarqueBagageiroInterface;
import Message.Request;
import Message.Response;
import genclass.GenericIO;
import static java.lang.Thread.sleep;

/**
 * Comunicação Bagageiro.
 * 
 * <p>Responsável pela comunicação entre o bagageiro e os monitores com os quais interage.
 * Cada operação que o bagageiro necessita de realizar sobre cada monitor corresponde ao 
 * estabelecimento de uma conexão e respectiva comunicação entre o Sistema Computacional onde
 * está a correr a thread <i>Bagageiro</i> e o Sistema Computacional onde corre o respectivo monitor.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class InterfaceBagageiro implements PoraoBagageiroInterface, RecolhaBagageiroInterface, ZonaDesembarqueBagageiroInterface, TransicaoBagageiroInterface {

    /**
     * Instanciação da <b>InterfaceBagageiro</b>
     */
    
    public InterfaceBagageiro() {}
    /**
     * Chamada remota ao monitor <b>Porao</b> no âmbito da operação <i>tryToCollectABag</i>
     * <ul>
     * <li> Estabelece a ligação com o respectivo monitor
     * <li> Envia uma mensagem que representa a chamada à função <i>tryToCollectABag</i>
     * <li> Espera pela resposta do servidor e devole-a para a thread <i>Bagageiro</i>
     * </ul>
     * @return Mala que o bagageiro recolheu
     */
    @Override
    public Mala tryToCollectABag() {
        ClientCom con = new ClientCom(hostNames[MON_PORAO], portNumber[MON_PORAO]);
        Request request;
        Response response;
        open(con);
        request = new Request(TRY_TO_COLLECT_A_BAG, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        if (response.getStatus() != OK) {
            GenericIO.writelnString("Bagageiro: Status de mensagem de resposta errado!");
            System.exit(1);
        }
        if (!(response.getAns() instanceof Mala) && response.getAns() != null) {
            GenericIO.writelnString("Bagageiro: Estou a espera de uma mala!");
            System.exit(1);
        }
        con.close();
        return (Mala) response.getAns();
    }
    
    /**
     * Chamada remota ao monitor <b>RecolhaBagagem</b> no âmbito da operação <i>carryItToAppropriateStore</i>
     * <ul>
     * <li> Estabelece a ligação com o respectivo monitor
     * <li> Envia uma mensagem que representa a chamada à função <i>carryItToAppropriateStore</i>; 
     * a mensagem contém os argumentos necessários para a chamada à função
     * <li> Espera pela resposta do servidor e devole-a para a thread <i>Bagageiro</i>
     * </ul>
     * @param mala mala que o bagageiro transporta
     * @return 
     * Local para onde levou a mala:
     * <ul>
     * <li>STOREROOM, zona de armazenamento temporário de bagagens
     * <li>BELT, zona de recolha de bagagens
     * </ul>
     * Alternativamente, caso o objecto mala seja um null, a informação de que o porão já se encontra vazio:
     * <ul>
     * <li>LOBBYCLEAN
     * </ul>
     */
    @Override
    public bagDest carryItToAppropriateStore(Mala mala) {
        ClientCom con = new ClientCom(hostNames[MON_RECOLHA_BAGAGEM], portNumber[MON_RECOLHA_BAGAGEM]);
        Request request;
        Response response;
        open(con);
        request = new Request(CARRY_IT_TO_APPROPRIATE_STORE, new Object[]{mala});
        con.writeObject(request);
        response = (Response) con.readObject();
        con.close();
        if (response.getStatus() != OK) {
            GenericIO.writelnString("Bagageiro: Status de mensagem de resposta errado!");
            System.exit(1);
        }
        if (!(response.getAns() instanceof bagDest)) {
            GenericIO.writelnString("Bagageiro: Estava a espera de um bagDest!");
            System.exit(1);
        }
        return (bagDest) response.getAns();
    }
    
    /**
     * Chamada remota ao monitor <b>ZonaDesembarque</b> no âmbito da operação <i>takeARest</i>
     * <ul>
     * <li> Estabelece a ligação com o respectivo monitor
     * <li> Envia uma mensagem que representa a chamada à função <i>takeARest</i>
     * <li> Espera pela resposta do servidor e retorna ao ciclo de vida do <i>Bagageiro</i>
     * </ul>
     */
    @Override
    public void takeARest() {
        ClientCom con = new ClientCom(hostNames[MON_ZONA_DESEMBARQUE], portNumber[MON_ZONA_DESEMBARQUE]);
        Request request;
        Response response;
        open(con);
        request = new Request(TAKE_A_REST, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        con.close();
        if (response.getStatus() != OK) {
            GenericIO.writelnString("Bagageiro: Status de mensagem de resposta errado!");
            System.exit(1);
        }
    }
    
    /**
     * Chamada remota ao monitor <b>ZonaDesembarque</b> no âmbito da operação <i>noMoreBagsToCollect</i>
     * <ul>
     * <li> Estabelece a ligação com o respectivo monitor
     * <li> Envia uma mensagem que representa a chamada à função <i>takeARest</i>
     * <li> Espera pela resposta do servidor e retorna ao ciclo de vida do <i>Bagageiro</i>
     * </ul>
     */
    @Override
    public void noMoreBagsToCollect() {
        ClientCom con = new ClientCom(hostNames[MON_ZONA_DESEMBARQUE], portNumber[MON_ZONA_DESEMBARQUE]);
        Request request;
        Response response;
        open(con);
        request = new Request(NO_MORE_BAGS_TO_COLLECT, new Object[]{});
        con.writeObject(request);
        response = (Response) con.readObject();
        con.close();
        if (response.getStatus() != OK) {
            GenericIO.writelnString("Bagageiro: Status de mensagem de resposta errado!");
            System.exit(1);
        }
    }
    
    /**
     * Chamada remota ao monitor <b>TransicaoAeroporto</b> no âmbito da operação <i>noMoreBagsToCollect</i>
     * <ul>
     * <li> Estabelece a ligação com o respectivo monitor
     * <li> Envia uma mensagem que representa a chamada à função <i>bagageiroDone</i>
     * <li> Espera pela resposta do servidor e retorna ao ciclo de vida do <i>Bagageiro</i>
     * </ul>
     */
    @Override
    public void bagageiroDone() {
        ClientCom con = new ClientCom(hostNames[MON_TRANSICAO_AEROPORTO], portNumber[MON_TRANSICAO_AEROPORTO]);
        Request request;
        Response response;
        open(con);
        request = new Request(BAGAGEIRO_DONE, new Object[]{});
        System.out.println("Bagageiro acabou!(ClientInterface)");
        con.writeObject(request);
        response = (Response) con.readObject();
        con.close();
        if (response.getStatus() != OK) {
            GenericIO.writelnString("Bagageiro: Status de mensagem de resposta errado!");
            System.exit(1);
        }    
    }
    
    /**
     * Método auxiliar que bloqueia o programa enquanto espera que a comunicação seja estabelecida
     * 
     * @param con canal de comunicação do lado do cliente 
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
