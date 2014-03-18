package Interfaces;

import sdaeroporto.Logging;

/**
 * Identifica e descreve as operações que um passageiro pode realizar sobre o monitor 
 * <b>Autocarro</b>
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface AutocarroPassageiroInterface {
    
    /**
     * Entrar no autocarro
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro entra no autocarro de forma ordenada e senta-se no assento a 
     * que corresponde o seu ticket.
     * O último passageiro a entrar anuncia ao motorista que já se sentou e 
     * espera que o motorista o leve até à zona de transferência do terminal de 
     * partida.
     * 
     * @param ticketID lugar onde o passageiro se pode sentar
     */

    public void enterTheBus(LoggingPassageiroInterface log,int ticketID,int passID);
    
    /**
     * Sair do autocarro
     * <p>
     * Invocador: Passageiro
     * <p>
     * O passageiro sai do autocarro e caso seja o último a sair notifica o 
     * motorista de que já não há mais ninguém no autocarro.
     * 
     * @param log
     * @param ticketID lugar onde o passageiro estava sentado 
     */
    
    public void leaveTheBus(LoggingPassageiroInterface log,int ticketID);

            
}