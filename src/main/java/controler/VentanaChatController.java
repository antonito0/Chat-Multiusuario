package controler;

import java.net.URL;
import java.util.ResourceBundle;

import client.ClientSocket;
import client.ConexionThread;
import model.Mensaje;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class VentanaChatController implements Initializable {

    @FXML
    private Label idlabel;
    @FXML
    private Label puertoLabel;
    @FXML
    private Label userLabel;
    @FXML
    private Label conDisconLabel;
    @FXML
    private TextField idField;
    @FXML
    private TextField puertoField;
    @FXML
    private TextField userField;
    @FXML
    private Button connectButton;
    @FXML
    private Button disconButton;
    @FXML
    private CheckBox privMessBox;
    @FXML
    private Label paraLabel;
    @FXML
    private TextField paraField;
    @FXML
    private TextField mensajeField;
    @FXML
    private Button enviarButton;
    @FXML
    private ListView<String> messageList;

    private ClientSocket cliente;
    private ConexionThread hiloRecepcion;

    @Override
    public void initialize(URL url, ResourceBundle rb) { //se ejecuta al abrir la ventana 
        conDisconLabel.setText("Desconectado");
    }

    //lee el puerto y se conecta con el servidor 
    @FXML
    private void conectar() {
        try {
            int puerto = Integer.parseInt(puertoField.getText());
            String usuario = userField.getText().trim();
            if (usuario.isEmpty()) {
                conDisconLabel.setText("Introduce un usuario");
                return;
            }
            cliente = new ClientSocket();
            if (cliente.conectar(puerto)) {
                cliente.enviarMensaje(Mensaje.conexion(usuario));
                conDisconLabel.setText("Conectado");
                hiloRecepcion = new ConexionThread(cliente, this);
                hiloRecepcion.start();
            } else {
                conDisconLabel.setText("Error de conexión");
            }
        } catch (NumberFormatException e) {
            conDisconLabel.setText("Introduce un puerto válido");
        }
    }

    // Cierra la conexion 
    @FXML
    private void desconectar() {
        if (cliente != null) {
            cliente.desconectar();
            conDisconLabel.setText("Desconectado");
        }
    }
    // Enviar un mensaje
    @FXML
    private void enviar() {
        if (cliente == null || !cliente.isConectado()) {
            conDisconLabel.setText("No estás conectado");
            return;
        }
        String contenido = mensajeField.getText().trim();
        String remitente = userField.getText().trim();
        if (contenido.isEmpty() || remitente.isEmpty()) {
            return;
        }
        Mensaje mensaje;
        if (privMessBox.isSelected()) {
            String destinatario = paraField.getText().trim();
            if (destinatario.isEmpty()) {
                conDisconLabel.setText("Introduce un destinatario");
                return;
            }
            mensaje = new Mensaje(contenido, remitente, destinatario);
        } else {
            mensaje = new Mensaje(contenido, remitente);
        }
        cliente.enviarMensaje(mensaje);
        mensajeField.clear();
    }

    @FXML
    private void alternarPrivado() {
        boolean privado = privMessBox.isSelected();
        paraLabel.setVisible(privado);
        paraField.setVisible(privado);
    }

   // Mostrar un mensaje recibido y añadirlo a la lista visual
    public void recibirMensaje(Mensaje mensaje) {
        // Tenemos dos hilos trabajando: el hilo de recepción,
        // que recibe los mensajes del servidor, y el hilo de JavaFX,
        // que se encarga de mostrar la ventana y actualizar sus elementos.

        // Platform.runLater() programa la ejecución de este código
        // en el hilo de JavaFX para actualizar la interfaz gráfica.
        Platform.runLater(new Runnable() {// Creamos una clase anónima que implementa Runnable.
            // Runnable es una interfaz de Java que permite definir
            // una tarea mediante su método run().
           
            @Override
            public void run() {
                // Obtenemos el contenido del mensaje recibido.
                String texto = mensaje.getContenido();
                // Comprobamos que el mensaje tenga un remitente.
                if (mensaje.getRemitente() != null) {
                    // Añadimos el nombre del remitente delante del mensaje. Si es privado, marcamos el remitente con (PRIVADO)
                    if (mensaje.getTipo().equals(Mensaje.MENSAJE_PRIVADO)) {
                        texto = mensaje.getRemitente() + " (PRIVADO): " + texto;
                    } else {
                        texto = mensaje.getRemitente() + ": " + texto;
                    }
                }
                // Añadimos el mensaje a la lista visual del chat.
                messageList.getItems().add(texto);
            }
        });
    }
}