package Interfaces;

import Estruturas.Globals;

/**
 * Identifica e descreve as operações que o motorista pode realizar sobre o monitor <b>Logging</b>
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public interface LoggingMotoristaInterface {

    /**
     * Reportar mudança de estado.
     * <p>
     * Invocador: Motorista
     * 
     * Motorista reporta mudança do seu estado.
     * 
     * @param state novo estado do motorista
     */
    public void reportState(Globals.motState state);
}
