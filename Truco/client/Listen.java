package client;

// Importing library
import java.util.Scanner;

public class Listen extends Thread {
    // ------------------------ Attributes ------------------------
    private Scanner input = null;
    private boolean running;

   // ------------------------ Construtor ------------------------
    public Listen(Scanner input) {
        this.input = input;}

    // ------------------------ Methods ------------------------
    @Override
    public void run() {
        do {
            String resposta = input.nextLine();
            System.out.println("Recebido: " + resposta);
        } while (running);
    }

    public void parar(){
        running = false;
    }
}