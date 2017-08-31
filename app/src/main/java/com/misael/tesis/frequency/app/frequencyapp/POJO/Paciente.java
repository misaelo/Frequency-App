package com.misael.tesis.frequency.app.frequencyapp.POJO;

import java.util.Date;

/**
 * Created by Misael on 15-nov-16.
 */

public class Paciente {

    private String IdPaciente;
    private String Pass;
    private String Nombre;
    private String ApellidoP;
    private String ApellidoM;
    private String FechaNacimiento;
    private String Ciudad;
    private String Direccion;
    private int Telefono;
    private String RutaImg;
    private String fkMedico;

    public String getIdPaciente() {
        return IdPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        IdPaciente = idPaciente;
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

    public String getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        FechaNacimiento = fechaNacimiento;
    }

    public String getCiudad() {
        return Ciudad;
    }

    public void setCiudad(String ciudad) {
        Ciudad = ciudad;
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

    public String getRutaImg() {
        return RutaImg;
    }

    public void setRutaImg(String rutaImg) {
        RutaImg = rutaImg;
    }

    public String getFkMedico() {
        return fkMedico;
    }

    public void setFkMedico(String fkMedico) {
        this.fkMedico = fkMedico;
    }
}
