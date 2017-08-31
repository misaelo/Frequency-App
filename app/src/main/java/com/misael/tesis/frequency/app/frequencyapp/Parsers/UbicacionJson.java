package com.misael.tesis.frequency.app.frequencyapp.Parsers;

import com.misael.tesis.frequency.app.frequencyapp.POJO.Ubicacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Misael on 03-dic-16.
 */

public class UbicacionJson {
    public static List<Ubicacion> parse(String content){
        try {
            JSONArray jsonArray = new JSONArray(content);
            List<Ubicacion> ubicacionList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Ubicacion ubicacion = new Ubicacion();

                ubicacion.setFkPaciente(jsonObject.getString("fk_paciente"));
                ubicacion.setLatitud(jsonObject.getDouble("latitud"));
                ubicacion.setLongitud(jsonObject.getDouble("longitud"));

                ubicacionList.add(ubicacion);
            }
            return ubicacionList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
