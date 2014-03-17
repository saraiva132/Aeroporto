/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdaeroporto;

import static Estruturas.AuxInfo.*;
import Estruturas.Bagageiro;
import Estruturas.Mala;
import Estruturas.Motorista;
import Estruturas.Passageiro;
import genclass.GenericIO;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class SDAeroporto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Bagageiro b;
        Passageiro[] p = new Passageiro[passMax];
        Motorista m;
        Autocarro auto;
        Porao porao;
        RecolhaBagagem recolha;
        TransferenciaTerminal transferencia;
        TransiçãoAeroporto transicao;
        ZonaDesembarque zona;
        Logging log;
        

        /* inicialização */
        GenericIO.writelnString("A começar Airport \n");

        ArrayList<Mala> malas = new ArrayList();
        int[] nMalasPass = new int[passMax];
        boolean[] dest = new boolean[passMax];
        int randtemp;

        //Gerar malas por passageiro
        //Gerar destino do passageiro
        for (int i = 0; i < passMax; i++) {
            nMalasPass[i] = new Random().nextInt(3);
            dest[i] = getRandomBoolean();
            for (int j = 0; j < nMalasPass[i]; j++) {
                malas.add(new Mala(i, !dest[i]));
            }
        }

        /*Inicialização das zonas de região crítica*/
        log = new Logging();
        porao = new Porao(malas);
        zona = new ZonaDesembarque();
        auto = new Autocarro();
        recolha = new RecolhaBagagem();
        transferencia = new TransferenciaTerminal();
        transicao = new TransiçãoAeroporto();

        /*Inicialização dos elementos activos*/
        b = new Bagageiro(zona, porao, recolha,log);
        m = new Motorista(auto, transferencia,log);
        m.start();
        b.start();
        for (int j = 0; j < nChegadas; j++) {
            
            System.out.println("-------------------------------------------------A começar uma nova simulação----------------------------------------------------------A começar uma nova simulação----------------------------------------");
            for (int w = 0; w < passMax; w++) {
                nMalasPass[w] = new Random().nextInt(3);
                dest[w] = getRandomBoolean();
                for (int l = 0; l < nMalasPass[w]; l++) {
                    malas.add(new Mala(w, !dest[w]));
                }
            }
            
            for (int i = 0; i < passMax; i++) {
                p[i] = new Passageiro(nMalasPass[i], i, j+1, dest[i], zona, auto, transicao, recolha, transferencia,log);
            }

            /* arranque da simulação */
            for (int i = 0; i < passMax; i++) {
                p[i].start();
            }

            /* aguardar o fim da simulação */
            for (int i = 0; i < passMax; i++) {
                try {
                    p[i].join();
                } catch (InterruptedException e) {
                }
                //GenericIO.writelnString("O passageiro " + i + " terminou.");
            }
        }

        try {
            m.join();
        } catch (InterruptedException e) {
        }
        GenericIO.writelnString(
                "O motorista terminou.");
        try {
            b.join();
        } catch (InterruptedException e) {
        }
        GenericIO.writelnString(
                "O bagageiro terminou.");

    }

    public static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }
}
