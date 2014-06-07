package Estruturas;

import java.io.Serializable;

/**
 * Identifica um tipo de dados de geral de retorno das operações (funções) que são executadas sobre os monitores.
 * <p>
 * A utilização desta estrutura de dados advém da necessidade de obtenção do relógio vectorial a cada operação que é realizada sobre os monitores.
 * 
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Reply implements Serializable{

    /**
     * Identificador do timestamp mais actualizado.
     * 
     * @serialField ts
     */
    private final VectorCLK timestamp;
    
    /**
     * Valor do objecto de retorno da operação realizada sobre o monitor.
     * 
     * @serialField retorno
     */
    private final Object retorno;

    /**
     * Instanciação e inicialização do tipo de dados Reply
     * 
     * @param ts identificador do timestamp
     * @param retorno valor de retorno
     */
    public Reply(VectorCLK ts, Object retorno) {
        this.timestamp = ts;
        this.retorno = retorno;
    }
    
    /**
     * Obter o identificador do timestamp
     * 
     * @return Identificador do timestamp
     */
    public VectorCLK getTimestamp() {
        return timestamp;
    }

    /**
     * Obter o valor do objecto de retorno da operação realizada sobre o monitor
     * 
     * @return Valor do objecto de retorno da operação realizada sobre o monitor
     */
    public Object getRetorno() {
        return retorno;
    }
}
