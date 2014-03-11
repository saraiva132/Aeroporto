/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Estruturas;

import Interface.*;

/**
 *
 * @author rafael
 */
public class Passageiro extends Thread{

    
private int state;
private int id;
private int nVoo;
private int nMalasTotal;
private int nMalasEmPosse;
private boolean finalDest;
private ZonaDesembarquePassageiroInterface desembarque;

public Passageiro(int nMalasTotal,int id,int nVoo,boolean finalDest,ZonaDesembarquePassageiroInterface desembarque)
{
    this.nMalasTotal = nMalasTotal;
    nMalasEmPosse = 0;
    this.desembarque = desembarque;
    this.finalDest = finalDest;
    this.nVoo = nVoo;
    this.id = id;
    state = Estados.WAITINGPLANE;
}


    public int getEstado() {
        return state;
    }

    public void setEstado(int state) {
        this.state = state;
    }
    
    @Override
    public void run()
    {
        int nextState = desembarque.whatShouldIDo(id, nVoo,finalDest,nMalasTotal);
        switch(nextState)
        {
            case 0:
                    break;
            case 1:
                    break;
            case 2:    
                    break;
        }
    }
}

