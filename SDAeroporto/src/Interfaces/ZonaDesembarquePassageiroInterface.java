/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import Estruturas.AuxInfo.destination;

/**
 * Identifica as operações que um passageiro pode realizar no monitor 
 * <b>ZonaDesembarque</b>
 * @author rafael
 */
public interface ZonaDesembarquePassageiroInterface {

    public destination whatShouldIDo(boolean dest,int nMalas);
}
