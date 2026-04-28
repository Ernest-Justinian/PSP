package TPSP.TPSP;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(
                    socket.getOutputStream(), true);

            // Mensaje de bienvenida
            output.println("Bienvenido al servidor");
            System.out.println("Hilo iniciado para: " + socket.getInetAddress());

            String message;

            // Comunicación continua con ESTE cliente
            while ((message = input.readLine()) != null) {

                if (message.equalsIgnoreCase("salir")) {
                    System.out.println("Cliente " + socket.getInetAddress() + " ha salido");
                    output.println("Conexión cerrada. Hasta luego!");
                    break;
                }

                // Mostrar mensaje en servidor
                System.out.println("[" + socket.getInetAddress() + "] " + message);

                // Respuesta al cliente
                output.println("Servidor recibió: " + message);
            }

        } catch (IOException e) {
            System.out.println("Error con cliente: " + socket.getInetAddress());
        } finally {
            try {
                socket.close();
                System.out.println("Conexión cerrada: " + socket.getInetAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}