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
public class vehiculo extends Fragment implements View.OnClickListener {

    EditText txtnroPlaca, txtmarca, txtcolor;
    Button btnGuardar;
    Comunicador cominicador;
    public vehiculo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehiculo, container, false);
    }
    public void cargarComponentes() {
        cominicador = (Comunicador) getActivity();
        txtnroPlaca = getActivity().findViewById(R.id.txtnro_placaF);
        txtmarca = getActivity().findViewById(R.id.txtMarcaF);
        txtcolor = getActivity().findViewById(R.id.txtColorF);
        btnGuardar = getActivity().findViewById(R.id.idbtnCrear_cuentaF);
        btnGuardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        cominicador.vehiculo(txtnroPlaca.getText().toString(), txtcolor.getText().toString(),txtmarca.getText().toString());
    }

}
