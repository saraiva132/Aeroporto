/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import Estruturas.Mala;
import Interfaces.PoraoBagageiroInterface;
import java.util.ArrayList;


/**
 *
 * @author rafael
 */
public class Porao implements PoraoBagageiroInterface {

ArrayList<Mala> malas;
    
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
        System.out.println("Procurando mala..");
     if (malas.isEmpty())
     { return null;
     }
     else
     { return malas.remove(0);
     }
}

}
