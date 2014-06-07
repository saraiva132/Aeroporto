/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdaeroporto;

import Estruturas.Globals;
import Registry.RecolhaBagagemRegister;
import genclass.GenericIO;
import java.rmi.RMISecurityManager;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class RecolhaBagagemMain {
    
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
        
        RecolhaBagagemRegister recolhaRegister = new RecolhaBagagemRegister();
        recolhaRegister.listening();
    }

}
