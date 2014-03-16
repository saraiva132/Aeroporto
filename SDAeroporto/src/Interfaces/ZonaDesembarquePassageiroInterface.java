package Interfaces;

import Estruturas.AuxInfo.destination;

/**
 * Identifica as operações que um passageiro pode realizar no monitor 
 * <b>ZonaDesembarque</b>
 * @author rafael
 */
public interface ZonaDesembarquePassageiroInterface {
    
    /**
     * Invocador: Passageiro
     * 
     * o que devo fazer
     * 
     * O passageiro após sair do avião reflecte sobre para onde deve ir. O último 
     * passageiro deve notificar o bagageiro de que já pode começar a ir buscar 
     * as malas ao porão do avião
     * 
     * @param dest TRUE se este aeroporto é o seu destino, FALSE caso contrário
     * @param nMalas número de malas que o passageiro contém
     * @return  qual o seu próximo passo dependendo da sua condição:
     * <li> WITH_BAGAGE caso este seja o seu destino e possua bagagens
     * <li> WTHOUT_BAGAGE caso este seja o seu destino e não possua bagagens
     * <li> IN_TRANSIT caso esteja em trânsito
     */
    public destination whatShouldIDo(boolean dest,int nMalas);
}
