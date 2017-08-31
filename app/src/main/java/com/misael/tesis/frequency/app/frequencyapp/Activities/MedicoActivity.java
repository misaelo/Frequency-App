package com.misael.tesis.frequency.app.frequencyapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.misael.tesis.frequency.app.frequencyapp.Conectar.HttpManager;
import com.misael.tesis.frequency.app.frequencyapp.Conectar.URLs;
import com.misael.tesis.frequency.app.frequencyapp.POJO.Clinica;
import com.misael.tesis.frequency.app.frequencyapp.POJO.Medico;
import com.misael.tesis.frequency.app.frequencyapp.Parsers.ClinicaJson;
import com.misael.tesis.frequency.app.frequencyapp.Parsers.MedicoJson;
import com.misael.tesis.frequency.app.frequencyapp.R;

import java.util.List;

public class MedicoActivity extends AppCompatActivity {

    List<Medico> medicoList;
    List<Clinica>  clinicaList;
    ImageView imgMedico;
    TextView txtNombre, txtRut, txtCorreo, txtClinica, txtDireccionClinica, txtTelefonoClinica, txtCorreoClinica;
    URLs urls = new URLs();
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        txtNombre = (TextView) findViewById(R.id.nombreDoctor);
        txtRut = (TextView) findViewById(R.id.rutDoctor);
        txtCorreo = (TextView) findViewById(R.id.correoDoctor);
        txtClinica = (TextView) findViewById(R.id.nombreClinica);
        txtCorreoClinica = (TextView) findViewById(R.id.correoClinica);
        txtDireccionClinica = (TextView) findViewById(R.id.direccionClinica);
        txtTelefonoClinica = (TextView) findViewById(R.id.telefonoClinica);

        SharedPreferences sharedPreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        final String medico = sharedPreferences.getString("medico", null);
        final String clinica = sharedPreferences.getString("clinica", null);

        PedirDatosClinca(urls.urlClinica(clinica));
        PedirDatos(urls.urlMedico(medico));

    }

    public void PedirDatos(String uri){
        progressDialog = ProgressDialog.show(MedicoActivity.this, "Cargando Datos", "Por favor espere ...",false, false);
        MyTask task = new MyTask();
        task.execute(uri);
    }

    public void CargarDatos(){
        if (medicoList != null){
            for (final Medico medico:medicoList) {
                txtNombre.setText(medico.getNombre() + " " + medico.getApellidoP() + " " + medico.getApellidoM());
                txtRut.setText(medico.getIdMedico());
                txtCorreo.setText(medico.getCorreo());
            }
        }else {
            Toast.makeText(getApplicationContext(),"Error Medico",Toast.LENGTH_LONG).show();
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
            progressDialog.dismiss();
            medicoList = MedicoJson.parse(result);
            CargarDatos();
        }
    }

    public void PedirDatosClinca(String uri){
        MyTaskClinica task = new MyTaskClinica();
        task.execute(uri);
    }

    public void CargarDatosClinica(){
        if (clinicaList != null){
            for (final Clinica clinica:clinicaList) {
                txtClinica.setText(clinica.getNombre());
                txtCorreoClinica.setText(clinica.getCorreo());
                txtTelefonoClinica.setText(""+clinica.getTelefono());
                txtDireccionClinica.setText(clinica.getDireccion() + ", "+ clinica.getComuna());
            }
        }else {
            Toast.makeText(getApplicationContext(),"Error Clinica",Toast.LENGTH_LONG).show();
        }
    }

    private class MyTaskClinica extends AsyncTask<String, String,String> {
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
            clinicaList = ClinicaJson.parse(result);
            CargarDatosClinica();
        }
    }
}
