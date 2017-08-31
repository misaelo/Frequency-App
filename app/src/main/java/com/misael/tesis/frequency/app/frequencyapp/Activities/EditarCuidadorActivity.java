package com.misael.tesis.frequency.app.frequencyapp.Activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.misael.tesis.frequency.app.frequencyapp.Activities.CuidadorListActivity;
import com.misael.tesis.frequency.app.frequencyapp.Activities.Login;
import com.misael.tesis.frequency.app.frequencyapp.Conectar.HttpManager;
import com.misael.tesis.frequency.app.frequencyapp.Conectar.RequestPackage;
import com.misael.tesis.frequency.app.frequencyapp.Conectar.URLs;
import com.misael.tesis.frequency.app.frequencyapp.POJO.Cuidador;
import com.misael.tesis.frequency.app.frequencyapp.Parsers.CuidadorJson;
import com.misael.tesis.frequency.app.frequencyapp.R;
import com.misael.tesis.frequency.app.frequencyapp.dummy.DummyContent;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class EditarCuidadorActivity extends AppCompatActivity {

    List<Cuidador> cuidadorList;
    EditText etxtNombre, etxtApellidoP, etxtApellidoM, etxtCorreo, etxtTelefono, etxtRut,etxtPass, etxtDireccion;
    AppCompatButton btnImagen, btnGuardar;
    ImageView imgCuidadorEdit;
    LinearLayout lytRegistra;
    URLs urls = new URLs();
    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";
    private String mPath;
    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cuidador);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etxtNombre = (EditText) findViewById(R.id.nombreEditar);
        etxtApellidoM = (EditText) findViewById(R.id.apellidomEditar);
        etxtApellidoP = (EditText) findViewById(R.id.apellidopEditar);
        etxtCorreo = (EditText) findViewById(R.id.correoEditar);
        etxtTelefono = (EditText) findViewById(R.id.telefonoEditar);
        etxtRut = (EditText) findViewById(R.id.rutEditar);
        etxtPass = (EditText) findViewById(R.id.passEditar);
        etxtDireccion = (EditText) findViewById(R.id.direccionEditar);
        btnImagen = (AppCompatButton) findViewById(R.id.imgEditar);
        btnGuardar = (AppCompatButton) findViewById(R.id.guardarEditar);
        imgCuidadorEdit =(ImageView) findViewById(R.id.imgCuidadorEditar);
        lytRegistra = (LinearLayout) findViewById(R.id.activity_editar_cuidador);

        String id = getIntent().getStringExtra("id");
        //Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
        PedirDatos(urls.urlCuidador(id));

        if (mayRequestStoragePermission())
            btnImagen.setEnabled(true);
        else
            btnImagen.setEnabled(false);


        btnImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passuno = etxtPass.getText().toString();
                String nombre = etxtNombre.getText().toString();
                String apellidop = etxtApellidoP.getText().toString();
                String apelldom = etxtApellidoM.getText().toString();
                String correo = etxtCorreo.getText().toString();
                String telefono = etxtTelefono.getText().toString();
                String rut = etxtRut.getText().toString();
                String direccion = etxtDireccion.getText().toString();
                if (!passuno.equals("")&&!nombre.equals("")&&!apelldom.equals("")&&!apellidop.equals("")
                        &&!correo.equals("")&&!telefono.equals("")&&!rut.equals("")&&!direccion.equals("")){
                    ActualizarDatos(urls.urlActualizaCuidador());
                }else {
                    Toast.makeText(getApplicationContext(),"Debe Completar el Formulario",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void PedirDatos(String uri){
        MyTaskCargar taskCargar = new MyTaskCargar();
        taskCargar.execute(uri);
    }

    public void CargarDatos(){
        if (cuidadorList != null){
            for (final Cuidador cuidador:cuidadorList) {
                etxtRut.append(cuidador.getIdCuidador());
                etxtNombre.append(cuidador.getNombre());
                etxtApellidoP.append(cuidador.getApellidoP());
                etxtApellidoM.append(cuidador.getApellidoM());
                etxtCorreo.append(cuidador.getCorreo());
                etxtTelefono.append(cuidador.getTelefono());
                etxtPass.append(cuidador.getPass());
                etxtDireccion.append(cuidador.getDireccion());
                Picasso.with(getApplicationContext()).load(cuidador.getRutaImagen()).into(imgCuidadorEdit);
            }
        }else {
            Toast.makeText(getApplicationContext(),"No se pudo Cargar los datos",Toast.LENGTH_LONG).show();
        }
    }

    private class MyTaskCargar extends AsyncTask<String, String,String> {
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

    public void ActualizarDatos(String uri){
        MyTask task = new MyTask();
        RequestPackage Req =new RequestPackage();
        Req.setMethod("GET");
        Req.setUri(uri);
        Req.setParam("id",getIntent().getStringExtra("id"));
        Req.setParam("pas",etxtPass.getText().toString());
        Req.setParam("nom",etxtNombre.getText().toString());
        Req.setParam("app",etxtApellidoP.getText().toString());
        Req.setParam("apm",etxtApellidoM.getText().toString());
        Req.setParam("dic",etxtDireccion.getText().toString());
        Req.setParam("cor",etxtCorreo.getText().toString());
        Req.setParam("tel",etxtTelefono.getText().toString());
        Req.setParam("img",urls.imgServer+"user-default.png");
        //Req.setParam("img","caso");
        Req.setParam("idn",etxtRut.getText().toString());
        SharedPreferences sharedPreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        final String paciente = sharedPreferences.getString("paciente", null);
        Req.setParam("fkp",paciente);
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
                Toast.makeText(getApplicationContext(),"Se actualizaron los datos con Exito",Toast.LENGTH_LONG).show();
                SharedPreferences sharedPreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
                final String paciente = sharedPreferences.getString("paciente", null);
                DummyContent dummyContent = new DummyContent();
                dummyContent.PedirDatos(urls.urlListaCuidador(paciente));
                Intent i = new Intent(getApplicationContext(), CuidadorListActivity.class);
                startActivity(i);
                finish();
            }else {
                Toast.makeText(getApplicationContext(),"Error al actualizar Datos",Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean mayRequestStoragePermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if ((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))) {
            Snackbar.make(lytRegistra, "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            });
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;
    }
    private void showOptions() {
        final CharSequence[] option = {"Tomar foto", "Elegir de galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(EditarCuidadorActivity.this);
        builder.setTitle("Eleige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(option[which] == "Tomar foto"){
                    openCamera();
                }else if(option[which] == "Elegir de galeria"){
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
                }else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mPath = savedInstanceState.getString("file_path");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this,
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                }
                            });


                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                    imgCuidadorEdit.setImageBitmap(bitmap);
                    break;
                case SELECT_PICTURE:
                    Uri path = data.getData();
                    imgCuidadorEdit.setImageURI(path);
                    break;

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(EditarCuidadorActivity.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                btnImagen.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditarCuidadorActivity.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.show();
    }
}
