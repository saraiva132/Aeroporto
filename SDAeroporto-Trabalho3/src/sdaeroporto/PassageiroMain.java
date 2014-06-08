package sdaeroporto;

import Estruturas.Globals;
import static Estruturas.Globals.*;
import Estruturas.Mala;
import Estruturas.VectorCLK;
import Interfaces.AutocarroPassageiroInterface;
import Interfaces.LoggingInterface;
import Interfaces.PoraoPassageiroInterface;
import Interfaces.RecolhaPassageiroInterface;
import Interfaces.TransferenciaPassageiroInterface;
import Interfaces.TransicaoPassageiroInterface;
import Interfaces.ZonaDesembarquePassageiroInterface;
import Threads.Passageiro;
import genclass.GenericIO;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Random;

/**
 * Este tipo de dados simula a solução ao problema <b>Rapsódia no Aeroporto</b>
 * do lado do cliente correspondente ao <i>passageiro</i>.
 * <p>
 * A comunicação baseia-se em Java RMI
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class PassageiroMain {

    static {
        System.setProperty("java.security.policy", "java.policy");
    }

    /**
     * Programa Principal
     */
    public static void main(String[] args) {
        GenericIO.writelnString("O cliente PassageiroMain foi estabelecido!");
        GenericIO.writelnString("A iniciar operacoes.");
        Globals.xmlParser();
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        Passageiro[] passageiro = new Passageiro[passMax];
        int[] nMalasPass = new int[passMax];
        boolean[] dest = new boolean[passMax];
        int passTRT = 0;
        ArrayList<Mala> malas = new ArrayList();

        Registry registry;
        AutocarroPassageiroInterface autoInter = null;
        RecolhaPassageiroInterface recolhaInter = null;
        TransicaoPassageiroInterface transicaoInter = null;
        ZonaDesembarquePassageiroInterface desembarqueInter = null;
        TransferenciaPassageiroInterface transferenciaInter = null;
        PoraoPassageiroInterface poraoInter = null;
        LoggingInterface logInter = null;
        try {
            registry = LocateRegistry.getRegistry(registryHostname, registryPort);
            poraoInter = (PoraoPassageiroInterface) registry.lookup("Porao");
            logInter = (LoggingInterface) registry.lookup("Logging");
            autoInter = (AutocarroPassageiroInterface) registry.lookup("Autocarro");
            recolhaInter = (RecolhaPassageiroInterface) registry.lookup("RecolhaBagagem");
            transicaoInter = (TransicaoPassageiroInterface) registry.lookup("TransiçãoAeroporto");
            desembarqueInter = (ZonaDesembarquePassageiroInterface) registry.lookup("ZonaDesembarque");
            transferenciaInter = (TransferenciaPassageiroInterface) registry.lookup("TransferenciaTerminal");
        } catch (RemoteException e) {
            GenericIO.writelnString("Excepção na localização do Monitor: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            GenericIO.writelnString("Um monitor não está registado: " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        }
        VectorCLK[] clk = new VectorCLK[passMax];
        for (int i = 0; i < clk.length; i++) {
            clk[i] = new VectorCLK();
        }
        for (int i = 0; i < nChegadas; i++) {

            for (int j = 0; j < passMax; j++) {
                nMalasPass[j] = Math.abs(new Random().nextInt() % (bagMax + 1));
                dest[j] = getRandomDestination();
                if (!dest[j]) {
                    passTRT++;
                }
                for (int l = 0; l < nMalasPass[j]; l++) {
                    malas.add(new Mala(j, !dest[j]));
                }
            }

            Mala[] obj = new Mala[malas.size()];

            for (int k = 0; k < malas.size(); k++) {
                obj[k] = malas.get(k);
            }
            try {

                poraoInter.sendLuggages(obj);
                logInter.nVoo(i + 1);
                logInter.setPorao(malas.size());
                logInter.malasInicial(nMalasPass);
                logInter.destino(dest);
                logInter.reportInitialStatus();
            } catch (RemoteException e) {
                System.exit(1);
            }

            for (int j = 0; j < passMax; j++) {
                passageiro[j] = new Passageiro(clk[j], nMalasPass[j], j, i + 1, dest[j], desembarqueInter, autoInter, transicaoInter, recolhaInter, transferenciaInter);
            }

            try {
                transferenciaInter.setnVoo(i + 1, passTRT);
            } catch (RemoteException e) {
                System.exit(1);
            }
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
            try {
                recolhaInter.resetNoMoreBags();
            } catch (RemoteException e) {
                System.exit(1);
            }

            malas.clear();
        }
        try {
            logInter.shutdownMonitor();
            transferenciaInter.shutdownMonitor();
            autoInter.shutdownMonitor();
            poraoInter.shutdownMonitor();
            recolhaInter.shutdownMonitor();
            transicaoInter.shutdownMonitor();
            desembarqueInter.shutdownMonitor();
        } catch (RemoteException e) {
            System.out.printf("Cant shutdown Monitor");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Gerar aleatoriamente o destino de um passageiro com a probabilidade de
     * 70% de ser destino final e com probabilidade de 30% de estar em trânsito.
     *
     * @return Informação sobre o tipo do passageiro:
     * <ul>
     * <li>TRUE, caso este seja o aeroporto de destino do passageiro
     * <li>FALSE, caso o passageiro esteja em trânsito
     * </ul>
     */
    public static boolean getRandomDestination() {
        return (Math.abs(new Random().nextInt() % 2)) < 0.7;
    }
}
