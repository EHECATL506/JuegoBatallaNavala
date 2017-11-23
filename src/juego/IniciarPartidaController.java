/**
*Autores:Ehecatl Said Cortina Gamboa
*Proyecto: Juego Batalla Naval 
*Fecha de creación:22-12-2015 
*Fecha de actualización:04-01-16
*Descripción:Clase controladora que permite escoger un usuario de los disponibles para poderle enviar una solicitud de partida.
*/
package juego;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONException;
import javafx.event.ActionEvent;
import static juego.BatallaController.turno;
import juego.UsuarioRegistradoController;
import juego.Juego;

public class IniciarPartidaController implements Initializable {
    public static Stage primaryStage;
    private static AnchorPane rootLayout;
    @FXML
    private Button botonRetar, botonCancelar;
    @FXML
    private TextField listaJugadores;
    @FXML
    private Label etiquetaUsuario, etiquetaListaUsuarios;

    /**
     * @Descripción Función que carga el documento fxml como un recurso y muestra su pantalla correspondiente. 
     * @param primaryStage se declara una etapa o escena principal para inciar el programa y se hace referencia para llamarla.
     */
     static void initRootLayout(Stage primaryStage) {
        try {
            IniciarPartidaController.primaryStage = primaryStage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Juego.class.getResource("IniciarPartida.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @Descripción Función que carga el documento fxml como un recurso y llama a la pantalla de solicitud de partida.
     */
    @FXML
    void botonRetar(ActionEvent event) throws JSONException{
        turno = true; 
        SolicitudPartidaController.initRootLayout(primaryStage);
    }

    /**
     * @Descripción Función que carga el documento fxml como un recurso y llama a la pantalla de usuario registrado que es el menu principal, para cuando se decide cancelar la accion..
     */
    @FXML
    void botonCancelar(ActionEvent event) {
        UsuarioRegistradoController.initRootLayout(primaryStage);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
