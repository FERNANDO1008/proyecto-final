package com.proyecto.estacionamiento;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.proyecto.estacionamiento.config.config;
import com.proyecto.estacionamiento.controlador.ControladorPersona;
import com.proyecto.estacionamiento.modelo.Cuenta;
import com.proyecto.estacionamiento.vista.Actividades.Activity_cuenta;
import com.proyecto.estacionamiento.vista.Actividades.mapbox;
import com.proyecto.estacionamiento.vista.Actividades.registroActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MyActivity";
    Button btncancelar, btnGuardar, btningresar;
    TextView txtconfig,txtnotienecuenta;
    EditText txtIPActual, txtIPNueva, txtusuario, txtclave;
    config conf = new config();
    ControladorPersona cont = new ControladorPersona(this);
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        cargarComponete();
    }

    public void cargarComponete() {
        txtnotienecuenta=findViewById(R.id.idnotieneCuenta);
        txtconfig = findViewById(R.id.idConfig);
        txtusuario = findViewById(R.id.idusername);
        txtusuario.setText("");
        txtclave = findViewById(R.id.idpassword);
        txtclave.setText("");
        btningresar = findViewById(R.id.idLogin);
        btningresar.setOnClickListener(this);
        txtconfig.setOnClickListener(this);
        txtnotienecuenta.setOnClickListener(this);
    }

    public void CrearCuenta(View v) {
        intent = new Intent(MainActivity.this, registroActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.idConfig:
                //se genera un dialog
                final Dialog configuracion = new Dialog(MainActivity.this);
                configuracion.setContentView(R.layout.dialog_configip);
                btnGuardar = configuracion.findViewById(R.id.btnGuardarIP);
                btncancelar = configuracion.findViewById(R.id.btnCancelarIP);
                //asigna la ip actual
                txtIPActual = configuracion.findViewById(R.id.txtIpactual);
                Log.i(TAG, "Ip actual " + IPBase());
                txtIPActual.setText(IPBase());
                txtIPActual.setEnabled(false);
                txtIPNueva = configuracion.findViewById(R.id.txtIpnueva);
                txtIPNueva.setText(conf.getIP());
                Log.i(TAG, "Ip Nueva " + conf.getIP());

                if (!txtIPNueva.getText().toString().isEmpty()) {

                }
                btnGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OutputStreamWriter escribir = null;
                        try {
                            escribir = new OutputStreamWriter(openFileOutput("ipconfig.txt", Context.MODE_PRIVATE));
                            escribir.write(txtIPNueva.getText().toString());
                            escribir.close();
                            mensaje("Ip configurada");
                            configuracion.hide();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                btncancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        configuracion.dismiss();
                    }
                });
                configuracion.show();
                break;
            case R.id.idLogin:
                if (validacionCampos()) {
                    Cuenta cuenta = new Cuenta();
                    cuenta.setClave(txtclave.getText().toString());
                    cuenta.setCorreo(txtusuario.getText().toString());
                    cont.getlogin(cuenta, new ControladorPersona.VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            try {
                                if (jsonObject.getBoolean("success")) {
                                    intent = new Intent(MainActivity.this, mapbox.class);
                                    intent.putExtra("data", jsonObject.getJSONObject("data").toString());
                                    startActivity(intent);
                                } else {
                                    mensaje(jsonObject.getJSONObject("informacion").toString());
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(VolleyError string) {
                            mensaje("error de retorno: " + string.getMessage());
                        }
                    });
                }
                break;
            case R.id.idnotieneCuenta:
                intent = new Intent(MainActivity.this, Activity_cuenta.class);
                startActivity(intent);
                break;
            default:
        }
    }

    Toast toast;


    public String IPBase() {
        String cadena = "";
        try {
            BufferedReader leer = new BufferedReader(new InputStreamReader(openFileInput("ipconfig.txt")));
            cadena = leer.readLine();
            leer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cadena;
    }

    public boolean validacionCampos() {
        if (txtusuario.getText().toString().isEmpty()) {
//            txtusuario.setHintTextColor(Color.RED);
            return false;
        }
        if (txtclave.getText().toString().isEmpty()) {
            // txtclave.setHintTextColor(Color.RED);
            return false;
        }

        return true;
    }

    public void mensaje(String mensaje) {
        toast = Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
