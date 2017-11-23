/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author EHECA
 */
import java.util.HashMap;
import java.util.Map;

public class Usuario {
    
    private static final Map<String, Usuario> Usuarios = new HashMap<String, Usuario>();

    public static Usuario de(String usuario) {
        Usuario usuariojuego = Usuarios.get(usuario);
        if (usuariojuego == null) {
            usuariojuego = new Usuario(usuario);
            Usuarios.put(usuario, usuariojuego);
        }
        return usuariojuego;
    }

    private Usuario(String usuario) {
        this.usuario = usuario;
    }
    
    private String usuario;

   /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }
    
    private String Contraseña = "";
    private String Correoelectronico = "";
    private String Nombrecompleto = "";

    /**
     * @return the Contraseña
     */
    public String getContraseña() {
        return Contraseña;
    }

    /**
     * @param Contraseña the Contraseña to set
     */
    public void setContraseña(String Contraseña) {
        this.Contraseña = Contraseña;
    }

    /**
     * @return the Correoelectronico
     */
    public String getCorreoelectronico() {
        return Correoelectronico;
    }

    /**
     * @param Correoelectronico the Correoelectronico to set
     */
    public void setCorreoelectronico(String Correoelectronico) {
        this.Correoelectronico = Correoelectronico;
    }

    /**
     * @return the Nombrecompleto
     */
    public String getNombrecompleto() {
        return Nombrecompleto;
    }

    /**
     * @param Nombrecompleto the Nombrecompleto to set
     */
    public void setNombrecompleto(String Nombrecompleto) {
        this.Nombrecompleto = Nombrecompleto;
    }

    
}
