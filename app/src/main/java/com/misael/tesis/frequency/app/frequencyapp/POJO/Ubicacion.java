package com.misael.tesis.frequency.app.frequencyapp.POJO;

/**
 * Created by Misael on 03-dic-16.
 */

public class Ubicacion {

    private String fkPaciente;
    private Double Latitud;
    private Double Longitud;

    public String getFkPaciente() {
        return fkPaciente;
    }

    public void setFkPaciente(String fkPaciente) {
        this.fkPaciente = fkPaciente;
    }

    public Double getLatitud() {
        return Latitud;
    }

    public void setLatitud(Double fecha) {
        Latitud = fecha;
    }

    public Double getLongitud() {
        return Longitud;
    }

    public void setLongitud(Double hora) {
        Longitud = hora;
    }
}
