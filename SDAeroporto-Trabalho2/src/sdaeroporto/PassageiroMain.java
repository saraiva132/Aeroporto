package sdaeroporto;

import ClientSide.InterfaceMain;
import Estruturas.Globals;
import static Estruturas.Globals.*;
import Estruturas.Mala;
import Threads.Passageiro;
import genclass.GenericIO;
import java.util.ArrayList;
import java.util.Random;

/**
 * Este tipo de dados simula a solução ao problema <b>Rapsódia no Aeroporto</b> do 
 * lado do cliente correspondente ao <i>passageiro</i>.
 * <p>
 * A comunicação baseia-se em passagem de mensagens sobre sockets usando o protocolo TCP.
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class PassageiroMain {

    /**
     * Programa Principal
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
                nMalasPass[j] = Math.abs( new Random().nextInt()%(bagMax+1));
                dest[j] = getRandomDestination();
                if (!dest[j]) {
                    passTRT++;
                }
                for (int l = 0; l < nMalasPass[j]; l++) {
                    malas.add(new Mala(j, !dest[j]));
                }
            }
            
            clientRequest.sendLuggages(malas);
            System.out.println("malas size: "+malas.size());
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
                
            }
            clientRequest.resetNoMoreBags();
            malas.clear();
        }
        for(int i = 0; i<Globals.hostNames.length;i++)
            clientRequest.closeMonitor(i);
        
    }

    /**
     * Gerar aleatoriamente o destino de um passageiro com a probabilidade de 70% de ser destino final e com probabilidade de 30% de estar em trânsito.
     * 
     * @return Informação sobre o tipo do passageiro:
     * <ul>
     * <li>TRUE, caso este seja o aeroporto de destino do passageiro
     * <li>FALSE, caso o passageiro esteja em trânsito
     * </ul>
     */
    public static boolean getRandomDestination() {
        return (Math.abs( new Random().nextInt()%2)) < 0.7;
    }
}
