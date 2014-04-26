/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdaeroporto;

import ClientSide.InterfaceMain;
import Estruturas.AuxInfo;
import static Estruturas.AuxInfo.*;
import Estruturas.Mala;
import Threads.Passageiro;
import genclass.GenericIO;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class PassageiroMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GenericIO.writelnString ("O cliente PassageiroMain foi estabelecido!");
        GenericIO.writelnString ("A iniciar operacoes.");
        
        InterfaceMain clientRequest = new InterfaceMain();
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
            
            clientRequest.sendLuggages(malas);
            
            clientRequest.nVoo(i + 1);

            clientRequest.setPorao(malas.size());

            clientRequest.malasInicial(nMalasPass);

            clientRequest.destino(dest);

            clientRequest.reportInitialStatus();

            for (int j = 0; j < passMax; j++) {
                passageiro[j] = new Passageiro(nMalasPass[j], j, i + 1, dest[j]);
            }

            clientRequest.setnVoo(i + 1, passTRT);
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
            clientRequest.resetNoMoreBags();
            malas.clear();
        }
        for(int i = 0; i<AuxInfo.hostName.length;i++)
            clientRequest.closeMonitor(i);
    }

    public static boolean getRandomBoolean() {
        return new Random().nextInt(1) < 0.5;
    }
}
