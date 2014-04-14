/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClientSide;

import Interfaces.AutocarroMotoristaInterface;
import Interfaces.TransferenciaMotoristaInterface;

/**
 *
 * @author rafael
 */
public class InterfaceMotorista implements AutocarroMotoristaInterface,TransferenciaMotoristaInterface{
   
    /**
     * @param hostName nome do sistema computacional onde está localizado o
     * servidor
     */
    private String hostname;

    /**
     * @param port número do port de escuta do servidor
     */
    private int port;

    public InterfaceMotorista(String hostName, int port) {
        this.hostname = hostName;
        this.port = port;
    }

    @Override
    public void announcingBusBoardingWaiting(int bilhetesvendidos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void goToDepartureTerminal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void goToArrivalTerminal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void parkTheBus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void parkTheBusAndLetPassOff() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasDaysWorkEnded() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int announcingBusBoardingShouting() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
