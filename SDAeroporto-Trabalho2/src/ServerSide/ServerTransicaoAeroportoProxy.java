/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerSide;

import sdaeroporto.TransicaoAeroportoMain;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class ServerTransicaoAeroportoProxy extends ServerProxy{
    private final TransicaoAeroportoMain transicaoMain;

    public ServerTransicaoAeroportoProxy(ServerCom sconi, ServerTransicaoAeroportoInterface transicaoInter,TransicaoAeroportoMain aeroportoMain) {
        super(sconi,transicaoInter,"TransicaoAeroportoProxy_" );
        this.transicaoMain=aeroportoMain;
    }
    
    @Override
    public void run(){
        if(super.requestAndReply())
            transicaoMain.close();
    }
    
   
}
