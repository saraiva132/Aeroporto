package Threads;

import Estruturas.Globals.bagCollect;
import Estruturas.Globals.destination;
import Estruturas.Globals.passState;
import Estruturas.Reply;
import Estruturas.VectorCLK;
import Interfaces.AutocarroPassageiroInterface;
import Interfaces.RecolhaPassageiroInterface;
import Interfaces.TransferenciaPassageiroInterface;
import Interfaces.TransicaoPassageiroInterface;
import Interfaces.ZonaDesembarquePassageiroInterface;
import java.rmi.RemoteException;

/**
 * Identifica o tipo de dados passageiro
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Passageiro extends Thread {

    /**
     * Identifica o passageiro
     *
     * @serialField id
     */
    private final int id;

    /**
     * Identifica o número de voo do passageiro
     *
     * @serialField nVoo
     */
    private final int nVoo;

    /**
     * Identifica o número de malas total do passageiro
     *
     * @serialField nMalasTotal
     */
    private final int nMalasTotal;

    /**
     * Identifica o número de malas que o passageiro tem em sua posse
     *
     * @serialField nMalasEmPosse
     */
    private int nMalasEmPosse;

    /**
     * Identifica se o passageiro está em trânsito, ou se acaba neste aeroporto
     * a sua viagem
     *
     * @serialField finalDest
     */
    private final boolean finalDest;

    /**
     * Auxilia a simulação do ciclo de vida e as operações que o passageiro pode
     * realizar sobre a zona de desembarque
     *
     * @serialField zona
     */
    private ZonaDesembarquePassageiroInterface desembarque;

    /**
     * Auxilia a simulação do ciclo de vida e as operações que o passageiro pode
     * realizar à saída do aeroporto
     *
     * @serialField transizao
     */
    private TransicaoPassageiroInterface transicao;

    /**
     * Auxilia a simulação do ciclo de vida e as operações que o passageiro pode
     * realizar sobre as zonas de recolha, de armazenamento temporário de
     * bagagens e guichet de reclamação do aeroporto
     *
     * @serialField recolha
     */
    private RecolhaPassageiroInterface recolha;

    /**
     * Auxilia a simulação do ciclo de vida e as operações que o passageiro pode
     * realizar sobre o autocarro
     *
     * @serialField auto
     */
    private AutocarroPassageiroInterface auto;

    /**
     * Auxilia a simulação do ciclo de vida e as operações que o passageiro pode
     * realizar sobre a zona de transferência entre os terminais de chegade e
     * partida
     *
     * @serialField transferencia
     */
    private TransferenciaPassageiroInterface transferencia;
    
    private VectorCLK vc;
    
    
    /**
     * Instanciação e inicialização do passageiro
     *
     * @param ts
     * @param nMalasTotal número de malas total do passageiro
     * @param id identificador do passageiro
     * @param nVoo número do voo em que o passageiro vem
     * @param finalDest TRUE caso este seja o aeroporto de destino do
     * passageiro, FALSE caso contrário
     * @param zona
     * @param auto
     * @param transicao
     * @param recolha
     * @param transferencia
     */
    public Passageiro(VectorCLK ts,int nMalasTotal, int id, int nVoo, boolean finalDest, ZonaDesembarquePassageiroInterface zona, AutocarroPassageiroInterface auto,
            TransicaoPassageiroInterface transicao, RecolhaPassageiroInterface recolha,
            TransferenciaPassageiroInterface transferencia) {
        this.desembarque = zona;
        this.auto = auto;
        this.recolha = recolha;
        this.transicao = transicao;
        this.transferencia = transferencia;
        this.nMalasTotal = nMalasTotal;
        nMalasEmPosse = 0;
        this.finalDest = finalDest;
        this.nVoo = nVoo;   //not used for now
        this.id = id;
        this.vc = ts;
        System.out.println("pass: "+ id +" inicial: "+vc.getVc()[id+2]);
    }

    /**
     * Implementa o ciclo de vida do passageiro
     */
    @Override
    public void run() {
        Reply temp;
        try {
            vc.Add(id+2);
            temp = desembarque.whatShouldIDo(vc,id, finalDest, nMalasTotal);
            destination nextState = (destination) temp.getRetorno();
            vc.CompareVector(temp.getTimestamp().getVc());
            bagCollect getBag;
            switch (nextState) {
                case WITH_BAGGAGE:

                    // System.out.println("tenho bagagem -----------------");
                    do {
                        vc.Add(id+2);
                        temp = recolha.goCollectABag(vc,id);
                        getBag = (bagCollect) temp.getRetorno();
                        vc.CompareVector(temp.getTimestamp().getVc());
                        if ((getBag) == bagCollect.MINE) {
                            nMalasEmPosse++;
                        }
                        //System.out.println("ID: " + id + " posse: " + nMalasEmPosse + " total: " + nMalasTotal);
                        //System.out.println(getBag.toString());

                    } while (nMalasEmPosse < nMalasTotal && getBag != bagCollect.NOMORE);
                    if (nMalasEmPosse < nMalasTotal) {
                        vc.Add(id+2);
                        vc.CompareVector(recolha.reportMissingBags(vc,id, nMalasTotal - nMalasEmPosse).getVc());
                    }
                    vc.Add(id+2);
                    vc.CompareVector(transicao.goHome(vc,id).getVc());
                    break;
                case IN_TRANSIT:
                    int ticket; //bilhete para entrar no autocarro.
                     System.out.println("pass: "+ id +" bef: "+vc.getVc()[id+2]);
                    vc.Add(id+2);
                    temp = transferencia.takeABus(vc,id);
                    ticket = (int) temp.getRetorno();
                    vc.CompareVector(temp.getTimestamp().getVc());
                   
                    vc.Add(id+2);
                    vc.CompareVector(auto.enterTheBus(vc,ticket, id).getVc());
                   
                    vc.Add(id+2);
                    vc.CompareVector(auto.leaveTheBus(vc,id, ticket).getVc());
                    
                    vc.Add(id+2);
                    vc.CompareVector(transicao.prepareNextLeg(vc,id).getVc());
                    System.out.println("pass: "+ id +" aft: "+vc.getVc()[id+2]);
                    break;
                case WITHOUT_BAGGAGE:
                    vc.Add(id+2);
                    vc.CompareVector(transicao.goHome(vc,id).getVc());
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
