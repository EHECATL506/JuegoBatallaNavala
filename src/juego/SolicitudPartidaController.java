/**
*Autores:Ehecatl Said Cortina Gamboa
*Proyecto: Juego Batalla Naval 
*Fecha de creación:01-01-2016 
*Fecha de actualización:04-01-16
*Descripción:Clase controladora que muestra una pantalla para responder una solicitud, dependiendo de la respuesta activa diferentes eventos como regresar al menu principal o iniciar la batalla.
*/
package juego;

import io.socket.emitter.Emitter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static juego.Juego.Usuario;
import static juego.LoginController.socket;
import org.json.JSONException;
import org.json.JSONObject;
import juego.Juego;
import static juego.BatallaController.turno;

public class SolicitudPartidaController implements Initializable {
    @FXML
    private Button botonCancelar, botonAceptar;
    @FXML
    private TextField campoNombre;
    public static Stage primaryStage;
    private static AnchorPane rootLayout;
    private static String listaUsuarios[] = new String[10];
    @FXML
    private String competidor;
    
    /**
     * @Descripción Función que carga el documento fxml como un recurso y muestra su pantalla correspondiente. 
     * @param primaryStage se declara una etapa o escena principal para inciar el programa y se hace referencia para llamarla.
     */
   public static void initRootLayout(Stage primaryStage) {
        try {
            SolicitudPartidaController.primaryStage = primaryStage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Juego.class.getResource("SolicitudPartida.fxml"));
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
        listaUsuarios[0] = "Diosvientosaid";
        listaUsuarios[1] = "DIXASMON";
        botonAceptar.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
            turno = true;
            JSONObject solicitud = new JSONObject();

            try {
                solicitud.accumulate("contrincante", campoNombre.getText());
                solicitud.accumulate("retador", Usuario);
            } catch (JSONException ex) {
                Logger.getLogger(SolicitudPartidaController.class.getName()).log(Level.SEVERE, null, ex);
            }

            socket.emit("solicitudBatallaSaliente", solicitud);
            RespuestaController.initRootLayout(primaryStage);
            socket.on("comenzarBatalla", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    Platform.runLater(
                            () -> {
                                TableroController.initRootLayout(primaryStage);
                            }
                    );
                }
            });
            socket.on("cambio", new Emitter.Listener() {
                @Override   
                public void call(Object... os) {
                    System.out.println("en solicitud recibe en evento cambiar " + os[0]);
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
                        () -> {
                            DerrotaController.initRootLayout(primaryStage);
                        }
                        );
                        
                    }
                }
            });

        });
        botonCancelar.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {

        });
    }
    
    /**
     * @Descripción Función que carga el documento fxml como un recurso y llama a la pantalla de usuario registrado que es el menu principal, para cuando se decide cancelar la accion..
     */
    @FXML
    void Regresar(ActionEvent event) {
        UsuarioRegistradoController.initRootLayout(primaryStage);
    }
   
}
