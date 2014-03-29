package Monitores;

import Estruturas.AuxInfo;
import Estruturas.Mala;
import Interfaces.LoggingBagageiroInterface;
import Interfaces.PoraoBagageiroInterface;
import java.util.ArrayList;

/**
 *
 * Monitor que simula a interacção do passageiro com o porão de um avião
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Porao implements PoraoBagageiroInterface {

    /**
     * Conjunto de malas que estão no porão do avião
     *
     * @serialField malas
     */
    ArrayList<Mala> malas;
    /**
     * Instanciação e inicialização do monitor <b>Porao</b>
     *
     * @param malas malas que se encontram no porão
     */
    public Porao(ArrayList<Mala> malas) {
        this.malas = malas;
    }

    /**
     * Tentar recolher uma mala
     * <p>
     * Invocador: bagageiro
     * <p>
     * O bagageiro desloca-se ao porão do avião e caso este não se encontre
     * vazio recolhe uma mala
     *
     * @param log referência para o monitor de logging; utilizado para reportar a evolução do estado global do problema
     * @return Mala que apanhou no porão e informação sobre o estado do mesmo
     */
    @Override
    public synchronized Mala tryToCollectABag(LoggingBagageiroInterface log) {
        //System.out.println("Procurando mala..");
        
//        BagageiroTransportation mala =new BagageiroTransportation();
//        if(malas.size()>1)
//            mala.setTransport(malas.remove(0), false);
//        else
//            mala.setTransport(malas.remove(0), true);
//        return mala;
        
        if (malas.isEmpty()) {
            return null;
        } else {
        log.reportState(AuxInfo.bagState.AT_THE_PLANES_HOLD);
            log.bagagemPorao();
            return malas.remove(0);
        }
    }
}
