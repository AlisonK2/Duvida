package server;

// Importing libraries
import util.Hand;
import util.Match;
import util.Result;
import util.Threads;
import java.net.Socket;
import util.Comunicacao;
import util.InitialData;

import java.util.ArrayList;

public class Attend extends Thread {
    // Instantiating variables
    Comunicacao comunicacao;
    Match match = new Match();
    private Socket clientSocket;
    private ArrayList<Attend> threads;

    public Attend(Socket clientSocket, ArrayList<Attend> threads) {
        this.threads = threads;
        this.clientSocket = clientSocket;
        comunicacao = new Comunicacao(clientSocket);
    }

    // Comunicação
    @Override
    public void run() {
        while (true) { // FAZER
            try {
                
                // Enviar variável threads
                if (comunicacao.receber().getClass() == Threads.class){
                    comunicacao.enviar(getThreads());

                // Receber escolhas do jogador
                } else if (comunicacao.receber().getClass() == InitialData.class){
                    InitialData initialData = (InitialData) comunicacao.receber();

                    match.getPlayer().setName(initialData.getName());
                    match.setGame_mode(initialData.getGame_mode());
                    match.setMatch_pack(initialData.getPack_type());    
                    match.setDifficulty(initialData.getBot_difficulty());

                // Enviar cartas da rodada para o jogador
                } else if (comunicacao.receber().getClass() == Hand.class){
                    comunicacao.enviar(match.start_match());
                    
                // Enviar resultado da partida pro jogador
                } else if (comunicacao.receber().getClass() == Result.class){
                    Result result = (Result) comunicacao.receber();
                    match.getPc().play(result.getPlayer().getUsed_card(), match.getDifficulty());

                    // winner = 0, jogador 1. winner = 1, jogador 2 ou computador.
                    int winner = match.who_is_the_winner(result.getPlayer().getUsed_card(), match.getPc().getUsed_card(), match.getManilha());
                    
                    // Enviar player e computer--------------------------------------------------------------------------
                    comunicacao.enviar(new Result(result.getPlayer(), match.getPc(), winner));
                    
                } 
            } catch (Exception e) {
                System.out.println(" >>> Erro na comunicação");
                System.out.println(e.getMessage());
                break;
            }
        }
    }

    public ArrayList<Attend> getThreads() {
        return threads;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}