
package server;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class Servidor {

    private static final int PUERTO = 5000;
    private static final int MAX_CLIENTES = 10;

    // Lista de clientes conectados
    private static ArrayList<ClienteHandler> clientes = new ArrayList<>();

    // Información del servidor
    private static LocalDateTime inicioServidor = LocalDateTime.now();


    private static String ultimoMensaje = "Todavía no se han recibido mensajes";
    private static LocalDateTime fechaUltimoMensaje = null;

    // Iniciar servidor
    public static void main(String[] args) {
    try (ServerSocket servidor = new ServerSocket(PUERTO)) { //ServerSocket para abrir el puerto y esperar conexiones

        System.out.println("Servidor iniciado en el puerto " + PUERTO);

        // Iniciar el monitor del servidor para el tiempo la clase MonitorServidor 
        MonitorServidor monitor = new MonitorServidor(inicioServidor);
        monitor.start();

        // Esperar conexiones continuamente
        while (true) {
            Socket socket = servidor.accept();
            System.out.println("Nuevo cliente conectado.");

            try {
                // Crear un objeto para atender al cliente en su propio hilo
                ClienteHandler cliente = new ClienteHandler(socket);

                // Comprobar si hay espacio para el nuevo cliente
                if (añadirCliente(cliente)) {
                    // Iniciar el hilo del cliente
                    cliente.start();
                    System.out.println("Cliente añadido. Clientes conectados: " + obtenerClientes().size());
                } else {
                    System.out.println("Servidor lleno. Cliente rechazado.");
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println("Error al preparar la conexión.");
                socket.close();
            }
        }
    } catch (IOException e) {
        System.out.println("Error al iniciar el servidor: " + e.getMessage());
    }
}

    // Añadir un cliente a la lista, pero primero comprueba que haya espacio
    public static synchronized boolean añadirCliente(ClienteHandler cliente) {
        if (clientes.size() < MAX_CLIENTES) {
            clientes.add(cliente);
            return true;
        }
        return false;
    }

    // Eliminar un cliente de la lista cuando se desconecta
    public static synchronized void eliminarCliente(ClienteHandler cliente) {
        clientes.remove(cliente);
    }

    // Obtener una copia de la lista de clientes
    public static synchronized List<ClienteHandler> obtenerClientes() {
        return new ArrayList<>(clientes);
    }

    // Guardar la información del último mensaje recibido
    public static synchronized void registrarMensaje(String mensaje) {
        ultimoMensaje = mensaje;
        fechaUltimoMensaje = LocalDateTime.now();
    }

    // Obtener el último mensaje recibido
    public static synchronized String obtenerUltimoMensaje() {
        if (fechaUltimoMensaje == null) {
            return ultimoMensaje;
        }

        return ultimoMensaje + " (" + fechaUltimoMensaje + ")";
    }

}
