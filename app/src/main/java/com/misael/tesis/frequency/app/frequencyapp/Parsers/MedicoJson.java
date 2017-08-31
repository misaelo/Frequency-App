package com.misael.tesis.frequency.app.frequencyapp.Parsers;

import com.misael.tesis.frequency.app.frequencyapp.POJO.Medico;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Misael on 15-nov-16.
 */

public class MedicoJson {

    public static List<Medico> parse(String content){
        try {
            JSONArray jsonArray = new JSONArray(content);
            List<Medico> medicoList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Medico medico = new Medico();

                medico.setIdMedico(jsonObject.getString("id_medico"));
                medico.setPass(jsonObject.getString("pass"));
                medico.setNombre(jsonObject.getString("nombre"));
                medico.setApellidoP(jsonObject.getString("apellido_p"));
                medico.setApellidoM(jsonObject.getString("apellido_m"));
                medico.setTelefono(jsonObject.getInt("telefono"));
                medico.setCorreo(jsonObject.getString("correo"));
                medico.setRutaImg(jsonObject.getString("ruta_img"));
                medico.setFkClinica("fk_clinica");

                medicoList.add(medico);
            }
            return medicoList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
