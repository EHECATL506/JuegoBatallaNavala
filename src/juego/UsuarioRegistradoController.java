/**
*Autores:Ehecatl Said Cortina Gamboa
*Proyecto: Juego Batalla Naval 
*Fecha de creación:22-11-2015 
*Fecha de actualización:04-01-16
*Descripción:Clase que muestra varias opciones a un usuario ya registrado, tales como buscar partida, crear una partida y mandar invitaciones de juego, ademas de cerrar sesion.
*/
package juego;

import io.socket.emitter.Emitter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static juego.LoginController.contrincante;
import static juego.LoginController.retador;
import static juego.LoginController.socket;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author EHECA
 */
public class UsuarioRegistradoController implements Initializable{
public static Stage primaryStage;
public static AnchorPane rootLayout;
    /**
     * @Descripción Función que carga el documento fxml como un recurso y muestra su pantalla correspondiente. 
     * @param primaryStage se declara una etapa o escena principal para inciar el programa y se hace referencia para llamarla.
     */
    static void initRootLayout(Stage primaryStage) {
        try {
            UsuarioRegistradoController.primaryStage = primaryStage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Juego.class.getResource("UsuarioRegistrado.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket.on("solicitudBatallaEntrante", new Emitter.Listener() {
            @Override
            public void call(Object... os) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject solicitar = (JSONObject) os[0];
                            contrincante = solicitar.getString("contrincante");
                            retador = solicitar.getString("retador");
                            InvitacionController.initRootLayout(primaryStage);
                        } catch (JSONException ex) {
                            Logger.getLogger(UsuarioRegistradoController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }

        });

    }


    @FXML
    Label EtiquetaLema,  EtiquetaTítulo;
    @FXML
    Button BotónSalir, BotónPartida,  BotónSalaPrivada, BotónEstadística;
   
    /**
     * @Descripción Función que carga el documento fxml como un recurso y muestra su pantalla correspondiente. 
     */
    @FXML
    void VerEstadísticas(ActionEvent event) {
       DerrotaController.initRootLayout(primaryStage);
    }
 /**
     * @Descripción Función que carga el documento fxml como un recurso y muestra su pantalla correspondiente. 
     */
    @FXML
    void BuscaPartida(ActionEvent event){
    IniciarPartidaController.initRootLayout(primaryStage);
    socket.on("solicitudBatallaEntrante", new Emitter.Listener() {

            @Override
            public void call(Object... os) {
                Platform.runLater(
                        () -> {
                            try {
                                JSONObject solicitar = (JSONObject) os[0];
                                contrincante = solicitar.getString("contrincante");
                                retador = solicitar.getString("retador");
                                juego.InvitacionController.initRootLayout(primaryStage);
                            } catch (JSONException ex) {
                                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                );
            }

        });
    }
    
/**
     * @Descripción Función que carga el documento fxml como un recurso y regresa a la pantalla principal.
     */
    @FXML
    void CerrarSesión(ActionEvent event) {
       LoginController.initRootLayout(primaryStage);
    }

    @FXML
    void CreaSalaPrivada(ActionEvent event) {
      SolicitudPartidaController.initRootLayout(primaryStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        socket.on("solicitudBatallaEntrante", new Emitter.Listener() {
            @Override
            public void call(Object... os) {
                Platform.runLater(
                        () -> {
                            try {
                                JSONObject solicitar = (JSONObject) os[0];
                                contrincante = solicitar.getString("contrincante");
                                retador = solicitar.getString("retador");
                                InvitacionController.initRootLayout(primaryStage);
                            } catch (JSONException ex) {
                                Logger.getLogger(UsuarioRegistradoController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                );
            }

        });
    }    
      
}

