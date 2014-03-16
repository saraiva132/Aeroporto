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
