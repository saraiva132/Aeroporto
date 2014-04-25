/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Message;

import java.io.Serializable;

/**
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Response implements Serializable{
    
     private final int status;

    private final Object ans;

    public Response(int status, Object ans) {
        this.status = status;
        this.ans = ans;
    }
    public int getStatus() {
        return status;
    }
    public Object getAns() {
        return ans;
    }  
}
