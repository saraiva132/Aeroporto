/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import Estruturas.Bagagem;
import Estruturas.Mala;

/**
 *
 * @author rafael
 */
public class Porao extends Bagagem {

    
public Porao(int nMalas)
{
    super(nMalas);
}

/**
 * Recolher uma mala do porao. Executada pelo bagageiro
 * @param bagID
 * @return 
 */
public synchronized boolean tryToCollectABag(int bagID)
{
    return true;
}

}
