package Interfaces;

/**
 *Identifica e descreve as operações que o motorista pode realizar sobre o 
 * monitor <b>TransferênciaTerminal</b>
 * 
 * @author rafael
 */
public interface TransferenciaMotoristaInterface {

    /**
     * Invocador - Motorista.
     * 
     * Motorista verifica se o trabalho já acabou. É acordado nas seguintes condições:
     * Se os passageiros na fila de espera, no passeio, cobrem a lotação do autocarro
     * Se a hora de partida chegou.
     * 
     * O trabalho dele acabou se à hora da partida não se encontrar ninguém no passeio!
     * @return True se acabou. False se não acabou ainda.
     */
    public boolean hasDaysWorkEnded();
    
   /**
     * Invocador - Motorista.
     * 
     * Motorista anuncia que a viagem vai começar, acorda um passageiro e adormece.
     * O objectivo deste método é chamar um passageiro de cada vez por ordem de chegada
     * na fila de espera. Entrada ordenada!
     * 
     * @return Número de passageiros que tomaram interesse em participar na viagem.
     * Limitado à lotação do Autocarro.
     */
    public int announcingBusBoardingShouting();
    
}
