package Monitores;

import ClientSide.InterfaceMonitoresLogging;
import Estruturas.AuxInfo;
import Estruturas.Mala;
import Interfaces.LoggingBagageiroInterface;
import Interfaces.PoraoBagageiroInterface;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * Monitor que simula a interacção do passageiro com o porão de um avião
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Porao implements PoraoBagageiroInterface {

    /**
     * Conjunto de malas que estão no porão do avião
     *
     * @serialField malas
     */
    ArrayList<Mala> malas;
    
    private InterfaceMonitoresLogging log;
    
    /**
     * Instanciação e inicialização do monitor <b>Porao</b>
     *
     */
    public Porao(){
        this.malas = new ArrayList<>();
        log = new InterfaceMonitoresLogging("Porao");
    }

    /**
     * Tentar recolher uma mala
     * <p>
     * Invocador: bagageiro
     * <p>
     * O bagageiro desloca-se ao porão do avião e caso este não se encontre
     * vazio recolhe uma mala
     *
     * @return Mala que apanhou no porão e informação sobre o estado do mesmo
     */
    @Override
    public synchronized Mala tryToCollectABag() {
        //System.out.println("Procurando mala..");
        
//        BagageiroTransportation mala =new BagageiroTransportation();
//        if(malas.size()>1)
//            mala.setTransport(malas.remove(0), false);
//        else
//            mala.setTransport(malas.remove(0), true);
//        return mala;
        
        if (malas.isEmpty()) {
            return null;
        } else {
            System.out.println("vim recolher mala");
            log.reportState(AuxInfo.bagState.AT_THE_PLANES_HOLD);
            log.bagagemPorao();
            return malas.remove(0);
        }
    }
    
    public void sendLuggages(Mala [] malas){
        this.malas.addAll(Arrays.asList(malas));
        System.out.println("adicionei "+ malas.length+" malas.");
    }
}
