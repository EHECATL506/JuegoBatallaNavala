/**
*Autores:Ehecatl Said Cortina Gamboa
*Proyecto: Juego Batalla Naval 
*Fecha de creación:22-11-2015 
*Fecha de actualización:04-01-16
*Descripción:Clase Login que permite que un usuario entre al juego y sus pantallas siguientes si ya fue registrado previamente.
*/
package juego;
import io.socket.client.IO;
import java.net.URISyntaxException;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class LoginController implements Initializable{
    @FXML
    Label EtiquetaUsuario,  EtiquetaTítulo, EtiquetaSesión;
    @FXML
    Button BotónRegistrar, BotónSalir, BotónInicioSesión;
    @FXML
    TextField CampoUsuario;
    @FXML
    Label EtiquetaContraseña, EtiquetaMensaje;
    @FXML
    PasswordField CampoContraseña;
    public static String contrincante;
    public static String retador;
    public static Stage primaryStage;
    private static AnchorPane rootLayout;
    public static Socket socket;
    
     /**
     * @Descripción Función que carga el documento fxml como un recurso y muestra su pantalla correspondiente. 
     * @param primaryStage se declara una etapa o escena principal para inciar el programa y se hace referencia para llamarla.
     */
      static void initRootLayout(Stage primaryStage) {
        try {
            LoginController.primaryStage = primaryStage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Juego.class.getResource("Login.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     /**
     * @Descripción Funcion que llama a la pantalla para registrar a un nuevo .
     */
    @FXML
    void RegistrarUsuario(ActionEvent event){
         NuevoUsuarioController.initRootLayout(primaryStage);
    }
     /**
     * @Descripción evento que recibe un  nombre de usuario y una contraseña, para posteriormente llamar a un evento del servidor y autenticar al usuario o no.
     *@throws NoSuchAlgorithmException
     */
     @FXML
    void IniciarSesión(ActionEvent event)throws NoSuchAlgorithmException{
        JSONObject usuario = new JSONObject();
        try {
            usuario.accumulate("user", CampoUsuario.getText());
            usuario.accumulate("pass", CampoContraseña.getText());
        } catch (JSONException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
      Juego.Usuario = CampoUsuario.getText();
        socket.emit("entrar", usuario);
        socket.emit("join", usuario);
         
        socket.on("verificado", new Emitter.Listener() {
            @Override
            public void call(Object... os) {
                Platform.runLater(
                        () -> {
                            if ((boolean) os[0]) {
                              UsuarioRegistradoController.initRootLayout(primaryStage);
                            } else {
                                JOptionPane.showConfirmDialog(null, "el usuario o la contraseña es incorrecto, reacciona");
                            }
                        }
                );
            }
        });
    }
    /**
     * @Descripción Funcion para salir del programa principal.
     */
    @FXML
    void Salir(ActionEvent event) {
        System.exit(0);
    }

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CampoUsuario.setPromptText("ingresa tu nombre de usuario aqui");
        CampoContraseña.setPromptText("ingresa tu Contraseña aqui");
        EtiquetaMensaje.setText("");  
         try {
            socket = IO.socket("http://localhost:8000");
            socket.connect();
        } catch (URISyntaxException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        CampoUsuario.setOnKeyTyped((KeyEvent event) -> {
            char car = event.getCharacter().charAt(0);
            if (Character.isSpaceChar(car)) {
                event.consume();
            }
            if (CampoUsuario.getText().length() > 15) {
                event.consume();
            }
        });
        CampoContraseña.setOnKeyTyped((KeyEvent event) -> {
            char car = event.getCharacter().charAt(0);
            if (Character.isSpaceChar(car)) {
                event.consume();
            }
            if (CampoContraseña.getText().length() > 10) {
                event.consume();
            }
        });
    }
    }