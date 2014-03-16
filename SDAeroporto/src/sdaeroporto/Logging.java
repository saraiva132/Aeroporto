/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdaeroporto;

import Estruturas.AuxInfo.*;
import static Estruturas.AuxInfo.passMax;

/**
 *
 * @author rafael
 */
public class Logging {

    private passState[] pstate;
    private bagState bstate;
    private motState mstate;

    public Logging() {
        pstate = new passState[passMax];
        init();
        bstate = bagState.WAITING_FOR_A_PLANE_TO_LAND;
        mstate = motState.PARKING_AT_THE_ARRIVAL_TERMINAL;
        for (int i = 0; i < passMax; i++) {
            pstate[i] = passState.AT_THE_DISEMBARKING_ZONE;
        }
    }

    private void init() {
        System.out.println("A começar logging...");
        System.out.println("Barbeiro     Motorista    Passageiro1   Passageiro2    Passageiro3   Passageiro4   Passageiro5");
    }

    private synchronized void reportStatus() {
        String lineStatus = "";
        lineStatus += " " + bstate.toString() + " ";
        lineStatus += " " + mstate.toString() + " ";
        for (int i = 0; i < passMax; i++) {
            lineStatus += " " + pstate[i].toString() + " ";
        }
        System.out.println(lineStatus);
    }

    public synchronized void reportState(int passID, passState state) {
        pstate[passID] = state;
        reportStatus();
    }

    public void reportState(motState state) {
        mstate = state;
        reportStatus();
    }

    public void reportState(bagState state) {
        bstate = state;
        reportStatus();
    }
}
