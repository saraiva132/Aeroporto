/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import Estruturas.AuxInfo.bagCollect;

/**
 *
 * @author rafael
 */
public interface RecolhaPassageiroInterface {
    
    public bagCollect goCollectABag(int id);
    
    public void reportMissingBags(int passageiroID,int malasPerdidas);

}
