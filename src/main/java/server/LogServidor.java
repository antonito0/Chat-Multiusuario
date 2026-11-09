package server;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogServidor {

    private static final String FICHERO = "servidor.log";
    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Escribe una línea en el log (consola + fichero)
    // synchronized: solo un hilo a la vez puede escribir
    private static synchronized void registrar(String evento) {
        String linea = "[" + LocalDateTime.now().format(FORMATO) + "] " + evento;

        // Mostrar por consola
        System.out.println(linea);

        // Guardar en fichero (true = añadir al final, no sobrescribir)
        try (PrintWriter pw = new PrintWriter(new FileWriter(FICHERO, true))) {
            pw.println(linea);
        } catch (IOException e) {
            System.out.println("Error al escribir en el log: " + e.getMessage());
        }
    }

    public static void usuarioEntra(String usuario) {
        registrar("ENTRA: " + usuario);
    }

    public static void usuarioSale(String usuario) {
        registrar("SALE: " + usuario);
    }

    public static void mensajePublico(String remitente, String contenido) {
        registrar("PÚBLICO | " + remitente + ": " + contenido);
    }

    public static void mensajePrivado(String remitente, String destinatario, String contenido) {
        registrar("PRIVADO | " + remitente + " -> " + destinatario + ": " + contenido);
    }
}