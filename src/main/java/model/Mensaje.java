
package model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Mensaje implements Serializable {

    private static final long serialVersionUID = 1L;

    // Tipos de mensajes
    public static final String RESPUESTA_SERVER = "respuesta_server";
    public static final String LISTA_CLIENTES = "lista_clientes";
    public static final String MENSAJE_PUBLICO = "mensaje_publico";
    public static final String MENSAJE_PRIVADO = "mensaje_privado";

    // Variables
    private String tipo;
    private List<String> clientes;
    private String contenido;
    private String remitente;
    private String destinatario;

    // Constructor para respuestas del servidor
    public Mensaje(String respuesta) {
        this.tipo = RESPUESTA_SERVER;
        this.contenido = respuesta;
    }

    // Constructor para listas de clientes
   public Mensaje(List<String> clientes, String contenido,String remitente) {
        this.tipo = LISTA_CLIENTES;//indica que es un mensaje con la lista de usuarios
        this.clientes = clientes;
        this.contenido = contenido;
        this.remitente = remitente;
    }

    // Constructor para mensajes públicos
    public Mensaje(String contenido, String remitente) {
        this.tipo = MENSAJE_PUBLICO;
        this.contenido = contenido;
        this.remitente = remitente;
    }

    // Constructor para mensajes privados
    public Mensaje(String contenido, String remitente,String destinatario) {
        this.tipo = MENSAJE_PRIVADO;
        this.contenido = contenido;
        this.remitente = remitente;
        this.destinatario = destinatario;
    }

    // Getters
    public String getTipo() {
        return tipo;
    }
    public List<String> getClientes() {
    return clientes;
    }   
    public String getContenido() {
        return contenido;
    }
    public String getRemitente() {
        return remitente;
    }
    public String getDestinatario() {
        return destinatario;
    }
}
