package ServerSide;

import sdaeroporto.AutocarroMain;

/**
 *
 * @author Hugo
 */
public class ServerAutocarroProxy extends ServerProxy{

    private final AutocarroMain autocarroMain;

    public ServerAutocarroProxy(ServerCom sconi, ServerAutocarroInterface autoInter,AutocarroMain autocarroMain) {
        super(sconi,autoInter,"AutocarroProxy_");
        this.autocarroMain = autocarroMain;
    }      
    
    
    @Override
    public void run(){
        if(super.requestAndReply())
            autocarroMain.close();
    }   
}