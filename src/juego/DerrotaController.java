/**
*Autores:Ehecatl Said Cortina Gamboa
*Proyecto: Juego Batalla Naval 
*Fecha de creación:23-12-2015 
*Fecha de actualización:04-01-16
*Descripción:Controlador de la clase que genera una pantalla cuando el usuario pierde una partida.
*/
package juego;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import juego.Juego;
import juego.UsuarioRegistradoController;

public class DerrotaController implements Initializable {
      @FXML
    private Button botoncerrar;
    @FXML
    private Label derrotamensaje, etiquetaperder;
    public static Stage primaryStage;
    private static AnchorPane rootLayout;
    
    /**
     * @Descripción Función que carga el documento fxml como un recurso y muestra su pantalla correspondiente. 
     * @param primaryStage se declara una etapa o escena principal para inciar el programa y se hace referencia para llamarla.
     */
     public  static void initRootLayout(Stage primaryStage) {
        try {
            DerrotaController.primaryStage = primaryStage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Juego.class.getResource("Derrota.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       botoncerrar.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
          UsuarioRegistradoController.initRootLayout(primaryStage);
        });
    }    
}
