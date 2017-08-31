package com.misael.tesis.frequency.app.frequencyapp.Activities.Fragments;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.misael.tesis.frequency.app.frequencyapp.Activities.DatosActivity;
import com.misael.tesis.frequency.app.frequencyapp.Activities.Login;
import com.misael.tesis.frequency.app.frequencyapp.Conectar.HttpManager;
import com.misael.tesis.frequency.app.frequencyapp.Conectar.RequestPackage;
import com.misael.tesis.frequency.app.frequencyapp.Conectar.URLs;
import com.misael.tesis.frequency.app.frequencyapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DatosUnoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DatosUnoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatosUnoFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    URLs url = new URLs();
    EditText etxtInicio, etxtFin,etxtInterrupInicio, etxtFinInterrup, etxtObserva;
    AppCompatCheckBox cbxDiaAnterior, cbxInterrupcion;
    AppCompatButton btnGuardaSueno;
    ProgressDialog progressDialog;
    public DatosUnoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DatosUnoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DatosUnoFragment newInstance(String param1, String param2) {
        DatosUnoFragment fragment = new DatosUnoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datos_uno, container, false);

        etxtInicio = (EditText)view.findViewById(R.id.inicioSueno);
        etxtFin = (EditText)view.findViewById(R.id.finSueno);
        cbxDiaAnterior = (AppCompatCheckBox)view.findViewById(R.id.diaAnterior);
        cbxInterrupcion = (AppCompatCheckBox)view.findViewById(R.id.interrupcion);
        etxtInterrupInicio = (EditText)view.findViewById(R.id.inicioInterrup);
        etxtFinInterrup = (EditText)view.findViewById(R.id.finInterrup);
        btnGuardaSueno = (AppCompatButton)view.findViewById(R.id.guardarSueno);
        etxtObserva = (EditText)view.findViewById(R.id.observaSueno);

        cbxDiaAnterior.setOnClickListener(this);
        cbxInterrupcion.setOnClickListener(this);

        etxtInterrupInicio.setEnabled(false);
        etxtFinInterrup.setEnabled(false);

        etxtInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedHour < 10 ){
                            if (selectedMinute<10){
                                etxtInicio.setText("0"+selectedHour + ":0" + selectedMinute);
                            }else {
                                etxtInicio.setText("0"+selectedHour + ":" + selectedMinute);
                            }
                        }else {
                            if (selectedMinute<10){
                                etxtInicio.setText(selectedHour + ":0" + selectedMinute);
                            }else {
                                etxtInicio.setText(selectedHour + ":" + selectedMinute);
                            }
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Seleccione Hora");
                mTimePicker.show();
            }
        });

        etxtFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedHour < 10 ){
                            if (selectedMinute<10){
                                etxtFin.setText("0"+selectedHour + ":0" + selectedMinute);
                            }else {
                                etxtFin.setText("0"+selectedHour + ":" + selectedMinute);
                            }
                        }else {
                            if (selectedMinute<10){
                                etxtFin.setText(selectedHour + ":0" + selectedMinute);
                            }else {
                                etxtFin.setText(selectedHour + ":" + selectedMinute);
                            }
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Seleccione Hora");
                mTimePicker.show();
            }
        });

        btnGuardaSueno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inicioS= etxtInicio.getText().toString();
                String finS = etxtFin.getText().toString();
                String interuppI = etxtInterrupInicio.getText().toString();
                String interrupF = etxtFinInterrup.getText().toString();

                if (!inicioS.equals("") && !finS.equals("")){
                    if (cbxInterrupcion.isChecked()){
                        if (!interuppI.equals("")&&!interrupF.equals("")){
                            //Toast.makeText(getActivity(),"todso adentro",Toast.LENGTH_SHORT).show();
                            progressDialog = ProgressDialog.show(getActivity(), "Subiendo Datos", "Por favor espere ...",false, false);
                            PedirDatos(url.urlSueno());
                        }else {
                            Toast.makeText(getActivity(),"Si se Interrumpio el Sueño debe Llenar los Horarios",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        progressDialog = ProgressDialog.show(getActivity(), "Subiendo Datos", "Por favor espere ...",false, false);
                        PedirDatos(url.urlSueno());
                    }
                }else {
                    Toast.makeText(getActivity(),"Debe Completar los Horarios",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public void habilita(){
        etxtInterrupInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedHour < 10 ){
                            if (selectedMinute<10){
                                etxtInterrupInicio.setText("0"+selectedHour + ":0" + selectedMinute);
                            }else {
                                etxtInterrupInicio.setText("0"+selectedHour + ":" + selectedMinute);
                            }
                        }else {
                            if (selectedMinute<10){
                                etxtInterrupInicio.setText(selectedHour + ":0" + selectedMinute);
                            }else {
                                etxtInterrupInicio.setText(selectedHour + ":" + selectedMinute);
                            }
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Seleccione Hora");
                mTimePicker.show();
            }
        });
        etxtFinInterrup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedHour < 10 ){
                            if (selectedMinute<10){
                                etxtFinInterrup.setText("0"+selectedHour + ":0" + selectedMinute);
                            }else {
                                etxtFinInterrup.setText("0"+selectedHour + ":" + selectedMinute);
                            }
                        }else {
                            if (selectedMinute<10){
                                etxtFinInterrup.setText(selectedHour + ":0" + selectedMinute);
                            }else {
                                etxtFinInterrup.setText(selectedHour + ":" + selectedMinute);
                            }
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Seleccione Hora");
                mTimePicker.show();
            }
        });
    }

    public void PedirDatos(String uri){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        final String User = sharedPreferences.getString("user", null);
        final String paciente = sharedPreferences.getString("paciente", null);

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        String fecha = date.format(today);

        MyTask task = new MyTask();
        RequestPackage Req =new RequestPackage();
        Req.setMethod("GET");
        Req.setUri(uri);
        Req.setParam("fec", fecha);
        Req.setParam("hri", etxtInicio.getText().toString());
        Req.setParam("hrf", etxtFin.getText().toString());
        Req.setParam("iti", etxtInterrupInicio.getText().toString());
        Req.setParam("itf", etxtFinInterrup.getText().toString());
        Req.setParam("obv", etxtObserva.getText().toString());
        Req.setParam("fkp", paciente);
        Req.setParam("fkc", User);

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
            progressDialog.dismiss();

            int re = result.length();
            if (re ==3){
                Toast.makeText(getActivity(),"Registro de Sueño Añadido con Exito",Toast.LENGTH_LONG).show();
                etxtInicio.setText(null);
                etxtFin.setText(null);
                etxtInterrupInicio.setText(null);
                etxtFinInterrup.setText(null);
                etxtObserva.setText(null);
                cbxDiaAnterior.setChecked(false);
                cbxDiaAnterior.setChecked(true);
            }else {
                Toast.makeText(getActivity(),"Error al Guardar Datos",Toast.LENGTH_LONG).show();
            }

        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.diaAnterior:
                if (cbxDiaAnterior.isChecked())
                    //Toast.makeText(getActivity(),"pinchaste la cosa anteriot",Toast.LENGTH_SHORT).show();
                    break;
            case R.id.interrupcion:
                if (cbxInterrupcion.isChecked()){
                    etxtInterrupInicio.setEnabled(true);
                    etxtFinInterrup.setEnabled(true);
                    habilita();
                }else {
                    etxtInterrupInicio.setEnabled(false);
                    etxtFinInterrup.setEnabled(false);
                    etxtInterrupInicio.setText(null);
                    etxtFinInterrup.setText(null);
                }
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
