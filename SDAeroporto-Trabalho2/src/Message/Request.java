package Message;

import java.io.Serializable;

/**
 * Formato de uma mensagem enviada por um <i>cliente</i> quando pretende realizar uma operação
 * sobre um monitor.
 * <p>
 * No âmbito deste problema existem 3 tipos de clientes:
 * <ul>
 * <li> As 3 entidades activas instanciadas (passageiro, motorista e bagageiro) 
 * que realizam operações sobre os monitores no âmbito do seu ciclo de vida
 * <li> As mains das 3 entidades activas instanciadas supramencionadas, que necessitam 
 * de realizar operações sobre os monitores a cada iteação de uma simulação e no 
 * final da simulação para terminar a execução dos monitores
 * <li> Os monitores (sobre os quais as 3 entidades activas instanciadas supramencionadas 
 * efectuam operações no âmbito do seu ciclo de vida) que necessitam de aceder ao monitor <i>Logging</i>
 * com o objectivo de alterar o estado geral do problema.
 * </ul>
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Request implements Serializable{
    /**
     * Identificador da operação que pretende invocar sobre o monitor para onde está a enviar a mensagem.
     * 
     * @serialField method
     */
    private final int method;

    /**
     * Parâmetros necessários para a correcta invocação de uma operação sobre um monitor.
     * 
     * @serialField args
     */
    private final Object[] args;

    /**
     * Instanciação de uma mensagem do tipo Request.
     * 
     * @param method identificador da operação
     * @param args parâmetros necessários para a correcta invocação da operação
     */
    public Request(int method, Object... args) {
        this.method = method;
        this.args = args;
    }
    /**
     * Obter o identificador da operação a invocar.
     * 
     * @return Identificador da operação
     */
    public int getMethodName() {
        return method;
    }
    /**
     * Obter os parâmetros necessários à correcta invocação de uma operação num monitor remoto.
     * 
     * @return parâmetros que a mensagem contém
     */
    public Object[] getArgs() {
        return args;
    }

    /**
     * Impressão dos identificadores dos campos internos. 
     * <p>Usada para fins de debugging.
     * @return string contendo a identificação da operação a ser chamada, bem como do número de parâmetros que esta mensagem contém.
     */
    @Override
    public String toString() {
        return "Request{" + "method=" + method + ", args.length=" + args.length + '}';
    }
    
    
}
