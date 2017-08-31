package com.misael.tesis.frequency.app.frequencyapp.Parsers;

import com.misael.tesis.frequency.app.frequencyapp.POJO.IDs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Misael on 15-nov-16.
 */

public class IDsJson {
    public static List<IDs> parse(String content){
        try {
            JSONArray jsonArray = new JSONArray(content);
            List<IDs> iDsList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                IDs iDs = new IDs();

                iDs.setIdClinica(jsonObject.getString("id_clinica"));
                iDs.setIdMedico(jsonObject.getString("id_medico"));
                iDs.setIdPaciente(jsonObject.getString("id_paciente"));

                iDsList.add(iDs);
            }
            return iDsList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
