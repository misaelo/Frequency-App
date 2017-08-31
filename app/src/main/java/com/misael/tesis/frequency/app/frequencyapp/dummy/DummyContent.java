package com.misael.tesis.frequency.app.frequencyapp.dummy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.misael.tesis.frequency.app.frequencyapp.Activities.CuidadorListActivity;
import com.misael.tesis.frequency.app.frequencyapp.Conectar.HttpManager;
import com.misael.tesis.frequency.app.frequencyapp.POJO.Cuidador;
import com.misael.tesis.frequency.app.frequencyapp.Parsers.CuidadorJson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {
    List<Cuidador> cuidadorList;
    ProgressDialog progressDialog;
    Context context;
    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 25;

    public void PedirDatos(String uri){
        MyTask task = new MyTask();
        task.execute(uri);
    }

    public void CargarDatos(){
        if (cuidadorList != null){
            ITEMS.clear();
            for (final Cuidador cuidador:cuidadorList) {
                //Log.e("Cuidador", "biene "+ cuidador.getNombre());
                addItem(new DummyItem(cuidador.getIdCuidador(),cuidador.getNombre(), cuidador.getApellidoP(),
                        cuidador.getApellidoM(), cuidador.getCorreo(), cuidador.getDireccion(), cuidador.getTelefono(), cuidador.getRutaImagen()));
            }
        }else {
            Toast.makeText(context,"Error al Cargar Cuidadores", Toast.LENGTH_LONG).show();
        }
    }

    private class MyTask extends AsyncTask<String, String,String> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //lo que se muestra al inicio
        }
        @Override
        protected String doInBackground(String... params) {
            //lo que se ve al final
            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            cuidadorList = CuidadorJson.parse(result);
            CargarDatos();
            //progressDialog.dismiss();
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
    /*
    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }*/

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String nombre;
        public final String apellidoP;
        public final String apellidoM;
        public final String correo;
        public final String direccion;
        public final String telefono;
        public final String imagen;

        public DummyItem(String id, String nombre, String apellidoP, String apellidoM, String correo, String direccion, String telefono, String imagen ) {
            this.id = id;
            this.nombre = nombre;
            this.apellidoP = apellidoP;
            this.apellidoM = apellidoM;
            this.correo = correo;
            this.direccion = direccion;
            this.telefono = telefono;
            this.imagen = imagen;
        }

        @Override
        public String toString() {

            return nombre +" "+ apellidoP +" "+ apellidoM;
        }
    }
}
