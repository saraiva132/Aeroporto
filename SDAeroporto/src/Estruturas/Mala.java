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
public class Mala {

    private int owner;
    private boolean transit;

    public Mala(int owner, boolean transit) {
        this.owner = owner;
        this.transit = transit;
    }

    public int getOwner() {
        return owner;
    }

    public boolean inTransit() {
        return transit;
    }

}
