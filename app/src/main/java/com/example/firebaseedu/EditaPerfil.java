package com.example.firebaseedu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.firebaseedu.Modelos.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditaPerfil.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditaPerfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditaPerfil extends Fragment implements View.OnClickListener{

    ImageView volver;
    Button actualizar;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EditaPerfil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditaPerfil.
     */
    // TODO: Rename and change types and number of parameters
    public static EditaPerfil newInstance(String param1, String param2) {
        EditaPerfil fragment = new EditaPerfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( getArguments() != null ) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        final View  v  = inflater.inflate(R.layout.fragment_edita_perfil, container, false);
        Context context = getActivity().getApplicationContext();
        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser Users =  firebaseAuth.getCurrentUser();
        try {

            final String uid = Users.getUid();
            JSONObject datos = new JSONObject();
            datos.put("uid",uid);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://limpi.mipantano.com/api/usuario_info",
                    datos, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Gson gson = new Gson();
                    Usuario json = gson.fromJson(response.toString(),Usuario.class);
                    EditText nombre,apellido,correo;
                    nombre = v.findViewById(R.id.editnombre);
                    apellido = v.findViewById(R.id.editapellido);
                    correo = v.findViewById(R.id.editemail);
                    nombre.setText(json.getNombre());
                    apellido.setText(json.getApellido());
                    correo.setText(Users.getEmail());

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error",error.toString());
                }
            });

            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        volver = (ImageView) v.findViewById(R.id.cerrar);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), home.class);
                startActivity(intent);
            }
        });

        //ACTUALIZAR INFORMACION
        actualizar = v.findViewById(R.id.btnactualizar);
        final EditText nombre,apellido,correo;
        nombre = v.findViewById(R.id.editnombre);
        apellido = v.findViewById(R.id.editapellido);
        correo = v.findViewById(R.id.editemail);
        final String uid = Users.getUid();
        final JSONObject datos = new JSONObject();
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    datos.put("uid",uid);
                    datos.put("nombre",nombre.getText().toString());
                    datos.put("apellido",apellido.getText().toString());
                    datos.put("correo",Users.getEmail());
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://limpi.mipantano.com/api/actualizar_usuario",
                            datos, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Res",response.toString());

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error",error.toString());
                        }
                    });

                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    queue.add(jsonObjectRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        return  v;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if ( mListener != null ) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if ( context instanceof OnFragmentInteractionListener ) {
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
    public void onClick(View v) {

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
