package Interfaces;

import Estruturas.Reply;
import Estruturas.VectorCLK;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Identifica as operações que um passageiro pode realizar no monitor 
 * <b>ZonaDesembarque</b>
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface ZonaDesembarquePassageiroInterface extends Remote{
    
    /**
     * O que devo fazer?
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro após sair do avião reflecte sobre para onde deve ir. O último 
     * passageiro deve notificar o bagageiro de que já pode começar a ir buscar 
     * as malas ao porão do avião
     * 
     * @param vc relógio vectorial do passageiro
     * @param passageiroID identificador do passageiro
     * @param dest 
     * <ul>
     * <li>TRUE se este aeroporto é o seu destino
     * <li>FALSE caso contrário
     * </ul>
     * @param nMalas número de malas que o passageiro contém
     * @return  Relógio vectorial actualizado juntamente com qual o seu próximo passo dependendo da sua condição:
     * <ul>
     * <li> WITH_BAGAGE caso este seja o seu destino e possua bagagens
     * <li> WTHOUT_BAGAGE caso este seja o seu destino e não possua bagagens
     * <li> IN_TRANSIT caso esteja em trânsito
     * </ul>
     * @throws java.rmi.RemoteException
     */
    public Reply whatShouldIDo(VectorCLK vc, int passageiroID, boolean dest,int nMalas) throws RemoteException;
    /**
     * Fechar o monitor ZonaDesembarque.
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro, após concluir o seu ciclo de vida invoca a operação para
     * fechar o monitor <i>ZonaDesembarque</i>.
     *
     * @throws RemoteException
     */
    public void shutdownMonitor() throws RemoteException;
}
