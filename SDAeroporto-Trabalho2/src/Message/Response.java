package Message;

import java.io.Serializable;

/**
 * Formato de uma mensagem resposta enviada por um monitor no âmbito de uma operação que foi realizada sobre ele.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Response implements Serializable {

    /**
     * Identificador de se a operação foi realizada com sucesso, ou se houve algum erro durante a execução da mesma.
     * 
     * @serialField status
     */
    private final int status;

    /**
     * Conteúdo da resposta enviada por um monitor no âmbito e uma operação realizada sobre ele. 
     * Caso a operação invocada não seja do tipo <i>void</i>, este campo toma o valor <i>null</i>.
     * 
     * @serialField ans
     */
    private final Object ans;
    
    private int serial;
    /**
     * Instanciação de uma mensagem do tipo Response
     * @param status status com que foi realizada a operação
     * @param ans resposta do monitor
     */
    public Response(int status,int serial, Object ans) {
        this.status = status;
        this.ans = ans;
        this.serial = serial;
    }

    /**
     * Obter o status com que a operação foi realizada sobre o monitor.
     * 
     * @return Status com que a operação foi realizada
     */
    public int getStatus() {
        return status;
    }
    
    public int getSerial(){
        return serial;
    }
    
    /**
     * Obter conteúdo da resposta enviada pelo monitor no âmbito da operação realizada sobre ele.
     * @return Resposta enviada pelo monitor
     */
    public Object getAns() {
        return ans;
    }
}
