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
public class Request implements Serializable{

    private final int method;

    private final Object[] args;

    public Request(int method, Object... args) {
        this.method = method;
        this.args = args;
    }
    
    public int getMethodName() {
        return method;
    }
    public Object[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "Request{" + "method=" + method + ", args.length=" + args.length + '}';
    }
    
    
}
