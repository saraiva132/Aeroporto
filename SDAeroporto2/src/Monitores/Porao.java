package Monitores;

import Estruturas.AuxInfo;
import Estruturas.Mala;
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
    
    Logging log;
    /**
     * Instanciação e inicialização do monitor <b>Porao</b>
     *
     * @param malas malas que se encontram no porão
     */
    public Porao(ArrayList<Mala> malas,Logging log) {
        this.log = log;
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
     * 
     * @return Mala que apanhou no porão, ou em caso do porão se encontrar
     * vazio, null
     */
    @Override
    public synchronized Mala tryToCollectABag() {
        //System.out.println("Procurando mala..");
         log.reportState(AuxInfo.bagState.AT_THE_PLANES_HOLD);
        if (malas.isEmpty()) {
            return null;
        } else {
            log.bagagemPorao();
            return malas.remove(0);
        }
    }

}
