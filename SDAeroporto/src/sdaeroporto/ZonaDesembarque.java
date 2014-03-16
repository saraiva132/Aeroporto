package sdaeroporto;
import Estruturas.AuxInfo.destination;
import static Estruturas.AuxInfo.passMax;
import Interfaces.ZonaDesembarqueBagageiroInterface;
import Interfaces.ZonaDesembarquePassageiroInterface;

/**
 *
 * @author rafael
 */
public  class ZonaDesembarque implements ZonaDesembarquePassageiroInterface,ZonaDesembarqueBagageiroInterface{

/**
 * Número de passageiros que faltam passar pela zona de desembarque.
 * 
 * @serialField nPass
 */    
private int nPass;
/**
 * O bagageiro ja pode começar a recolher malas
 * 
 * @serialField canGo
 */
private boolean canGo;

/**
 * Instanciação e inicialização do monitor <b>ZonaDesembarque</b>
 */

public ZonaDesembarque()
{
   nPass = passMax;
   canGo = false;
}


    /**
     * Invocador: Bagageiro
     * 
     * Descansar
     * 
     * O bagageiro descansa enquanto o próximo voo não chega e o último 
     * passageiro não sai do avião
     */
@Override
public synchronized void  takeARest()
{
    System.out.println("Taking a Rest guys...");
    try {
    while(!canGo)
            wait();
        } catch (InterruptedException ex) {
        }
    canGo = false;
}

/**
     * Invocador: Passageiro
     * 
     * o que devo fazer
     * 
     * O passageiro após sair do avião reflecte sobre para onde deve ir. O último 
     * passageiro deve notificar o bagageiro de que já pode começar a ir buscar 
     * as malas ao porão do avião
     * 
     * @param dest TRUE se este aeroporto é o seu destino, FALSE caso contrário
     * @param nMalas número de malas que o passageiro contém
     * @return  qual o seu próximo passo dependendo da sua condição:
     * <li> WITH_BAGAGE caso este seja o seu destino e possua bagagens
     * <li> WTHOUT_BAGAGE caso este seja o seu destino e não possua bagagens
     * <li> IN_TRANSIT caso esteja em trânsito
     */
@Override
public synchronized destination whatShouldIDo(boolean dest,int nMalas)
{
    System.out.println("What should i do!");
    nPass--;
    if(nPass == 0)
    {
        //reset da variavel nPass
        canGo = true;
        nPass = passMax;
        notify();
    }
    if(dest && nMalas == 0)
    {
        return destination.WITHOUT_BAGGAGE;
    }
    else if(dest)
        return destination.WITH_BAGGAGE;
    else
        return destination.IN_TRANSIT;
    
}

    /**
     * Invocador: Bagageiro
     * 
     * Não existem mais malas para apanhar
     * 
     * O bagageiro, após verificar que o porão já se encontra vazio, dirige-se à
     * sua sala de espera
     */
@Override
public synchronized void noMoreBagsToCollect()
{
    System.out.println("No more bags to collect!");
}

}
