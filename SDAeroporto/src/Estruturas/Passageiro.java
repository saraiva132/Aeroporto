/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas;

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
        int nextState = desembarque.whatShouldIDo(finalDest, nMalasTotal);
        switch (nextState) {
            case 0:
                while (nMalasEmPosse < nMalasTotal) {
                    recolha.goCollectABag(id);
                    nMalasEmPosse++;
                    state = passState.AT_THE_LUGGAGE_COLLECTION_POINT;
                }
                transicao.goHome();
                state = passState.EXITING_THE_ARRIVAL_TERMINAL;
                break;
            case 1:
                transferencia.takeABus(id);
                state = passState.AT_THE_ARRIVAL_TRANSFER_TERMINAL;
                auto.enterTheBus(id);
                state = passState.TERMINAL_TRANSFER;
                auto.leaveTheBus(id);
                state = passState.AT_THE_DEPARTURE_TRANSFER_TERMINAL;
                transicao.prepareNextLeg();
                state = passState.ENTERING_THE_DEPARTURE_TERMINAL;
                break;
            case 2:
                transicao.goHome();
                state = passState.EXITING_THE_ARRIVAL_TERMINAL;
                break;
        }
    }
}
