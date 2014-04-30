/**
 * Contém a definição das mensagens necessárias para a comunicação entre as diferentes 
 * entidades definidas na solução distribuída ao problema <b>Rapsódia no Aeroporto</b>.
 * <p>
 * É aqui definido o tipo de mensagem </i>Request</i>, necessário quando um cliente 
 * pretende realizar uma operação num monitor que se encontra a correr num Sistema 
 * Computacional diferente do seu. Para efeitos de controle de erros, é também aqui 
 * criado um tipo de dados <i>MessageResquestException</i> que define a excepção lançada 
 * aquando da detecção de uma mensagem <i>Request</i> inválida.
 * <p>
 * Para a resposta dos monitores aos pedidos dos clientes é utilizado o tipo de dados <i>Response</i>.
 * Analogamente ao que acontece com o tipo de dados <i>Request</i> é também aqui criado o tipo de dados
 * <i>MessageRequestException</i> para definir a excepção lançada aquando da detecção de uma mensagem <i>Response</i> inválida.
 * 
 */

package Message;
