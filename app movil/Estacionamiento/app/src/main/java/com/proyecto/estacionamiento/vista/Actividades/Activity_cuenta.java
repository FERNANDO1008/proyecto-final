package com.proyecto.estacionamiento.vista.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.proyecto.estacionamiento.MainActivity;
import com.proyecto.estacionamiento.R;
import com.proyecto.estacionamiento.controlador.ControladorPersona;
import com.proyecto.estacionamiento.modelo.Cuenta;
import com.proyecto.estacionamiento.modelo.Persona;
import com.proyecto.estacionamiento.modelo.Vehiculo;

import org.json.JSONObject;

public class Activity_cuenta extends AppCompatActivity implements View.OnClickListener {

    EditText txtcedula, txtnombre, txtapellido, txtdieccion, txtcelular, txtcoreo, txtclave, txtnro_placa, txtmarca, txtcolor;
    Button btnguardar;
    Persona persona = new Persona();
    Cuenta cuenta = new Cuenta();
    Vehiculo vehiculo = new Vehiculo();
    ControladorPersona controladorPersona = new ControladorPersona(this);
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);
        getSupportActionBar().setTitle("Crear Cuenta");
        cargarComponetes();
    }

    public void cargarComponetes() {
        txtcedula = findViewById(R.id.txtCedulaR);
        txtnombre = findViewById(R.id.txtNombreR);
        txtapellido = findViewById(R.id.txtApellidoR);
        txtdieccion = findViewById(R.id.txtDireccionR);
        txtcelular = findViewById(R.id.txtCedulaR);
        txtcoreo = findViewById(R.id.txtCorreoR);
        txtclave = findViewById(R.id.txtPasswordR);
        txtnro_placa = findViewById(R.id.txtnro_placaR);
        txtmarca = findViewById(R.id.txtMarcaR);
        txtcolor = findViewById(R.id.txtColorR);
        btnguardar = findViewById(R.id.idbtnCrear_cuentaR);
        btnguardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.idbtnCrear_cuentaR:
                persona.setCedula(txtcedula.getText().toString());
                persona.setApellidos(txtapellido.getText().toString());
                persona.setNombres(txtnombre.getText().toString());
                persona.setDireccion(txtdieccion.getText().toString());
                persona.setCelular(txtcelular.getText().toString());
                cuenta.setCorreo(txtcoreo.getText().toString());
                cuenta.setClave(txtclave.getText().toString());
                vehiculo.setNro_placa(txtnro_placa.getText().toString());
                vehiculo.setMarca(txtmarca.getText().toString());
                vehiculo.setColor(txtcolor.getText().toString());
                controladorPersona.insert(persona, vehiculo, cuenta, new ControladorPersona.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject string) {
                        Intent intent = new Intent(Activity_cuenta.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(VolleyError string) {
                        Log.i(TAG, "error: " + string);
                    }
                });

                break;
            default:
        }
    }
}
