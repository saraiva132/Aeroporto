package Estruturas;

import static Estruturas.Globals.passMax;
import java.io.Serializable;

/**
 * Identifica o tipo de dados - Classe que representa um relógio vectorial.
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class VectorCLK implements Comparable<VectorCLK>, Serializable {

    /**
     * Mantém os valores dos relógios.
     */
    private int[] vector;
    
    /**
     * Construtor que inicializa os relógios a zero.
     */
    public VectorCLK() {
        vector = new int[passMax + 2];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = 0;
        }
    }
    
    /**
     * Construtor que recebe um relogio vectorial como argumento.
     * @param vc - Vector clock que serve como base para a construção da classe.
     */
    public VectorCLK(VectorCLK vc) {
        vector = new int[passMax + 2];
        this.vector = vc.CloneVector();
    }
    
    
    /**
     * Retorna o relógio vectorial
     * @return - Relogio vectorial
     */
    public int[] getVc() {
        return vector;
    }
    
    /**
     * Incrementa um relógio.
     * @param id - Id do relógio a incrementar.
     */
    public synchronized void Add(int id) {
        vector[id]++;
    }
    
    /**
     * Compara dois relógios vectoriais e actualiza o relógio local caso seja necessário.
     * @param ts - Relógio vectorial para comparar.
     */
    public synchronized void CompareVector(int[] ts) {
        for (int i = 0; i < vector.length; i++) {
            if (ts[i] > vector[i]) {
                vector[i] = ts[i];
            }
        }
    }
    /**
     * Copia o relógio vectorial e retorna-o.
     * @return - Cópia do relógio vectorial.
     */
    public synchronized int[] CloneVector() {
        int[] copia = new int[vector.length];
        for (int i = 0; i < vector.length; i++) {
            copia[i] = vector[i];
        }
        return copia;
    }
    /**
     * Método que efectua override, a partir da interface comparable, do compareTo.
     * Compara o relógio vectorial local com o recebido por argumento.
     * @param ts - Relógio com o qual se vai comparar.
     * @return 
     * <ul>
     * <li> -1, se o relógio local estiver desactualizado em relação ao comparado.
     * <li>  0, caso não se consiga chegar a uma conclusão ou caso os relógios sejam iguais.
     * <li>  1, se o relógio local estiver mais actualizado que o recebido por argumento.
     * </ul>
     */
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
