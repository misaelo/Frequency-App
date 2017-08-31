package com.misael.tesis.frequency.app.frequencyapp.POJO;

/**
 * Created by Misael on 19-nov-16.
 */

public class Clinica {

    private String IdClinica;
    private String Pass;
    private String Nombre;
    private String Region;
    private String Comuna;
    private String Direccion;
    private int Telefono;
    private String Correo;

    public String getIdClinica() {
        return IdClinica;
    }

    public void setIdClinica(String idClinica) {
        IdClinica = idClinica;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getComuna() {
        return Comuna;
    }

    public void setComuna(String comuna) {
        Comuna = comuna;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public int getTelefono() {
        return Telefono;
    }

    public void setTelefono(int telefono) {
        Telefono = telefono;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }
}
