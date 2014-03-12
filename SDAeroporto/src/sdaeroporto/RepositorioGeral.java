/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdaeroporto;

import Estruturas.*;

/**
 *
 * @author rafael
 */
public class RepositorioGeral {

    /**
     * Bagageiro
     */
//private Bagageiro b;
//private Passageiro p[];
//private Motorista m;
    private static int BagageiroState;

    private static int MotoristaState;

    private static int[] PassageiroState;

    public RepositorioGeral(int nPass) {
        PassageiroState = new int[nPass];
        BagageiroState = AuxInfo.WAITINGPLANE;
        MotoristaState = AuxInfo.PARKINGARRIVAL;

        for (int i = 0; i < nPass; i++) {
            PassageiroState[i] = AuxInfo.ZONADESEMBARQUE;
        }
    }

    public static synchronized int getBagageiroState() {
        return BagageiroState;
    }

    public static synchronized void setBagageiroState(int State) {
        BagageiroState = State;
    }

    public static synchronized int getMotoristaState() {
        return MotoristaState;
    }

    public static synchronized void setMotoristaState(int State) {
        MotoristaState = State;
    }

    public static synchronized int getPassageiroState(int passageiroID) {
        return PassageiroState[passageiroID];
    }

    public static synchronized void setPassageiroState(int passageiroID,int passageiroState) {
        PassageiroState[passageiroID] = passageiroState;
    }
}
