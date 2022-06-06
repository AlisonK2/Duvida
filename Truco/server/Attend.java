package server;

// Importing libraries
import java.net.Socket;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.PrintStream;

public class Attend extends Thread {
    // Instantiating variables
    Scanner input = null;
    PrintStream output = null;
    private Socket clientSocket;
    private ArrayList<Attend> threads;

    public Attend(Socket clientSocket, ArrayList<Attend> threads) {
        this.clientSocket = clientSocket;
        this.threads = threads;
    }

    @Override
    public void run() {
        // Comunicação
        try {
            input = new Scanner(clientSocket.getInputStream());
            output = new PrintStream(clientSocket.getOutputStream());

            String msg;
            do {
                msg = input.nextLine();
                System.out.println(" >>> Recebido: " + msg);
                for (Attend attend : threads) {
                    attend.sendMessage(msg);
                }
            } while (!msg.equals("exit"));

            input.close();
            output.close();

        } catch (Exception e) {
            System.out.println(" >>> Erro na comunicação");
            System.out.println(e.getMessage());
        }
    }

    // Envio de mensagem
    public void sendMessage(String msg) {
        output.println(msg);
    }
}
