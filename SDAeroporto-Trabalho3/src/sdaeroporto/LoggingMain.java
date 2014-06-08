package sdaeroporto;

import Estruturas.Globals;
import Registry.LoggingRegister;
import genclass.GenericIO;
import java.rmi.RMISecurityManager;

/**
 * Este tipo de dados simula a solução do lado do servidor referente ao monitor
 * <i>Logging</i> do problema
 * <b>Rapsódia no Aeroporto</b>.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class LoggingMain {

    static {
        System.setProperty("java.security.policy", "java.policy");
    }

    /**
     * Programa Principal.
     */
    public static void main(String[] args) {
        Globals.xmlParser();
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        GenericIO.writelnString("Security manager was installed!");
        LoggingRegister logRegister = new LoggingRegister();
        logRegister.run();
    }

}
