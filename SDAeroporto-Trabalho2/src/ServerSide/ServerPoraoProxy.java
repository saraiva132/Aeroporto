/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerSide;

import sdaeroporto.PoraoMain;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ServerPoraoProxy extends ServerProxy{
    private final PoraoMain poraoMain;
    
    public ServerPoraoProxy(ServerCom sconi, ServerPoraoInterface poraoInter, PoraoMain poraoMain) {
        super(sconi,poraoInter,"PoraoProxy_");
        this.poraoMain=poraoMain;
    }
    
    @Override
    public void run(){
        if( super.requestAndReply() )
            poraoMain.close();
    }
    
}
