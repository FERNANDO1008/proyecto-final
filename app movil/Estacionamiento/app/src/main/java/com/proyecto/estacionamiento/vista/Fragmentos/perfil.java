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
public class perfil extends Fragment implements View.OnClickListener {


    EditText txtcedula, txtnombres, txtapellidos, txtdireccion, txtcelular;
    Comunicador cominicador;
    Button btnfrag1;

    public perfil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    public void cargarComponentes() {
        cominicador = (Comunicador) getActivity();
        txtcedula = getActivity().findViewById(R.id.txtCedulaF);
        txtnombres = getActivity().findViewById(R.id.txtNombreF);
        txtapellidos = getActivity().findViewById(R.id.txtApellidoF);
        txtdireccion = getActivity().findViewById(R.id.txtDireccionF);
        txtcelular = getActivity().findViewById(R.id.txtCelularF);
        btnfrag1 = getActivity().findViewById(R.id.btnfrag1);
        btnfrag1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        cominicador.perfil(txtcedula.getText().toString(), txtnombres.getText().toString(), txtapellidos.getText().toString(), txtdireccion.getText().toString(), txtcelular.getText().toString());
    }
}
