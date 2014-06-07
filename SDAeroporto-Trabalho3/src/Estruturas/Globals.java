package Estruturas;

import java.io.File;
import java.io.Serializable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Armazena as constantes necessárias para a simulação do problema.
 *
 * @author Rafael Figueiredo 59863
 * @author Hugo Frade 59399
 */
public class Globals {
    /**
     * Host for remote registry
     * 
     * @serialField registryHostname
     */
    
    public static String registryHostname;
    /**
     * Port on which the registry accepts requests
     * 
     * @serialField registryPort
     */
    public static int registryPort = 22257;
    
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
    public static int nChegadas;

    /**
     * Identificação do número máximo de bagagens por passageiro
     *
     * @serialField bagMax
     */
    public static int bagMax;

    /**
     * Identificação da lotação máxima do autocarro
     *
     * @serialField lotação
     */
    
    public static int lotação;
    
    /**
     * Identificação do número de passageiros que chega por voo
     *
     * @serialField passMax
     */
    public static int passMax;

    /**
     * Identificação dos possíveis estados de um passageiro ao longo do seu
     * ciclo de vida no âmbito do problema <b>Rapsódia no Aeroporto</b>
     */
    public static enum passState implements Serializable {

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
        public String toString() {
            switch (this) {
                case AT_THE_DISEMBARKING_ZONE:
                    return "ADZ";
                case AT_THE_LUGGAGE_COLLECTION_POINT:
                    return "BEL";
                case AT_THE_BAGGAGE_RECLAIM_OFFICE:
                    return "RCL";
                case EXITING_THE_ARRIVAL_TERMINAL:
                    return "HOM";
                case AT_THE_ARRIVAL_TRANSFER_TERMINAL:
                    return "ATT";
                case TERMINAL_TRANSFER:
                    return "BUS";
                case AT_THE_DEPARTURE_TRANSFER_TERMINAL:
                    return "DTT";
                case ENTERING_THE_DEPARTURE_TERMINAL:
                    return "EDT";
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Identificação dos possíveis estados do bagageiro ao longo do seu ciclo de
     * vida no âmbito do problema <b>Rapsódia no Aeroporto</b>
     */
    public static enum bagState implements Serializable {

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
         * O bagageiro encontra-se a descarregar uma mala de um passageiro, cujo
         * destino era este aeroporto, na passadeira da zona de recolha de
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
        public String toString() {
            switch (this) {
                case WAITING_FOR_A_PLANE_TO_LAND:
                    return "W4P";
                case AT_THE_PLANES_HOLD:
                    return "APH";
                case AT_THE_LUGGAGE_BELT_CONVERYOR:
                    return "BEL";
                case AT_THE_STOREROOM:
                    return "STR";
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Identificação dos possíveis estados do motorista ao longo do seu ciclo de
     * vida no âmbito do problema <b>Rapsódia no Aeroporto</b>
     */
    public static enum motState implements Serializable {

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
        public String toString() {
            switch (this) {
                case PARKING_AT_THE_ARRIVAL_TERMINAL:
                    return "PAT";
                case DRIVING_FORWARD:
                    return "DRF";
                case DRIVING_BACKWARD:
                    return "DRB";
                case PARKING_AT_THE_DEPARTURE_TERMINAL:
                    return "PDT";
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Identifica os possíveis tipos de passageiro que existem no âmbito do
     * problema <b>Rapsódia no Aeroporto</b>.
     */
    public static enum destination implements Serializable {

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
         * Este aeroporto corresponde ao destino do passageiro; e este não
         * possui bagagens.
         */
        WITHOUT_BAGGAGE;

    }

    /**
     * Identifica os possíveis tipos de malas que existem no âmbito do problema
     * <b>Rapsódia no Aeroporto</b>, de acordo com o tipo de passageiro a que
     * corresponde o seu owner.
     */
    public static enum bagDest implements Serializable {

        /**
         * O owner da mala está em trânsito, logo ela deve ser depositada na
         * zona de armazenamento temporário de bagagens do aeroporto.
         */
        STOREROOM,
        /**
         * O aeroporto corresponde ao destino do owner da mala, logo ela deve
         * ser depositada na passadeira de recolha de bagagens.
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
         * O passageiro não conseguiu recolher, pelo menos uma mala sua, com
         * sucesso.
         *
         */
        NOMORE,
        /**
         * O passageiro não conseguiu recolher esta mala.
         *
         */
        UNSUCCESSFUL,
    }


    /*Identificacao dos monitores*/
    /**
     * Identificador do monitor <i>ZonaDesembarque</i> no âmbito dos arrays de
     * endereços remotos e portas dos monitores
     *
     * @serialField MON_ZONA_DESEMBARQUE
     */
    public static final int MON_ZONA_DESEMBARQUE = 0;
    /**
     * Identificador do monitor <i>RecolhaBagagem</i> no âmbito dos arrays de
     * endereços remotos e portas dos monitores
     *
     * @serialField MON_RECOLHA_BAGAGEM
     */
    public static final int MON_RECOLHA_BAGAGEM = 1;
    /**
     * Identificador do monitor <i>Porao</i> no âmbito dos arrays de endereços
     * remotos e portas dos monitores
     *
     * @serialField MON_PORAO
     */
    public static final int MON_PORAO = 2;
    /**
     * Identificador do monitor <i>TransferenciaTerminal</i> no âmbito dos
     * arrays de endereços remotos e portas dos monitores
     *
     * @serialField MON_TRANSFERENCIA_TERMINAL
     */
    public static final int MON_TRANSFERENCIA_TERMINAL = 3;
    /**
     * Identificador do monitor <i>Autocarro</i> no âmbito dos arrays de
     * endereços remotos e portas dos monitores
     *
     * @serialField MON_AUTOCARRO
     */
    public static final int MON_AUTOCARRO = 4;
    /**
     * Identificador do monitor <i>TransicaoAeroporto</i> no âmbito dos arrays
     * de endereços remotos e portas dos monitores
     *
     * @serialField MON_TRANSICAO_AEROPORTO
     */
    public static final int MON_TRANSICAO_AEROPORTO = 5;
    /**
     * Identificador do monitor <i>Logging</i> no âmbito dos arrays de endereços
     * remotos e portas dos monitores
     *
     * @serialField MON_LOGGING
     */
    public static final int MON_LOGGING = 6;
    
    /**
     * Endereços dos monitores remotos
     */
    /**
     * Endereços de todos os monitores existentes no âmbito do problema
     * <b>Rapsódia no Aeroporto</b>.
     *
     * Contém 7 elementos:
     * <ul>
     * <li> 0 - ZonaDesembarque
     * <li> 1 - RecolhaBagagem
     * <li> 2 - Porao
     * <li> 3 - TransferenciaTerminal
     * <li> 4 - Autocarro
     * <li> 5 - TransicaoAeroporto
     * <li> 6 - Logging
     * </ul>
     *
     * @serialField hostNames
     */
    public static final String[] hostNames = new String[7];

    /**
     * Portas nas quais os servidores proxy dos monitoes estão à escuta no
     * âmbito do problema <b>Rapsódia no Aeroporto</b>.
     *
     * Contém 7 elementos:
     * <ul>
     * <li> 0 - ZonaDesembarque
     * <li> 1 - RecolhaBagagem
     * <li> 2 - Porao
     * <li> 3 - TransferenciaTerminal
     * <li> 4 - Autocarro
     * <li> 5 - TransicaoAeroporto
     * <li> 6 - Logging
     * </ul>
     */
    public static final int[] portNumber = {22250,
        22251,
        22252,
        22253,
        22254,
        22255,
        22256};

    /**
     * Função encarregue de realizar o parsing do ficheiro xml de configuração da simulação que se irá realizar.
     * <p>
     * Este ficheiro de configuração contém os hostnames das máquinas onde cada monitor está a correr bem como 
     * <ul>
     * <li> o número de voos que se irão realizar,
     * <li> o número de passageiros que vem em cad voo,
     * <li> o número de bagagens máximo que cada passageiro pode carregar,
     * <li> a lotação do autocarro.
     * </ul>
     */
    public static void xmlParser() {
        try {
            File sim = new File("conf.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(sim);
            doc.getDocumentElement().normalize();
            NodeList simulacao = doc.getDocumentElement().getElementsByTagName("Simulation");
            Element siml = (Element) simulacao.item(0);
            NodeList chegadas = siml.getElementsByTagName("voo");
            nChegadas = Integer.parseInt(chegadas.item(0).getTextContent().replaceAll(" ", "").replaceAll("\n", ""));
            NodeList bag = doc.getDocumentElement().getElementsByTagName("bag");
            bagMax = Integer.parseInt(bag.item(0).getTextContent().replaceAll(" ", "").replaceAll("\n", ""));
            NodeList pass = doc.getDocumentElement().getElementsByTagName("pass");
            passMax = Integer.parseInt(pass.item(0).getTextContent().replaceAll(" ", "").replaceAll("\n", ""));
            NodeList lot = doc.getDocumentElement().getElementsByTagName("lotacao");
            lotação = Integer.parseInt(lot.item(0).getTextContent().replaceAll(" ", "").replaceAll("\n", ""));
            String[] monitores = {"ZonaDesembarque", "RecolhaBagagem", "Porao", "TransferenciaTerminal", "Autocarro", "TransicaoAeroporto", "Logging"};
            for (int i = 0; i < 7; i++) {
                NodeList monconf = doc.getDocumentElement().getElementsByTagName(monitores[i]);
                Element monitorConfig = (Element) monconf.item(0);
                NodeList address = monitorConfig.getElementsByTagName("nome");
                hostNames[i] = address.item(0).getTextContent().replaceAll(" ", "").replaceAll("\n", "");
            }
                NodeList monconf = doc.getDocumentElement().getElementsByTagName("Registry");
                Element monitorConfig = (Element) monconf.item(0);
                NodeList address = monitorConfig.getElementsByTagName("nome");
                registryHostname = address.item(0).getTextContent().replaceAll(" ", "").replaceAll("\n", "");
            

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println(nChegadas);
        System.out.println(bagMax);
        System.out.println(passMax);
        System.out.println(lotação);
    }
}

/*
 public static final String[] hostNames = {"l040101-ws1.clients.ua.pt",
 "l040101-ws2.clients.ua.pt",
 "l040101-ws3.clients.ua.pt",
 "l040101-ws4.clients.ua.pt",
 "l040101-ws5.clients.ua.pt",
 "l040101-ws7.clients.ua.pt",
 "l040101-ws8.clients.ua.pt",};
 */
