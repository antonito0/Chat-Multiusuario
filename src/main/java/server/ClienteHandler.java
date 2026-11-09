
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Mensaje;

public class ClienteHandler extends Thread {
// ESTA CLASE SE ENCARGA DE ATENDER A CADA CLIENTE QUE SE CONECTA AL SERVIDOR

    private Socket socket; // representa la conexión del cliente con el servidor
    private ObjectOutputStream salida; // permite enviar objetos al cliente
    private ObjectInputStream entrada; // permite recibir objetos del cliente

    private String usuario; // guarda el nombre del usuario de esta conexión

    // Constructor: representa la conexión con el cliente y prepara los flujos de entrada y salida
    public ClienteHandler(Socket socket) throws IOException {
        this.socket = socket;
        salida = new ObjectOutputStream(socket.getOutputStream());
        salida.flush();
        entrada = new ObjectInputStream(socket.getInputStream());
    }

    // Atender al cliente
    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {

                // Recibir un mensaje del cliente
                Mensaje mensaje = (Mensaje) entrada.readObject();
                String tipo = mensaje.getTipo();

                if (tipo.equals(Mensaje.CONEXION)) {
                    // Mensaje de conexión: guardamos el usuario y lo registramos en el log
                    usuario = mensaje.getRemitente();
                    LogServidor.usuarioEntra(usuario);

                } else {
                    // Por si llega un mensaje sin haber enviado la conexión
                    if (usuario == null) {
                        usuario = mensaje.getRemitente();
                        LogServidor.usuarioEntra(usuario);
                    }

                    // Guardar el último mensaje para el monitor
                    Servidor.registrarMensaje(mensaje.getRemitente() + ": " + mensaje.getContenido());

                    // Comprobar el tipo de mensaje recibido
                    if (tipo.equals(Mensaje.MENSAJE_PUBLICO)) {
                        LogServidor.mensajePublico(mensaje.getRemitente(), mensaje.getContenido());
                        enviarPublico(mensaje);
                    } else if (tipo.equals(Mensaje.MENSAJE_PRIVADO)) {
                        LogServidor.mensajePrivado(mensaje.getRemitente(),
                                mensaje.getDestinatario(), mensaje.getContenido());
                        enviarPrivado(mensaje);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Un cliente se ha desconectado.");
        } finally {
            // Cerramos el socket, registramos la salida y eliminamos el cliente de la lista
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar la conexión.");
            }

            if (usuario != null) {
                LogServidor.usuarioSale(usuario);
            }

            Servidor.eliminarCliente(this);
        }
    }

    // Enviar un mensaje público a todos los clientes conectados
    private void enviarPublico(Mensaje mensaje) {
        for (ClienteHandler cliente : Servidor.obtenerClientes()) {
            cliente.enviarMensaje(mensaje);
        }
    }

    // Enviar un mensaje privado al destinatario y al remitente
    private void enviarPrivado(Mensaje mensaje) {
        for (ClienteHandler cliente : Servidor.obtenerClientes()) {
            if (cliente.usuario != null &&
                (cliente.usuario.equals(mensaje.getDestinatario()) ||cliente.usuario.equals(mensaje.getRemitente()))) {
                cliente.enviarMensaje(mensaje);
            }
        }
    }

    // Permite que el servidor envíe un mensaje a este cliente (le envía un objeto Mensaje)
    // synchronized para que no haya problemas de concurrencia si varios hilos intentan enviar mensajes al mismo tiempo
    public synchronized void enviarMensaje(Mensaje mensaje) {
        try {
            salida.writeObject(mensaje);
            salida.flush();
        } catch (IOException e) {
            System.out.println("Error al enviar el mensaje al cliente.");
        }
    }
}
