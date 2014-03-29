package Threads;

import Estruturas.AuxInfo.bagCollect;
import Estruturas.AuxInfo.destination;
import Interfaces.AutocarroPassageiroInterface;
import Interfaces.ZonaDesembarquePassageiroInterface;
import Estruturas.AuxInfo.passState;
import Interfaces.LoggingPassageiroInterface;
import Interfaces.RecolhaPassageiroInterface;
import Interfaces.TransferenciaPassageiroInterface;
import Interfaces.TransicaoPassageiroInterface;

/**
 * Identifica o tipo de dados passageiro
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Passageiro extends Thread {

    /**
     * Identifica o estado do passageiro
     *
     * @serialField state
     */
    private passState state;

    /**
     * Identifica o passageiro
     *
     * @serialField id
     */
    private int id;

    /**
     * Identifica o número de voo do passageiro
     *
     * @serialField nVoo
     */
    private int nVoo;

    /**
     * Identifica o número de malas total do passageiro
     *
     * @serialField nMalasTotal
     */
    private int nMalasTotal;

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
    private boolean finalDest;

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
     * Auxilia no logging da evolução dos estados do problema ao longo da
     * simulação
     *
     * @serialField log
     */
    private LoggingPassageiroInterface log;

    /**
     * Instanciação e inicialização do passageiro
     *
     * @param nMalasTotal número de malas total do passageiro
     * @param id identificador do passageiro
     * @param nVoo número do voo em que o passageiro vem
     * @param finalDest TRUE caso este seja o aeroporto de destino do
     * passageiro, FALSE caso contrário
     * @param zona monitor corresondente à zona de desembarque
     * @param auto monitor correspondente ao autorcarro
     * @param transicao monitor correspondente à saída do aeroporto
     * @param recolha monitor correspondente às zonas de recolha e armazenamento
     * temporário de bagagens
     * @param transferencia monitor correspondente à zona de transferência entre
     * terminais
     * @param log monitor correspondente ao logging do problema
     */
    public Passageiro(int nMalasTotal, int id, int nVoo, boolean finalDest,
            ZonaDesembarquePassageiroInterface zona, AutocarroPassageiroInterface auto,
            TransicaoPassageiroInterface transicao, RecolhaPassageiroInterface recolha,
            TransferenciaPassageiroInterface transferencia, LoggingPassageiroInterface log) {
        this.nMalasTotal = nMalasTotal;
        nMalasEmPosse = 0;
        this.desembarque = zona;
        this.auto = auto;
        this.recolha = recolha;
        this.transicao = transicao;
        this.transferencia = transferencia;
        this.log = log;
        this.finalDest = finalDest;
        this.nVoo = nVoo;   //not used for now
        this.id = id;
    }

    /**
     * Implementa o ciclo de vida do passageiro
     */
    @Override
    public void run() {
        destination nextState = desembarque.whatShouldIDo(id,finalDest, nMalasTotal,log);
        bagCollect getBag = null;
        switch (nextState) {
            case WITH_BAGGAGE:
                
                // System.out.println("tenho bagagem -----------------");
                do {
                    if ( (getBag=recolha.goCollectABag(id,log) ) == bagCollect.MINE) {
                        nMalasEmPosse++;
                    }
                    //System.out.println("ID: " + id + " posse: " + nMalasEmPosse + " total: " + nMalasTotal);
                    //System.out.println(getBag.toString());

                } while (nMalasEmPosse < nMalasTotal && getBag != bagCollect.NOMORE);
                if (nMalasEmPosse < nMalasTotal) {
                    recolha.reportMissingBags(id, nMalasTotal - nMalasEmPosse,log);
                }
                transicao.goHome(id,log);
                break;
            case IN_TRANSIT:
                int ticket; //bilhete para entrar no autocarro.
                ticket = transferencia.takeABus(log,id);
                auto.enterTheBus( log,ticket,id);
                auto.leaveTheBus(id, log,ticket);
                transicao.prepareNextLeg(id,log);
                break;
            case WITHOUT_BAGGAGE:
                transicao.goHome(id,log);
                break;
        }
    }
}
