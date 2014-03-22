/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Estruturas.AuxInfo;

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
    public void reportState(int passID, AuxInfo.passState state);
    
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
     * @param nMalas número de malas que passageiro tem em sua posse
     */
    public void malasActual(int passID, int nMalas);

    /**
     * Reportar número de malas total.
     * 
     * <p>Invocador: Passageiro
     * 
     * <p>Passageiro reporta quantas malas tem no total.
     * 
     * @param passID identificador do passageiro
     * @param nMalas número de malas totais que petencem ao passageiro
     */
    public void malasInicial(int passID, int nMalas);

    
    /**
     * Reportar tipo de passageiro.
     * 
     * <p>Invocador: Passageiro
     * 
     * <p>Passageiro reporta se está em trânsito  ou se este aeroporto corresponde ao seu destino.
     * 
     * @param passID identificador do passageiro
     * @param destino 
     * <ul>
     * <li>FALSE caso esteja em trânsito
     * <li>TRUE caso contrário
     * </ul>
     */
    public void destino(int passID, Boolean destino);

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
     * Invocador: Passageiro
     * 
     * <p>Passageiro, após sair do autocarro no terminal de partida, reporta o estado
     * do mesmo.
     * @param seats assentos do autocarro
     */
    public void autocarroState(int[] seats);
}
