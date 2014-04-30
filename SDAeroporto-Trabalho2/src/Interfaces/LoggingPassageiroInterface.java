package Interfaces;

import Estruturas.Globals;

/**
 * Identifica e descreve as operações que o passageiro pode realizar sobre o monitor <b>Logging</b>
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface LoggingPassageiroInterface {

    /**
     * Reportar mudança de estado.
     * 
     * <p>Invocador: Passageiro
     * 
     * <p>O passageiro reporta a mudança do seu estado.
     * 
     * @param passID identificador do passageiro
     * @param state novo estado do passageiro
     */
    public void reportState(int passID, Globals.passState state);
    
    /**
     * Reportar retiro de bagagem.
     * 
     * <p>Invocador: Passageiro
     * 
     * <p> Passageiro reporta que tirou uma bagagem sua da passadeira de recolha de bagagens.
     * @param take True, representa que retirou a mala com sucesso.
     */
    public void bagagemBelt(boolean take);

    /**
     * Reportar número de malas em posse.
     * 
     * <p>Invocador: Passageiro
     * 
     * <p>Passageiro reporta quantas malas tem em sua posse.
     * 
     * @param passID identificador do passageiro
     */
    public void malasActual(int passID);

    /**
     * Reportar entrada para a fila de espera.
     * 
     * <p>Invocador: Passageiro
     * 
     * <p>Passageiro reporta que se encontra na fila na zona de transferência 
     * entre terminais à espera de entrar para o autocarro.
     * 
     * @param id identificador do passageiro
     */
    public void addfilaEspera(int id);
    
    /**
     * Reportar saída da fila de espera.
     * 
     * <p>Invocador: Passageiro
     * 
     * <p>Passageiro reporta que saiu da fila de espera para entrar no autocarro.
     */
    public void removefilaEspera();

    /**
     * Reportar o estado dos assentos do autocarro.
     * 
     * <p>Invocador: Passageiro
     * 
     * <p>Passageiro, após sair do autocarro no terminal de partida, reporta o estado
     * do mesmo.
     * @param seats assentos do autocarro
     */
    public void autocarroState(int[] seats);
    
    /**
     *  Reportar falta de malas
     * 
     * <p>Invocador: Passageiro
     * 
     * <p> Passageiro antes de sair do aeroporto reporta que perdeu malas
     * @param malasPerdidas número de malas perdidas
     */
    public void missingBags(int malasPerdidas);
}
