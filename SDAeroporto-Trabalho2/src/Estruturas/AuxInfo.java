package Estruturas;

import java.io.Serializable;

/**
 * Armazena as constantes necessárias para a simulação do problema.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class AuxInfo {
    
    /**
     * Nome do ficheiro de logging
     *
     * @serialField fileName
     */
    public static String fileName = "log.txt";
    
    /**
     * Identificação do número de voos da simulação
     * 
     * @serialField chegadas
     */
    public static final int nChegadas = 50;
    
    /**
     * Identificação do número máximo de bagagens por passageiro
     * 
     * @serialField bagMax
     */
    public static final int bagMax = 4;
    
    /**
     * Identificação da lotação máxima do autocarro
     * 
     * @serialField lotação
     */
    public static final int lotação = 5;
    
    /**
     * Identificação do número de passageiros que chega por voo
     * 
     * @serialField passMax
     */
    public static final int passMax = 6;
    
    /**
     * Identificação dos possíveis estados de um passageiro ao longo do seu  
     * ciclo de vida no âmbito do problema <b>Rapsódia no Aeroporto</b>
     */
    public static enum passState implements Serializable{
        
        /**
         * O passageiro encontra-se na zona de desembarque, após o seu voo ter 
         * chegado.
         */
        AT_THE_DISEMBARKING_ZONE,            
        
        /**
         * O passageiro, após chegar ao seu destino, encontra-se a recolher as 
         * suas bagagens.
         */
        AT_THE_LUGGAGE_COLLECTION_POINT,
        
        /**
         * O passageiro encontra-se a reclamar a falta de uma (ou mais) das suas 
         * malas no guichet de reclamação do aeroporto.
         */
        AT_THE_BAGGAGE_RECLAIM_OFFICE,
        
        /**
         * O passageiro encontra-se a ir para casa.
         */
        EXITING_THE_ARRIVAL_TERMINAL,
        
      /**
        * O passageiro encontra-se no passeio, na zona de transferência do 
        * terminal de chegada à espera que o motorista o permita entrar no 
        * autocarro.
        */
        AT_THE_ARRIVAL_TRANSFER_TERMINAL,
        
        /**
         * O passageiro encontra-se no autocarro a realizar a transferência 
         * entre o terminal de chegada e o de partida.
         * 
         */
        TERMINAL_TRANSFER,
        
        /**
         * O passageiro chega à zonade transferência do terminal de partida .
         */
        AT_THE_DEPARTURE_TRANSFER_TERMINAL,
        
        /**
         * O passageiro encontra-se no terminal de saída à espera que o seu voo 
         * parta.
         */
        ENTERING_THE_DEPARTURE_TERMINAL;
        
        @Override
        public String toString(){
        switch(this){
            case AT_THE_DISEMBARKING_ZONE: return "ADZ";
            case AT_THE_LUGGAGE_COLLECTION_POINT: return "BEL";
            case AT_THE_BAGGAGE_RECLAIM_OFFICE: return "RCL";
            case EXITING_THE_ARRIVAL_TERMINAL: return "HOM";
            case AT_THE_ARRIVAL_TRANSFER_TERMINAL: return "ATT";
            case TERMINAL_TRANSFER: return "BUS";
            case AT_THE_DEPARTURE_TRANSFER_TERMINAL: return "DTT";
            case ENTERING_THE_DEPARTURE_TERMINAL: return "EDT";
            default: throw new IllegalArgumentException();
        }    
        }
    }
    /**
     * Identificação dos possíveis estados do bagageiro ao longo do seu  
     * ciclo de vida no âmbito do problema <b>Rapsódia no Aeroporto</b>
     */
    public static enum bagState implements Serializable{

        /**
         * O bagageiro encontra-se à espera que um voo chegue e que todos os 
         * passageiros ( do respectivo voo) desembarquem.
         */
        WAITING_FOR_A_PLANE_TO_LAND,
        
        /**
         * O bagageiro encontra-se no porão do avião a recolher uma bagagem de 
         * um passageiro do voo que acabou de chegar.
         */
        AT_THE_PLANES_HOLD,
        
        /**
         * O bagageiro encontra-se a descarregar uma mala de um passageiro, 
         * cujo destino era este aeroporto, na passadeira da zona de recolha de 
         * bagagens, após a ter transportado desde o porão.
         */
        AT_THE_LUGGAGE_BELT_CONVERYOR,
        
        /**
         * O bagageiro encontra-se a descarregar uma malaa de um passageiro, que
         * está em trânsito, na sala de armazenamento de bagagens, após a ter 
         * transportado desde o porão.
         */
        AT_THE_STOREROOM;
        @Override
        public String toString(){
        switch(this){
            case WAITING_FOR_A_PLANE_TO_LAND: return "W4P";
            case AT_THE_PLANES_HOLD: return "APH";
            case AT_THE_LUGGAGE_BELT_CONVERYOR: return "BEL";
            case AT_THE_STOREROOM: return "STR";    
            default: throw new IllegalArgumentException();
        }    
        }
    }
    
    /**
     * Identificação dos possíveis estados do motorista ao longo do seu  
     * ciclo de vida no âmbito do problema <b>Rapsódia no Aeroporto</b>
     */
    public static enum motState implements Serializable{
        /**
         * O motorista encontra-se na zona de transferência do terminal de 
         * chegada à espera que cheguem tantos passageiros quantos o autocarro 
         * permite transportar, ou que sejam horas de realizar a viagem entre os 
         * terminais.
         */
        PARKING_AT_THE_ARRIVAL_TERMINAL, 
        
        /**
         * O motorista encontra-se a realizar a viagem entre a zona de 
         * transferência do terminal de chegada e a zona de transferência do 
         * terminal de partida.
         */
        DRIVING_FORWARD,
        
        /**
         * O motorista encontra-se a realizar a viagem entre a zona de 
         * transferência do terminal de partida e a zona de transferência do 
         * terminal de chegada.
         */
        DRIVING_BACKWARD,
        
        /**
         * O motorista encontra-se no terminal de partida à espera que todos os 
         * passageiros saiam do autocarro.
         */
        PARKING_AT_THE_DEPARTURE_TERMINAL;
        
        @Override
        public String toString(){
        switch(this){
            case PARKING_AT_THE_ARRIVAL_TERMINAL: return "PAT";
            case DRIVING_FORWARD: return "DRF";
            case DRIVING_BACKWARD: return "DRB"; 
            case PARKING_AT_THE_DEPARTURE_TERMINAL: return "PDT";   
            default: throw new IllegalArgumentException();
        }    
        }
    }
    
    /**
     * Identifica os possíveis tipos de passageiro que existem no âmbito do 
     * problema <b>Rapsódia no Aeroporto</b>.
     */
    public static enum destination implements Serializable{
        
        /**
         * O passageiro está em trânsito.
         */
        IN_TRANSIT, 
        
        /**
         * Este aeroporto corresponde ao destino do passageiro; e este possui 
         * bagagens que necessitam de ser recolhidas.
         */    
        WITH_BAGGAGE,
        
        /**
         * Este aeroporto corresponde ao destino do passageiro; e este não possui
         * bagagens.
         */
        WITHOUT_BAGGAGE;
        
    }
    
    /**
     * Identifica os possíveis tipos de malas que existem no âmbito do 
     * problema <b>Rapsódia no Aeroporto</b>, de acordo com o tipo 
     * de passageiro a que corresponde o seu owner.
     */
    
    public static enum bagDest implements Serializable{
        
        /**
         * O owner da mala está em trânsito, logo ela deve ser depositada na 
         * zona de armazenamento temporário de bagagens do aeroporto.
         */
        STOREROOM,
        
        /**
         * O aeroporto corresponde ao destino do owner da mala, logo ela deve ser 
         * depositada na passadeira de recolha de bagagens.
         */
        BELT,
        
        /**
         * O porão encontra-se sem malas!
         */
        LOBBYCLEAN,
    }
    
    /**
     * Identifica o tipo de mala que o passageiro recolheu da passadeira de 
     * recolha de bagagens.
     */
    
    public static enum bagCollect implements Serializable {
        
        /**
         * O passageiro recolheu uma mala com sucesso .
         */
        MINE,
        
        /**
         * O passageiro não conseguiu recolher, pelo menos uma mala sua, 
         * com sucesso.
         * 
         */
        NOMORE,
        
        /**
         * O passageiro não conseguiu recolher esta mala.
         * 
         */
        UNSUCCESSFUL,
    }
    
    
    /*resposta do servidor para os clientes quando está tudo OK*/
    
    /**
     * Não ocorreu erro na troca de mensagens.
     */
    public static final int OK = 0;
    
    /**
     * Ocorreu um erro na troca de mensagens.
     */
    public static final int ERROR = 1;
    
    /*operacoes realizadas sobre os monitores*/
    
    /**
     * Invocações remotas de métodos sobre os monitores
     */
    public static final int ANNOUNCING_BUS_BOARDING_WAITING = 0;
    public static final int GO_TO_DEPARTURE_TERMINAL = 1;
    public static final int GO_TO_ARRIVAL_TERMINAL = 2;
    public static final int PARK_THE_BUS = 4;
    public static final int PARK_THE_BUS_AND_LET_PASS_OFF = 5;
    public static final int ENTER_THE_BUS = 6;
    public static final int LEAVE_THE_BUS = 7;
    public static final int TRY_TO_COLLECT_A_BAG = 8;
    public static final int CARRY_IT_TO_APPROPRIATE_STORE = 9;
    public static final int GO_COLLECT_A_BAG = 10;
    public static final int REPORT_MISSING_BAGS = 11;
    public static final int HAS_DAYS_WORK_ENDED = 12;
    public static final int ANNOUNCING_BUS_BOARDING_SHOUTING = 13;
    public static final int TAKE_A_BUS = 14;
    public static final int GO_HOME = 15;
    public static final int PREPARE_NEXT_LEG = 16;
    public static final int NO_MORE_BAGS_TO_COLLECT = 17;
    public static final int WHAT_SHOULD_I_DO = 18;
    public static final int TAKE_A_REST = 19;
    
    /*auxs*/
    /**
     * Invocações remotas auxiliares de métodos sobre os monitores
     */
    public static final int RESET_NOMORE_BAGS = 20;
    public static final int SET_N_VOO = 21;
    public static final int SEND_LUGAGES = 17;
    
    /*operacoes realizadas sobre o logging*/
    
    /**
     * Actualização remota de informação no repositório geral
     */
    public static final int REPORT_INITIAL_STATUS = 0;
    public static final int REPORT_STATE_PASSAGEIRO = 1;
    public static final int REPORT_STATE_MOTORISTA = 2;
    public static final int REPORT_STATE_BAGAGEIRO = 3;
    public static final int N_VOO = 4;
    public static final int SET_PORAO = 5;
    public static final int BAGAGEM_PORAO = 6;
    public static final int BAGAGEM_BELT = 7;
    public static final int BAGAGEM_STORE = 8;
    public static final int MALAS_ACTUAL = 9;
    public static final int MALAS_INICIAL =10;
    public static final int DESTINO = 11;
    public static final int MISSING_BAGS = 12;
    public static final int ADD_FILA_ESPERA = 13;
    public static final int REMOVE_FILA_ESPERA = 14;
    public static final int AUTOCARRO_STATE = 15;
    public static final int CLOSE = 99;
    public static final int REPORT_FINAL_STATUS = 17;
    
    
    
    /*Identificacao dos monitores*/
    public static final int MON_ZONA_DESEMBARQUE = 0;
    public static final int MON_RECOLHA_BAGAGEM = 1;
    public static final int MON_PORAO = 2;
    public static final int MON_TRANSFERENCIA_TERMINAL = 3;
    public static final int MON_AUTOCARRO = 4;
    public static final int MON_TRANSICAO_AEROPORTO = 5;
    public static final int MON_LOGGING = 6;
    
    /**
     * Endereços dos monitores remotos
     */
    public static final String [] hostName = { "127.0.0.1",
                                        "127.0.0.1",
                                        "127.0.0.1",
                                        "127.0.0.1",
                                        "127.0.0.1",
                                        "127.0.0.1",
                                        "127.0.0.1",};
    
    /**
     * Portas
     */
    public static final int [] portNumber = {4000,
                                       4001,
                                       4002,
                                       4003,
                                       4004,
                                       4005,
                                       4006 };
    
    
}
