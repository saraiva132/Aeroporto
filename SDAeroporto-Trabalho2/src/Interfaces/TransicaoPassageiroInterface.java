package Interfaces;

/**
 * Identifica e descreve as operações que um passageiro pode realizar sobre o 
 * monitor <b>TransiçãoAeroporto</b>
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface TransicaoPassageiroInterface {

    /**
     * Ir para casa
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro, cujo destino final é este aeroporto, abandona o aeroporto e
     * vai para casa. Espera até ao último passageiro do seu voo chegar à saída 
     * do terminal de chegada ou ao terminal de partida, que tem a responsabilidade
     * de notificar os outros passageiros que podem ir embora e que o bagageiro tenha recolhido todas as malas do porão.
     * @param passageiroId identificador do passageiro
     */     
    public void goHome(int passageiroId);

    /**
     * Entrar no terminal de partida
     * <p>
     * Invocador: Passageiro
     * <p> 
     * O passageiro, que se encontra em trânsito,prepara o próximo voo.Espera 
     * até ao último passageiro do seu voo chegar à saída do terminal de chegada 
     * ou ao terminal de partida, que tem a responsabilidade de notificar os 
     * outros passageiros que podem ir embora e que o bagageiro tenha recolhido todas as malas do porão.
     * @param passageiroId identificador do passageiro
     */
    public void prepareNextLeg(int passageiroId);

}
