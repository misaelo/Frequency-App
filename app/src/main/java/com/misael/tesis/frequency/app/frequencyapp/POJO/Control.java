package com.misael.tesis.frequency.app.frequencyapp.POJO;

/**
 * Created by Misael on 15-nov-16.
 */

public class Control {
    private int IdControl;
    private String Fecha;
    private String Hora;
    private String fkPaciente;
    private String fkMedico;

    public int getIdControl() {
        return IdControl;
    }

    public void setIdControl(int idControl) {
        IdControl = idControl;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getFkPaciente() {
        return fkPaciente;
    }

    public void setFkPaciente(String fkPaciente) {
        this.fkPaciente = fkPaciente;
    }

    public String getFkMedico() {
        return fkMedico;
    }

    public void setFkMedico(String fkMedico) {
        this.fkMedico = fkMedico;
    }
}
