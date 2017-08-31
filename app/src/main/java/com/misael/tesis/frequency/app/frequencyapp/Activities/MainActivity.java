package com.misael.tesis.frequency.app.frequencyapp.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.misael.tesis.frequency.app.frequencyapp.Conectar.HttpManager;
import com.misael.tesis.frequency.app.frequencyapp.Conectar.URLs;
import com.misael.tesis.frequency.app.frequencyapp.POJO.Cuidador;
import com.misael.tesis.frequency.app.frequencyapp.Parsers.CuidadorJson;
import com.misael.tesis.frequency.app.frequencyapp.R;
import com.misael.tesis.frequency.app.frequencyapp.Ajustes.SettingsActivity;
import com.misael.tesis.frequency.app.frequencyapp.dummy.DummyContent;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    List<Cuidador> cuidadorList;
    TextView txtNombre, txtCorreo, txtDia, txtMes;
    ImageView imgUser;
    CardView cdvCalendario, cdvDatos, cdvCuidador, cdvCuidadorAdd, cdvSitios, cdvPreguntas, cdvEmergencias, cdvUbicaion;
    URLs urls = new URLs();
    DummyContent dummyContent = new DummyContent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //instancia texto de menu lateral
        View header=navigationView.getHeaderView(0);
        txtNombre = (TextView) header.findViewById(R.id.nombreUser);
        txtCorreo = (TextView) header.findViewById(R.id.correoUser);
        imgUser = (ImageView) header.findViewById(R.id.imgUser);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        SimpleDateFormat year_date = new SimpleDateFormat("yyy");
        SimpleDateFormat day_date = new SimpleDateFormat("dd");
        String mes = month_date.format(calendar.getTime());
        String dia = day_date.format(calendar.getTime());
        String ano = year_date.format(calendar.getTime());

        cdvCalendario = (CardView) findViewById(R.id.calendarCard);
        cdvEmergencias = (CardView) findViewById(R.id.emergenciaCard);
        cdvDatos = (CardView) findViewById(R.id.datosCard);
        cdvCuidador = (CardView) findViewById(R.id.cuidadorCard);
        cdvCuidadorAdd = (CardView) findViewById(R.id.cuidadorAddCard);
        cdvSitios = (CardView) findViewById(R.id.sitiosCard);
        cdvPreguntas = (CardView) findViewById(R.id.preguntasCard);
        cdvUbicaion = (CardView) findViewById(R.id.ubicacionCard);


        txtDia = (TextView) findViewById(R.id.dia);
        txtMes = (TextView) findViewById(R.id.mes);
        txtDia.setText(dia);
        txtMes.setText(mes +" "+ ano );

        cdvCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(i);
            }
        });

        cdvUbicaion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(i);
            }
        });

        cdvDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DatosActivity.class);
                startActivity(i);
            }
        });

        cdvCuidador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarCuidadores();
            }
        });
        cdvCuidadorAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegistraCuidadorActivity.class);
                startActivity(i);
            }
        });

        cdvPreguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(urls.pagina);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        cdvSitios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(urls.pagina);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        cdvEmergencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSingleListDialog().show();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        final String User = sharedPreferences.getString("user", null);
        //final String Pass = sharedPreferences.getString("pass", null);
        PedirDatos(urls.urlCuidador(User));

        final String paciente = sharedPreferences.getString("paciente", null);
        dummyContent.PedirDatos(urls.urlListaCuidador(paciente));
    }

    public AlertDialog createSingleListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        final CharSequence[] items = new CharSequence[5];

        items[0] = "SAPU";
        items[1] = "Hospital";
        items[2] = "Ambulancia";
        items[3] = "Carabineros";
        items[4] = "Bomberos";

        builder.setTitle("Numeros de Emergencia")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        switch(items.length){
                            case 0:
                                intent.setData(Uri.parse("tel: 131"));
                                break;
                            case 1:
                                intent.setData(Uri.parse("tel:131"));
                                break;
                            case 2:
                                intent.setData(Uri.parse("tel:131"));
                                break;
                            case 3:
                                intent.setData(Uri.parse("tel:133"));
                                break;
                            case 4:
                                intent.setData(Uri.parse("tel:132"));
                                break;
                        }
                        startActivity(intent);
                    }
                });

        return builder.create();
    }


    public void PedirDatos(String uri){
        MyTask task = new MyTask();
        task.execute(uri);
    }

    public void CargarDatos(){
        if (cuidadorList != null){
            for (final Cuidador cuidador:cuidadorList) {
                txtCorreo.setText(cuidador.getCorreo());
                txtNombre.setText(cuidador.getNombre() + " " + cuidador.getApellidoP() + " " + cuidador.getApellidoM());
                Picasso.with(getApplicationContext()).load(cuidador.getRutaImagen()).into(imgUser);
            }
        }else {
            Toast.makeText(getApplicationContext(),"No se pudo Cargar Datos",Toast.LENGTH_LONG).show();
        }
    }

    public void CargarCuidadores(){
        Intent i = new Intent(getApplicationContext(), CuidadorListActivity.class);
        startActivity(i);
    }

    private class MyTask extends AsyncTask<String, String,String>{
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
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.logout){
            Logout();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.calendario) {
            Intent i = new Intent(getApplicationContext(), CalendarActivity.class);
            startActivity(i);
        } else if (id == R.id.cuidadores) {
            CargarCuidadores();
        } else if (id == R.id.paciente) {
            Intent i = new Intent(getApplicationContext(), PacientActivity.class);
            startActivity(i);
        } else if (id == R.id.doctor) {
            Intent i = new Intent(getApplicationContext(), MedicoActivity.class);
            startActivity(i);
        } else if (id == R.id.notificacion) {

        } else if (id == R.id.ajustes) {
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void Logout(){
        SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("user", null);
        editor.putString("pass", null);
        editor.putString("clinica", null);
        editor.putString("medico", null);
        editor.putString("paciente", null);
        editor.apply();

        Toast.makeText(getApplicationContext(),"Session Cerrada",Toast.LENGTH_LONG).show();
        Intent i = new Intent(getApplicationContext(), Login.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
