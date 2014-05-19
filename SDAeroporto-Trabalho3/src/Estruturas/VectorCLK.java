/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas;

import static Estruturas.Globals.passMax;
import java.io.Serializable;

/**
 *
 * @author rafael
 */
public class VectorCLK implements Comparable,Serializable{

    private int[] vc;

    public VectorCLK() {
        vc = new int[passMax + 2];
        for(int i = 0;i<vc.length;i++){
            vc[i] = 0;
        }
    }

    public int[] getVc() {
        return vc;
    }
    
    public VectorCLK(int [] vc){
        this.vc = vc;
    }

    public synchronized void Add(int id) {
        vc[id]++;
    }

    public synchronized void CompareVector(int[] ts) {
        for (int i = 0; i < vc.length; i++) {
            if (ts[i] > vc[i]) {
                vc[i] = ts[i];
            }
        }
    }

    public synchronized int[] CloneVector() {
        int[] copia = new int[vc.length];
        for (int i = 0; i < vc.length; i++) {
            copia[i] = vc[i];
        }
        return copia;
    }

    @Override
    public int compareTo(Object t) {
        return 0;
    }
}
