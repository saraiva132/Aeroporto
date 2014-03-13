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
public class AuxInfo {
    
    public static int chegadas = 5;
    public static int bagMax = 2;
    public static int lotação = 3;
    public static int passMax = 6;
    
    public static enum passState {

        AT_THE_DISEMBARKING_ZONE, //PASSAGEIRO            
        AT_THE_LUGGAGE_COLLECTION_POINT,
        AT_THE_BAGGAGE_RECLAIM_OFFICE,
        EXITING_THE_ARRIVAL_TERMINAL,
        AT_THE_ARRIVAL_TRANSFER_TERMINAL,
        TERMINAL_TRANSFER,
        AT_THE_DEPARTURE_TRANSFER_TERMINAL,
        ENTERING_THE_DEPARTURE_TERMINAL
    }

    public static enum bagState {

        WAITING_FOR_A_PLANE_TO_LAND, //BAGAGEIRO
        AT_THE_PLANES_HOLD,
        AT_THE_LUGGAGE_BELT_CONVERYOR,
        AT_THE_STOREROOM
    }

    public static enum motState {

        PARKING_AT_THE_ARRIVAL_TERMINAL, //MOTORISTA
        DRIVING_FORWARD,
        DRIVING_BACKWARD,
        PARKING_AT_THE_DEPARTURE_TERMINAL
    }
    
    public static enum destination {
        IN_TRANSIT, //PASSAGEIRO_DEST
        WITH_BAGGAGE,
        WITHOUT_BAGGAGE,
    }
}
