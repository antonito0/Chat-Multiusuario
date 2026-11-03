package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ListaMensajes {
	private Map<String, ArrayList<Mensaje>> mensajes; // Mapa sincronizado de clientes con sus hilos

	// Constructor
	public ListaMensajes()
	{
		this.mensajes = Collections.synchronizedMap(new HashMap<>());   
	}
	
	// Añade un mensaje al HashMap
	public synchronized void nuevoMensaje(Mensaje mensaje) 
	{
        ArrayList<Mensaje> lista = mensajes.getOrDefault(mensaje.getRemitente(), new ArrayList<>()); // Obtiene la lista de mensajes y si no existe la crea
		lista.add(mensaje); // Añade el mensaje a la lista
        mensajes.put(mensaje.getRemitente(), lista); // Añade o reemplaza el usuario
	}
}