package TPSP.TPSP;

import java.io.*;
import java.net.*;

public class Client {

	private static final String HOST = "localhost";
	private static final int PORT = 5000;

	public static void main(String[] args) {

		try (Socket socket = new Socket(HOST, PORT);

				BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

			// Alias del usuario
			System.out.print("Introduce tu alias: ");
			String username = console.readLine();

			// Logger local
			LoggerCliente logger = new LoggerCliente(username);

			// Enviar alias al servidor
			output.println(username);

			logger.log("Alias enviado: " + username);

			// Hilo para recibir mensajes
			new Thread(() -> {
				try {

					String response;

					while ((response = input.readLine()) != null) {

						System.out.println(response);

						logger.log("SERVIDOR: " + response);
					}

				} catch (IOException e) {

					System.out.println("Conexión cerrada");
				}
			}).start();

			// Enviar mensajes
			String userInput;

			while ((userInput = console.readLine()) != null) {

				output.println(userInput);

				logger.log("CLIENTE: " + userInput);

				if (userInput.equalsIgnoreCase("salir")) {
					break;
				}
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}