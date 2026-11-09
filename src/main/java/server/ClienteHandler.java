
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
    public void run() { // ejecuta el hilo cuando se llama al método start() de la clase ClienteHandler
        try {
            while (!socket.isClosed()) { // permite recibir mensajes continuamente si cumple la condición

                // Recibir un mensaje del cliente y convertirlo a un objeto Mensaje
                Mensaje mensaje = (Mensaje) entrada.readObject();
                
                //Asi cada vez que un cliente envie un mensaje, el servidor guardara quien lo ha enviado y su contenido 
                Servidor.registrarMensaje(mensaje.getRemitente() + ": " + mensaje.getContenido());
                
                // Guardar el nombre del usuario la primera vez que envía un mensaje
                if (usuario == null) {
                    usuario = mensaje.getRemitente();
                    LogServidor.usuarioEntra(usuario);
                }

                // Comprobar el tipo de mensaje recibido
                if (mensaje.getTipo().equals(Mensaje.MENSAJE_PUBLICO)) {
                    enviarPublico(mensaje);
                } else if (mensaje.getTipo().equals(Mensaje.MENSAJE_PRIVADO)) {
                    enviarPrivado(mensaje);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Un cliente se ha desconectado.");
        } finally { // se ejecuta cuando termina la atención del cliente. Cerramos el socket y eliminamos el cliente de la lista del servidor
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar la conexión.");
            }

            if (usuario != null) {
                LogServidor.usuarioSale(usuario);
            }

            Servidor.eliminarCliente(this); // this significa el objeto actual, ya que el método eliminar recibe un objeto ClienteHandler
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
