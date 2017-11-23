/**
*Autores:Ehecatl Said Cortina Gamboa
*Proyecto: Juego Batalla Naval 
*Fecha de creación:22-11-2015 
*Fecha de actualización:04-01-16
*Descripción:Clase principal que llama a la pantalla login del juego.
*/
package juego;
import javafx.stage.Stage;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Juego extends Application{
    static Stage primaryStage;
    public static String Usuario;
    
    /**
     * @Descripción Función que carga el documento fxml como un recurso y muestra su pantalla correspondiente. 
     * @param primaryStage se declara una etapa o escena principal para inciar el programa y se hace referencia para llamarla.
     */
    @Override
     public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Batalla Naval");
        LoginController.initRootLayout(primaryStage);
    }
    
     public static void main(String[] args) {
        launch(args);
    }
}