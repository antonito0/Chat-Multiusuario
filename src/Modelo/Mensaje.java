
import java.time.LocalDateTime;

public class Mensaje {

    private String emisor;
    private String destinatario;
    private String contenido;
    private boolean privado;
    private LocalDateTime fecha;

    public Mensaje(String emisor, String destinatario,String contenido, boolean privado) {
        this.emisor = emisor;
        this.destinatario = destinatario;
        this.contenido = contenido;
        this.privado = privado;
        this.fecha = LocalDateTime.now();
    }

    public String getEmisor() {
        return emisor;
    }
    public String getDestinatario() {
        return destinatario;
    }
    public String getContenido() {
        return contenido;
    }
    public boolean isPrivado() {
        return privado;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }


    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    public void setPrivado(boolean privado) {
        this.privado = privado;
    }
    // Mostrar mensaje
    
    
    @Override
    public String toString() {
       //Condidion para mirar si es privado o no y mostrarlo de manera diferente
        
    }
}
