/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import Threads.Bagageiro;
import genclass.GenericIO;

/**
 *
 * @author Hugo
 */
public class BagageiroMain {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GenericIO.writelnString ("O cliente BagageiroMain foi estabelecido!");
        GenericIO.writelnString ("A iniciar operacoes.");
        Bagageiro bagageiro = new Bagageiro();
        bagageiro.start();
        try {
            bagageiro.join();
        } catch (InterruptedException ex) {
            GenericIO.writelnString("Erro a terminar bagageiro!");
            System.exit(-1);
        }
    }
}
