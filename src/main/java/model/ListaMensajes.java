package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListaMensajes {

    // Mapa que guarda los mensajes de cada usuario
    private Map<String, ArrayList<Mensaje>> mensajes;

    // Constructor
    public ListaMensajes() {
        this.mensajes = new HashMap<>();
    }

    // Añade un mensaje al historial del remitente
    public synchronized void nuevoMensaje(Mensaje mensaje) {
        ArrayList<Mensaje> lista = mensajes.get(mensaje.getRemitente()); //primero obtiene el nombre del usuario que ha enviafo el mensaje y depues busca en el maoa si ese usuario ya tinee un alista 
        if (lista == null) { //busca la lista de un usuari y sino existe pues la crea 
            lista = new ArrayList<>();
            mensajes.put(mensaje.getRemitente(), lista); //guardamos esa lista y le asociamos le nombre del usuario
        }
        lista.add(mensaje);
    }

    // Devuelve los mensajes guardados de un usuario
    public synchronized ArrayList<Mensaje> obtenerMensajes(String usuario) {
        ArrayList<Mensaje> lista = mensajes.get(usuario);
        if (lista == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(lista);
    }
}