package com.misael.tesis.frequency.app.frequencyapp.Conectar;

/**
 * Created by Misael on 29-nov-16.
 */

public class URLs {

    //public String server = "http://192.168.56.1:80/tesis/webservice/";
    //public String imgServer = "http://192.168.56.1:80/tesis/imagen/";
    public String pagina = "http://frequency.hol.es";
    public String server = "http://frequency.hol.es:80/webservice/";
    public String imgServer = "http://frequency.hol.es:80/imagen/";

    public String urlCuidador(String idCuidador){
        String url = server+"cuidador.php?id="+idCuidador;
        return url;
    }

    public String urlValida(String idCuidador, String pass){
        String url = server+"valida.php?id="+idCuidador+"&pas="+pass;
        return url;
    }

    public String urlUbicacion(String idpaciente){
        String url = server+"ubicacion.php?fkp="+idpaciente;
        return url;
    }

    public String urlPaciente(String idPaciente){
        String url = server+"paciente.php?id="+idPaciente;
        return url;
    }

    public String urlClinica(String idClinica){
        String url = server+"clinica.php?id="+idClinica;
        return url;
    }

    public String urlControl(String idPaciente,String idMedico){
        String url = server+"control.php?idp="+idPaciente+"&idm="+idMedico;
        return url;
    }

    public String urlMedico(String idMedico){
        String url = server+"medico.php?id="+idMedico;
        return url;
    }

    public String urlListaCuidador(String idPaciente){
        String url = server+"listacuidador.php?id="+idPaciente;
        return url;
    }

    public String urlDatosPaciente(String idCuidador, String pass){
        String url = server+"datospaciente.php?id="+idCuidador+"&pas="+pass;
        return url;
    }

    public String urlIngresoCuidador(){
        String url = server+"ingreso_cuidador.php";
        return url;
    }

    public String urlElininaCuidador(){
        String url = server+"eliminar_cuidador.php";
        return url;
    }

    public String urlActualizaCuidador(){
        String url = server+ "actualizar_cuidador.php";
        return url;
    }

    public String urlComidas(){
        String url =server+ "comida.php";
        return url;
    }

    public String urlObservacion(){
        String url = server+"observacion.php";
        return url;
    }

    public String urlSueno(){
        String url = server+"sueno.php";
        return url;
    }

}
