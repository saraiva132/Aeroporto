/**
 * Contém os tipos de dados instanciados correspondentes às entidades activas do problema
 * <b>Rapsódia no Aeroporto</b>.
 * 
 * <p>Neste package são definidas e implementadas as classes correspondentes ao:
 * <ul>
 * <li>Passageiro
 * <li>Bagageiro
 * <li>Motorista
 * </ul>
 * 
 * <p>O ciclo de vida de um passageiro pode ser determinado fundamentalmente em 
 * função do facto deste aeroporto corresponder ao seu destino final ou se 
 * encontrar  em trânsito. Caso se verifique o primeiro caso, a alto nível, o que ele n
 * ecessita de fazer é ir buscar as suas malas (no caso de as ter) e ir para casa; caso se
 * verifique o segundo deverá apanhar o autocarro para se dirigir ao terminal de 
 * partida do seu próximo voo.
 * 
 * <p>O ciclo de vida do bagageiro é facilmente descrito por esperar que um avião 
 * chegue ao aeroporto e após a aterragem do mesmo recolher todas as malas que se
 * encontram no seu porão e transportá-las para uma passadeira de recolha de bagagens
 * ou, alternativamente, para uma zona de armazenamento temporário dependendo do 
 * seu owner.
 * 
 * <p> O ciclo de vida do motorista corresponde a esperar que passageiros cheguem 
 * à zona de transferência do terminal de chegada e posteriormente levá-los até ao
 * terminal de partida.
 * 
 */

package Threads;
