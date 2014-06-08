/**
 * Contém as estruturas necessárias para a instanciação, registo e remoção de objectos 
 * remotos localizados no mesmo <i>local registry service</i>.
 * <p>
 * É aqui declarado e definido o tipo de dados encarregue por inicializar e registar 
 * um objecto remoto que permite o registo de outros objectos remotos, correspondentes 
 * aos monitores da nossa solução ao problema <b>Rapsódia no Aeroporto</b>.
 * 
 * Os ficheiros identificados por <b><i>MonitorName</i>Register</b> correspondem aos tipos de dados 
 * encarregues por inicializarem, registarem (e no final da simulação removerem) 
 * os objectos remotos correspondentes aos monitores identificados por <i>MonitorName</i>.
 * 
 * <p>
 * A comunicação é baseada em Java RMI.
 */
package Registry;
