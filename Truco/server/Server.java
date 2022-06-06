package server;

// Importing libraries
import util.Card;
import util.Match;
import util.Computer;
import java.net.Socket;
import java.util.ArrayList;
import java.net.ServerSocket;

public class Server { 
    // Instantiating variables
    Match match = new Match();
    Computer pc = new Computer();

    // ------------------------ Main ------------------------
    public static void main(String[] args) {
        // Instantiating  variables
        final int PORT = 1234;
        ServerSocket serverSocket;
        ArrayList<Attend> threads;
        Socket clientSocket = null;

        // Criar socket
        try {
            serverSocket = new ServerSocket(PORT);
            threads = new ArrayList<>();
        } catch (Exception e) {
            System.out.println(" >>> Porta " + PORT + " indisponível.");
            System.out.println(e.getMessage());
            return;
        }

        // Esperar conexão
        try {
            do {
                System.out.println(" >>> Aguardando conexão...");
                clientSocket = serverSocket.accept();
                Attend attend = new Attend(clientSocket, threads);
                attend.start();
                threads.add(attend);
            } while (true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Encerrar conexão
        try {
            clientSocket.close();
            serverSocket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    // ------------------------ Getters ------------------------
    public int getGame_mode() {
        return match.getGame_mode();}
        
    public Double getDifficulty() {
        return match.getDifficulty();}

    public ArrayList<Card> getMatch_pack() {
        return match.getMatch_pack();}
        
    public ArrayList<Card> getRound_pack() {
        return match.getRound_pack();}

    public Computer getPc() {
        return pc;}

    // ------------------------ Setters ------------------------
    public void setGame_mode(int i) {
        match.setGame_mode(i);}

    public void setDifficulty(double d) {
        match.setDifficulty(d);}
    
    public void setMatch_clean_pack() {
        match.setMatch_clean_pack();} 

    public void setMatch_dirty_pack() {
        match.setMatch_dirty_pack();}

    public void setRound_pack() {
        match.setRound_pack();}

    public void setPc_hand (ArrayList<Card> computer_hand) {
        pc.setHand(computer_hand);}

    // ------------------------ Methods ------------------------
    public void embaralhar() {
        match.embaralhar();}

    public void dar_cartas(ArrayList<Card> player_hand, ArrayList<Card> computer_hand) {
        match.dar_cartas(player_hand, computer_hand);}

    public Card setting_manilha(Card mesa) {
        return match.setting_manilha(mesa);}

    public int who_is_the_winner(Card used_card, Card used_card2, Card manilha) {
        return match.who_is_the_winner(used_card, used_card2, manilha);}
    
    public void pc_play(Card card) {
        pc.play(card, match.getDifficulty());}
}