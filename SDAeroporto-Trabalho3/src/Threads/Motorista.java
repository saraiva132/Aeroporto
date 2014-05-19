/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Estruturas.Globals.motState;
import Estruturas.Reply;
import Estruturas.VectorCLK;
import Interfaces.AutocarroMotoristaInterface;
import Interfaces.TransferenciaMotoristaInterface;
import java.rmi.RemoteException;

/**
 * Identifica o tipo de dados motorista
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Motorista extends Thread {

    /**
     * Auxilia a simulação do ciclo de vida e as operações que o motorista pode
     * realizar sobre o autocarro
     *
     * @serialField auto
     */
    private AutocarroMotoristaInterface auto;

    /**
     * Auxilia a simulação do ciclo de vida e as operações que o motorista pode
     * realizar sobre a zona de transferência entre os terminais de chegade e
     * partida
     *
     * @serialField transferencia
     */
    private TransferenciaMotoristaInterface transferencia;
    
    private VectorCLK vc;

    /**
     * Instanciação e inicialização do motorista
     */
    public Motorista(AutocarroMotoristaInterface auto, TransferenciaMotoristaInterface transferencia) {
        this.auto = auto;
        this.transferencia = transferencia;
        vc = new VectorCLK();
    }

    /**
     * Implementa o ciclo de vida do motorista
     */
    @Override
    public void run() {
        boolean hasWork = true;
        try {
            while (hasWork) {
                Reply temp;
                vc.Add(1);
                temp = transferencia.hasDaysWorkEnded(vc);
                if ((boolean) temp.getRetorno()) {
                    hasWork = false;
                }
                vc = temp.getTs();
                vc.Add(1);
                temp = transferencia.announcingBusBoardingShouting(vc);
                vc = temp.getTs();
                vc.Add(1);
                vc = auto.announcingBusBoardingWaiting(vc, (int) temp.getRetorno());
                vc.Add(1);
                vc = auto.goToDepartureTerminal(vc);
                vc.Add(1);
                vc = auto.parkTheBusAndLetPassOff(vc);
                vc.Add(1);
                vc = auto.goToArrivalTerminal(vc);
                vc.Add(1);
                vc = auto.parkTheBus(vc);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
