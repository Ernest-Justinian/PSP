package TPSP.TPSP;

import java.io.*;
import java.net.*;

public class Server {

	private static final int PORT = 5000;

	public static void main(String[] args) {

		System.out.println("Servidor iniciado...");

		try (ServerSocket serverSocket = new ServerSocket(PORT)) {

			while (true) {

				Socket socket = serverSocket.accept();

				System.out.println("Nuevo cliente conectado: " + socket.getInetAddress());

				ClientHandler clientHandler = new ClientHandler(socket);

				new Thread(clientHandler).start();
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}