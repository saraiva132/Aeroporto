/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Estruturas;

import Estruturas.Mala;

/**
 *
 * @author rafael
 */
public abstract class Bagagem {
    
/**
 * CAM de malas
 */    
private Mala [] malas;

/**
 * Numero de malas existentes.
 */
private int nMalas; 

public Bagagem(int nMalas)
{
    this.nMalas = nMalas;
}

}
