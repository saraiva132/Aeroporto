package sdaeroporto;

import Monitores.Logging;
import Monitores.Porao;
import Monitores.Autocarro;
import Monitores.TransiçãoAeroporto;
import Monitores.TransferenciaTerminal;
import Monitores.RecolhaBagagem;
import Monitores.ZonaDesembarque;
import Threads.Motorista;
import Threads.Passageiro;
import Threads.Bagageiro;
import Estruturas.*;
import static Estruturas.AuxInfo.*;
import genclass.FileOp;
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

        Bagageiro bagageiro;
        Passageiro[] passageiro = new Passageiro[passMax];
        Motorista motorista;
        Autocarro auto;
        Porao porao;
        RecolhaBagagem recolha;
        TransferenciaTerminal transferencia;
        TransiçãoAeroporto transicao;
        ZonaDesembarque zona;
        Logging log;
        boolean success;                                     // validação de dados de entrada
        char opt;                                            // opção
        
        /* inicialização */
        do
      { GenericIO.writeString ("Nome do ficheiro de armazenamento da simulação? ");
        fileName = GenericIO.readlnString ();
        if (FileOp.exists (".", fileName))
           { do
             { GenericIO.writeString ("Já existe um directório/ficheiro com esse nome. Quer apagá-lo (s - sim; n - não)? ");
               opt = GenericIO.readlnChar ();
             } while ((opt != 's') && (opt != 'n'));
           success = opt == 's';
           }
           else success = true;
      } while (!success);

        ArrayList<Mala> malas = new ArrayList();
        int[] nMalasPass = new int[passMax];
        boolean[] dest = new boolean[passMax];
        int passTRT=0;

        /*Inicialização das zonas de região crítica*/
        log = new Logging();
        porao = new Porao(malas);
        zona = new ZonaDesembarque();
        auto = new Autocarro();
        recolha = new RecolhaBagagem();
        transferencia = new TransferenciaTerminal();
        transicao = new TransiçãoAeroporto();
//        log.reportInitialStatus();
        /*Inicialização dos elementos activos*/
        bagageiro = new Bagageiro(zona, porao, recolha, log);
        motorista = new Motorista(auto, transferencia, log);
        motorista.start();
        bagageiro.start();
        
        for (int j = 0; j < nChegadas; j++) {

            for (int w = 0; w < passMax; w++) {
                nMalasPass[w] = new Random().nextInt(bagMax + 1);
                dest[w] = getRandomBoolean();
                if(!dest[w])
                    passTRT++;
                for (int l = 0; l < nMalasPass[w]; l++) {
                    malas.add(new Mala(w, !dest[w]));
                }
            }
            
            log.nVoo(j + 1);
            log.setPorao(malas.size());
            log.malasInicial(nMalasPass);
            log.destino(dest);
            log.reportInitialStatus();
            
            for (int i = 0; i < passMax; i++) {
                passageiro[i] = new Passageiro(nMalasPass[i], i, j + 1, dest[i], zona, auto, transicao, recolha, transferencia, log);
            }
            
            transferencia.setnVoo(j + 1,passTRT);
            passTRT=0;
            bagageiro.setnVoo(j + 1);

            /* arranque da simulação */
            for (int i = 0; i < passMax; i++) {
                passageiro[i].start();
            }
            /* aguardar o fim da simulação */
            for (int i = 0; i < passMax; i++) {
                try {
                    passageiro[i].join();
                } catch (InterruptedException e) {
                }
                //GenericIO.writelnString("O passageiro " + i + " do voo " + (j + 1) + " terminou.");
            }
            recolha.resetNoMoreBags();
//            if(j< nChegadas -1)
//                log.reportInitialStatus();
        }
        try {
            motorista.join();

        } catch (InterruptedException e) {
        }
        
        try {
            bagageiro.join();
        } catch (InterruptedException e) {
        }
        log.reportFinalStatus();
        log.close();

    }

    public static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }
}
