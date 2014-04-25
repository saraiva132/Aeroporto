/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdaeroporto;

import ClientSide.InterfaceMain;
import static Estruturas.AuxInfo.*;
import Estruturas.Mala;
import Threads.Passageiro;
import genclass.GenericIO;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Hugo
 */
public class PassageiroMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GenericIO.writelnString ("O cliente PassageiroMain foi estabelecido!");
        GenericIO.writelnString ("A iniciar operacoes.");
        
        InterfaceMain clientResquest = new InterfaceMain();
        Passageiro[] passageiro = new Passageiro[passMax];
        int[] nMalasPass = new int[passMax];
        boolean[] dest = new boolean[passMax];
        int passTRT = 0;
        ArrayList<Mala> malas = new ArrayList();

        for (int i = 0; i < nChegadas; i++) {
            
            for (int j = 0; j < passMax; j++) {
                nMalasPass[j] = new Random().nextInt(bagMax + 1);
                dest[j] = getRandomBoolean();
                if (!dest[j]) {
                    passTRT++;
                }
                for (int l = 0; l < nMalasPass[j]; l++) {
                    malas.add(new Mala(j, !dest[j]));
                }
            }
            
            clientResquest.sendLuggages(malas);
            
            clientResquest.nVoo(i + 1);

            clientResquest.setPorao(malas.size());

            clientResquest.malasInicial(nMalasPass);

            clientResquest.destino(dest);

            clientResquest.reportInitialStatus();

            for (int j = 0; j < passMax; j++) {
                passageiro[j] = new Passageiro(nMalasPass[j], j, i + 1, dest[j]);
            }

            clientResquest.setnVoo(i + 1, passTRT);
            passTRT = 0;

            /* arranque da simulação */
            for (int j = 0; j < passMax; j++) {
                passageiro[j].start();
            }
            /* aguardar o fim da simulação */
            for (int j = 0; j < passMax; j++) {
                try {
                    passageiro[j].join();
                } catch (InterruptedException e) {
                }
                //GenericIO.writelnString("O passageiro " + i + " do voo " + (j + 1) + " terminou.");
            }
            clientResquest.resetNoMoreBags();
            malas.clear();
        }
        clientResquest.reportFinalStatus();
        clientResquest.close();
    }

    public static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }
}
