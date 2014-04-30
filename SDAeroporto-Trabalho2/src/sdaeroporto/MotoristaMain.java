package sdaeroporto;

import ClientSide.InterfaceMain;
import Estruturas.Globals;
import Threads.Motorista;
import genclass.GenericIO;

/**
 * Este tipo de dados simula a solução ao problema <b>Rapsódia no Aeroporto</b> do 
 * lado do cliente correspondente ao <i>motorista</i>.
 * <p>
 * A comunicação baseia-se em passagem de mensagens sobre sockets usando o protocolo TCP.
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class MotoristaMain {

    /**
     * Programa Principal
     */
    public static void main(String[] args) {
        GenericIO.writelnString ("O cliente MotoristaMain foi estabelecido!");
        GenericIO.writelnString ("A iniciar operacoes.");
        Motorista motorista = new Motorista();
        InterfaceMain clientResquest = new InterfaceMain();
        motorista.start();
        try {
            motorista.join();
        } catch (InterruptedException ex) {
            GenericIO.writelnString("Erro a terminar motorista!");
            System.exit(-1);
        }
        for(int i = 0; i<Globals.hostNames.length;i++)
            clientResquest.closeMonitor(i);
    }
    
}
