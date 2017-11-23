/**
*Autores:Ehecatl Said Cortina Gamboa
*Proyecto: Juego Batalla Naval 
*Fecha de creación:01-01-2016 
*Fecha de actualización:04-01-16
*Descripción:Clase que permite generar los datos necesarios para identificar los barcos, pintar de cierto color, pintar las zonas atacadas.
*/
package juego;
import io.socket.emitter.Emitter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import static juego.LoginController.socket;

public class BatallaController implements Initializable {
    @FXML
    private GridPane tablero2, tablero1;
    @FXML
    private Label etiquetaTituloJuego;
    @FXML
    private TitledPane zonas1;
    @FXML
    private TitledPane zonas2;
    public static boolean turno;
    public static Stage primaryStage;
    private static AnchorPane rootLayout;
    
    /**
     * @Descripción Función que carga el documento fxml como un recurso y muestra su pantalla correspondiente. 
     * @param primaryStage se declara una etapa o escena principal para inciar el programa y se hace referencia para llamarla.
     */
    static void initRootLayout(Stage primaryStage) {
        try {
            UsuarioRegistradoController.primaryStage = primaryStage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Juego.class.getResource("Batalla.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @Descripción Función que obtiene las posiciones de los barcos donde fueron colocados por el usuario y los separa por color.
     * @param ubicacionbarco
     * @param ubicacionsubmarino
     *@param ubicacioncarguero 
     *@param ubicacionguerra
     */
     public void pintarBarcosContrario(String ubicacionbarco[], String ubicacionsubmarino[],
            String ubicacioncarguero[], String ubicacionguerra[]) {
        for (int i = 0; i < ubicacionbarco.length; i++) {
            Button boton = new Button("");
            boton.setDisable(true);
            boton.setMaxSize(30, 30);
            boton.setId("" + Character.getNumericValue(ubicacionbarco[i].charAt(0)) + Character.getNumericValue(ubicacionbarco[i].charAt(1)) + "");
            boton.setStyle("-fx-background-color: black;");
            tablero2.add(boton, Character.getNumericValue(ubicacionbarco[i].charAt(0)), Character.getNumericValue(ubicacionbarco[i].charAt(1)));
            GridPane.setValignment(boton, VPos.CENTER);
            GridPane.setHalignment(boton, HPos.CENTER);
        }
        for (int i = 0; i < ubicacionsubmarino.length; i++) {
            Button boton = new Button("");
            boton.setDisable(true);
            boton.setMaxSize(30, 30);
            boton.setId("" + Character.getNumericValue(ubicacionsubmarino[i].charAt(0)) + Character.getNumericValue(ubicacionsubmarino[i].charAt(1)) + "");
            boton.setStyle("-fx-background-color: maroon;");
           tablero2.add(boton, Character.getNumericValue(ubicacionsubmarino[i].charAt(0)), Character.getNumericValue(ubicacionsubmarino[i].charAt(1)));
            GridPane.setValignment(boton, VPos.CENTER);
            GridPane.setHalignment(boton, HPos.CENTER);
        }
        for (int i = 0; i < ubicacioncarguero.length; i++) {
            Button boton = new Button("");
            boton.setDisable(true);
            boton.setMaxSize(30, 30);
            boton.setId("" + Character.getNumericValue(ubicacioncarguero[i].charAt(0)) + Character.getNumericValue(ubicacioncarguero[i].charAt(1)) + "");
            boton.setStyle("-fx-background-color: red;");
            tablero2.add(boton, Character.getNumericValue(ubicacioncarguero[i].charAt(0)), Character.getNumericValue(ubicacioncarguero[i].charAt(1)));
            GridPane.setValignment(boton, VPos.CENTER);
            GridPane.setHalignment(boton, HPos.CENTER);
        }
        for (int i = 0; i < ubicacionguerra.length; i++) {
            Button boton = new Button("");
            boton.setDisable(true);
            boton.setMaxSize(30, 30);
            boton.setId("" + Character.getNumericValue(ubicacionguerra[i].charAt(0)) + Character.getNumericValue(ubicacionguerra[i].charAt(1)) + "");
            boton.setStyle("-fx-background-color:darkgoldenrod;");
            tablero2.add(boton, Character.getNumericValue(ubicacionguerra[i].charAt(0)), Character.getNumericValue(ubicacionguerra[i].charAt(1)));
            GridPane.setValignment(boton, VPos.CENTER);
            GridPane.setHalignment(boton, HPos.CENTER);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       tirosContrario();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Button boton = new Button("" + i + j + "");
                boton.setMaxSize(30, 30);
                boton.setId("" + i + j + "");
                boton.setOnAction((ActionEvent event) -> {
                    System.out.println(boton.getId());
                    if (turno) {
                        JSONObject tiro = new JSONObject();
                        try {
                            tiro.accumulate("coordenada", boton.getId());
                            tiro.accumulate("contrincante", "SAID93");
                            tiro.accumulate("retador", "STEERVEN");
                        } catch (JSONException ex) {
                            Logger.getLogger(BatallaController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.out.println("cargando cañones "+tiro);
                        socket.emit("tiro1", tiro);
                        socket.on("acertar", new Emitter.Listener() {
                            
                            @Override
                            public void call(Object... os) {
                                if (os[0].equals(true)) {
                                    Platform.runLater(
                                            () -> {
                                                turno = true;
                                                System.out.println("encontraste:la posicion de un enemigo "+turno);
                                                boton.setStyle("-fx-background-color:gray;");
                                            }
                                    );
                                } else {
                                    
                                    Platform.runLater(
                                            () -> {
                                                
                                                turno = false;
                                                System.out.println("encontraste:mas agua en el mar, felicidades :v "+turno);
                                                boton.setStyle("-fx-background-color:red;");
                                            }
                                    );
                                    
                                }
                            }
                            
                        });
                    } else {
                        event.consume();
                    }
                    System.out.println("turno despues de acertar" + turno);
                });

                tablero1.add(boton, i, j);
                GridPane.setValignment(boton, VPos.CENTER);
                GridPane.setHalignment(boton, HPos.CENTER);

            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Button boton = new Button("" + i + j + "");
                boton.setMaxSize(30, 30);
                boton.setId("" + i + j + "");
                boton.setStyle("-fx-background-color:brown;");
                boton.setDisable(true);
                tablero2.add(boton, i, j);
                GridPane.setValignment(boton, VPos.CENTER);
                GridPane.setHalignment(boton, HPos.CENTER);
            }
        }
    }
    /**
     * @Descripción Función que obtiene las posiciones de las zonas atcadas y las separa por su color.
     */
    public void tirosContrario() {
        boolean tiro = false;
        String posicion = "";
        Button boton = new Button("" + posicion.charAt(0) + posicion.charAt(1) + "");
        if (tiro) {
            boton.setStyle("-fx-background-color:green;");
        } else {
            boton.setStyle("-fx-background-color: darkcyan;");
        }
        boton.setDisable(true);
        tablero2.add(boton, Character.getNumericValue(posicion.charAt(0)), Character.getNumericValue(posicion.charAt(1)));
        GridPane.setValignment(boton, VPos.CENTER);
        GridPane.setHalignment(boton, HPos.CENTER);
    }
    }    
    

