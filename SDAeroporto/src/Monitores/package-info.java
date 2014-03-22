/**
 * Contém a implementação dos monitores necessários à simulação das diferentes 
 * zonas de interacção entre as três entidades activas definidas no âmbito do problema
 * <b>Rapsódia no Aeroporto</b>: Passageiro, Bagageiro e Motorista.
 * 
 * <p>A solução proposta baseou-se na definição de 6 zonas de interacção para a 
 * modulação do problema:
 * <ul>
 * <li>Zona de Desembarque
 * <li>Porão do avião
 * <li>Zona de recolha e armazenamento temporário de bagagens
 * <li>Zona de transferência de terminais
 * <li>Autocarro
 * <li>Saída do Aeroporto & Entrada no terminal de partida
 * </ul>
 * 
 * <p>A <i>zona de desermbarque</i> é representada pelo monitor <b>ZonaDesembarque</b>.
 * Este monitor simula a interacção entre o bagageiro e os passageiros à saída do 
 * avião: o primeiro encontra-se à espera que o avião aterre e quando os passageiros 
 * saem do avião pensam sobre para onde deverão ir; o último a sair do avião deve 
 * avisar o bagageiro de que já pode ir recolher as suas bagagens.
 * 
 * <p>O <i>porão do avião</i> é representado pelo monitor <b>Porao</b>. Este monitor 
 * simula a acção do bagageiro ir buscar as malas dos passageiros que acabaram de aterrar.
 * 
 * <p>A <i>zona de recolha e armazenamento de bagagens</i> é representada pelo monitor 
 * <b>RecolhaBagagem</b>. Este monitor simula a interacção entre o bagageiro e os passageiros
 * na zona de recolha de bagagens. Além desta interacção, é também neste monitor onde o bageiro 
 * armazena as malas dos passageiros que estão em trânsito e o passageiro que não tenha conseguido
 * apanhar todas as suas malas reclama a falta da(s) mesma(s). 
 * 
 * <p>A <i>zona de transferência entre termiais</i> é representada pelo monitor 
 * <b>TransferenciaTerminal</b>. Este monitor simula a interacção entre o motorista
 * e os passageiros que estão em trânsito e que vão formando fila à medida que chegam
 * ao passeio.
 * 
 * <p>
 * 
 * <p>O <i>autocarro</i> é representado pelo monitor <b>Autocarro</b>. Este monitor 
 * simula a interacção entre o motorista e os passageiros que estão em trânsito. Esta
 * interacção decorre quando os passageiros entram no autocarro, o motorista os 
 * leva até ao terminal de partida e quando eles saem do autocarro.
 */

package Monitores;
