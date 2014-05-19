/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas;

import java.io.Serializable;

/**
 *
 * @author rafael
 */
public class Reply implements Serializable{

    private final VectorCLK ts;
    private final Object retorno;

    public Reply(VectorCLK ts, Object retorno) {
        this.ts = ts;
        this.retorno = retorno;
    }

    public VectorCLK getTs() {
        return ts;
    }

    public Object getRetorno() {
        return retorno;
    }
}
