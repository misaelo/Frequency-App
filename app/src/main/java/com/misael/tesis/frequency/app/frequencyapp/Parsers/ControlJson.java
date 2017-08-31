package com.misael.tesis.frequency.app.frequencyapp.Parsers;

import com.misael.tesis.frequency.app.frequencyapp.POJO.Control;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Misael on 15-nov-16.
 */

public class ControlJson {
    public static List<Control> parse(String content){
        try {
            JSONArray jsonArray = new JSONArray(content);
            List<Control> controlList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Control control = new Control();

                control.setIdControl(jsonObject.getInt("id_control"));
                control.setFecha(jsonObject.getString("fecha"));
                control.setHora(jsonObject.getString("hora"));
                control.setFkPaciente(jsonObject.getString("fk_paciente"));
                control.setFkMedico(jsonObject.getString("fk_medico"));


                controlList.add(control);
            }
            return controlList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
