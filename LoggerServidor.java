package TPSP.TPSP;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class LoggerServidor {

	private static final String FILE_NAME = "log_servidor.txt";

	// synchronized evita errores con varios hilos
	public static synchronized void log(String cliente, String mensaje) {

		try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {

			String timestamp = LocalDateTime.now().toString();

			writer.println("[" + timestamp + "] " + "[" + cliente + "] " + mensaje);

		} catch (IOException e) {

			System.out.println("Error escribiendo log del servidor");
		}
	}
}