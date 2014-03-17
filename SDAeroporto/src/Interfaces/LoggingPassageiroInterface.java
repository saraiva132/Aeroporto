/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Estruturas.AuxInfo;
import java.util.LinkedList;

/**
 *
 * @author rafael
 */
public interface LoggingPassageiroInterface {

    public void reportState(int passID, AuxInfo.passState state);
    
    public void bagagemBelt(boolean take);

    public void malasActual(int passID, int nMalas);

    public void malasInicial(int passID, int nMalas);

    public void destino(int passID, Boolean destino);

    public void filaEspera(Object [] oi);

    public void autocarroState(int[] seats);
}
