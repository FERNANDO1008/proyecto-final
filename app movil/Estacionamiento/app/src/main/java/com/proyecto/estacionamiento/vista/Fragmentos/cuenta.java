package com.proyecto.estacionamiento.vista.Fragmentos;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.proyecto.estacionamiento.R;
import com.proyecto.estacionamiento.modelo.Comunicador;

/**
 * A simple {@link Fragment} subclass.
 */
public class cuenta extends Fragment implements View.OnClickListener {

    EditText txtcorreo, txtclave, txtconfirmar;
    Comunicador cominicador;
    Button btnfrag2;
    public cuenta() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cuenta, container, false);
    }

    public void cargarComponentes() {
        cominicador = (Comunicador) getActivity();
        txtcorreo = getActivity().findViewById(R.id.txtCorreoF);
        txtclave = getActivity().findViewById(R.id.txtPasswordF);
        txtconfirmar = getActivity().findViewById(R.id.txtConfirmaPasswordF);
        btnfrag2 = getActivity().findViewById(R.id.btnfrag2);
        btnfrag2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        cominicador.cuenta(txtcorreo.getText().toString(), txtclave.getText().toString());
    }

}
