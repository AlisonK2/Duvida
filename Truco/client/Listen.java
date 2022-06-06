package client;

// Importing library
import java.util.Scanner;

public class Listen extends Thread {
    // ------------------------ Attributes ------------------------
    private Scanner input = null;

   // ------------------------ Construtor ------------------------
    public Listen(Scanner input) {
        this.input = input;}

    // ------------------------ Methods ------------------------
    @Override
    public void run() {
        do {
            String resposta = input.nextLine();
            System.out.println(resposta);
        } while (true);
    }
}