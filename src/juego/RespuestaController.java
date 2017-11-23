/**
*Autores:Ehecatl Said Cortina Gamboa
*Proyecto: Juego Batalla Naval 
*Fecha de creación:26-12-2015 
*Fecha de actualización:04-01-16
*Descripción:Clase controladora que muestra una pantalla al usuario que mando una solicitud mientras espera que el usuario destino respónda la solicitud de partida.
*/
package juego;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import juego.Juego;

/**
 * FXML Controller class
 *
 * @author EHECA
 */
public class RespuestaController implements Initializable {
    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    /**
     * @Descripción Función que carga el documento fxml como un recurso y muestra su pantalla correspondiente. 
     * @param primaryStage se declara una etapa o escena principal para inciar el programa y se hace referencia para llamarla.
     */
    static void initRootLayout(Stage primaryStage) {
        try {
            RespuestaController.primaryStage = primaryStage;      
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Juego.class.getResource("Respuesta.fxml"));
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
        // TODO
    }    
    
}
