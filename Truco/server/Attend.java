package server;

// Importing libraries
import util.Match;
import util.Result;
import java.net.Socket;
import util.Comunicacao;
import util.InitialData;

import java.util.ArrayList;

public class Attend extends Thread {
    // Instantiating variables
    Comunicacao comunicacao;
    Comunicacao comunicacao2;
    Match match;
    private Socket clientSocket;
    private Socket client2Socket;
    private ArrayList<Attend> threads;

    public Attend(Socket clientSocket, ArrayList<Attend> threads) {
        this.threads = threads;
        this.clientSocket = clientSocket;
        comunicacao = new Comunicacao(clientSocket);
        match = new Match();
    }

    // Comunicação
    @Override
    public void run() {
        try {
            // ------------------------ Receber escolhas do jogador ------------------------
            InitialData initialData = (InitialData) comunicacao.receber();
            match.getPlayer1().setName(initialData.getName());
            match.setGame_mode(initialData.getGame_mode());
            match.setMatch_pack(initialData.getPack_type());    
            match.setDifficulty(initialData.getBot_difficulty());

            // Caso um segundo jogador tenha entrado ele envia somente o nome
            if (client2Socket != null) {
                match.getPlayer2().setName(initialData.getName());
            } 

            // ------------------------ Enviar cartas da rodada para o jogador ------------------------
            comunicacao.enviar(match.start_match());
            
            if (client2Socket == null) {
               singleplayer();
            } else {
                multiplayer();
            }
                
        } catch (Exception e) {
            System.out.println(" >>> Erro na comunicação");
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Attend> getThreads() {
        return threads;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
    
    public void addJogador(Socket sc) {
        client2Socket = sc;
        comunicacao2 = new Comunicacao(client2Socket);
    }

    public void singleplayer() {
        int winner; 
        Result result = (Result) comunicacao.receber();

        match.Computer_play(result.getPlayer().getUsed_card(), match.getDifficulty());
        winner = match.who_is_the_winner(result.getPlayer().getUsed_card(), match.getComputer().getUsed_card(), match.getManilha());
        comunicacao.enviar(new Result(result.getPlayer(), match.getComputer(), winner));
    }

    public void multiplayer() {
        // verificar vencedor
        // enviar resposta jogador 1
        // enviar resposta jogador 2
        int winner;
        Result result = (Result) comunicacao.receber();
        Result result2 = (Result) comunicacao2.receber();

        winner = match.who_is_the_winner(result.getPlayer().getUsed_card(),result2.getPlayer().getUsed_card(), match.getManilha());
        comunicacao.enviar(new Result(result.getPlayer(), match.getPlayer2(), winner));
        comunicacao2.enviar(new Result(match.getPlayer2(), result.getPlayer(), winner));
    }
}