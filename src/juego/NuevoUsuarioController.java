/**
*Autores:Ehecatl Said Cortina Gamboa
*Proyecto: Juego Batalla Naval 
*Fecha de creación:22-11-2015 
*Fecha de actualización:04-01-16
*Descripción:Clase que permite registrar a un nuevo usuario para que pueda incorporarse a la base de datos y despues pueda ingresar al juego.
*/
package juego;
import static juego.LoginController.socket;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class NuevoUsuarioController implements Initializable{    
    @FXML
    Label Mensaje, EtiquetaUsuario, Etiquetacorreo, EtiquetaNombre, Titulo, TItuloregistrar, EtiquetaContraseña;
    @FXML
    Button BotónRegistra, Botónatrás;
    @FXML
    TextField camponombre, campocorreo, Campoperfil, campocontraseña;
    public static Stage primaryStage;
    private static AnchorPane rootLayout;

     /**
     * @Descripción Función que carga el documento fxml como un recurso y muestra su pantalla correspondiente. 
     * @param primaryStage se declara una etapa o escena principal para inciar el programa y se hace referencia para llamarla.
     */
     static void initRootLayout(Stage primaryStage) {
        try {
            NuevoUsuarioController.primaryStage = primaryStage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Juego.class.getResource("NuevoUsuario.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @Descripción Funcion para registrar un nuevo usuario, pide datos como nombre de usuario, contraseña,nombre compelto y correo electronico,si no existe en la bd lo registra.
     * @param cadena recibe  una cadena para iniciar la comparacion.
     * @throws NoSuchAlgorithmException
     */
    @FXML
    void RegistraUsuario(ActionEvent event) throws NoSuchAlgorithmException {
        JSONObject nuevoUsuario = new JSONObject();
        if (!Campoperfil.getText().isEmpty()) {
            try {
                nuevoUsuario.accumulate("user", Campoperfil.getText());
                nuevoUsuario.accumulate("pass", campocontraseña.getText());
                nuevoUsuario.accumulate("correo", campocorreo.getText());
                nuevoUsuario.accumulate("nombre", camponombre.getText());

            } catch (JSONException ex) {
                Logger.getLogger(NuevoUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(nuevoUsuario);
            socket.emit("registrar", nuevoUsuario);
            socket.emit("join", nuevoUsuario);
            socket.on("registroCorrecto", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    if (os[0].equals(true)) {
                        System.out.println(os[0]);
                        JOptionPane.showMessageDialog(null, "el usuario se agrego correctamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "El usuario ya esta registrado");
                    }
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }
    /**
     * @return si los caracteres ingresados son letras o numeros.
     * @Descripción Funcion para verificar siuna cadena tiene caracteres validos.
     * @param cadena recibe  una cadena para iniciar la comparacion.
     */
    public boolean esAlfanumerico(String cadena) {
        for (int i = 0; i < cadena.length(); ++i) {
            char caracter = cadena.charAt(i);
            if (!Character.isLetterOrDigit(caracter)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * @return tamaño de cadena correcto o incorrecto
     * @Descripción Funcion para verificar si una cadena del tipo usuario es del tamaño correcto y no excede al que recibe la bd.
     * @param cadena recibe  una cadena para iniciar la comparacion.
     */
    public boolean tamañoUsurioCorrecto(String cadena) {
        return cadena.length() <= 15;
    }
    
   /**
     * @return si el tamaño de la contraseña esta en lo ideal.
     * @Descripción Funcion para verificar si una cadena para la contraseña es del tamaño correcto y no excede el limite delo recibido por la bd.
     * @param contraseña recibe  una cadena para iniciar la comparacion.
     */
    public boolean tamañoContraseñaCorrecto(String contraseña) {
        return contraseña.length() >= 5 && contraseña.length() <= 10;
    }
    /**
     * @return si la cadena esta vacia o no.
     * @Descripción Funcion para verificar si una cadena tiene al menos un caracter escrito.
     * @param cadena recibe  una cadena para iniciar la comparacion.
     */
    public boolean esVacio(String cadena) {
        return cadena.equals("");
    }
    /**
     * @Descripción Funcion para regresar al menu principal.
     */
    @FXML
    void Cancelar(ActionEvent event) {
       LoginController.initRootLayout(primaryStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       socket.connect();
        Campoperfil.setOnKeyTyped((KeyEvent event) -> {
            char car = event.getCharacter().charAt(0);
            if (Character.isSpaceChar(car)) {
                event.consume();
            }
            if (Campoperfil.getText().length() > 20) {
                event.consume();
            }
       });
        campocontraseña.setOnKeyTyped((KeyEvent event) -> {
            char car = event.getCharacter().charAt(0);
            if (Character.isSpaceChar(car)) {
                event.consume();
            }
            if (campocontraseña.getText().length() > 10) {
                event.consume();
            }
       });
        campocorreo.setOnKeyTyped((KeyEvent event) -> {
            char car = event.getCharacter().charAt(0);
            if (Character.isSpaceChar(car)) {
                event.consume();
            }
            if (campocorreo.getText().length() > 30) {
                event.consume();
            }
       });
        
        camponombre.setOnKeyTyped((KeyEvent event) -> {
            char car = event.getCharacter().charAt(0);
            if (Character.isSpaceChar(car)) {
                event.consume();
            }
            if (camponombre.getText().length() > 30) {
                event.consume();
            }
       });
    }    
    
}
