package client;

// Importing libraries
import util.Card;
import util.Player;
import server.Server;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintStream;

public class Client {
    // Instantiating static variables
    static String msg_setting_pack;
    static String msg_setting_match;
    static Random random = new Random();
    static Player player = new Player();
    static String msg_setting_difficulty;
    static Scanner sc = new Scanner(System.in);
    static Server server = new Server();
    
    // ------------------------ Main ------------------------
    public static void main(String[] args) {
        // Instantiating variables
        final int PORT = 1234;
        Socket socket = null;
        Scanner input = null;
        PrintStream output = null;
        final String IP = "127.0.0.1";
        
        // Solicitar conexão
        try {
            socket = new Socket(IP, PORT);
        } catch (Exception e) {
            System.out.println("\n >>> Não foi possível conectar ao servidor.\n\n");
            System.out.println(e.getMessage());
            return;
        }
        
        // Comunicação
        try {
            input = new Scanner(socket.getInputStream());
            output = new PrintStream(socket.getOutputStream());
            Listen listen = new Listen(input);
            listen.start();
            
            setting_match();
            start_match();
                      
        } catch (Exception e) {
            System.out.println(" >>> Erro na comunicação");
            System.out.println(e.getMessage());
        }
        
        // Encerrar conexão
        try {
            input.close();
            output.close();
            socket.close();         
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    // ------------------------ Methods ------------------------
    public static void setting_match() {
        System.out.println("\n >>> Digite o seu nome:");
        System.out.print(" --> ");
        msg_setting_match = sc.nextLine();

        player.setName(msg_setting_match);

        try {
            clear_console();
        } catch (InterruptedException | IOException e) {
            System.out.println("\n\n >>> Erro inesperado");
            System.out.println(e.getMessage());
        }

        do {
            System.out.println("\n Bem-vindo/a, " + player.getName() + ", ao jogo de TRUCO\n" 
                            + " 1 - Modo de jogo solo\n"
                            + " 2 - Aguardar mais jogadores\n"
                            + " 3 - Sair\n"
                            + "\n >>> Escolha uma opção:");
            
            System.out.print(" --> ");
            msg_setting_match = sc.nextLine();

            try {
                clear_console();
            } catch (InterruptedException | IOException e) {
                System.out.println("\n\n >>> Erro inesperado\n");
                System.out.println(e.getMessage());
            }

            switch (msg_setting_match) {
                case "1":
                    server.setGame_mode(0); // Game mode igual a 0, jogador Vs Computador
                    setting_pack();
                    break;

                case "2":
                    break;

                case "3":
                    System.out.println("\n\n >>> Volte sempre!"
                                       + "\n >>> Desconectado");
                    break;
    
                default:
                    System.out.println("\n >>> Por favor, digite uma opção válida\n");
                    break;
            }
        } while (!msg_setting_match.equals("3"));
    }

    public static void setting_pack() {
        do {
            System.out.println("\n Tipos de baralhos: \n"
                             + " 1 - Limpo (3, 2, A, K, J, Q)\n"
                             + " 2 - Sujo  (3, 2, A, K, J, Q, 7, 6, 5, 4)\n"
                             + " 3 - Voltar ao menu anterior\n"
                             + "\n >>> Deseja jogar com qual baralho: ");

            System.out.print(" --> ");
            msg_setting_pack = sc.nextLine();

            try {
                clear_console();
            } catch (InterruptedException | IOException e) {
                System.out.println("\n\n >>> Erro inesperado");
                System.out.println(e.getMessage());
            }
    
            switch (msg_setting_pack) {
                case "1":
                    server.setMatch_clean_pack();
                    server.setRound_pack();
                    setting_difficulty();
                    break;
            
                    case "2":
                    server.setMatch_dirty_pack();
                    server.setRound_pack();
                    setting_difficulty();
                    break;

                case "3":
                    break;

                default:
                    System.out.println("\n >>> Por favor, digite uma opção válida\n");
                    break;
            }
        } while (!msg_setting_pack.equals("3"));
    }

    public static void setting_difficulty() {
        do {
            System.out.println("\n Dificuldades: \n"
                             + " 1 - Fácil\n"
                             + " 2 - Normal\n"
                             + " 3 - Díficil\n"
                             + " 4 - Profissional\n"
                             + " 5 - Voltar ao menu anterior\n"
                             + "\n >>> Em qual nível deseja jogar: ");
            
            System.out.print(" --> ");
            msg_setting_difficulty = sc.nextLine();

            try {
                clear_console();
            } catch (InterruptedException | IOException e) {
                System.out.println("\n\n >>> Erro inesperado");
                System.out.println(e.getMessage());
            }
    
            switch (msg_setting_difficulty) {
                case "1":
                    server.setDifficulty(0.40);
                    msg_setting_difficulty = "5";
                    msg_setting_pack = "3";
                    msg_setting_match = "3";
                    break;
            
                case "2":
                    server.setDifficulty(0.55);
                    msg_setting_difficulty = "5";
                    msg_setting_pack = "3";
                    msg_setting_match = "3";
                    break;

                case "3":
                    server.setDifficulty(0.75);
                    msg_setting_difficulty = "5";
                    msg_setting_pack = "3";
                    msg_setting_match = "3";
                    break;

                case "4":
                    server.setDifficulty(0.99);
                    msg_setting_difficulty = "5";
                    msg_setting_pack = "3";
                    msg_setting_match = "3";
                    break;

                case "5":
                    break;

                default:
                    System.out.println("\n >>> Por favor, digite uma opção válida\n");
                    break;
            }
        } while (!msg_setting_difficulty.equals("5"));
    }

    private static void start_match() {
        do { // Partida até 12
            int j = 0;
            int msg = 0;
            Card mesa = null;
            Card manilha = null;
            Card used_card = new Card();
            ArrayList<Card> used_cards = new ArrayList<>();
            ArrayList<Card> player_hand = new ArrayList<>();
            ArrayList<Card> computer_hand = new ArrayList<>();
            
    
            do { // Rodada, melhor de 3
                server.embaralhar();
                server.dar_cartas(player_hand, computer_hand);  

                mesa = server.getRound_pack().get(6);
                manilha = server.setting_manilha(mesa);                
                player.setHand(player_hand);
                server.setPc_hand(computer_hand);

                System.out.println("\n >>> " + player.getName() + " vs Computador"
                                 + "\n >>> Virou um " + mesa.getSymbol() + " na mesa.");

                do { 
                    do {
                        if (msg > 4 || msg == 0) { //verificar isso aqui mano
                            System.out.println("\nPor favor, digite uma opção válida.\n");
                        } 

                        System.out.println("\n >>> A manilha é " + manilha.getSymbol()
                                         + "\n >>> Sua mão:"
                                         + "\n 1 - " + player_hand.get(0).getSymbol() + " " + player_hand.get(0).getSuit().getName() 
                                         + "\n 2 - " + player_hand.get(1).getSymbol() + " " + player_hand.get(1).getSuit().getName()
                                         + "\n 3 - " + player_hand.get(2).getSymbol() + " " + player_hand.get(2).getSuit().getName()
                                         + "\n 4 - Pedir Truco\n"
                                         + "\n >>> Escolha uma opção: ");  
                        System.out.print(" --> ");
                        msg = sc.nextInt();

                    } while (msg > 4);
                    
                    used_card = player_hand.get(msg - 1);
                    
                    if (used_cards.contains(used_card)){
                        try {
                            clear_console();
                        } catch (InterruptedException | IOException e) {
                            System.out.println("\n\n >>> Erro inesperado");
                            System.out.println(e.getMessage());
                        }  
                        System.out.println("\n >>> Você já usou esta carta, jogue outra, por favor. \n");  
                    }

                } while (used_cards.contains(used_card) || msg > 4); // Enquanto ele tentar jogar uma carda que ja jogou
            
                switch (msg) {
                    case 1:
                    case 2:
                    case 3:
                        used_cards.add(used_card);
                        player.setUsed_card(used_card);
                        server.pc_play(player.getUsed_card());

                        try {
                            clear_console();
                        } catch (InterruptedException | IOException e) {
                            System.out.println("\n\n >>> Erro inesperado");
                            System.out.println(e.getMessage());
                        } 

                        System.out.println("\n\n >>> " + player.getName() + " jogou " + used_card.getSymbol() + " " + used_card.getSuit().getName());
                        System.out.println(" >>> Computador jogou " + server.getPc().getUsed_card().getSymbol() + " " + server.getPc().getUsed_card().getSuit().getName());

                        int h = server.who_is_the_winner(player.getUsed_card(), server.getPc().getUsed_card(), manilha);   
                        if (h == 1){
                            player.setRound_score(player.getRound_score() + 1);
                            System.out.println("\n\n\n >>> " + player.getName() + " levou essa !" 
                                                 + "\n >>> Pontos da rodadas: " + player.getRound_score()
                                                 + "\n >>> Pontos da partida: " + player.getMatch_score() 
                                               + "\n\n >>> Computador" 
                                                 + "\n >>> Pontos da rodadas: " + server.getPc().getRound_score()
                                                 + "\n >>> Pontos da partida: " + server.getPc().getMatch_score() + "\n\n\n");
                        } else {
                            server.getPc().setRound_score(server.getPc().getRound_score() + 1);
                            System.out.println("\n\n\n >>> O computador levou essa !" 
                                                 + "\n >>> Pontos da rodadas: " + server.getPc().getRound_score()
                                                 + "\n >>> Pontos da partida: " + server.getPc().getMatch_score() 
                                               + "\n\n >>> " + player.getName()
                                                 + "\n >>> Pontos da rodadas: " + player.getRound_score()
                                                 + "\n >>> Pontos da partida: " + player.getMatch_score() + "\n\n\n");
                        }
                        break;
                    
                    case 4:
                        
                        break;
                    
                    default:
                        System.out.println("\n >>> Por favor, digite uma opção válida\n");
                        break;
                }
                
                if (player.getRound_score() == 2){
                    player.setMatch_score(player.getMatch_score() + 1);

                    System.out.println("\n\n >>> " + player.getName() + " venceu essa rodada!"
                                       + "\n >>> Pontos da partida: " + player.getMatch_score()
                                     + "\n\n >>> Computador: "
                                       + "\n >>> Pontos da partida:" + server.getPc().getMatch_score() + "\n");
                } else if (server.getPc().getRound_score() == 2){
                    server.getPc().setMatch_score(server.getPc().getMatch_score() + 1);

                    System.out.println("\n\n >>> Computador venceu essa rodada!"
                                       + "\n >>> Pontos da partida: " + server.getPc().getMatch_score()
                                     + "\n\n >>> " + player.getName() + ":"
                                       + "\n >>> Pontos da partida:" + player.getMatch_score() + "\n");
                }

                j++; 
            } while (j < 2 || (player.getRound_score() < 2 && server.getPc().getRound_score() < 2)); 

            // Limpando a mão para o próximo round --> Partida (vai até 12 pontos) --> Possui no máximo 12 round ao todo --> Dentro de cada round tem 3 outros rounds 
            player_hand.clear();
            computer_hand.clear();
            used_cards.clear();
            player.setRound_score(0);
            server.getPc().setRound_score(0);

        } while (player.getMatch_score() != 12 && server.getPc().getMatch_score() != 12); // jogo vai até alguem atingir de 12 pontos
        
        if (player.getMatch_score() > server.getPc().getMatch_score()){
            System.out.println("\n\n >>> " + player.getName().toUpperCase() + "VENCEU O JOGO! VOCÊ É FODA"
                               + "\n >>> Pontos da partida: " + player.getMatch_score()
                             + "\n\n >>> Computador: "
                               + "\n >>> Pontos da partida:" + server.getPc().getMatch_score());
        } else if (server.getPc().getMatch_score() > player.getMatch_score()){

            System.out.println("\n\n >>> GAME OVER PRA VOCÊ MEU PARCEIRO !!!"
                               + "\n >>> O COMPUTADOR VENCEU O JOGO! VIVA ÀS MÁQUINAS !!!"
                               + "\n >>> Pontos da partida: " + server.getPc().getMatch_score()
                             + "\n\n >>> " + player.getName() + ":"
                               + "\n >>> Pontos da partida:" + player.getMatch_score());
        }
    }

    public static void clear_console() throws InterruptedException, IOException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }
}