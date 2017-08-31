package com.misael.tesis.frequency.app.frequencyapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.misael.tesis.frequency.app.frequencyapp.Conectar.ConecLogin;
import com.misael.tesis.frequency.app.frequencyapp.Conectar.HttpManager;
import com.misael.tesis.frequency.app.frequencyapp.Conectar.URLs;
import com.misael.tesis.frequency.app.frequencyapp.POJO.IDs;
import com.misael.tesis.frequency.app.frequencyapp.Parsers.IDsJson;
import com.misael.tesis.frequency.app.frequencyapp.R;

import java.util.List;

public class Login extends AppCompatActivity {

    ConecLogin conecLogin = new ConecLogin();
    public EditText EdtUsuario, EdtPass;
    public AppCompatButton BtnIngresar;
    List<IDs> iDsList;
    URLs urls = new URLs();
    public static final String MyPREFERENCES = "Login" ;
    public static final String Name = "user";
    public static final String Pass = "pass";
    public static final String Paciente = "paciente";
    public static final String Medico = "medico";
    public static final String Clinica = "clinica";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EdtUsuario = (EditText) findViewById(R.id.usuario);
        EdtPass = (EditText) findViewById(R.id.pass);
        BtnIngresar = (AppCompatButton) findViewById(R.id.ingresar);



        BtnIngresar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (EdtPass.getText().toString().isEmpty() && EdtUsuario.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Debe ingresar un usuario y contraseña", Toast.LENGTH_SHORT).show();
                }else {
                    //Toast.makeText(getApplicationContext(), "llama al metodo", Toast.LENGTH_SHORT).show();
                    myClickHandler();
                    //boton();
                }
            }
        });

    }

    public void myClickHandler() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            boton();
        } else {
            Toast.makeText(getApplicationContext(), "Sin Conexion", Toast.LENGTH_LONG).show();
            //this.recreate();
        }
    }
    public void boton(){
        progressDialog = ProgressDialog.show(Login.this, "Iniciando Sesión", "Por favor espere ...",false, false);
        final SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String user = EdtUsuario.getText().toString();
                final String pas= EdtPass.getText().toString();
                final String resultado = conecLogin.EnviarDatos(user, pas );

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = conecLogin.Valida(resultado);
                        if (r > 0) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Name, user);
                            editor.putString(Pass, pas);
                            editor.apply();
                            PedirDatos(urls.urlDatosPaciente(user,pas));
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Usuario o Contraseña Incorrectos", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }).start();
    }
    public void PedirDatos(String uri){
        MyTask task = new MyTask();
        task.execute(uri);
    }

    public void CargarDatos(){
        if (iDsList != null){
            for (final IDs iDs:iDsList) {
                SharedPreferences sharedPreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Clinica, iDs.getIdClinica());
                editor.putString(Medico, iDs.getIdMedico());
                editor.putString(Paciente, iDs.getIdPaciente());
                editor.apply();
                progressDialog.dismiss();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        }else {
            Toast.makeText(getApplicationContext(),"caagastemen",Toast.LENGTH_LONG).show();
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
            iDsList = IDsJson.parse(result);
            CargarDatos();
        }
    }


}