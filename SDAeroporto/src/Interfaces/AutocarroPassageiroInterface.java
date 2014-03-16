package Interfaces;

/**
 * Identifica e descreve as operações que um passageiro pode realizar sobre o monitor 
 * <b>Autocarro</b>
 *
 * @author rafael
 */
public interface AutocarroPassageiroInterface {
    
    /**
     * Invocador: Passageiro
     * Entrar no autocarro
     * 
     * O passageiro entra no autocarro de forma ordenada e senta-se no assento a 
     * que corresponde o seu ticket.
     * 
     * Anuncia ao motorista que já se sentou e espera que o motorista o leve até
     * à zona de transferência do terminal de partida.
     * 
     * @param ticketID lugar onde o passageiro se pode sentar
     */

    public void enterTheBus(int ticketID);
    
    /**
     * Invocador: Passageiro
     * Sair do autocarro
     * 
     * O passageiro sai do autocarro e caso seja o último a sair notifica o 
     * motorista de que já não há mais ninguém no autocarro.
     * 
     * @param ticketID lugar onde o passageiro estava sentado 
     */
    
    public void leaveTheBus(int ticketID);
            
}