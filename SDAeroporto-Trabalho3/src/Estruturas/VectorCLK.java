package Estruturas;

import static Estruturas.Globals.passMax;
import java.io.Serializable;

/**
 * Identifica o tipo de dados 
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class VectorCLK implements Comparable<VectorCLK>, Serializable {

    
    private int[] vector;

    public VectorCLK() {
        vector = new int[passMax + 2];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = 0;
        }
    }
    
    public VectorCLK(VectorCLK vc) {
        vector = new int[passMax + 2];
        this.vector = vc.CloneVector();
    }

    public int[] getVc() {
        return vector;
    }
    
    public synchronized void Add(int id) {
        vector[id]++;
    }

    public synchronized void CompareVector(int[] ts) {
        for (int i = 0; i < vector.length; i++) {
            if (ts[i] > vector[i]) {
                vector[i] = ts[i];
            }
        }
    }

    public synchronized int[] CloneVector() {
        int[] copia = new int[vector.length];
        for (int i = 0; i < vector.length; i++) {
            copia[i] = vector[i];
        }
        return copia;
    }

    @Override
    public int compareTo(VectorCLK ts) {
        boolean older = false, conc = false, newer = false;

        for (int i = 0; i < vector.length; i++) {
            if (vector[i] > ts.getVc()[i]) {
                newer = true;
                for (int j = i; j < vector.length; j++) {
                    if (vector[j] < ts.getVc()[j]) {
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
