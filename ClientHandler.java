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
			output.println("Introduce tu nombre o alias:");

			username = input.readLine();

			System.out.println("Usuario conectado: " + username + " (" + socket.getInetAddress() + ")");

			LoggerServidor.log(username, "Cliente conectado desde " + socket.getInetAddress() + ":" + socket.getPort());

			output.println("Bienvenido, " + username);

			String option;

			// Bucle del menú
			while (true) {

				mostrarMenu();

				option = input.readLine();

				if (option == null) {
					break;
				}

				switch (option) {

				case "1":

					output.println("Describe la situación:");

					String denuncia = input.readLine();

					LoggerServidor.log(username, "DENUNCIA: " + denuncia);

					output.println("Tu denuncia ha sido registrada correctamente.");

					break;

				case "2":

					output.println("Este sistema permite reportar situaciones de bullying de forma anónima.");

					LoggerServidor.log(username, "Consultó información");

					break;

				case "3":

					output.println("Hasta luego, " + username);

					LoggerServidor.log(username, "Cliente desconectado");

					System.out.println(username + " ha salido");

					socket.close();

					return;

				default:

					output.println("Opción no válida");
				}
			}

		} catch (IOException e) {

			LoggerServidor.log(username, "ERROR: " + e.getMessage());

			System.out.println("Error con cliente: " + username);

		} finally {

			try {

				socket.close();

				System.out.println("Conexión cerrada: " + username);

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	private void mostrarMenu() {

		output.println("");
		output.println("----- MENÚ -----");
		output.println("1. Denunciar una situación");
		output.println("2. Consultar información");
		output.println("3. Salir");
		output.println("Elige una opción:");
	}
}