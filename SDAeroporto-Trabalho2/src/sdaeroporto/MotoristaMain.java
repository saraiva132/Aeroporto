/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import Threads.Motorista;
import genclass.GenericIO;

/**
 *
 * @author Hugo
 */
public class MotoristaMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GenericIO.writelnString ("O cliente MotoristaMain foi estabelecido!");
        GenericIO.writelnString ("A iniciar operacoes.");
        Motorista motorista = new Motorista();
        motorista.start();
        try {
            motorista.join();
        } catch (InterruptedException ex) {
            GenericIO.writelnString("Erro a terminar motorista!");
            System.exit(-1);
        }
    }
    
}
