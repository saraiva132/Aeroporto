/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Estruturas;

/**
 *
 * @author rafael
 */
public class Motorista extends Thread{
    
private int state;

public Motorista()
{
    state = Estados.PARKINGARRIVAL;
}

   public int getEstado() {
        return state;
    }

    public void setEstado(int state) {
        this.state = state;
    }

}
