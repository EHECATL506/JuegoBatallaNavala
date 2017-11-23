/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;


/**
 * FXML Controller class
 *
 * @author EHECA
 */
public class PerfilController implements Initializable  {

    @FXML
    private Button CancelarRegistro;

    @FXML
    private Label EtiquetaUsuario;

    @FXML
    private TextField CampoNombre;

    @FXML
    private Button RegistrarUsuario;

    @FXML
    private Label EtiquetaNombre;

    @FXML
    private TextField CampoUsuario;

    @FXML
    private TextField CampoCorreo;

    @FXML
    private Label EtiquetaCorreo;

    @FXML
    private Label EtiquetaTitulo;

    @FXML
    private TextField CampoContraseña;

    @FXML
    void RegistraUsuario(ActionEvent event) {

    }

    @FXML
    void CancelarRegistro(ActionEvent event) {

    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CampoUsuario.setPromptText("ingresa tu nombre de usuario aqui");
        CampoContraseña.setPromptText("ingresa tu Contraseña aqui");
    }    
    
}
