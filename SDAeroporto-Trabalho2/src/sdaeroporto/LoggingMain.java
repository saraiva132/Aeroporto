/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdaeroporto;

import static Estruturas.Globals.MON_LOGGING;
import static Estruturas.Globals.portNumber;
import Monitores.Logging;
import ServerSide.ServerLoggingInterface;
import ServerSide.ServerLoggingProxy;
import java.io.PrintStream;
import ServerSide.ServerCom;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class LoggingMain {
    private ServerCom listeningSocket;
    private boolean canEnd=false;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new LoggingMain().listening();
    }
    
    public void listening(){
        
        PrintStream stdout = System.out;
        Logging log = new Logging();
        ServerLoggingInterface logInter = new ServerLoggingInterface(log);
        ServerCom commSocket;
        ServerLoggingProxy logProxy;
        listeningSocket = new ServerCom(portNumber[MON_LOGGING]);
        listeningSocket.start();
        stdout.println("O serviço Logging foi estabelecido!");
        stdout.println("O servidor esta em escuta.");

        while (!canEnd) {
            commSocket = listeningSocket.accept();
            logProxy = new ServerLoggingProxy(commSocket, logInter,stdout,this);
            logProxy.start();
        }
    }
    
    public void close(){
        canEnd=true;
        //scon.end();
        System.exit(0);
    }

}
