package com.misael.tesis.frequency.app.frequencyapp.Parsers;

import com.misael.tesis.frequency.app.frequencyapp.POJO.Paciente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Misael on 15-nov-16.
 */

public class PacienteJson {
    public static List<Paciente> parse(String content){
        try {
            JSONArray jsonArray = new JSONArray(content);
            List<Paciente> pacienteList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Paciente paciente = new Paciente();

                paciente.setIdPaciente(jsonObject.getString("id_paciente"));
                paciente.setPass(jsonObject.getString("pass"));
                paciente.setNombre(jsonObject.getString("nombre"));
                paciente.setApellidoP(jsonObject.getString("apellido_p"));
                paciente.setApellidoM(jsonObject.getString("apellido_m"));
                paciente.setFechaNacimiento(jsonObject.getString("fecha_nacimiento"));
                paciente.setCiudad(jsonObject.getString("ciudad"));
                paciente.setDireccion(jsonObject.getString("direccion"));
                paciente.setTelefono(jsonObject.getInt("telefono"));
                paciente.setRutaImg(jsonObject.getString("ruta_img"));
                paciente.setFkMedico("fk_medico");

                pacienteList.add(paciente);
            }
            return pacienteList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
