package Interfaces;

/**
 * Identifica e descreve as operações que um passageiro pode realizar sobre o 
 * monitor <b>TransiçãoAeroporto</b>
 * 
 * @author rafael
 */
public interface TransicaoPassageiroInterface {

    /**
     * Invocador: Passageiro
     * 
     * Ir para casa
     * 
     * O passageiro, cujo destino final é este aeroporto, abandona o aeroporto e
     * vai para casa. Espera até ao último passageiro do seu voo chegar à saída 
     * do terminal de chegada ou ao terminal de partida, que tem a responsabilidade
     * de notificar os outros passageiros que podem ir embora.
     */    
    public void goHome();

    /**
     * Invocador: Passageiro
     * 
     * Entrar no terminal de partida
     * 
     * O passageiro, que se encontra em trânsito,prepara o próximo voo.Espera 
     * até ao último passageiro do seu voo chegar à saída do terminal de chegada 
     * ou ao terminal de partida, que tem a responsabilidade de notificar os 
     * outros passageiros que podem ir embora.
     * 
     */
    public void prepareNextLeg();

}
