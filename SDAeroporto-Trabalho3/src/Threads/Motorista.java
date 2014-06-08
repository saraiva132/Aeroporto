/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;
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
    /**
     * Relógio Vectorial do motorista.
     * 
     * @serialField clk
     */
    private VectorCLK clk;

    /**
     * Instanciação e inicialização do motorista
     * 
     * @param auto referência para o objecto remoto correspondente ao monitor Autocarro
     * @param transferencia referência para o objecto remoto correspondente ao monitor TransferenciaTerminal
     */
    public Motorista(AutocarroMotoristaInterface auto, TransferenciaMotoristaInterface transferencia) {
        this.auto = auto;
        this.transferencia = transferencia;
        clk = new VectorCLK();
    }

    /**
     * Implementa o ciclo de vida do motorista
     */
    @Override
    public void run() {
        boolean hasWork = true;
        try {
            while (hasWork) {
                Reply response;
                clk.Add(1);
                response = transferencia.hasDaysWorkEnded(clk);
                if ((boolean) response.getRetorno()) {
                    hasWork = false;
                }
                clk = response.getTimestamp();
                clk.Add(1);
                response = transferencia.announcingBusBoardingShouting(clk);
                clk = response.getTimestamp();
                clk.Add(1);
                clk = auto.announcingBusBoardingWaiting(clk, (int) response.getRetorno());
                clk.Add(1);
                clk = auto.goToDepartureTerminal(clk);
                clk.Add(1);
                clk = auto.parkTheBusAndLetPassOff(clk);
                clk.Add(1);
                clk = auto.goToArrivalTerminal(clk);
                clk.Add(1);
                clk = auto.parkTheBus(clk);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
