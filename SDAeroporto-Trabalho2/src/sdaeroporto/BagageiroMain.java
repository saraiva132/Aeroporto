package sdaeroporto;

import ClientSide.InterfaceMain;
import Estruturas.Globals;
import Threads.Bagageiro;
import genclass.GenericIO;

/**
 * Este tipo de dados simula a solução ao problema <b>Rapsódia no Aeroporto</b> do 
 * lado do cliente correspondente ao <i>bagageiro</i>.
 * <p>
 * A comunicação baseia-se em passagem de mensagens sobre sockets usando o protocolo TCP.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class BagageiroMain {
    /**
     * Programa Principal
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
        for(int i = 0; i<Globals.hostNames.length;i++)
            clientResquest.closeMonitor(i);
    }
}
