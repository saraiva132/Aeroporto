package Threads;

import Estruturas.Globals.bagCollect;
import Estruturas.Globals.destination;
import Estruturas.Globals.passState;
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

    /**
     * Instanciação e inicialização do passageiro
     *
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
    public Passageiro(int nMalasTotal, int id, int nVoo, boolean finalDest, ZonaDesembarquePassageiroInterface zona, AutocarroPassageiroInterface auto,
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
    }

    /**
     * Implementa o ciclo de vida do passageiro
     */
    @Override
    public void run() {
        try {
            destination nextState = desembarque.whatShouldIDo(id, finalDest, nMalasTotal);
            bagCollect getBag;
            switch (nextState) {
                case WITH_BAGGAGE:

                    // System.out.println("tenho bagagem -----------------");
                    do {
                        if ((getBag = recolha.goCollectABag(id)) == bagCollect.MINE) {
                            nMalasEmPosse++;
                        }
                        //System.out.println("ID: " + id + " posse: " + nMalasEmPosse + " total: " + nMalasTotal);
                        //System.out.println(getBag.toString());

                    } while (nMalasEmPosse < nMalasTotal && getBag != bagCollect.NOMORE);
                    if (nMalasEmPosse < nMalasTotal) {
                        recolha.reportMissingBags(id, nMalasTotal - nMalasEmPosse);
                    }
                    transicao.goHome(id);
                    break;
                case IN_TRANSIT:
                    int ticket; //bilhete para entrar no autocarro.
                    ticket = transferencia.takeABus(id);
                    auto.enterTheBus(ticket, id);
                    auto.leaveTheBus(id, ticket);
                    transicao.prepareNextLeg(id);
                    break;
                case WITHOUT_BAGGAGE:
                    transicao.goHome(id);
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}