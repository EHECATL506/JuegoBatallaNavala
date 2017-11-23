/**
*Autores:Ehecatl Said Cortina Gamboa
*Proyecto: Juego Batalla Naval 
*Fecha de creación:16-12-2015 
*Fecha de actualización:04-01-16
*Descripción:Clase controladora que permite colocar los barcos en las posiciones dadas por el usuario, ademas de verificar que tengan algun valor valido y ademas manda las posiciones al servidor.
*/
package juego;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import juego.Juego;
import static juego.LoginController.socket;
import juego.UsuarioRegistradoController;
import org.json.JSONException;
import org.json.JSONObject;

public class TableroController implements Initializable{
    private int columna, fila;
    @FXML
    private Button Botónsalir,  botonguerra, botonsubmarino, botoncarguero, Barco; 
    @FXML
    private GridPane Tablero;
    @FXML
    private TitledPane Zonas;
    @FXML
    private Label Etiquetatitulo;            
    @FXML
    private TextField orientacionsubmarino, orientacioncarguero, orientacionbarco,orientacionguerra, guerray, guerrax, carguerox, cargueroy, submarinox, submarinoy, barcoy, barcox;
    public static Stage primaryStage;
    private static AnchorPane rootLayout;
    private JSONObject ubicacion = new JSONObject();
    
    
     /**
     * @Descripción Función encargada de verificar que todos los barcos han sido colocados y que a su vez manda las posiciones donde se han colocado al servidor.
     */
     @FXML
    public void accionaceptar(ActionEvent event) {
        if (barcox.getText().equals("") || carguerox.getText().equals("") || guerrax.getText().equals("") || submarinox.getText().equals("")) {
            if (barcoy.getText().equals("") || cargueroy.getText().equals("") || guerray.getText().equals("")
                    || submarinoy.getText().equals("")) {
                System.out.println("Hay algunos barcos de tu flota que aun faltan por posicionarse");
                System.out.println("Los barcos en tus posicions, que comienze el fuego");
                for (int i = 0; i < 2; i++) {
                    System.out.println("barco esta en esta en:" + " " + ubicacionbarco[i] + ",");
                }
                for (int i = 0; i < 3; i++) {
                    System.out.println("submarino esta en esta en:" + " " + ubicacionsubmarino[i] + ",");
                }
                for (int i = 0; i < 4; i++) {
                    System.out.println("carguero esta en esta en:" + " " + ubicacioncarguero[i] + ",");
                }
                for (int i = 0; i < 5; i++) {
                    System.out.println("guerra esta en esta en:" + " " + ubicacionguerra[i] + ",");
                }
            }
        } else {
            System.out.println("Todos los barcos en posicion");
            for (int i = 0; i < 2; i++) {
                  try {
                        ubicacion.accumulate("barco"+(i+1)+"", ubicacionbarco[i]);
                    } catch (JSONException ex) {
                        Logger.getLogger(TableroController.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            for (int i = 0; i < 3; i++) {
                try {
                    ubicacion.accumulate("submarino"+(i+1)+"", ubicacionsubmarino[i]);
                } catch (JSONException ex) {
                    Logger.getLogger(TableroController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for (int i = 0; i < 4; i++) {
                try {
                     ubicacion.accumulate("carguero"+(i+1)+"", ubicacioncarguero[i]);
                } catch (JSONException ex) {
                    Logger.getLogger(TableroController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for (int i = 0; i < 5; i++) {
                try {
                    ubicacion.accumulate("guerra"+(i+1)+"", ubicacionguerra[i]);
                } catch (JSONException ex) {
                    Logger.getLogger(TableroController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println(ubicacion);
        socket.emit("acomodarBarcosEnTablero", ubicacion);
        BatallaController.initRootLayout(primaryStage);
        }
    
    private static String ubicacionbarco[] = new String[2];
    private static String ubicacionsubmarino[] = new String[3];
    private static String ubicacioncarguero[] = new String[4];
    private static String ubicacionguerra[] = new String[5];
    private int vacio[][] = new int[10][10];
    
    /**
     * 
     * @Descripción Función que carga el documento fxml como un recurso y muestra el menu principal de un usuario registrado.
     */
    @FXML
    void Regresar(ActionEvent event) throws Exception, Throwable {
      UsuarioRegistradoController.initRootLayout(primaryStage);
    }
    
    /**
     * @Descripción Función que carga el documento fxml como un recurso y muestra su pantalla correspondiente. 
     * @param primaryStage se declara una etapa o escena principal para inciar el programa y se hace referencia para llamarla.
     */
    public static void initRootLayout(Stage primaryStage) {
        try {
           TableroController.primaryStage = primaryStage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Juego.class.getResource("Tablero.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @Descripción Función que verfica que sea una orientacion valida.
     * @param posicion recibe un caracter a comparar con el cual se decide que tipo de orientacion se le dara.
     */
  public boolean orientacion(String posicion) {
        if (posicion.equalsIgnoreCase("h") || posicion.equalsIgnoreCase("v")) {
            return true;
        }
        return false;
    }
    /**
     * @Descripción Función que se encarga de verificar que este ocupada una casilla en caso de que se quiera poner un barco en una zona previamente ocupada. 
     * @param x se refiere a la variable columna del tablero.
     *  @param y se refiere a la coordenada o fila del tablero
     *  @return si esta ocupada una casilla se le asigna falso.
     */
    public boolean ocupado(String x, String y) {
        fila = Integer.parseInt(y);
        columna = Integer.parseInt(x);
        if (vacio[columna][fila] == 0) {
            return true;
        }
        return false;
    }
    /**
     * @Descripción Función que se encarga de verificar que haya ubicaciones validas. 
     * @param x se refiere a la variable columna del tablero.
     *  @return si esta ocupada una casilla se le asigna falso.
     */
    public boolean coordenadaX(String x) {
        if (x.equalsIgnoreCase("0") || x.equalsIgnoreCase("1") || x.equalsIgnoreCase("2")
                || x.equalsIgnoreCase("3") || x.equalsIgnoreCase("4") || x.equalsIgnoreCase("5")
                || x.equalsIgnoreCase("6") || x.equalsIgnoreCase("7") || x.equalsIgnoreCase("8") || x.equalsIgnoreCase("9")) {
            return true;
        }
        return false;

    }
/**
     * @Descripción Función que se encarga de verificar que haya ubicaciones validas .
     *  @param y se refiere a la coordenada o fila del tablero
     *  @return si esta ocupada una casilla se le asigna falso.
     */
    public boolean coordenadaY(String y) {
        if (y.equalsIgnoreCase("0") || y.equalsIgnoreCase("1") || y.equalsIgnoreCase("2")
                || y.equalsIgnoreCase("3") || y.equalsIgnoreCase("4") || y.equalsIgnoreCase("5")
                || y.equalsIgnoreCase("6") || y.equalsIgnoreCase("7") || y.equalsIgnoreCase("8") || y.equalsIgnoreCase("9")) {
            return true;
        }
        return false;
    }
     /**
     * @Descripción Función se encarga de agregar los barcos en el tablero segun la posicion, orientacion y el tipo de barco que se haya elegido.
     * @param espacios el tanto de espacio que se ocupara en el tablero.
     * @param posicion se refiere a la orientacion y las coordenadas donde se hayan elegido colocar.
     * @param x valores del eje x.
     * @param y valores del eje y.
     * @param tipoBarco el tipo de barco que se colocara en el tablero, varia de entre los 4 disponibles.
     */
    public void agregarBarcos(Button botones, int espacios, String posicion, String x, String y, int tipoBarco) {
        switch (tipoBarco) {
            case 1:
                if (posicion.equalsIgnoreCase("h")) {
                    fila = Integer.parseInt(y);
                    columna = Integer.parseInt(x);
                    if (vacio[columna][fila] == 1) {
                        if (columna >= 9) {
                            for (int j = 0; j < espacios; j++) {
                                botones = new Button("" + columna + "," + fila);
                                botones.setId("Barco");
                                botones.setMaxSize(45, 45);
                                botones.setStyle("-fx-background-color: black;");
                                Tablero.add(botones, columna, fila);
                                
                                ubicacionbarco[j] = "" + columna + "," + fila;
                                vacio[columna][fila] = 0;
                                columna--;
                            }
                        } else {
                            for (int j = 0; j < espacios; j++) {
                                botones = new Button("" + columna + "," + fila);
                                botones.setId("Barco");
                                botones.setMaxSize(45, 45);
                                botones.setStyle("-fx-background-color: black;");
                                Tablero.add(botones, columna, fila);
                                
                                ubicacionbarco[j] = "" + columna + "," + fila;
                                vacio[columna][fila] = 0;
                                columna++;
                            }
                        }
                    } else {
                        break;
                    }
                }
                if (posicion.equalsIgnoreCase("v")) {
                    columna = Integer.parseInt(x);
                    fila = Integer.parseInt(y);
                    if (vacio[fila][columna] == 1) {
                        if (fila >= 9) {
                            for (int j = 0; j < espacios; j++) {
                                botones = new Button("" + columna + "," + fila);
                                botones.setId("Barco");
                                botones.setMaxSize(45, 45);
                                botones.setStyle("-fx-background-color: black;");
                                Tablero.add(botones, columna, fila);
                                
                                ubicacionbarco[j] = "" + columna + "," + fila;
                                vacio[columna][fila] = 0;
                                fila--;
                            }
                        } else {
                            for (int j = 0; j < espacios; j++) {
                                botones = new Button("" + columna + "," + fila);
                                botones.setId("Barco");
                                botones.setMaxSize(45, 45);
                                botones.setStyle("-fx-background-color: black;");
                                Tablero.add(botones, columna, fila);
                                
                                ubicacionbarco[j] = "" + columna + "," + fila;
                                vacio[columna][fila] = 0;
                                fila++;
                            }
                        }
                    } else {
                        break;
                    }
                }
                break;
            case 2:
                if (posicion.equalsIgnoreCase("h")) {
                    fila = Integer.parseInt(y);
                    columna = Integer.parseInt(x);
                    if (vacio[columna][fila] == 1) {
                        if (columna >= 8) {
                            for (int j = 0; j < espacios; j++) {
                                botones = new Button("" + columna + "," + fila);
                                botones.setId("botonsubmarino");
                                botones.setMaxSize(45, 45);
                                botones.setStyle("-fx-background-color: darkcyan;");
                                Tablero.add(botones, columna, fila);
                                
                                ubicacionsubmarino[j] = "" + columna + "," + fila;
                                vacio[columna][fila] = 0;
                                columna--;
                            }
                        } else {
                            for (int j = 0; j < espacios; j++) {
                                botones = new Button("" + columna + "," + fila);
                                botones.setId("botonsubmarino");
                                botones.setMaxSize(45, 45);
                                botones.setStyle("-fx-background-color: darkcyan;");
                                Tablero.add(botones, columna, fila);
                              
                                ubicacionsubmarino[j] = "" + columna + "," + fila;
                                vacio[columna][fila] = 0;
                                columna++;
                            }
                        }
                    } else {
                        break;
                    }
                }
                if (posicion.equalsIgnoreCase("v")) {
                    columna = Integer.parseInt(x);
                    fila = Integer.parseInt(y);
                    if (vacio[fila][columna] == 1) {
                        if (fila >= 8) {
                            for (int j = 0; j < espacios; j++) {
                                botones = new Button("" + columna + "," + fila);
                                botones.setId("botonsubmarino");
                                botones.setMaxSize(45, 45);
                                botones.setStyle("-fx-background-color: darkcyan;");
                                Tablero.add(botones, columna, fila);
                              
                                ubicacionsubmarino[j] = "" + columna + "," + fila;
                                vacio[columna][fila] = 0;
                                fila--;
                            }
                        } else {
                            for (int j = 0; j < espacios; j++) {
                                botones = new Button("" + columna + "," + fila);
                                botones.setId("botonsubmarino");
                                botones.setMaxSize(45, 45);
                                botones.setStyle("-fx-background-color: darkcyan;");
                                Tablero.add(botones, columna, fila);
                                
                                ubicacionsubmarino[j] = "" + columna + "," + fila;
                                vacio[columna][fila] = 0;
                                fila++;
                            }
                        }
                    } else {
                        break;
                    }
                }
                break;
            case 3:
                if (posicion.equalsIgnoreCase("h")) {
                    fila = Integer.parseInt(y);
                    columna = Integer.parseInt(x);
                    if (vacio[columna][fila] == 1) {
                        if (columna >= 8) {
                            for (int j = 0; j < espacios; j++) {
                                botones = new Button("" + columna + "," + fila);
                                botones.setId("botoncarguero");
                                botones.setMaxSize(45, 45);
                                botones.setStyle("-fx-background-color: brown;");
                                Tablero.add(botones, columna, fila);
                                
                                ubicacioncarguero[j] = "" + columna + "," + fila;
                                vacio[columna][fila] = 0;
                                columna--;
                            }
                        } else {
                            for (int j = 0; j < espacios; j++) {
                                botones = new Button("" + columna + "," + fila);
                                botones.setId("botoncarguero");
                                botones.setMaxSize(45, 45);
                                botones.setStyle("-fx-background-color: brown;");
                                Tablero.add(botones, columna, fila);
                                
                                ubicacioncarguero[j] = "" + columna + "," + fila;
                                vacio[columna][fila] = 0;
                                columna++;
                            }
                        }
                    } else {
                        break;
                    }
                }
                if (posicion.equalsIgnoreCase("v")) {
                    columna = Integer.parseInt(x);
                    fila = Integer.parseInt(y);
                    if (vacio[fila][columna] == 1) {
                        if (fila >= 8) {
                            for (int j = 0; j < espacios; j++) {
                                botones = new Button("" + columna + "," + fila);
                                botones.setId("botoncarguero");
                                botones.setMaxSize(45, 45);
                                botones.setStyle("-fx-background-color: brown;");
                                Tablero.add(botones, columna, fila);
                                
                                ubicacioncarguero[j] = "" + columna + "," + fila;
                                vacio[columna][fila] = 0;
                                fila--;
                            }
                        }
                        for (int j = 0; j < espacios; j++) {
                            botones = new Button("" + columna + "," + fila);
                            botones.setId("botoncarguero");
                            botones.setMaxSize(45, 45);
                            botones.setStyle("-fx-background-color: brown;");
                            Tablero.add(botones, columna, fila);
                           
                            ubicacioncarguero[j] = "" + columna + "," + fila;
                            vacio[columna][fila] = 0;
                            fila++;
                        }
                    } else {
                        break;
                    }
                }
                break;
            case 4:
                if (posicion.equalsIgnoreCase("h")) {
                    fila = Integer.parseInt(y);
                    columna = Integer.parseInt(x);
                    if (vacio[columna][fila] == 1) {
                        if (columna >= 7) {
                            for (int j = 0; j < espacios; j++) {
                                botones = new Button("" + columna + "," + fila);
                                botones.setId("botonguerra");
                                botones.setMaxSize(45, 45);
                                botones.setStyle("-fx-background-color: darkgreen;");
                                Tablero.add(botones, columna, fila);
                                
                                ubicacionguerra[j] = "" + columna + "," + fila;
                                vacio[columna][fila] = 0;
                                columna--;
                            }
                        } else {
                            for (int j = 0; j < espacios; j++) {
                                botones = new Button("" + columna + "," + fila);
                                botones.setId("botonguerra");
                                botones.setMaxSize(45, 45);
                                botones.setStyle("-fx-background-color: darkgreen;");
                                Tablero.add(botones, columna, fila);
                                
                                ubicacionguerra[j] = "" + columna + "," + fila;
                                vacio[columna][fila] = 0;
                                columna++;
                            }
                        }
                    } else {
                        break;
                    }
                }
                if (posicion.equalsIgnoreCase("v")) {
                    columna = Integer.parseInt(x);
                    fila = Integer.parseInt(y);
                    if (vacio[fila][columna] == 1) {
                        if (fila >= 7) {
                            for (int j = 0; j < espacios; j++) {
                                botones = new Button("" + columna + "," + fila);
                                botones.setId("botonguerra");
                                botones.setMaxSize(45, 45);
                                botones.setStyle("-fx-background-color: darkgreen;");
                                Tablero.add(botones, columna, fila);
                                
                                ubicacionguerra[j] = "" + columna + "," + fila;
                                vacio[columna][fila] = 0;
                                fila--;
                            }
                        } else {
                            for (int j = 0; j < espacios; j++) {
                                botones = new Button("" + columna + "," + fila);
                                botones.setId("botonguerra");
                                botones.setMaxSize(45, 45);
                                botones.setStyle("-fx-background-color:red;");
                                Tablero.add(botones, columna, fila);
                                
                                ubicacionguerra[j] = "" + columna + "," + fila;
                                vacio[columna][fila] = 0;
                                fila++;
                            }
                        }
                    } else {
                        break;
                    }
                }
                break;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                vacio[i][j] = 1;
            }
        }
        this.llenarTablero();
        orientacionbarco.setOnKeyTyped((KeyEvent event) -> {
            char car = event.getCharacter().charAt(0);
            
            if (orientacionbarco.getText().length() > 0) {
                event.consume();
            }
        });
        orientacionsubmarino.setOnKeyTyped((KeyEvent event) -> {
            char car = event.getCharacter().charAt(0);
            
            if (orientacionsubmarino.getText().length() > 0) {
                event.consume();
            }
        });
        orientacioncarguero.setOnKeyTyped((KeyEvent event) -> {
            char car = event.getCharacter().charAt(0);
            
            if (orientacioncarguero.getText().length() > 0) {
                event.consume();
            }
        });
        orientacionguerra.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                char car = event.getCharacter().charAt(0);
                if (orientacionguerra.getText().length() > 0) {
                    event.consume();
                }
            }
        });
        barcox.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                char car = event.getCharacter().charAt(0);
                if (barcox.getText().length() > 0) {
                    event.consume();
                }
            }
        });
        barcoy.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                char car = event.getCharacter().charAt(0);
                if (barcoy.getText().length() > 0) {
                    event.consume();
                }
            }
        });
        submarinox.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                char car = event.getCharacter().charAt(0);
                if (submarinox.getText().length() > 0) {
                    event.consume();
                }
            }
        });
        submarinoy.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                char car = event.getCharacter().charAt(0);
                if (submarinoy.getText().length() > 0) {
                    event.consume();
                }
            }
        });
        carguerox.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                char car = event.getCharacter().charAt(0);
                if (carguerox.getText().length() > 0) {
                    event.consume();
                }
            }
        });
        cargueroy.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                char car = event.getCharacter().charAt(0);
                if (cargueroy.getText().length() > 0) {
                    event.consume();
                }
            }
        });
        guerrax.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                char car = event.getCharacter().charAt(0);
                if (guerrax.getText().length() > 0) {
                    event.consume();
                }
            }
        });
        guerray.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                char car = event.getCharacter().charAt(0);
                if (guerray.getText().length() > 0) {
                    event.consume();
                }
            }
        });
    }

    public void llenarTablero() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.Tablero.setGridLinesVisible(true);
                Button barcos = new Button();
                barcos.setMaxSize(45, 45);
                barcos.setId("libre");
                barcos.setStyle("-fx-background-color: lightblue;");
                this.Tablero.add(barcos, i, j);
               
                Barco.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
                    String bc = orientacionbarco.getText();
                    String ejeX = barcox.getText();
                    String ejeY = barcoy.getText();
                    if (coordenadaX(ejeX) == true && coordenadaY(ejeY) == true) {
                        if (!bc.equals("") && !ejeX.equals("") && !ejeY.equals("")) {
                            if (orientacion(bc) == true) {
                                if (ocupado(ejeX, ejeY) == false) {
                                    agregarBarcos(barcos, 2, bc, ejeX, ejeY, 1);
                                    orientacionbarco.setDisable(true);
                                    barcox.setDisable(true);
                                    barcoy.setDisable(true);
                                } else {
                                    if (ocupado(ejeX, ejeY) == true) {
                                        orientacionbarco.setDisable(false);
                                        barcox.setDisable(false);
                                        barcoy.setDisable(false);
                                    }
                                }

                            }
                            if (orientacion(bc) == false) {
                                orientacionbarco.setDisable(false);
                                barcox.setDisable(false);
                                barcoy.setDisable(false);
                            }
                        }
                        if (bc.equals("") && ejeX.equals("") && ejeY.equals("")) {
                           orientacionbarco.setDisable(false);
                            barcox.setDisable(false);
                            barcoy.setDisable(false);
                        }
                    }
                    if (coordenadaX(ejeX) == false || coordenadaY(ejeY) == false) {
                        barcox.setDisable(false);
                        barcoy.setDisable(false);
                    }
                });
                botonsubmarino.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
                    String sb = orientacionsubmarino.getText();
                    String ejeX = submarinox.getText();
                    String ejeY = submarinoy.getText();
                    if (coordenadaX(ejeX) == true && coordenadaY(ejeY) == true) {
                        if (!sb.equals("") && !ejeX.equals("") && !ejeY.equals("")) {
                            if (orientacion(sb) == true) {
                                if (ocupado(ejeX, ejeY) == false) {
                                    agregarBarcos(barcos, 3, sb, ejeX, ejeY, 2);
                                    orientacionsubmarino.setDisable(true);
                                    submarinox.setDisable(true);
                                    submarinoy.setDisable(true);
                                } else {
                                    if (ocupado(ejeX, ejeY) == true) {
                                        orientacionsubmarino.setDisable(false);
                                        submarinox.setDisable(false);
                                        submarinoy.setDisable(false);
                                    }
                                }

                            }
                            if (orientacion(sb) == false) {
                                orientacionsubmarino.setDisable(false);
                                submarinox.setDisable(false);
                                submarinoy.setDisable(false);
                            }
                        }
                        if (sb.equals("") && ejeX.equals("") && ejeY.equals("")) {
                            orientacionsubmarino.setDisable(false);
                            submarinox.setDisable(false);
                            submarinoy.setDisable(false);
                        }
                    }
                    if (coordenadaX(ejeX) == false || coordenadaY(ejeY) == false) {
                        submarinox.setDisable(false);
                        submarinoy.setDisable(false);
                    }

                });
                botoncarguero.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
                    String cg = orientacioncarguero.getText();
                    String ejeX = carguerox.getText();
                    String ejeY = cargueroy.getText();
                    if (coordenadaX(ejeX) == true && coordenadaY(ejeY) == true) {
                        if (!cg.equals("") && !ejeX.equals("") && !ejeY.equals("")) {
                            if (orientacion(cg) == true) {
                                if (ocupado(ejeX, ejeY) == false) {
                                    orientacioncarguero.setDisable(true);
                                    carguerox.setDisable(true);
                                    cargueroy.setDisable(true);
                                    agregarBarcos(barcos, 4, cg, ejeX, ejeY, 3);
                                }else{
                                    if (ocupado(ejeX, ejeY) == true) {
                                    orientacioncarguero.setDisable(false);
                                    carguerox.setDisable(false);
                                    cargueroy.setDisable(false);
                                }
                                }
                                
                            }
                            if (orientacion(cg) == false) {
                                orientacioncarguero.setDisable(false);
                                carguerox.setDisable(false);
                                cargueroy.setDisable(false);
                            }
                        }
                        if (cg.equals("") && ejeX.equals("") && ejeY.equals("")) {
                            orientacioncarguero.setDisable(false);
                            carguerox.setDisable(false);
                            cargueroy.setDisable(false);
                        }
                    }
                    if (coordenadaX(ejeX) == false || coordenadaY(ejeY) == false) {
                        carguerox.setDisable(false);
                        cargueroy.setDisable(false);
                    }

                });
                botonguerra.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
                    String gr = orientacionguerra.getText();
                    String ejeX = guerrax.getText();
                    String ejeY = guerray.getText();
                    if (coordenadaX(ejeX) == true && coordenadaY(ejeY) == true) {
                        if (!gr.equals("") && !ejeX.equals("") && !ejeY.equals("")) {
                            if (orientacion(gr) == true) {
                                if (ocupado(ejeX, ejeY) == false) {
                                    agregarBarcos(barcos, 5, gr, ejeX, ejeY, 4);
                                    orientacionguerra.setDisable(true);
                                    guerrax.setDisable(true);
                                    guerray.setDisable(true);
                                }else{
                                    if (ocupado(ejeX, ejeY) == true) {
                                    orientacionguerra.setDisable(false);
                                    guerrax.setDisable(false);
                                    guerray.setDisable(false);
                                }
                                }
                                
                            }
                            if (orientacion(gr) == false) {
                                orientacionguerra.setDisable(false);
                                guerrax.setDisable(false);
                                guerray.setDisable(false);
                            }
                        }
                        if (gr.equals("") && ejeX.equals("") && ejeY.equals("")) {
                           orientacionguerra.setDisable(false);
                            guerrax.setDisable(false);
                            guerray.setDisable(false);
                        }
                    }
                    if (coordenadaX(ejeX) == false || coordenadaY(ejeY) == false) {
                        guerrax.setDisable(false);
                        guerray.setDisable(false);
                    }
                });
            }
        }
    }
}
//problemas con el barco de guerra, verificalo