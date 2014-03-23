package Interfaces;

import Estruturas.AuxInfo;

/**
 * Identifica e descreve as operações que o bagageiro pode realizar sobre o monitor <b>Logging</b>
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface LoggingBagageiroInterface {

    /**
     * Reportar mudança de estado.
     * 
     * <p>Invocador: Bagageiro
     * 
     * <p>Bagageiro reporta mudança do seu estado
     * 
     * @param state novo estado do bagageiro
     */
    public void reportState(AuxInfo.bagState state);

    /**
     * Reportar retirada de uma mala do porão.
     * 
     * <p>Invocador: Bagageiro
     * 
     * <p>Bagageiro reporta que retirou uma mala do porão do avião.
     */
    public void bagagemPorao();

    /**
     * Reportar depósito de bagagem.
     * 
     * <p>Invocador: Bagageiro
     * 
     * <p> Bagageiro reporta que colocou uma bagagem na passadeira de recolha de bagagens.
     * 
     * @param take False, representa que colocou a mala com sucesso. 
     */
    public void bagagemBelt(boolean take);

    /**
     * Reportar depósito de bagagem.
     * 
     * <p>Invocador: Bagageiro
     * 
     * <p> Bagageiro reporta que colocou uma bagagem na zona de armazenamento temporário de bagagens.
     */
    public void bagagemStore();

}
