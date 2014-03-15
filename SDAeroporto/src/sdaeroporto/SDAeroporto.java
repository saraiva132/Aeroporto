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
 * @author rafael
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

        int nIter;//Used?
        String fName;

        /* inicialização */
        GenericIO.writelnString("A começar Aeoroport\n");

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
                malas.add(new Mala(i, dest[i]));
            }
        }

        /*Inicialização das zonas de região crítica*/
        porao = new Porao(malas);
        zona = new ZonaDesembarque();
        auto = new Autocarro();
        recolha = new RecolhaBagagem();
        transferencia = new TransferenciaTerminal();
        transicao = new TransiçãoAeroporto();

        /*Inicialização dos elementos activos*/
        b = new Bagageiro(zona, porao, recolha);
        m = new Motorista(auto, transferencia);

        for (int i = 0; i < passMax; i++) {
            p[i] = new Passageiro(nMalasPass[i], i, 1, dest[i], zona, auto, transicao, recolha, transferencia);
        }

        /* arranque da simulação */
        m.start();
        b.start();
        for (int i = 0; i < passMax; i++) {
            p[i].start();
        }

        /* aguardar o fim da simulação */
    }

    public static boolean getRandomBoolean() {
        return Math.random() < 0.5;
        //I tried another approaches here, still the same result
    }
}
