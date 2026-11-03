/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controler;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Unai.Ibarguren
 */
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
    private ListView<?> messageList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
