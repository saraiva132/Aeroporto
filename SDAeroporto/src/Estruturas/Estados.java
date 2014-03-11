/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Estruturas;

/**
 *
 * @author rafael
 */
public class Estados {

     public final static int 
                            ZONADESEMBARQUE = 0,     //PASSAGEIRO            
                            RECOLHA = 1,                 
                            GUICHET = 2,
                            ARRIVALTRANSFER  = 3,                 
                            EXIT = 4,                  
                            TRANSFERTERMINAL = 5,                 
                            DEPARTURETRANSFER = 6,                  
                            DEPARTURETERMINAL= 7,           
                            
                            WAITINGPLANE = 0,   //BAGAGEIRO
                            PLANEHOLD = 1,
                            LUGGAGEBELT = 2,
                            STOREROOM = 3,
             
                            PARKINGARRIVAL = 0, //MOTORISTA
                            DRIVINGFORWARD = 1,
                            DRIVINGBACKWARD = 2,
                            PARKINGDEPARTURE = 3;
}
