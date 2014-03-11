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
public class Bagageiro extends Thread{
    
private int state;
ZonaDesembarqueBagageiroInterface zona;

public Bagageiro(ZonaDesembarqueBagageiroInterface zona)
{
    this.zona = zona;
}

    public int getEstado() {
        return state;
    }

    public void setEstado(int state) {
        this.state = state;
    }

}
