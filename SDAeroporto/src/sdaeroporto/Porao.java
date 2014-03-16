package sdaeroporto;

import Estruturas.Mala;
import Interfaces.PoraoBagageiroInterface;
import java.util.ArrayList;


/**
 * 
 * Monitor que simula a interacção do passageiro com o porão de um avião 
 *
 * @author rafael
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
public Porao(ArrayList<Mala> malas)
{
    this.malas = malas;
}

    /**
     * Invocador: bagageiro
     * 
     * Tentar recolher uma mala
     * 
     * O bagageiro desloca-se ao porão do avião e caso este não se encontre vazio
     * recolhe uma mala
     * 
     * @return a mala que apanhou no porão, ou em caso do porão se encontrar 
     * vazio, null
     */
    @Override
    public synchronized Mala tryToCollectABag()
    {
        //System.out.println("Procurando mala..");
        if (malas.isEmpty())
        { return null;
        }
        else
        { return malas.remove(0);
        }
    }

}
