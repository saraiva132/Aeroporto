/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Estruturas.AuxInfo;

/**
 *
 * @author rafael
 */
public interface LoggingBagageiroInterface {

    public void reportState(AuxInfo.bagState state);

    public void bagagemPorao();

    public void bagagemBelt(boolean take);

    public void bagagemStore();

}
