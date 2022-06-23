package server;

// Importing libraries

import java.net.Socket;
import util.Comunicacao;
import java.util.ArrayList;
import java.net.ServerSocket;

public class Server { 
    // ------------------------ Main ------------------------
    public static void main(String[] args) {
        // Instantiating  variables
        int game_mode;
        final int PORT = 1234;
        Comunicacao comunicacao;
        ServerSocket serverSocket;
        ArrayList<Attend> threads;
        Socket clientSocket = null;
        Socket client2Socket = null;

        // Criar socket
        try {
            threads = new ArrayList<>();
            serverSocket = new ServerSocket(PORT);
            comunicacao = new Comunicacao(clientSocket);
        } catch (Exception e) {
            System.out.println(" >>> Porta " + PORT + " indisponível.");
            System.out.println(e.getMessage());
            return;
        }

        // Esperar conexão
        try {
            do {
                System.out.println(" >>> Aguardando conexão...");
                Attend attend = null;
                clientSocket = serverSocket.accept();
                game_mode = (int) comunicacao.receber();

                // Singleplayer, primeiro e único jogador
                if (game_mode == 1) { 
                    attend = new Attend(clientSocket, threads);  
                    attend.start();
                    attend = null;
                } ;
                
                // Multiplayer
                if (game_mode == 2) {
                    // Primeiro jogador
                    if (attend == null) {
                        attend = new Attend(clientSocket, threads);
                        attend.addJogador(clientSocket);

                    // Segundo jogador
                    } else {
                        attend.addJogador(client2Socket);
                        attend.start();
                    }
                }
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
}