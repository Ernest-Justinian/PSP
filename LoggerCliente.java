package TPSP.TPSP;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class LoggerCliente {

	private String fileName;

	public LoggerCliente(String username) {

		this.fileName = "sesion_" + username + ".txt";
	}

	public synchronized void log(String mensaje) {

		try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {

			String timestamp = LocalDateTime.now().toString();

			writer.println("[" + timestamp + "] " + mensaje);

		} catch (IOException e) {

			System.out.println("Error escribiendo log cliente");
		}
	}
}