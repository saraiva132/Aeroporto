package Estruturas;

/**
 * Identifica o tipo de dados mala
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Mala {
  /**
    * Identifica o passageiro dono da mala
    * 
    * @serialField owner
    */
    private int owner;
    
    /**
     * Identifica se o owner da mala está ou não em trânsito
     * 
     * <li> TRUE, se está em trânsito
     * <li> FALSE, se não está em trânsito
     */
    private boolean transit;
    
    /**
     * Instanciação da mala
     * 
     * @param owner identificação do passageiro dono da mala
     * @param transit identificação do tipo de passageiro dono da mala
     */
    public Mala(int owner, boolean transit) {
        this.owner = owner;
        this.transit = transit;
    }
    /**
    * 
    * @return owner id
    */
    public int getOwner() {
        return owner;
    }

    /**
     * 
     * @return tipo de passageiro
     * <li> TRUE o passageiro está em trânsito
     * <li> False o passageiro não está em trânsito
     */
    public boolean inTransit() {
        return transit;
    }

}
