package client;

import controler.VentanaChatController;
import model.Mensaje;

public class ConexionThread extends Thread { //el Thread permite ejecutar esta clase en un hilo independiente

    private ClientSocket cliente; //representa la conexión del cliente con el servidor
    private VentanaChatController controlador; //comunica los mensajes recividos a la ventana

    // Constructor
    public ConexionThread(ClientSocket cliente, VentanaChatController controlador) {
        this.cliente = cliente;
        this.controlador = controlador;
    }

    // Recibir mensajes continuamente
    @Override
    public void run() {
        while (cliente.isConectado()) { //mantiene la recepcion de mensajes mietars el cliente este conectado
            Mensaje mensaje = cliente.recibirMensaje();
            if (mensaje == null) {
                break;
            }
            controlador.recibirMensaje(mensaje);
        }
    }
}