package com.misael.tesis.frequency.app.frequencyapp.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.widget.ImageView;
import android.widget.Toast;

import com.misael.tesis.frequency.app.frequencyapp.Conectar.HttpManager;
import com.misael.tesis.frequency.app.frequencyapp.Conectar.RequestPackage;
import com.misael.tesis.frequency.app.frequencyapp.Conectar.URLs;
import com.misael.tesis.frequency.app.frequencyapp.R;
import com.misael.tesis.frequency.app.frequencyapp.dummy.DummyContent;

/**
 * An activity representing a single Cuidador detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link CuidadorListActivity}.
 */

public class CuidadorDetailActivity extends AppCompatActivity {

    FloatingActionButton floEditar, floEliminar;
    ImageView imgCuidador;
    URLs urls = new URLs();
    DummyContent dummyContent = new DummyContent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuidador_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        final CuidadorDetailFragment fragment = new CuidadorDetailFragment();
        //final String mma;
        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(CuidadorDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(CuidadorDetailFragment.ARG_ITEM_ID));
            //CuidadorDetailFragment fragment = new CuidadorDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.cuidador_detail_container, fragment)
                    .commit();
        }
        final String id = fragment.getArguments().getString("item_id");

        imgCuidador = (ImageView) findViewById(R.id.imgCuidadorDetalle);
        floEditar = (FloatingActionButton) findViewById(R.id.editarCuidador);
        floEliminar = (FloatingActionButton) findViewById(R.id.eliminarCuidador);

        floEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EditarCuidadorActivity.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });

        floEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(CuidadorDetailActivity.this)
                        .setTitle("Borrar Cuidador")
                        .setMessage("¿Esta seguro que desea borrar este cuidador?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //Toast.makeText(getApplicationContext(),"apreto si en id="+mma,Toast.LENGTH_SHORT).show();
                                PedirDatos(urls.urlElininaCuidador(),id);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"No se realizaron cambios",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void PedirDatos(String uri,String id){

        MyTask task = new MyTask();
        RequestPackage Req =new RequestPackage();
        Req.setMethod("GET");
        Req.setUri(uri);
        Req.setParam("id", id);

        task.execute(Req);
    }

    private class MyTask extends AsyncTask<RequestPackage, String,String> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //lo que se muestra al inicio
        }
        @Override
        protected String doInBackground(RequestPackage... params) {
            //lo que se ve al final
            String content = HttpManager.getDataEnvio(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            int re = result.length();
            if (re ==3){
                Toast.makeText(getApplicationContext(),"Se Elimino al Cuidador",Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }else {
                Toast.makeText(getApplicationContext(),"Error al Eliminar",Toast.LENGTH_LONG).show();
            }

        }
    }

}
