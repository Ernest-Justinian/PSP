package TPSP.TPSP;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    private static final int PORT = 5000;
    private static Set<ClientHandler> clients = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Servidor iniciado...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Nuevo cliente conectado");

                ClientHandler client = new ClientHandler(socket);
                clients.add(client);

                new Thread(client).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Enviar mensaje a todos los clientes
    public static void broadcast(String message, ClientHandler excludeUser) {
        for (ClientHandler client : clients) {
            if (client != excludeUser) {
                client.sendMessage(message);
            }
        }
    }

    // Eliminar cliente
    public static void removeClient(ClientHandler client) {
        clients.remove(client);
    }
}