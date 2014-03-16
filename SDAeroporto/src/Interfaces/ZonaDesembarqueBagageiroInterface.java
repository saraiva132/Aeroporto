package Interfaces;

/**
 * Identifica e descreve as operações que o bagageiro pode realizar sobre o 
 * monitor <b>ZonaDesembarque</b>
 *
 * @author rafael
 */
public interface ZonaDesembarqueBagageiroInterface {

    /**
     * Invocador: Bagageiro
     * 
     * Descansar
     * 
     * O bagageiro descansa enquanto o próximo voo não chega e o último 
     * passageiro não sai do avião
     */
    public void takeARest();
    
    /**
     * Invocador: Bagageiro
     * 
     * Não existem mais malas para apanhar
     * 
     * O bagageiro, após verificar que o porão já se encontra vazio, dirige-se à
     * sua sala de espera
     */
    public void noMoreBagsToCollect();
}
