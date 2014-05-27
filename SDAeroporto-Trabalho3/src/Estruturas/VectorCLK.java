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
public class VectorCLK implements Comparable<VectorCLK>, Serializable {

    private int[] vc;

    public VectorCLK() {
        vc = new int[passMax + 2];
        for (int i = 0; i < vc.length; i++) {
            vc[i] = 0;
        }
    }

    public int[] getVc() {
        return vc;
    }

    public VectorCLK(int[] vc) {
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
    /*public int compareTo(VectorCLK ts) {
        
     int difM = 0;
     int difm = 0;
     for (int i = 0; i < vc.length; i++) {
     if (vc[i] > ts.getVc()[i]) {
     difM += vc[i] - ts.getVc()[i];
     }
            
     if(vc[i] < ts.getVc()[i]){
     difm += ts.getVc()[i] - vc[i];
     }
     }
        
     if(difM == 0 && difM < difm){
     return -1;
     }
     else if(difm == 0 && difM > difm){
     return 1;
     }
     else if(difm == difM){
     return 0;
     }
     else{
     return 0;
     }
     }*/

    public int compareTo(VectorCLK ts) {
        boolean older = false, conc = false, newer = false;

        for (int i = 0; i < vc.length; i++) {
            if (vc[i] > ts.getVc()[i]) {
                newer = true;
                for (int j = i; j < vc.length; j++) {
                    if (vc[j] < ts.getVc()[j]) {
                        conc = true;
                        break;
                    }
                }
            }
        }
            if (!conc && !newer) {
                older = true;
            }

            if (older) {
                return -1;
            } else if (conc) {
                return 0;
            } else {
                return 1;
            }
    } 
}
