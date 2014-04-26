/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerSide;

import sdaeroporto.TransferenciaTerminalMain;


/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ServerTransferenciaTerminalProxy extends ServerProxy{
    private TransferenciaTerminalMain transferenciaMain;
    public ServerTransferenciaTerminalProxy(ServerCom sconi, ServerTransferenciaTerminalInterface transferenciaInter,TransferenciaTerminalMain transferenciaMain) {
        super(sconi,transferenciaInter,"TransferenciaTerminalProxy_" );
        this.transferenciaMain=transferenciaMain;
    }
    
    
    
    @Override
    public void run(){
        if(super.requestAndReply())
            transferenciaMain.close();
    }
   
}
