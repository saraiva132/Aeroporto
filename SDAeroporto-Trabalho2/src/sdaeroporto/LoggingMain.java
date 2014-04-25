/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdaeroporto;

import static Estruturas.AuxInfo.MON_LOGGING;
import static Estruturas.AuxInfo.portNumber;
import Monitores.Logging;
import ServerSide.ServerLoggingInterface;
import ServerSide.ServerLoggingProxy;
import genclass.GenericIO;
import java.io.PrintStream;
import serverSide.ServerCom;

/**
 *
 * @author Hugo
 */
public class LoggingMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PrintStream stdout = System.out;
        Logging log = new Logging();
        ServerLoggingInterface logInter = new ServerLoggingInterface(log);
        ServerCom scon, sconi;
        ServerLoggingProxy logProxy;
        scon = new ServerCom(portNumber[MON_LOGGING]);
        scon.start();
        stdout.println("O serviço Logging foi estabelecido!");
        stdout.println("O servidor esta em escuta.");

        while (true) {
            sconi = scon.accept();
            logProxy = new ServerLoggingProxy(sconi, logInter,stdout);
            logProxy.start();
        }

    }

}
