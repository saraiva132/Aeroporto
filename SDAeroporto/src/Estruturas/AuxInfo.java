package Estruturas;

/**
 * Armazena as constantes necessárias para a simulação do problema.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class AuxInfo {
    /**
     * Identificação do número de voos da simulação
     * 
     * @serialField chegadas
     */
    public static final int nChegadas = 1;
    
    /**
     * Identificação do número máximo de bagagens por passageiro
     * 
     * @serialField bagMax
     */
    public static final int bagMax = 2;
    
    /**
     * Identificação da lotação máxima do autocarro
     * 
     * @serialField lotação
     */
    public static final int lotação = 3;
    
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
    public static enum passState {
        
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
            case AT_THE_DISEMBARKING_ZONE: return "DESEMBARCAR";
            case AT_THE_LUGGAGE_COLLECTION_POINT: return "RECOLHER";
            case AT_THE_BAGGAGE_RECLAIM_OFFICE: return "RECLAMAÇÂO";
            case EXITING_THE_ARRIVAL_TERMINAL: return "CASA";
            case AT_THE_ARRIVAL_TRANSFER_TERMINAL: return "PASSEIO";
            case TERMINAL_TRANSFER: return "AUTOCARRO";
            case AT_THE_DEPARTURE_TRANSFER_TERMINAL: return "SAIDA";
            case ENTERING_THE_DEPARTURE_TERMINAL: return "PROXIMO";
            default: throw new IllegalArgumentException();
        }    
        }
    }
    /**
     * Identificação dos possíveis estados do bagageiro ao longo do seu  
     * ciclo de vida no âmbito do problema <b>Rapsódia no Aeroporto</b>
     */
    public static enum bagState {

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
            case WAITING_FOR_A_PLANE_TO_LAND: return "REST";
            case AT_THE_PLANES_HOLD: return "BUSCAR MALA";
            case AT_THE_LUGGAGE_BELT_CONVERYOR: return "BELT";
            case AT_THE_STOREROOM: return "STOREROOM";    
            default: throw new IllegalArgumentException();
        }    
        }
    }
    
    /**
     * Identificação dos possíveis estados do motorista ao longo do seu  
     * ciclo de vida no âmbito do problema <b>Rapsódia no Aeroporto</b>
     */
    public static enum motState {
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
            case PARKING_AT_THE_ARRIVAL_TERMINAL: return "NO PASSEIO";
            case DRIVING_FORWARD: return "FRENTE";
            case DRIVING_BACKWARD: return "TRAS"; 
            case PARKING_AT_THE_DEPARTURE_TERMINAL: return "TERMINAL";   
            default: throw new IllegalArgumentException();
        }    
        }
    }
    
    /**
     * Identifica os possíveis tipos de passageiro que existem no âmbito do 
     * problema <b>Rapsódia no Aeroporto</b>.
     */
    public static enum destination {
        
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
    
    public static enum bagDest {
        
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
    
    public static enum bagCollect {
        
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
}
