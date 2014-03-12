/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;

import static Estruturas.AuxInfo.passMax;
import Interfaces.PoraoBagageiroInterface;
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author rafael
 */
public class Porao implements PoraoBagageiroInterface {

private int [] Malas;

private int nMalas;
    
public Porao(ArrayList<Integer> malas,int nMalas)
{
    this.nMalas = nMalas;
        Malas = new int[nMalas];
        int index = 0;
        for(int i=0;i<passMax;i++)
        {
            for(int j= 0; j<malas.get(passMax);j++)
            {
                Malas[index] = i;
                index++;
            }
        }
}

/**
 * Recolher uma mala do porao. Executada pelo bagageiro
 * @param bagID
 * @return 
 */
@Override
public synchronized int tryToCollectABag()
{
    if (nMalas == 0)
        return 0;
    else
    {
        nMalas--;
        return Malas[nMalas];
    }
}

}
