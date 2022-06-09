package client;

// Importing libraries
import util.Card;
import util.Player;
import util.Requisicao;
import util.Computer;
import java.net.Socket;
import util.Comunicacao;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;

public class Client {
    // Instantiating static variables
    static String msg_setting_pack;
    static String msg_setting_match;
    static Random random = new Random();
    static Player player = new Player();
    static String msg_setting_difficulty;
    static Scanner sc = new Scanner(System.in);

    // ------------------------ Main ------------------------
    public static void main(String[] args) {
        // Instantiating variables
        Socket socket = null;
        Scanner input = null;
        final int PORT = 1234;
        final String IP = "127.0.0.1";
        Comunicacao comunicacao;
        Requisicao requisicao;

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
            // output = new PrintStream(socket.getOutputStream());
            Listen listen = new Listen(input);
            listen.start();

            setting_match(comunicacao);
            start_match(comunicacao);

        } catch (Exception e) {
            System.out.println(" >>> Erro na comunicação");
            System.out.println(e.getMessage());
        }

        // Encerrar conexão
        try {
            input.close();
            // output.close();
            socket.close();
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    // ------------------------ Methods ------------------------
    public static void setting_match(Comunicacao comunicacao) {
        System.out.println("\n >>> Digite o seu nome:");
        System.out.print(" --> ");
        msg_setting_match = sc.nextLine();

        player.setName(msg_setting_match);
        Requisicao requisicao = new Requisicao(14, player);
        comunicacao.enviar(requisicao); // Envia a variável "player" para ser armazenada no Match

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
                    requisicao = new Requisicao(1, 0);
                    comunicacao.enviar(requisicao); // Requisita método "setGame_mode()"
                    setting_pack(comunicacao);
                    break;

                case "2":
                    break;

                case "3":
                    System.out.println("\n\n >>> Volte sempre!"
                            + "\n >>> Desconectado\n\n\n");
                    break;

                default:
                    System.out.println("\n >>> Por favor, digite uma opção válida\n");
                    break;
            }
        } while (!msg_setting_match.equals("3"));
    }

    public static void setting_pack(Comunicacao comunicacao) {
        Requisicao requisicao;

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
                    requisicao = new Requisicao(2);
                    comunicacao.enviar(requisicao); // Requisita método "setMatch_clean_pack()"

                    requisicao = new Requisicao(4);
                    comunicacao.enviar(requisicao); // Requisita método "setMatch_clean_pack()"
                    setting_difficulty(comunicacao);
                    break;

                case "2":
                    requisicao = new Requisicao(3);
                    comunicacao.enviar(requisicao); // Requisita método "setMatch_dirty_pack()"

                    requisicao = new Requisicao(4);
                    comunicacao.enviar(requisicao); // Requisita método "setMatch_clean_pack()"
                    setting_difficulty(comunicacao);
                    break;

                case "3":
                    break;

                default:
                    System.out.println("\n >>> Por favor, digite uma opção válida\n");
                    break;
            }
        } while (!msg_setting_pack.equals("3"));
    }

    public static void setting_difficulty(Comunicacao comunicacao) {
        Requisicao requisicao;

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
                    requisicao = new Requisicao(5, 0.4);
                    comunicacao.enviar(requisicao); // Requisita método "setDifficulty()"
                    msg_setting_difficulty = "5";
                    msg_setting_pack = "3";
                    msg_setting_match = "3";
                    break;

                case "2":
                    requisicao = new Requisicao(5, 0.55);
                    comunicacao.enviar(requisicao); // Requisita método "setDifficulty()"
                    msg_setting_difficulty = "5";
                    msg_setting_pack = "3";
                    msg_setting_match = "3";
                    break;

                case "3":
                    requisicao = new Requisicao(5, 0.75);
                    comunicacao.enviar(requisicao); // Requisita método "setDifficulty()"
                    msg_setting_difficulty = "5";
                    msg_setting_pack = "3";
                    msg_setting_match = "3";
                    break;

                case "4":
                    requisicao = new Requisicao(5, 0.9);
                    comunicacao.enviar(requisicao); // Requisita método "setDifficulty()"
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

    private static void start_match(Comunicacao comunicacao) {
        Requisicao requisicao;
        Computer pc = new Computer();

        do { // Partida até 12
            int j = 0;
            int msg = 1;
            Card mesa = null;
            Card manilha = null;
            Card used_card = new Card();
            ArrayList<Card> used_cards = new ArrayList<>();
            ArrayList<Card> player_hand = new ArrayList<>();
            ArrayList<Card> computer_hand = new ArrayList<>();

            do { // Rodada, melhor de 3
                requisicao = new Requisicao(10);
                comunicacao.enviar(requisicao); // Requisita método "embaralhar()"

                requisicao = new Requisicao(11, player_hand, computer_hand);
                comunicacao.enviar(requisicao); // Requisita método "dar_cartas()"

                requisicao = new Requisicao(15);
                comunicacao.enviar(requisicao); // Requisita método "getPlayer().getHand()"
                player_hand = (ArrayList<Card>) comunicacao.receber().getValor();

                requisicao = new Requisicao(16);
                comunicacao.enviar(requisicao); // Requisita método "getPc().getHand()"
                computer_hand = (ArrayList<Card>) comunicacao.receber().getValor();

                requisicao = new Requisicao(8);
                comunicacao.enviar(requisicao); // Requisita método "getRound_pack()"
                mesa = (Card) comunicacao.receber().getValor(); // Recebe variável "Round_pack"

                requisicao = new Requisicao(7, mesa);
                comunicacao.enviar(requisicao); // Requisita método "setting_manilha()"
                manilha = (Card) comunicacao.receber().getValor(); // Recebe variável "manilha"

                System.out.println("\n >>> " + player.getName() + " vs Computador"
                        + "\n >>> Virou um " + mesa.getSymbol() + " na mesa.");

                do {
                    do {
                        if (msg > 4 || msg == 0) { // verificar isso aqui mano
                            System.out.println("\nPor favor, digite uma opção válida.\n");
                        }

                        System.out.println("\n >>> A manilha é " + manilha.getSymbol()
                                + "\n >>> Sua mão:"
                                + "\n 1 - " + player_hand.get(0).getSymbol() + " "
                                + player_hand.get(0).getSuit().getName()
                                + "\n 2 - " + player_hand.get(1).getSymbol() + " "
                                + player_hand.get(1).getSuit().getName()
                                + "\n 3 - " + player_hand.get(2).getSymbol() + " "
                                + player_hand.get(2).getSuit().getName()
                                + "\n 4 - Pedir Truco\n"
                                + "\n >>> Escolha uma opção: ");
                        System.out.print(" --> ");
                        msg = sc.nextInt();

                    } while (msg > 4);

                    used_card = player_hand.get(msg - 1);

                    if (used_cards.contains(used_card)) {
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
                        requisicao = new Requisicao(12, player.getUsed_card());
                        comunicacao.enviar(requisicao); // Requisita método "pc_play()"

                        try {
                            clear_console();
                        } catch (InterruptedException | IOException e) {
                            System.out.println("\n\n >>> Erro inesperado");
                            System.out.println(e.getMessage());
                        }
                        
                        requisicao = new Requisicao(9);
                        comunicacao.enviar(requisicao); // Requisita variável "pc"
                        pc = (Computer) comunicacao.receber().getValor(); // Recebe variável "pc"

                        System.out.println("\n\n >>> " + player.getName() + " jogou " + used_card.getSymbol() + " "
                                + used_card.getSuit().getName());
                        System.out.println(" >>> Computador jogou " + pc.getUsed_card().getSymbol() + " "
                                + pc.getUsed_card().getSuit().getName());

                        requisicao = new Requisicao(13, player.getUsed_card(), pc.getUsed_card(), manilha);
                        comunicacao.enviar(requisicao);
                        int h = (int) comunicacao.receber().getValor();
                        if (h == 1) {
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

                j++;
            } while (j < 2 || (player.getRound_score() < 2 && pc.getRound_score() < 2));

            // Limpando a mão para o próximo round --> Partida (vai até 12 pontos) -->
            // Possui no máximo 12 round ao todo --> Dentro de cada round tem 3 outros
            // rounds
            player_hand.clear();
            computer_hand.clear();
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