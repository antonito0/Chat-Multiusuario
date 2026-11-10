package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Mensaje;

public class ClientSocket {

    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;

    private boolean conectado = false;
    private boolean servidorLleno = false;

    // Conectar con el servidor
    public boolean conectar(int puerto) {
        servidorLleno = false;
        try {
            socket = new Socket("localhost", puerto);
            salida = new ObjectOutputStream(socket.getOutputStream());
            salida.flush();
            entrada = new ObjectInputStream(socket.getInputStream());

            // El servidor responde si nos acepta o si está lleno
            Mensaje respuesta = (Mensaje) entrada.readObject();
            if (Mensaje.SERVIDOR_LLENO.equals(respuesta.getContenido())) {
                servidorLleno = true;
                socket.close();
                return false;
            }

            conectado = true;
            System.out.println("Conectado al servidor.");
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al conectar: " + e.getMessage());
            return false;
        }
    }

    // Enviar un mensaje al servidor (le envia un objeto Mensaje)
    public void enviarMensaje(Mensaje mensaje) {
        try {
            salida.writeObject(mensaje);
            salida.flush();
        } catch (IOException e) {
            System.out.println("Error al enviar el mensaje.");
        }
    }

    // Recibir un mensaje del servidor(recibe un objeto Mensaje)
    public Mensaje recibirMensaje() {
        try {
            return (Mensaje) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al recibir el mensaje.");
            return null;
        }
    }

    // Desconectar del servidor
    public void desconectar() {
        try {
            conectado = false;
            if (socket != null) {
                socket.close();
            }
            System.out.println("Desconectado del servidor.");
        } catch (IOException e) {
            System.out.println("Error al desconectar.");
        }
    }

    public boolean isConectado() { //con esto comprobamos si el cliente esta conectado o no al servidor
        return conectado;
    }

    //Nos dice si hay sitio en el servidor
    public boolean isServidorLleno() {
        return servidorLleno;
    }
}