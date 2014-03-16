package Interfaces;

/**
 *Identifica e descreve as operações que o motorista pode realizar sobre o 
 * monitor <b>TransferênciaTerminal</b>
 * 
 * @author rafael
 */
public interface TransferenciaMotoristaInterface {

     /**
      * Invocador: Motorista
      * 
      * O dia de trabalho já acabou
      * 
      * O motorista verifica se verifica se o seu dia de trabalho já acabou.
      * 
      * @return TRUE, caso tenho acabado
      *         FALSE, caso contrário
      */
    public boolean hasDaysWorkEnded();
    
    /**
     * Invocador: Motorista
     * 
     * Existem passageiros para uma viagem começar.
     * 
     * @return Número de passageiros que tomaram interesse em participar na viagem.
     * Limitado à lotação do Autocarro.
     */
    public int announcingBusBoardingShouting();
    
}
