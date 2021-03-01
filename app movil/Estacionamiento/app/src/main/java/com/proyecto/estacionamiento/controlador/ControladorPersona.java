package com.proyecto.estacionamiento.controlador;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.proyecto.estacionamiento.config.config;
import com.proyecto.estacionamiento.config.configSinglenton;
import com.proyecto.estacionamiento.modelo.Cuenta;
import com.proyecto.estacionamiento.modelo.Persona;
import com.proyecto.estacionamiento.modelo.Vehiculo;

import org.json.JSONException;
import org.json.JSONObject;

public class ControladorPersona extends AppCompatActivity {
    Context context;
    //url del servicio
    String getAll = "persona";
    String login = "persona/inicio";
    private static final String TAG = "MyActivity";

    public interface VolleyCallback {
        void onSuccess(JSONObject string);

        void onError(VolleyError string);
    }

    config conf = new config();

    public ControladorPersona(Context context) {
        this.context = context;
    }


    /**
     * Metodo para listar todo los usuarios
     *
     * @param callback
     */
    public void getAll(final VolleyCallback callback) {
        String host = conf.urlBase(context);
        String path = host.concat(getAll);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, path, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "Response: " + response);
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
                Log.i(TAG, "Listar: " + error);
            }
        });//Accion get, la url ,
        configSinglenton.getInstance(context).addToRequestqueue(request);
    }

    /**
     * Metodo para iniciar session
     *
     * @param callback
     */
    public void getlogin(Cuenta cuenta, final VolleyCallback callback) {
        String host = conf.urlBase(context);
        String path = host.concat(login);
        Log.i(TAG, "Url: " + path);
        JSONObject json = new JSONObject();
        try {
            json.put("correo", cuenta.getCorreo());
            json.put("clave", cuenta.getClave());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, path, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Log.i(TAG, "Response: " + response);
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
//                Log.i(TAG, "Error: " + error.getMessage());
            }
        });//Accion get, la url ,
        configSinglenton.getInstance(context).addToRequestqueue(request);
    }

    /**
     * Metodo que me permite insertar un alumno return callback
     *
     * @param persona
     * @param callback
     */
    public void insert(Persona persona, Vehiculo vehiculo, Cuenta cuenta, final VolleyCallback callback) {
        String host = conf.urlBase(context);
        String path = host.concat(getAll);
        JSONObject json = new JSONObject();
        try {
            json.put("cedula", persona.getCedula());
            json.put("nombres", persona.getNombres());
            json.put("apellidos", persona.getApellidos());
            json.put("direccion", persona.getDireccion());
            json.put("telefono", persona.getCelular());
            json.put("celular", persona.getCelular());
            json.put("clave", cuenta.getClave());
            json.put("correo", cuenta.getCorreo());
            json.put("estado", 1);
            json.put("nro_placa", vehiculo.getNro_placa());
            json.put("marca", vehiculo.getMarca());
            json.put("color", vehiculo.getColor());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, path, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        });
        configSinglenton.getInstance(context).addToRequestqueue(request);
    }
}
