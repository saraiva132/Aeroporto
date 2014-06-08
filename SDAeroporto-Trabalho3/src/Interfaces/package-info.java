/**
 * Contém as interfaces necessárias para a implementação da solução concorrente 
 * do problema  <b>Rapsódia no Aeroporto</b>.
 * 
 * <p>As interfaces aqui armazenadas definem as operações que cada uma das entidades 
 * activas (passageiro, bagageiro e motorista) pode realizar sobre cada monitor 
 * definido no package <b>Monitores</b>.
 * 
 * <p>Foi definido que o ciclo de vida do bagageiro decorre sob os monitores
 * <b>Logging</b>, <b>Porao</b>, <b>RecolhaBagagem</b> e <b>ZonaDesembarque</b>.
 * Posto isto definimos as operações que ele pode fazer sob cada um destes monitores
 * <b>LoggingBagageiroInterface</b>, <b>PoraoBagageiroInterface</b>, <b>RecolhaBagageiroInterface</b> 
 * e <b>ZonaDesembarqueBagageiroInterface</b>, respectivamente.
 * 
 * <p>De igual forma, as operações que o passageiro pode realizar sob todos os monitores
 * em que o seu ciclo de vida decorre encontram-se definidas em <b>AutocarroPassageiroInterface</b>, 
 * <b>LoggingPassageiroInterface</b>, <b>RecolhaBagagemPassageiroInterface</b>, 
 * <b>TransferenciaPassageiroInterface</b>,<b>TransicaoPassageiroInterface</b e 
 * <b>ZonaDesembarquePassageiroInterface</b>. Por seu lado, o motorista tem as suas 
 * operações definidas nas interfaces <b>AutocarroMotoristaInterface</b>, 
 * <b>LoggingMotoristaInterface</b> e <b>TransferenciaMotoristaInterface</b>.
 * 
 * <p>
 * Além destas interfaces, para terminar os monitores é neessário que todas as 3 
 * entidades activas tenham uma operação definida para terminar cada um dos monitores: <i>shutdownMonitor</i>
 * 
 * <p>O objectivo da definição destas interfaces é estabelecer como cada uma destas 
 * três entidades deve operar sobre cada monitor e assegurar que apenas possam realizar 
 * as operações que lhes competem.
 * 
 */

package Interfaces;
