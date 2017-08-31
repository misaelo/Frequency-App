package com.misael.tesis.frequency.app.frequencyapp.Parsers;

import com.misael.tesis.frequency.app.frequencyapp.POJO.Cuidador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Misael on 09-nov-16.
 */

public class CuidadorJson {

    public static List<Cuidador> parse(String content){
        try {
            JSONArray jsonArray = new JSONArray(content);
            List<Cuidador> cuidadorList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Cuidador cuidador = new Cuidador();

                cuidador.setIdCuidador(jsonObject.getString("id_cuidador"));
                cuidador.setPass(jsonObject.getString("pass"));
                cuidador.setNombre(jsonObject.getString("nombre"));
                cuidador.setApellidoP(jsonObject.getString("apellido_p"));
                cuidador.setApellidoM(jsonObject.getString("apellido_m"));
                cuidador.setDireccion(jsonObject.getString("direccion"));
                cuidador.setCorreo(jsonObject.getString("correo"));
                cuidador.setTelefono(jsonObject.getString("telefono"));
                cuidador.setRol(jsonObject.getInt("rol"));
                cuidador.setRutaImagen(jsonObject.getString("ruta_img"));
                cuidador.setFkPaciente("fk_paciente");

                cuidadorList.add(cuidador);
            }
            return cuidadorList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
