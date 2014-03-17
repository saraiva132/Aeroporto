package sdaeroporto;

import Estruturas.AuxInfo.*;
import static Estruturas.AuxInfo.passMax;

/**
 * Monitor correspondente ao Repositório Geral de Informação. 
 * Necessário apenas para efeitos de logging
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Logging {
    
    /**
     * Array com os estados de todos os passageiros
     * 
     * @serialField pstate
     */
    private passState[] pstate;
    
    /**
     * Estado do bagageiro
     * 
     * @serialField bstate
     */
    private bagState bstate;
    
    /**
     * Estado do motorista
     * 
     * @serialField mstate
     */
    private motState mstate;

    
    /**
     * Instanciação e inicialização do monitor <b>Logging</b>
     */
    public Logging() {
        pstate = new passState[passMax];
        init();
        bstate = bagState.WAITING_FOR_A_PLANE_TO_LAND;
        mstate = motState.PARKING_AT_THE_ARRIVAL_TERMINAL;
        for (int i = 0; i < passMax; i++) {
            pstate[i] = passState.AT_THE_DISEMBARKING_ZONE;
        }
    }
    
    /**
     * Função auxiliar utilizada para inicializar o logging
     */
    private void init() {
        System.out.println("A começar logging...");
        System.out.println("                Barbeiro                Motorista                  Passageiro1              Passageiro2              Passageiro3              Passageiro4              Passageiro5");
    }

    private synchronized void reportStatus() {
        
        System.out.printf("%25s %25s ",bstate,mstate);
        for(int i= 0;i<passMax; i++)
            System.out.printf("%25s ",pstate[i]);
        System.out.println();
    }
    
    /**
     * Invocador: Passageiro
     * 
     * O passageiro altera o seu estado
     * 
     * @param passID identificador do passageiro
     * @param state novo estado do passageiro
     */
    public synchronized void reportState(int passID, passState state) {
        pstate[passID] = state;
        reportStatus();
    }
    
    /**
     * Invocador: Motorista
     * 
     * O motorista altera o seu estado
     *
     * @param state novo estado do motorista
     */
    public void reportState(motState state) {
        mstate = state;
        reportStatus();
    }

    /**
     * Invocador: Bagageiro
     * 
     * O bagageiro altera o seu estado
     * 
     * @param state novo estado do bagageiro
     */
    public void reportState(bagState state) {
        bstate = state;
        reportStatus();
    }
}
