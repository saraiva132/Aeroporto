package Threads;

import ClientSide.InterfacePassageiro;
import Estruturas.AuxInfo.bagCollect;
import Estruturas.AuxInfo.destination;
import Estruturas.AuxInfo.passState;

/**
 * Identifica o tipo de dados passageiro
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Passageiro extends Thread {

    /**
     * Identifica o estado do passageiro
     *
     * @serialField state
     */
    private passState state;

    /**
     * Identifica o passageiro
     *
     * @serialField id
     */
    private final int id;

    /**
     * Identifica o número de voo do passageiro
     *
     * @serialField nVoo
     */
    private final int nVoo;

    /**
     * Identifica o número de malas total do passageiro
     *
     * @serialField nMalasTotal
     */
    private final int nMalasTotal;

    /**
     * Identifica o número de malas que o passageiro tem em sua posse
     *
     * @serialField nMalasEmPosse
     */
    private int nMalasEmPosse;

    /**
     * Identifica se o passageiro está em trânsito, ou se acaba neste aeroporto
     * a sua viagem
     *
     * @serialField finalDest
     */
    private final boolean finalDest;
    
    private InterfacePassageiro passageiroI= null;

    /**
     * Instanciação e inicialização do passageiro
     *
     * @param nMalasTotal número de malas total do passageiro
     * @param id identificador do passageiro
     * @param nVoo número do voo em que o passageiro vem
     * @param finalDest TRUE caso este seja o aeroporto de destino do
     * passageiro, FALSE caso contrário
     */
    public Passageiro(int nMalasTotal, int id, int nVoo, boolean finalDest) {
        this.nMalasTotal = nMalasTotal;
        nMalasEmPosse = 0;
        this.finalDest = finalDest;
        this.nVoo = nVoo;   //not used for now
        this.id = id;
        this.passageiroI = new InterfacePassageiro("Passageirod_"+id);
    }

    /**
     * Implementa o ciclo de vida do passageiro
     */
    @Override
    public void run() {
        destination nextState = passageiroI.whatShouldIDo(id,finalDest, nMalasTotal);
        bagCollect getBag;
        switch (nextState) {
            case WITH_BAGGAGE:
                
                // System.out.println("tenho bagagem -----------------");
                do {
                    if ( (getBag=passageiroI.goCollectABag(id) ) == bagCollect.MINE) {
                        nMalasEmPosse++;
                    }
                    //System.out.println("ID: " + id + " posse: " + nMalasEmPosse + " total: " + nMalasTotal);
                    //System.out.println(getBag.toString());

                } while (nMalasEmPosse < nMalasTotal && getBag != bagCollect.NOMORE);
                if (nMalasEmPosse < nMalasTotal) {
                    passageiroI.reportMissingBags(id, nMalasTotal - nMalasEmPosse);
                }
                passageiroI.goHome(id);
                break;
            case IN_TRANSIT:
                int ticket; //bilhete para entrar no autocarro.
                ticket = passageiroI.takeABus(id);
                passageiroI.enterTheBus(ticket,id);
                passageiroI.leaveTheBus(id,ticket);
                passageiroI.prepareNextLeg(id);
                break;
            case WITHOUT_BAGGAGE:
                passageiroI.goHome(id);
                break;
        }
    }
}
