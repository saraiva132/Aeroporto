/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import ClientSide.InterfaceMain;
import Estruturas.AuxInfo;
import Threads.Bagageiro;
import genclass.GenericIO;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class BagageiroMain {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GenericIO.writelnString ("O cliente BagageiroMain foi estabelecido!");
        GenericIO.writelnString ("A iniciar operacoes.");
        Bagageiro bagageiro = new Bagageiro();
        InterfaceMain clientResquest = new InterfaceMain();
        bagageiro.start();
        try {
            bagageiro.join();
        } catch (InterruptedException ex) {
            GenericIO.writelnString("Erro a terminar bagageiro!");
            System.exit(-1);
        }
        for(int i = 0; i<AuxInfo.hostName.length;i++)
            clientResquest.closeMonitor(i);
    }
}
