package com.proyecto.estacionamiento.vista.Actividades;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.proyecto.estacionamiento.R;
import com.proyecto.estacionamiento.controlador.ControladorVehiculo;
import com.proyecto.estacionamiento.modelo.Vehiculo;
import com.proyecto.estacionamiento.vista.adapter.AdapterVehiculo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityVehiculo extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MyActivity";
    String cedula;
    RecyclerView recyclerViewVehiculo;
    AdapterVehiculo adapter;
    List<Vehiculo> lista;
    Vehiculo vehiculo;
    ControladorVehiculo controladorVehiculo = new ControladorVehiculo(this);
    Toast toast;
    FloatingActionButton btnagregarVehiculo;
    Button btncancelar, btnguardar;
    EditText txtnro_placa, txtmarca, txtcolor;
    TextInputLayout inputNro_placa, inputMarca, inputColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Vehiculo");
        cedula = getIntent().getExtras().getString("cedula");
        Log.i(TAG, "Data mapbox: " + cedula);
        cargarComponentes();
    }

    public void cargarComponentes() {
        recyclerViewVehiculo = findViewById(R.id.recyclerVehiculo);
        btnagregarVehiculo = findViewById(R.id.btnAgregarVehiculo);
        btnagregarVehiculo.setOnClickListener(this);
        listarVehiculos();
    }


    public void listarVehiculos() {
        lista = new ArrayList<>();
        try {
            controladorVehiculo.getAll(cedula, new ControladorVehiculo.VolleyCallback() {
                @Override
                public void onSuccess(JSONObject json) {
                    Log.i(TAG, json.toString());
                    try {
                        if (json.getBoolean("success")) {
                            JSONArray jsonObject = json.getJSONObject("data").getJSONArray("vehiculo");
                            for (int i = 0; i < jsonObject.length(); i++) {
                                vehiculo = new Vehiculo();
                                try {
                                    vehiculo.setNro_placa(jsonObject.getJSONObject(i).getString("nro_placa"));
                                    vehiculo.setMarca(jsonObject.getJSONObject(i).getString("marca"));
                                    vehiculo.setColor(jsonObject.getJSONObject(i).getString("color"));
                                    lista.add(vehiculo);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            cargarRecicler(lista);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    Log.i(TAG, "Listar: " + error);
                    mensaje(error.getMessage());
                }
            });

        } catch (Exception ex) {

        }
    }

    public void cargarRecicler(List<Vehiculo> listas) {
        adapter = new AdapterVehiculo(listas);
        recyclerViewVehiculo.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarDatos(v);
            }
        });
        recyclerViewVehiculo.setAdapter(adapter);
    }

    public void cargarDatos(View v) {
//        String idAlumno = lista.get(recyclerViewVehiculo.getChildAdapterPosition(v)).getId();
//        controladorAlumnoVolly.getById(idAlumno, new ControladorAlumnoVolly.VolleyCallback() {
//            @Override
//            public void onSuccess(JSONObject json) {
//                String estado = null;
//                try {
//                    estado = json.getString("estado");
//                    Log.i(TAG, "Estado: " + estado);
//                    if (estado.equals("1")) {
//                        txtId.setEnabled(false);
//                        txtId.setText(json.getJSONObject("alumno").getString("idAlumno"));
//                        txtNombre.setText(json.getJSONObject("alumno").getString("nombre"));
//                        txtDireccion.setText(json.getJSONObject("alumno").getString("direccion"));
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    public void mensaje(String mensaje) {
        toast = Toast.makeText(ActivityVehiculo.this, mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAgregarVehiculo:
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
                bottomSheetDialog.setContentView(R.layout.dialog_registro_vehiculo);
                bottomSheetDialog.setCanceledOnTouchOutside(false);
                btncancelar = bottomSheetDialog.findViewById(R.id.idbtnCencelarDialog);
                btnguardar = bottomSheetDialog.findViewById(R.id.idbtnGuardarDialog);
                txtnro_placa = bottomSheetDialog.findViewById(R.id.txtnro_placaDialog);
                txtmarca = bottomSheetDialog.findViewById(R.id.txtMarcaDialog);
                txtcolor = bottomSheetDialog.findViewById(R.id.txtColorDialog);
                inputNro_placa = bottomSheetDialog.findViewById(R.id.input_nro_placa);
                inputMarca = bottomSheetDialog.findViewById(R.id.input_marca);
                inputColor = bottomSheetDialog.findViewById(R.id.input_color);
                btncancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                btnguardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vehiculo.setNro_placa(txtnro_placa.getText().toString());
                        vehiculo.setMarca(txtmarca.getText().toString());
                        vehiculo.setColor(txtcolor.getText().toString());
                        controladorVehiculo.insert(vehiculo,cedula, new ControladorVehiculo.VolleyCallback() {
                            @Override
                            public void onSuccess(JSONObject json) {
                                Log.i(TAG, json.toString());
                                try {
                                    mensaje(json.getString("informacion"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(VolleyError string) {
                                Log.i(TAG, string.toString());
                            }
                        });
                    }
                });
                bottomSheetDialog.show();
                break;
            default:
        }
    }
}
