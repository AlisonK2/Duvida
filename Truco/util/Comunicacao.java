package util;

// Importing libraries
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Comunicacao {
    // Instantiating variables
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public Comunicacao(Socket socket) {
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void enviar(Object requisicao) {
        try {
            output.writeObject(requisicao);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Object receber() {
        try {
            return input.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }  
}