/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;
import Estruturas.AuxInfo.destination;
import static Estruturas.AuxInfo.passMax;
import Interfaces.ZonaDesembarqueBagageiroInterface;
import Interfaces.ZonaDesembarquePassageiroInterface;

/**
 *
 * @author rafael
 */
public  class ZonaDesembarque implements ZonaDesembarquePassageiroInterface,ZonaDesembarqueBagageiroInterface{

/**
 * Número de passageiros que faltam passar pela zona de desembarque.
 */    
private int nPass;
/**
 * O bagageiro ja pode começar a recolher malas
 */
private boolean canGo;

public ZonaDesembarque()
{
   nPass = passMax;
   canGo = false;
}


/**
 * O bagageiro descansa enquanto um avião chega.
 */
@Override
public synchronized void  takeARest()
{
    try {
    while(!canGo)
            wait();
        } catch (InterruptedException ex) {
        }
    canGo = false;
}

/**
 * O passageiro decide o que vai fazer.(Acontece imediatamente depois de desembarcar)
 * @param dest
 * @param nMalas
 * @return 
 */
@Override
public synchronized destination whatShouldIDo(boolean dest,int nMalas)
{
    nPass--;
    if(nPass == 0)
    {
        //reset da variavel nPass
        canGo = true;
        notify();
    }
    if(dest && nMalas == 0)
    {
        return destination.WITHOUT_BAGGAGE;
    }
    else if(dest)
        return destination.WITH_BAGGAGE;
    else
        return destination.IN_TRANSIT;
    
}

/**
 * O bagageiro já não tem malas para pegar.
 */
@Override
public synchronized void noMoreBagsToCollect()
{
}

}
