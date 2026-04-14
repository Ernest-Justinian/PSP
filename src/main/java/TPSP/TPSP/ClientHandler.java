package TPSP.TPSP;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            // Solicitar nombre
            output.println("Introduce tu nombre:");
            username = input.readLine();

            System.out.println(username + " se ha conectado");
            output.println("Bienvenido " + username);

            Server.broadcast(username + " se ha unido al chat", this);

            String message;

            // Comunicación continua
            while ((message = input.readLine()) != null) {

                if (message.equalsIgnoreCase("salir")) {
                    break;
                }

                String fullMessage = username + ": " + message;
                System.out.println(fullMessage);

                Server.broadcast(fullMessage, this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Server.removeClient(this);
                socket.close();
                System.out.println(username + " se ha desconectado");
                Server.broadcast(username + " ha salido del chat", this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        output.println(message);
    }
}
