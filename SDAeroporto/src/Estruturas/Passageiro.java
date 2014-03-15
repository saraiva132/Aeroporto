/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas;

import Estruturas.AuxInfo.bagCollect;
import Estruturas.AuxInfo.destination;
import Interfaces.AutocarroPassageiroInterface;
import Interfaces.ZonaDesembarquePassageiroInterface;
import Estruturas.AuxInfo.passState;
import Interfaces.RecolhaPassageiroInterface;
import Interfaces.TransferenciaPassageiroInterface;
import Interfaces.TransicaoPassageiroInterface;

/**
 *
 * @author rafael
 */
public class Passageiro extends Thread {

    private passState state;
    private int id;
    private int nVoo;
    private int nMalasTotal;
    private int nMalasEmPosse;
    private boolean finalDest;

    private ZonaDesembarquePassageiroInterface desembarque;
    private TransicaoPassageiroInterface transicao;
    private RecolhaPassageiroInterface recolha;
    private AutocarroPassageiroInterface auto;
    private TransferenciaPassageiroInterface transferencia;

    public Passageiro(int nMalasTotal, int id, int nVoo, boolean finalDest,
            ZonaDesembarquePassageiroInterface zona, AutocarroPassageiroInterface auto,
            TransicaoPassageiroInterface transicao, RecolhaPassageiroInterface recolha,
            TransferenciaPassageiroInterface transferencia) {
        this.nMalasTotal = nMalasTotal;
        nMalasEmPosse = 0;
        this.desembarque = zona;
        this.auto = auto;
        this.recolha = recolha;
        this.transicao = transicao;
        this.transferencia = transferencia;
        this.finalDest = finalDest;
        this.nVoo = nVoo;
        this.id = id;
        state = passState.AT_THE_DISEMBARKING_ZONE;
    }

    @Override
    public void run() {
        destination nextState = desembarque.whatShouldIDo(finalDest, nMalasTotal);
        bagCollect getBag = null;
        switch (nextState) {
            case WITH_BAGGAGE:
                state = passState.AT_THE_LUGGAGE_COLLECTION_POINT;
                System.out.println("tenho bagagem -----------------");
                do {
                    getBag = recolha.goCollectABag(id);
                    if (getBag == bagCollect.MINE) {
                        nMalasEmPosse++;
                    }
                    System.out.println("ID: " + id + " posse: " + nMalasEmPosse + " total: " + nMalasTotal);
                    //System.out.println(getBag.toString());

                } while (nMalasEmPosse < nMalasTotal && getBag != bagCollect.NOMORE);
                if (nMalasEmPosse < nMalasTotal) {
                    state = passState.AT_THE_BAGGAGE_RECLAIM_OFFICE;
                    recolha.reportMissingBags(id, nMalasTotal - nMalasEmPosse);
                }
                transicao.goHome();
                state = passState.EXITING_THE_ARRIVAL_TERMINAL;
                break;
            case IN_TRANSIT:
                int ticket; //bilhete para entrar no autocarro.
                ticket = transferencia.takeABus(id);
                state = passState.AT_THE_ARRIVAL_TRANSFER_TERMINAL;
                auto.enterTheBus(ticket);
                state = passState.TERMINAL_TRANSFER;
                auto.leaveTheBus(ticket);
                state = passState.AT_THE_DEPARTURE_TRANSFER_TERMINAL;
                transicao.prepareNextLeg();
                state = passState.ENTERING_THE_DEPARTURE_TERMINAL;
                break;
            case WITHOUT_BAGGAGE:
                transicao.goHome();
                state = passState.EXITING_THE_ARRIVAL_TERMINAL;
                break;
        }
        System.out.println("PASSAGEIRO MORREU PAH!");
    }
}
