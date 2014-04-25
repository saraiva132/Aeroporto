/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerSide;

import sdaeroporto.RecolhaBagagemMain;

/**
 *
 * @author Hugo
 */
public class ServerRecolhaBagagemProxy extends ServerProxy{
    private final RecolhaBagagemMain recolhaMain;
    
    public ServerRecolhaBagagemProxy(ServerCom sconi, ServerRecolhaBagagemInterface recolhaInter,RecolhaBagagemMain recolhaMain) {
        super(sconi,recolhaInter,"RecolhaBagagemProxy_" );
        this.recolhaMain=recolhaMain;
    }  
        
    @Override
    public void run(){
        if(super.requestAndReply())
            recolhaMain.close();
    }
    
    
}
