/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientSide;

import static Estruturas.AuxInfo.*;
import Estruturas.Mala;
import Interfaces.PoraoBagageiroInterface;
import Interfaces.RecolhaBagageiroInterface;
import Interfaces.ZonaDesembarqueBagageiroInterface;
import Message.Request;
import Message.Response;
import genclass.GenericIO;
import static java.lang.Thread.sleep;

/**
 *
 * @author Hugo
 */
public class InterfaceBagageiro implements PoraoBagageiroInterface, RecolhaBagageiroInterface, ZonaDesembarqueBagageiroInterface {

    @Override
    public Mala tryToCollectABag() {
        ClientCom con = new ClientCom(hostName[MON_PORAO], portNumber[MON_PORAO]);
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

    @Override
    public bagDest carryItToAppropriateStore(Mala mala) {
        ClientCom con = new ClientCom(hostName[MON_RECOLHA_BAGAGEM], portNumber[MON_RECOLHA_BAGAGEM]);
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

    @Override
    public void takeARest() {
        ClientCom con = new ClientCom(hostName[MON_ZONA_DESEMBARQUE], portNumber[MON_ZONA_DESEMBARQUE]);
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

    @Override
    public void noMoreBagsToCollect() {
        ClientCom con = new ClientCom(hostName[MON_ZONA_DESEMBARQUE], portNumber[MON_ZONA_DESEMBARQUE]);
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
