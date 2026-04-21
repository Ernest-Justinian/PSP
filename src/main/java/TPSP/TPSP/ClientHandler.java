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

            String message;

            // Comunicación continua
            while ((message = input.readLine()) != null) {

                if (message.equalsIgnoreCase("salir")) {
                    System.out.println("Cliente desconectado");
                    output.println("Conexión cerrada. Hasta luego!");
                    break;
                }

                // Mostrar mensaje en servidor
                System.out.println("Cliente: " + message);

                // Respuesta al cliente
                output.println("Servidor recibió: " + message);
            }

        } catch (IOException e) {
            System.out.println("Error con el cliente");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}