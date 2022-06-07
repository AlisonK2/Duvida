package server;

// Importing libraries

import java.net.Socket;
import java.util.ArrayList;
import java.net.ServerSocket;

public class Server { 
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
}