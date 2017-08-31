package com.misael.tesis.frequency.app.frequencyapp.POJO;

/**
 * Created by Misael on 15-nov-16.
 */

public class Medico {

    private String IdMedico;
    private String Pass;
    private String Nombre;
    private String ApellidoP;
    private String ApellidoM;
    private int Telefono;
    private String Correo;
    private String RutaImg;
    private String fkClinica;

    public String getIdMedico() {
        return IdMedico;
    }

    public void setIdMedico(String idCuidador) {
        IdMedico = idCuidador;
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

    public String getRutaImg() {
        return RutaImg;
    }

    public void setRutaImg(String rutaImg) {
        RutaImg = rutaImg;
    }

    public String getFkClinica() {
        return fkClinica;
    }

    public void setFkClinica(String fkClinica) {
        this.fkClinica = fkClinica;
    }
}
