/**
 * Lado do cliente.
 * 
 * <p>Contém as classes necessárias para o estabelecimento da comunicação entre as 
 * threads cliente e os monitores (servidores), baseando-se na passagem de mensagens 
 * sobre sockets usando o protocolo TCP.
 * 
 * <p>Os clientes da nossa solução correspondem às três entidades activas: 
 * <ul>
 * <li><b>InterfacePassageiro</b>
 * <li><b>InterfaceBagageiro</b>
 * <li><b>InterfaceMotorista</b>,
 * </ul>
 * que necessitam de comunicar com os monitores para a simulação do problema 
 * <b>Rapsódia no Aeroporto</b>.
 * 
 * <p>Além destes, ainda necessitamos de mais dois tipos de cliente:
 * <ul>
 * <li><b>InterfaceMain</b>, que é responsável por estabelecer a comunicação entre as 
 * <i>main</i>'s (responsáveis por lançarem/terminarem os threads passageiros/motorista/bagageiro 
 * bem como gerar a aleatoriedade de cada simulação) e os monitores <i>Logging</i>, 
 * <i>TransferenciaTerminal</i>, <i>RecolhaBagagem</i> e <i>Porao</i> com o objectivo 
 * de actualizar o estado de cada monitor no âmbito de cada iteração da simulação. 
 * Além destas operações necessitam também de terminar todos os monitores;
 * <li><b>InterfaceMonitoresLogging</b>, responsável por por estabelecer a comunicação 
 * com o monitor <i>Logging</i> a partir de todos os outros  monitores com o objectivo 
 * de actualizar o estado geral do problema no âmbito das operações que vão sendo realizadas em cada monitor.
 * </ul>
 */

package ClientSide;
