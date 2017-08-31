package com.misael.tesis.frequency.app.frequencyapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.misael.tesis.frequency.app.frequencyapp.Conectar.HttpManager;
import com.misael.tesis.frequency.app.frequencyapp.Conectar.URLs;
import com.misael.tesis.frequency.app.frequencyapp.POJO.Control;
import com.misael.tesis.frequency.app.frequencyapp.POJO.Paciente;
import com.misael.tesis.frequency.app.frequencyapp.Parsers.ControlJson;
import com.misael.tesis.frequency.app.frequencyapp.Parsers.PacienteJson;
import com.misael.tesis.frequency.app.frequencyapp.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PacientActivity extends AppCompatActivity {

    List<Paciente> pacienteList;
    List<Control> controlList;
    ImageView imgPaciente;
    TextView txtNombre, txtRut, txtEdad, txtDireccion, txtCiudad;
    TextView txtControles;
    URLs urls = new URLs();
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtNombre = (TextView) findViewById(R.id.nombrePaciente);
        txtRut = (TextView) findViewById(R.id.rutPaciente);
        txtEdad = (TextView) findViewById(R.id.edadPaciente);
        txtDireccion = (TextView) findViewById(R.id.direccionPaciente);
        txtCiudad = (TextView) findViewById(R.id.ciudadPaciente);
        txtControles = (TextView) findViewById(R.id.controles);

        SharedPreferences sharedPreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        final String paciente = sharedPreferences.getString("paciente", null);
        final String medico = sharedPreferences.getString("medico", null);

        PedirDatos(urls.urlPaciente(paciente));
        PedirDatosControles(urls.urlControl(paciente,medico));

    }

    public void PedirDatos(String uri){
        progressDialog = ProgressDialog.show(PacientActivity.this, "Cargando Datos", "Por favor espere ...",false, false);
        MyTask task = new MyTask();
        task.execute(uri);
    }

    public void CargarDatos(){
        if (pacienteList != null){
            for (final Paciente paciente:pacienteList) {
                txtEdad.setText(paciente.getFechaNacimiento());
                txtNombre.setText(paciente.getNombre() + " " + paciente.getApellidoP() + " " + paciente.getApellidoM());
                txtRut.setText(paciente.getIdPaciente());
                txtDireccion.setText(paciente.getDireccion());
                txtCiudad.setText(paciente.getCiudad());
            }
        }else {
            Toast.makeText(getApplicationContext(),"Error al Cargar datos",Toast.LENGTH_LONG).show();
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
            pacienteList = PacienteJson.parse(result);
            CargarDatos();
        }
    }

    public void PedirDatosControles(String uri){
        MyTaskControl task = new MyTaskControl();
        task.execute(uri);
    }

    public void CargarDatosControles(){
        if (controlList != null){
            for (final Control control:controlList) {
                String completionDate1 = control.getFecha();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                try {
                    date = df.parse(completionDate1);
                    DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
                    txtControles.append(df1.format(date) + " a las " + control.getHora()+"\n \n");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }else {
            Toast.makeText(getApplicationContext(),"Error al Cargar Controles",Toast.LENGTH_LONG).show();
        }
    }

    private class MyTaskControl extends AsyncTask<String, String,String> {
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
            controlList = ControlJson.parse(result);
            CargarDatosControles();
        }
    }
}
