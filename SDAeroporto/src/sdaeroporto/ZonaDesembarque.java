/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdaeroporto;
import Estruturas.Estados;
import Interface.*;

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
/**
 * Número de voo
 */
private int nVoo;

public ZonaDesembarque(int nPass, int nVoo)
{
   this.nPass = nPass;
   this.nVoo = nVoo;
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
 * @param passageiroID
 * @param nVoo
 * @param dest
 * @param nMalas
 * @return 
 */
@Override
public synchronized int whatShouldIDo(int passageiroID,int nVoo,boolean dest,int nMalas)
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
        return 0;
    }
    else if(dest)
        return 1;
    else
        return 2;
    
}

/**
 * O bagageiro já não tem malas para pegar.
 */
@Override
public synchronized void noMoreBagsToCollect()
{
    RepositorioGeral.setBagageiroState(Estados.WAITINGPLANE);
}
}
