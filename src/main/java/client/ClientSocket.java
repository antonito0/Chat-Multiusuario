package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket {
    private Socket socket;
    private PrintWriter salida;
    private BufferedReader entrada;

    private boolean conectado = false;

    // Conectar con el servidor
    public boolean conectar( int puerto) {
        try {
            socket = new Socket("localhost",puerto);
            salida = new PrintWriter(socket.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            conectado = true;
            System.out.println("Conectado al servidor.");
            return true;
        } catch (IOException e) {
            System.out.println("Error al conectar: " + e.getMessage());
            return false;
        }
    }
}
