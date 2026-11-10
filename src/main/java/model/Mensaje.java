
package model;

import java.io.Serializable;
import java.util.List;

public class Mensaje implements Serializable {

    private static final long serialVersionUID = 1L;

    // Tipos de mensajes
    public static final String RESPUESTA_SERVER = "respuesta_server";
    public static final String LISTA_CLIENTES = "lista_clientes";
    public static final String MENSAJE_PUBLICO = "mensaje_publico";
    public static final String MENSAJE_PRIVADO = "mensaje_privado";
    public static final String CONEXION = "conexion";
    public static final String CONEXION_OK = "conexion_ok";
    public static final String SERVIDOR_LLENO = "servidor_lleno";

    // Variables
    private String tipo;
    private List<String> clientes;
    private String contenido;
    private String remitente;
    private String destinatario;

    // Constructor para las respuestas del servidor
    public Mensaje(String respuesta) {
        this.tipo = RESPUESTA_SERVER;
        this.contenido = respuesta;
    }

    // Constructor para las listas de clientes
    public Mensaje(List<String> clientes, String contenido, String remitente) {
        this.tipo = LISTA_CLIENTES;// indica que es un mensaje con la lista de usuarios
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
    public Mensaje(String contenido, String remitente, String destinatario) {
        this.tipo = MENSAJE_PRIVADO;
        this.contenido = contenido;
        this.remitente = remitente;
        this.destinatario = destinatario;
    }

    public Mensaje(String tipo, String remitente, String destinatario, String contenido) {
        this.tipo = tipo;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.contenido = contenido;
    }
    public Mensaje() {
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
    //setters 
    public void setTipo(String tipo) {
    this.tipo = tipo;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    // Mensaje que envía el cliente al conectarse para decir quién es
    public static Mensaje conexion(String usuario) {
        Mensaje m = new Mensaje(usuario);
        m.tipo = CONEXION;
        m.remitente = usuario;
        return m;
    }
}
