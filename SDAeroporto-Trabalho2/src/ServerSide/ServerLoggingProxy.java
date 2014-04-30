package ServerSide;

import java.io.PrintStream;
import sdaeroporto.LoggingMain;
/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ServerLoggingProxy extends ServerProxy{

    private static PrintStream stdout;
    private final LoggingMain logMain;

    /**
     *
     * @param sconi
     * @param logInter
     * @param stdout
     */
    public ServerLoggingProxy(ServerCom sconi, ServerLoggingInterface logInter, PrintStream stdout,LoggingMain logMain) {
        super(sconi,logInter,"LoggingProxy_");
        this.logMain = logMain;
        this.stdout = stdout;
    }
    
    @Override
    public void run(){
        if(super.requestAndReply())
            logMain.close();
    }
    
}
