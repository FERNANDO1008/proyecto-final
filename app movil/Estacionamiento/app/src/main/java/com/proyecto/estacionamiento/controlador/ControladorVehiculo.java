package com.proyecto.estacionamiento.controlador;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.proyecto.estacionamiento.config.config;
import com.proyecto.estacionamiento.config.configSinglenton;
import com.proyecto.estacionamiento.modelo.Vehiculo;

import org.json.JSONException;
import org.json.JSONObject;

public class ControladorVehiculo {
    Context context;
    //url del servicio
    String getPersona = "persona/buscarPersona/";
    String insertVehiculo = "persona/regVehiculo";

    private static final String TAG = "MyActivity";

    public interface VolleyCallback {
        void onSuccess(JSONObject string);

        void onError(VolleyError string);
    }

    config conf = new config();

    public ControladorVehiculo(Context context) {
        this.context = context;
    }

    /**
     * Metodo para listar todo los usuarios
     *
     * @param callback
     */
    public void getAll(String cedula, final VolleyCallback callback) {
        String host = conf.urlBase(context);
        String path = host.concat(getPersona) + cedula;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, path, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        });//Accion get, la url ,
        configSinglenton.getInstance(context).addToRequestqueue(request);
    }

    /**
     * Metodo que me permite insertar un alumno return callback
     *
     * @param vehiculo
     * @param callback
     */
    public void insert(Vehiculo vehiculo,String cedula, final VolleyCallback callback) {
        String host = conf.urlBase(context);
        String path = host.concat(insertVehiculo);
        JSONObject json = new JSONObject();
        Log.i(TAG, "Placa: " + vehiculo.getNro_placa());
        try {
            json.put("cedula", cedula);
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
