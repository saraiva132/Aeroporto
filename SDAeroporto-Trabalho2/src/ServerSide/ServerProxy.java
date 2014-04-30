package ServerSide;

import static Estruturas.Globals.SHUTDOWN_MONITOR;
import Message.MessageRequestException;
import Message.Request;
import Message.Response;
import genclass.GenericIO;

/**
 * Este tipo de dados define a classe mãe de todos os threads agentes prestadores 
 * de serviços <i>Server*Monitor_Name*Proxy</i> para a solução do problema <b>Rapsódia no Aeroporto</b>.
 * <p>
 * É responsável por ler o pedido do cliente, delegar o ser processamento ao <i>Server*Monitor_Name*Interface</i>
 * e enviar uma mensagem de resposta de volta ao cliente. * 
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ServerProxy extends Thread {

    /**
     * Contador de threads lançados
     *
     * @serialField nProxy
     */
    private static int nProxy=0;
    /**
     * Instância da interface a um monitor genérico.
     * 
     * @serialField monInterface
     */
    private ServerInterface monInterface=null;
    
    /**
     * Identificador da thread.
     * 
     * @serialField name
     */
    private String name=null;
    /**
     * Canal de comunicação.
     * 
     * @serialField sconi
     */
    private ServerSide.ServerCom sconi=null;
    /**
     * 
     * @param sconi canal de comunicação
     * @param monInterface interface a um monitor genérico no âmbito do problema <b>Rapsódia no Aeroporto</b>
     * @param name identificador da thread
     */
    public ServerProxy(ServerCom sconi, ServerInterface monInterface, String name) {
        this.name =name; 
        this.monInterface = monInterface;
        this.sconi = sconi;
    }
    
    /**
     * Operação a ser realizada durante o ciclo de vida de um thread agente prestador de serviços:
     * <ul>
     * <li>ler pedido do cliente;
     * <li>processar pedido do cliente;
     * <li>enviar resposta para o cliente;
     * <li>fechar canal de comunicação.
     * </ul>
     * @return Informação se o servidor pode terminar a sua execução:
     * <ul>
     * <li>TRUE, caso possa
     * <li>FALSE, caso contrário
     * </ul>
     * 
     */
    protected boolean requestAndReply(){
        Request request;
        Response response = null;
        request = (Request) sconi.readObject();
        boolean canEnd=false;
        try {
            response = monInterface.processAndReply(request);
            if(request.getMethodName() == SHUTDOWN_MONITOR)
                canEnd=(Boolean) response.getAns();
        } catch (MessageRequestException e) {
            GenericIO.writelnString("Thread " + name + ": " + e.getMessage() + "!");
            GenericIO.writelnString(e.getRequestErrorMessage().toString());
            System.err.println(e);
            System.exit(1);
        }catch(Exception e){
            GenericIO.writelnString("Thread " + name + ": " + request.toString() + "!");
            System.err.println(e);
            System.exit(1);
        }
        sconi.writeObject(response);
        sconi.close();              
        return canEnd;
    }
}
