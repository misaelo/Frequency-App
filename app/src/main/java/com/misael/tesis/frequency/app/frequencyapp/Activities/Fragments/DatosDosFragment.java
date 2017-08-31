package com.misael.tesis.frequency.app.frequencyapp.Activities.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
 * {@link DatosDosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DatosDosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatosDosFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    URLs urls = new URLs();
    public String[] Niveles = {"Seleccione","Bajo","Normal","Alto", "Exagerado"};
    Spinner spnNiveles;
    AppCompatCheckBox cbxDigestion;
    AppCompatButton btnGuardaComida;
    EditText etxtCantidad, etxtSintomas, etxtObserva;
    ProgressDialog progressDialog;
    public DatosDosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DatosDosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DatosDosFragment newInstance(String param1, String param2) {
        DatosDosFragment fragment = new DatosDosFragment();
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
        View view = inflater.inflate(R.layout.fragment_datos_dos, container, false);
        spnNiveles = (Spinner)view.findViewById(R.id.nivelApetito);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line, Niveles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spnNiveles.setAdapter(adapter);

        etxtCantidad = (EditText)view.findViewById(R.id.comidaNumero);
        etxtObserva = (EditText)view.findViewById(R.id.observaComida);
        etxtSintomas = (EditText)view.findViewById(R.id.sintomas);
        cbxDigestion = (AppCompatCheckBox)view.findViewById(R.id.digestion);
        btnGuardaComida = (AppCompatButton)view.findViewById(R.id.guardarComida);

        cbxDigestion.setOnClickListener(this);
        etxtSintomas.setEnabled(false);

        btnGuardaComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cantidad = etxtCantidad.getText().toString();
                String sintomas = etxtSintomas.getText().toString();
                int posicion = spnNiveles.getSelectedItemPosition();

                if (posicion != 0 && !cantidad.equals("")){
                    if (cbxDigestion.isChecked()){
                        if (!sintomas.equals("")){
                            //Toast.makeText(getActivity()," puedo cargar1",Toast.LENGTH_LONG).show();
                            progressDialog = ProgressDialog.show(getActivity(), "Subiendo Datos", "Por favor espere ...",false, false);
                            PedirDatos(urls.urlComidas());
                        }else {
                            Toast.makeText(getActivity(),"Debe Ingresar los Sintomas",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        progressDialog = ProgressDialog.show(getActivity(), "Subiendo Datos", "Por favor espere ...",false, false);
                        PedirDatos(urls.urlComidas());
                        //Toast.makeText(getActivity(),"puedo cargar sin check",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getActivity(),"Debe Completar el registro de Comidas",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view ;
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
        Req.setParam("nva", Integer.toString(spnNiveles.getSelectedItemPosition()));
        Req.setParam("ncs", etxtCantidad.getText().toString());
        Req.setParam("sin", etxtSintomas.getText().toString());
        Req.setParam("obv",etxtObserva.getText().toString());
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
                Toast.makeText(getActivity(),"Registro de Comidas Añadido con Exito",Toast.LENGTH_LONG).show();
                etxtObserva.setText(null);
                etxtSintomas.setText(null);
                etxtCantidad.setText(null);
                spnNiveles.setSelection(0);
                cbxDigestion.setChecked(false);
            }else {
                Toast.makeText(getActivity(),"Error al Guardar Dattos",Toast.LENGTH_LONG).show();
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
            case R.id.digestion:
                if (cbxDigestion.isChecked()){
                    etxtSintomas.setEnabled(true);
                }else {
                    etxtSintomas.setEnabled(false);
                    etxtSintomas.setText(null);
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
