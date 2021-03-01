package com.proyecto.estacionamiento.controlador;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.proyecto.estacionamiento.config.config;
import com.proyecto.estacionamiento.config.configSinglenton;
import com.proyecto.estacionamiento.modelo.Ticket;

import org.json.JSONException;
import org.json.JSONObject;

public class ControladorEstacionamiento {
    Context context;
    //url del servicio
    String All = "estacionamiento";
    String ticket = "estacionamiento/ticket";
    private static final String TAG = "MyActivity";

    public interface VolleyCallback {
        void onSuccess(JSONObject string);

        void onError(VolleyError string);
    }

    config conf = new config();

    public ControladorEstacionamiento(Context context) {
        this.context = context;
    }

    /**
     * Metodo para listar todo los usuarios
     *
     * @param callback
     */
    public void getAll(final VolleyCallback callback) {
        String host = conf.urlBase(context);
        String path = host.concat(All);
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
     * @param ticketv
     * @param callback
     */
    public void insert(Ticket ticketv, final VolleyCallback callback) {
        String host = conf.urlBase(context);
        String path = host.concat(ticket);
        JSONObject json = new JSONObject();
        try {
            json.put("Persona_id", ticketv.getId_persona());
            json.put("Sitio_id", ticketv.getId_sitio());
            json.put("fechaIni", ticketv.getFechaini());
            json.put("fechaFin", ticketv.getFechafin());
            json.put("costoHora", ticketv.getCostohora());
            json.put("costoTotal", ticketv.getCostototal());
            json.put("horaInicio", ticketv.getHoraini());
            json.put("horaFin", ticketv.getFechafin());

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
