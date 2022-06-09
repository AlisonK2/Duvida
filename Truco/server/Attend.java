package server;

// Importing libraries
import util.Card;
import util.Match;
import util.Player;
import util.Computer;
import util.Requisicao;
import java.net.Socket;
import util.Comunicacao;
import java.util.ArrayList;

public class Attend extends Thread {
    // Instantiating variables
    Comunicacao comunicacao;
    Match match = new Match();
    private Socket clientSocket;
    Computer pc = new Computer();
    private ArrayList<Attend> threads;

    public Attend(Socket clientSocket, ArrayList<Attend> threads) {
        this.threads = threads;
        this.clientSocket = clientSocket;
        comunicacao = new Comunicacao(clientSocket);
    }

    // Comunicação
    @Override
    public void run() {
        while (true) { // TODO: ALTERAR para encerrar de acordo com o jogo
            try {
                Requisicao requisicao = comunicacao.receber();

                switch ((int) requisicao.getId_Requisicao()) {
                    // ------------------------ Setters ------------------------
                    case 1:
                        match.setGame_mode((int) requisicao.getValor());
                        break;
                    case 2:
                    case 3:
                        if (requisicao.getId_Requisicao() == 2) {
                            match.setMatch_clean_pack();

                        } else if ((int) requisicao.getId_Requisicao() == 3) {
                            match.setMatch_dirty_pack();
                        }
                        break;
                    case 4:
                        match.setRound_pack();
                        break;
                    case 5:
                        match.setDifficulty((Double) requisicao.getValor());
                        break;
                    case 6:
                        pc.setHand((ArrayList<Card>) requisicao.getValor());
                        break;

                    // ------------------------ Getters ------------------------
                    case 7:
                        comunicacao.enviar(new Requisicao(7, match.setting_manilha((Card) requisicao.getValor())));
                        break;
                    case 8:
                        comunicacao.enviar(new Requisicao(8, match.getRound_pack().get(6)));
                        break;
                    case 9:
                        comunicacao.enviar(new Requisicao(9, pc));
                        break;
                    case 15:
                    case 16:
                        if ((int) requisicao.getId_Requisicao() == 15) {
                            comunicacao.enviar(new Requisicao(15, match.getPlayer().getHand()));

                        } else if ((int) requisicao.getId_Requisicao() == 16) {
                            comunicacao.enviar(new Requisicao(16, match.getPc().getHand()));
                        }
                        break;

                    // ------------------------ Methods ------------------------
                    case 10:
                        match.embaralhar();
                        break;
                    case 11:
                        ArrayList<Card> player_hand = (ArrayList<Card>) requisicao.getValor();
                        ArrayList<Card> computer_hand = (ArrayList<Card>) requisicao.getValor2();
                        match.dar_cartas(player_hand, computer_hand);
                        break;
                    case 12:
                        pc.play((Card) requisicao.getValor(), match.getDifficulty());
                        break;
                    case 13:
                        Card used_card = (Card) requisicao.getValor();
                        Card used_card2 = (Card) requisicao.getValor2();
                        Card manilha = (Card) requisicao.getValor3();
                        comunicacao.enviar(new Requisicao(13, match.who_is_the_winner(used_card, used_card2, manilha)));
                        break;
                    case 14:
                        match.setPlayer((Player) requisicao.getValor());
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println(" >>> Erro na comunicação");
                System.out.println(e.getMessage());
                break;
            }

        }
    }

    // ------------------------ Getters ------------------------
    public int getGame_mode() {
        return match.getGame_mode();
    }

    public Double getDifficulty() {
        return match.getDifficulty();
    }

    public ArrayList<Card> getMatch_pack() {
        return match.getMatch_pack();
    }

    // ------------------------ Setters ------------------------

    public void setRound_pack() {
        match.setRound_pack();
    }

    // ------------------------ Methods ------------------------
    public void pc_play(Card card) {
        pc.play(card, match.getDifficulty());
    }
}