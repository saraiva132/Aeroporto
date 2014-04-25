/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerSide;

import sdaeroporto.ZonaDesembarqueMain;

/**
 *
 * @author Hugo
 */
public class ServerZonaDesembarqueProxy extends ServerProxy{
    
    private final ZonaDesembarqueMain desembarqueMain;
    
    public ServerZonaDesembarqueProxy(ServerCom sconi, ServerZonaDesembarqueInterface desembarqueInter,ZonaDesembarqueMain deesembarqueMain) {
        super(sconi,desembarqueInter,"ZonaDesembarqueProxy_");
        this.desembarqueMain=deesembarqueMain;
    }
    
    @Override
    public void run(){
       if(super.requestAndReply())
           desembarqueMain.close();
    }
    
    
    
}
