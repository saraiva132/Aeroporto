/**
 * Lado do Servidor.
 * <p>
 * Contém as classes necessárias para o processamento no lado do servidor dos 
 * pedidos de operações feitos do lado do cliente. Estes pedidos são realizados 
 * baseando-se na passagem de mensagens sobre sockets usando o protocolo TCP.
 * <p>
 * Existe um servidor por cada um dos monitores da nossa solução ao problema <b>Rapsódia no Aeroporto</b>.
 * <p>
 * Por cada monitor temos uma classe <i>Server*Monitor_Name*Proxy</i> que faz <i>extends</i> 
 * a <i>ServeProxy</i> e define o thread agente prestador de serviço do monitor em questão. É responsável por ler o pedido do cliente
 * e delegar ao tipo de dados <i>Server*Monitor_Name*Interface</i> o processamento do mesmo. 
 * Por sua vez, o <i>Server*Monitor_Name*Interface</i> valida a mensagem que contém o
 * pedido do cliente e efectua a operação requerida na mesma sobre o monitor sobre o qual está encarregue.
 * Após realizar a operação devolve para a thread agente prestador de serviço a resposta adequada,
 * este que fica depois encarregue de enviar a resposta de volta ao cliente e termina a sua execução.
 * <p>
 * No desenvolvimento desta solução utilizámos o tipo de dados fornecido pelo professor <i>ServerCom</i>.
 */

package ServerSide;
