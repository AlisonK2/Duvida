package client;

// Importing libraries
import util.Card;
import util.Hand;
import util.Player;
import util.Result;
import java.net.Socket;
import util.Comunicacao;
import util.InitialData;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;

public class Client {
    // Instantiating static variables
    static int game_mode;
    static int pack_type;
    static Double bot_difficulty;
    static Comunicacao comunicacao;
    static Random random = new Random();
    static Player player = new Player();
    static Player computer = new Player();
    static Scanner sc = new Scanner(System.in);

    // ------------------------ Main ------------------------
    public static void main(String[] args) {
        // Instantiating variables
        Socket socket;
        Scanner input;
        final int PORT = 1234;
        final String IP = "127.0.0.1";

        // Solicitar conexão
        try {
            socket = new Socket(IP, PORT);
            comunicacao = new Comunicacao(socket);
        } catch (Exception e) {
            System.out.println("\n >>> Não foi possível conectar ao servidor.\n\n");
            System.out.println(e.getMessage());
            return;
        }

        // Comunicação
        try {
            input = new Scanner(socket.getInputStream());
            Listen listen = new Listen(input);
            listen.start();
            int replay = 1;

            setting_name();

            do {
                // Implementar caso for segundo jogador para não configurar a partida, pois o jogador um já configurou
                setting_match();
                setting_pack();

                if (game_mode == 1) {
                    setting_difficulty();
                }    

                comunicacao.enviar(game_mode);


                // mensagem do próprio cliente opcional: (single: ok, dupla: aguarde o próximo jogador)
                
                comunicacao.enviar(new InitialData(player.getName(), game_mode, pack_type, bot_difficulty));
                
                start_match();

                do {
                    if (replay != 1 && replay != 2) {
                        System.out.println("\n >>> Por favor, digite uma opção válida. \n\n");
                    }

                    System.out.println("\n >>> Deseja jogar novamente:"
                                      + "\n 1 - Sim"
                                      + "\n 2 - Não \n");
                    System.out.print(" --> ");
                    replay = sc.nextInt();

                } while (replay != 1 && replay != 2);

            } while (replay == 1);

        } catch (Exception e) {
            System.out.println(" >>> Erro na comunicação");
            System.out.println(e.getMessage());
        }

        // Encerrar conexão
        try {
            // input.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ------------------------ Methods ------------------------
    public static void setting_name() {
        System.out.println("\n >>> Digite o seu nome:");
        System.out.print(" --> ");
        player.setName(sc.nextLine());

        try {
            clear_console();
        } catch (InterruptedException | IOException e) {
            System.out.println("\n\n >>> Erro inesperado\n");
            System.out.println(e.getMessage());
        }
    }

    public static void setting_match() {
        do {
            System.out.println("\n Bem-vindo/a, " + player.getName() + ", ao jogo de TRUCO\n"
                    + " 1 - Modo de jogo solo\n"
                    + " 2 - Aguardar mais jogadores\n"
                    + "\n >>> Escolha uma opção:");

            System.out.print(" --> ");
            game_mode = sc.nextInt();

            try {
                clear_console();
            } catch (InterruptedException | IOException e) {
                System.out.println("\n\n >>> Erro inesperado\n");
                System.out.println(e.getMessage());
            }

            switch (game_mode) {
                case 1:
                case 2:
                    break;
                default:
                    game_mode = 0;
                    System.out.println("\n\n >>> Por favor, digite uma opção válida\n\n");
                    break;
            }
        } while (game_mode == 0);
    }

    public static void setting_pack() {
        do {
            System.out.println("\n Tipos de baralhos: \n"
                    + " 1 - Limpo (3, 2, A, K, J, Q)\n"
                    + " 2 - Sujo  (3, 2, A, K, J, Q, 7, 6, 5, 4)\n"
                    + "\n >>> Deseja jogar com qual baralho: ");

            System.out.print(" --> ");
            pack_type = sc.nextInt();

            try {
                clear_console();
            } catch (InterruptedException | IOException e) {
                System.out.println("\n\n >>> Erro inesperado");
                System.out.println(e.getMessage());
            }

            switch (pack_type) {
                case 1:
                case 2:
                    break;
                default:
                    pack_type = 0;
                    System.out.println("\n >>> Por favor, digite uma opção válida\n");
                    break;
            }
        } while (pack_type == 0);
    }

    public static void setting_difficulty() {
        do {
            System.out.println("\n Dificuldades: \n"
                    + " 1 - Fácil\n"
                    + " 2 - Normal\n"
                    + " 3 - Díficil\n"
                    + " 4 - Profissional\n"
                    + "\n >>> Em qual nível deseja jogar: ");

            System.out.print(" --> ");
            bot_difficulty = sc.nextDouble();

            try {
                clear_console();
            } catch (InterruptedException | IOException e) {
                System.out.println("\n\n >>> Erro inesperado");
                System.out.println(e.getMessage());
            }

            switch (bot_difficulty.intValue()) {
                case 1:
                    bot_difficulty = 0.10;
                    break;

                case 2:
                    bot_difficulty = 0.20;
                    break;

                case 3:
                    bot_difficulty = 0.50;
                    break;

                case 4:
                    bot_difficulty = 0.60;                
                    break;
                default:
                    bot_difficulty = 0.0;
                    System.out.println("\n >>> Por favor, digite uma opção válida\n");
                    break;
            }
        } while (bot_difficulty == 0.0);
    }

    private static void start_match() {
        Player pc = new Player();

        do { // Partida até 12
            int mao = 0;
            int msg = 1;
            String player2name;
            Card mesa = new Card();
            Card manilha = new Card();
            Card used_card = new Card();
            ArrayList<Card> used_cards = new ArrayList<>();

            do {  // Rodada, possui 3 mãos ou até alguém completar 2 pontos
                 // Envia requisição para: Emabaralhar, dar as cartas, dizer a carta 
                // que vira na mesa e consequentemente definir a manilha
                Hand hand = (Hand) comunicacao.receber();

                // Váriaveis recebidas do servidor sendo configuradas do lado do Client 
                mesa = hand.getMesa();
                manilha = hand.getManilha();
                player.setHand(hand.getHand()); 
                player2name = hand.getPlayer2name();

                if (game_mode == 1){
                    System.out.println("\n >>> " + player.getName() + " vs Computador"
                                     + "\n >>> Virou um " + mesa.getSymbol() + " na mesa.");
                } else {
                    System.out.println("\n >>> " + player.getName() + " " + player2name
                                     + "\n >>> Virou um " + mesa.getSymbol() + " na mesa.");
                }

                do { // Loop para garantir que o jogador não jogará uma carta que já usou ou uma opção inválida
                    
                    System.out.println("\n >>> A manilha é " + manilha.getSymbol()
                                     + "\n >>> Sua mão:"
                                     + "\n 1 - " + player.getHand().get(0).getSymbol() + " " + player.getHand().get(0).getSuit().getName()
                                     + "\n 2 - " + player.getHand().get(1).getSymbol() + " " + player.getHand().get(1).getSuit().getName()
                                     + "\n 3 - " + player.getHand().get(2).getSymbol() + " " + player.getHand().get(2).getSuit().getName()
                                     + "\n 4 - Pedir Truco\n"
                                     + "\n >>> Escolha uma opção: ");
                    System.out.print(" --> ");
                    msg = sc.nextInt();

                    used_card = player.getHand().get(msg - 1);

                    if (used_cards.contains(used_card)) {
                        try {
                            clear_console();
                        } catch (InterruptedException | IOException e) {
                            System.out.println("\n\n >>> Erro inesperado");
                            System.out.println(e.getMessage());
                        }
                        System.out.println("\n >>> Você já usou esta carta, jogue outra, por favor. \n");
                    }

                } while (used_cards.contains(used_card)); // Enquanto ele tentar jogar uma carda que ja jogou

                switch (msg) {
                    case 1:
                    case 2:
                    case 3:
                        used_cards.add(used_card);
                        player.setUsed_card(used_card);

                        comunicacao.enviar(new Result(player, computer, 0));
                        Result result = (Result) comunicacao.receber();
                        pc = result.getPlayer2_or_computer();

                        try {
                            clear_console();
                        } catch (InterruptedException | IOException e) {
                            System.out.println("\n\n >>> Erro inesperado");
                            System.out.println(e.getMessage());
                        }

                        System.out.println("\n\n >>> " + player.getName() + " jogou " + used_card.getSymbol() + " "
                                + used_card.getSuit().getName());

                        if (game_mode == 1) {
                            System.out.println(" >>> Computador jogou " + pc.getUsed_card().getSymbol() + " " + pc.getUsed_card().getSuit().getName());
                        } else {
                            System.out.println(" >>> " + player2name + " " + pc.getUsed_card().getSymbol() + " " + pc.getUsed_card().getSuit().getName());
                        }

                        if (result.getWinner() == 0) {
                            player.setRound_score(player.getRound_score() + 1);
                            System.out.println("\n\n\n >>> " + player.getName() + " levou essa !"
                                    + "\n >>> Pontos da rodadas: " + player.getRound_score()
                                    + "\n >>> Pontos da partida: " + player.getMatch_score()
                                    + "\n\n >>> Computador"
                                    + "\n >>> Pontos da rodadas: " + pc.getRound_score()
                                    + "\n >>> Pontos da partida: " + pc.getMatch_score() + "\n\n\n");
                        } else {
                            pc.setRound_score(pc.getRound_score() + 1);
                            System.out.println("\n\n\n >>> O computador levou essa !"
                                    + "\n >>> Pontos da rodadas: " + pc.getRound_score()
                                    + "\n >>> Pontos da partida: " + pc.getMatch_score()
                                    + "\n\n >>> " + player.getName()
                                    + "\n >>> Pontos da rodadas: " + player.getRound_score()
                                    + "\n >>> Pontos da partida: " + player.getMatch_score() + "\n\n\n");
                        }
                        break;
                    case 0:
                    case 4:
                        break;
                    default:
                        System.out.println("\n >>> Por favor, digite uma opção válida\n");
                        break;
                }

                if (player.getRound_score() == 2) {
                    player.setMatch_score(player.getMatch_score() + 1);

                    System.out.println("\n\n >>> " + player.getName() + " venceu essa rodada!"
                            + "\n >>> Pontos da partida: " + player.getMatch_score()
                            + "\n\n >>> Computador: "
                            + "\n >>> Pontos da partida:" + pc.getMatch_score() + "\n");
                } else if (pc.getRound_score() == 2) {
                    pc.setMatch_score(pc.getMatch_score() + 1);

                    System.out.println("\n\n >>> Computador venceu essa rodada!"
                            + "\n >>> Pontos da partida: " + pc.getMatch_score()
                            + "\n\n >>> " + player.getName() + ":"
                            + "\n >>> Pontos da partida:" + player.getMatch_score() + "\n");
                }

                mao++;
            } while (mao < 2 || (player.getRound_score() < 2 && pc.getRound_score() < 2));

            // Limpando a mão para o próximo round --> Partida (vai até 12 pontos) -->
            // Possui no máximo 12 round ao todo --> Dentro de cada round tem 3 outros
            // rounds
            player.getHand().clear();
            computer.getHand().clear();
            used_cards.clear();
            player.setRound_score(0);
            pc.setRound_score(0);

        } while (player.getMatch_score() != 12 && pc.getMatch_score() != 12); // jogo vai até alguem atingir de 12
                                                                              // pontos

        if (player.getMatch_score() > pc.getMatch_score()) {
            System.out.println("\n\n >>> " + player.getName().toUpperCase() + "VENCEU O JOGO! VOCÊ É FODA"
                    + "\n >>> Pontos da partida: " + player.getMatch_score()
                    + "\n\n >>> Computador: "
                    + "\n >>> Pontos da partida:" + pc.getMatch_score());
        } else if (pc.getMatch_score() > player.getMatch_score()) {

            System.out.println("\n\n >>> GAME OVER PRA VOCÊ MEU PARCEIRO !!!"
                    + "\n >>> O COMPUTADOR VENCEU O JOGO! VIVA ÀS MÁQUINAS !!!"
                    + "\n >>> Pontos da partida: " + pc.getMatch_score()
                    + "\n\n >>> " + player.getName() + ":"
                    + "\n >>> Pontos da partida:" + player.getMatch_score());
        }
    }

    public static void clear_console() throws InterruptedException, IOException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }
}