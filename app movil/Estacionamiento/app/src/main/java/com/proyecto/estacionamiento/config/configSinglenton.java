package com.proyecto.estacionamiento.config;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class configSinglenton {
    private RequestQueue queue;
    private Context context;
    private ImageLoader mImageLoader;

    private static configSinglenton miInstancia;

    public configSinglenton(Context context) {
        this.context = context;//para manejo de la vista
        queue = getRequestQueue();//manejo de peticiones
    }

    public RequestQueue getRequestQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return queue;
    }

    /**
     * Permite no crear mas objetos si ya esta creado
     * con mi instancia
     * @param context
     * @return
     */
    public static synchronized configSinglenton getInstance(Context context){
        if (miInstancia==null){
            miInstancia=new configSinglenton(context);
        }
        return miInstancia;
    }

    /**
     * Lista de peticiones
     * @param request
     * @param <T>
     */
    public <T> void addToRequestqueue(Request request){
        queue.add(request);
    }
}
