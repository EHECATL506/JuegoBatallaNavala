/**
*Autores:Ehecatl Said Cortina Gamboa
*Proyecto: Juego Batalla Naval 
*Fecha de creación:22-12-2015 
*Fecha de actualización:04-01-16
*Descripción:Clase controladora que permite mandar una solicitud a un jugador para iniciar el juego, despliega la pantalla correspondiente, segun el resultado del juego.
*/
package juego;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static juego.Juego.Usuario;
import static juego.LoginController.socket;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import static juego.LoginController.retador;
import static juego.BatallaController.turno;
import juego.UsuarioRegistradoController;

/**
 * FXML Controller class
 *
 * @author EHECA
 */
public class InvitacionController implements Initializable {
    public static Stage primaryStage;
    public static AnchorPane rootLayout;
    
    @FXML
    private Label Invita,tituloindicacion;
    @FXML
    private Button botonCancelar, botonInvitar;
    @FXML
    private TextField campoNombre;

     /**
     * @Descripción Función que carga el documento fxml como un recurso y muestra su pantalla correspondiente. 
     * @param primaryStage se declara una etapa o escena principal para inciar el programa y se hace referencia para llamarla.
     */
    public static void initRootLayout(Stage primaryStage) {
        try {
            InvitacionController.primaryStage = primaryStage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Juego.class.getResource("Invitacion.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        botonInvitar.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
            turno = false;
            socket.on("cambio", new Emitter.Listener() {
                @Override
                public void call(Object... os) {                   
                    System.out.println("Espera hay una invitacion que fue enviada"+os[0]);
                    if (os[0].equals(true)) {
                        Platform.runLater(
                                () -> {
                                    turno = true;
                                }
                        );
                    } else {
                        Platform.runLater(
                                () -> {
                                    turno = false;
                                }
                        );
                    }
                }
            });
            socket.on("ganaste", new Emitter.Listener() {

                @Override
                public void call(Object... os) {
                    if (os[0].equals(true)) {
                        Platform.runLater(
                                () -> {
                                    GanadorController.initRootLayout(primaryStage);
                                }
                        );
                    } else {
                        Platform.runLater(
                        ()->{
                            DerrotaController.initRootLayout(primaryStage);
                        }
                        );
                    }
                }
            });
           TableroController.initRootLayout(primaryStage);
            JSONObject ack = new JSONObject();
            try {
                ack.accumulate("contrincate", Usuario);
                ack.accumulate("retador", retador);
            } catch (JSONException ex) {
                Logger.getLogger(InvitacionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            socket.emit("acept", ack);
        });
    }
    }    
    

