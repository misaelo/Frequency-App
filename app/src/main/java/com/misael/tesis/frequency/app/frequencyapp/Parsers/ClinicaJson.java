package com.misael.tesis.frequency.app.frequencyapp.Parsers;

import com.misael.tesis.frequency.app.frequencyapp.POJO.Clinica;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Misael on 19-nov-16.
 */

public class ClinicaJson {
    public static List<Clinica> parse(String content){
        try {
            JSONArray jsonArray = new JSONArray(content);
            List<Clinica> clinicaList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Clinica clinica = new Clinica();

                clinica.setIdClinica(jsonObject.getString("id_clinica"));
                clinica.setPass(jsonObject.getString("pass"));
                clinica.setNombre(jsonObject.getString("nombre"));
                clinica.setRegion(jsonObject.getString("region"));
                clinica.setComuna(jsonObject.getString("comuna"));
                clinica.setDireccion(jsonObject.getString("direccion"));
                clinica.setTelefono(jsonObject.getInt("telefono"));
                clinica.setCorreo(jsonObject.getString("correo"));

                clinicaList.add(clinica);
            }
            return clinicaList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
