package com.misael.tesis.frequency.app.frequencyapp.POJO;

/**
 * Created by Misael on 08-nov-16.
 */

public class Cuidador {
    private String IdCuidador;
    private String Pass;
    private String Nombre;
    private String ApellidoP;
    private String ApellidoM;
    private String Direccion;
    private String Correo;
    private String Telefono;
    private int Rol;
    private String RutaImagen;
    private String fkPaciente;

    public String getIdCuidador() {

        return IdCuidador;
    }

    public void setIdCuidador(String idCuidador) {
        IdCuidador = idCuidador;
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

    public String getApellidoP() {
        return ApellidoP;
    }

    public void setApellidoP(String apellidoP) {
        ApellidoP = apellidoP;
    }

    public String getApellidoM() {
        return ApellidoM;
    }

    public void setApellidoM(String apellidoM) {
        ApellidoM = apellidoM;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public int getRol() {
        return Rol;
    }

    public void setRol(int rol) {
        Rol = rol;
    }

    public String getRutaImagen() {
        return RutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        RutaImagen = rutaImagen;
    }

    public String getFkPaciente() {
        return fkPaciente;
    }

    public void setFkPaciente(String fkPaciente) {
        this.fkPaciente = fkPaciente;
    }
}
