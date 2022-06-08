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
    
    public void enviar(Object id_Requisicao) {
        try {
            output.writeObject(id_Requisicao);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void enviar(Object id_Requisicao, Object valor) {
        try {
            output.writeObject(id_Requisicao); 
            output.writeObject(valor);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void enviar(Object id_Requisicao, Object valor, Object valor2) {
        try {
            output.writeObject(id_Requisicao);
            output.writeObject(valor);
            output.writeObject(valor2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void enviar(Object id_Requisicao, Object valor, Object valor2, Object valor3) {
        try {
            output.writeObject(id_Requisicao);
            output.writeObject(valor);
            output.writeObject(valor2);
            output.writeObject(valor3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void envia_resposta(Object resposta) {
        try {
            output.writeObject(resposta);
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