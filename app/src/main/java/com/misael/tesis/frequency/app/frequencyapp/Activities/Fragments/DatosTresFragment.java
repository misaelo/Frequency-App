package com.misael.tesis.frequency.app.frequencyapp.Activities.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
 * {@link DatosTresFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DatosTresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatosTresFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DatosTresFragment() {
        // Required empty public constructor
    }
    URLs urls = new URLs();
    EditText etxtObserva;
    AppCompatButton btnGuardaObserva;
    ProgressDialog progressDialog;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DatosTresFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DatosTresFragment newInstance(String param1, String param2) {
        DatosTresFragment fragment = new DatosTresFragment();
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
        View view = inflater.inflate(R.layout.fragment_datos_tres, container, false);
        btnGuardaObserva = (AppCompatButton)view.findViewById(R.id.guardarObserva);
        etxtObserva = (EditText)view.findViewById(R.id.observaO);

        btnGuardaObserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String observa= etxtObserva.getText().toString();
                if (!observa.equals("")){
                    progressDialog = ProgressDialog.show(getActivity(), "Subiendo Datos", "Por favor espere ...",false, false);
                    PedirDatos(urls.urlObservacion());
                }else {
                    Toast.makeText(getActivity(),"Debe Agregar una Observacion",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
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
        Req.setParam("obv",etxtObserva.getText().toString());
        Req.setParam("fkp",paciente);
        Req.setParam("fkc",User);

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
                Toast.makeText(getActivity(),"Nueva Observación añadida con Exito",Toast.LENGTH_LONG).show();
                etxtObserva.setText(null);
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
