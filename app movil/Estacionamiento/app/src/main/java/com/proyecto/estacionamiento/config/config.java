package com.proyecto.estacionamiento.config;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class config {

    private static final String TAG = "MyActivity";

    public String IPBase(Context cont) {
        String cadena = "";
        try {
            BufferedReader leer = new BufferedReader(new InputStreamReader(cont.openFileInput("ipconfig.txt")));
            cadena = leer.readLine();
            leer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//
//        String cadena = null;
//        InputStream input = cont.getResources().openRawResource(R.raw.ipconfig);
//        try {
//            BufferedReader lector = new BufferedReader(new InputStreamReader(input));
//            cadena = lector.readLine();
//            lector.close();
//        } catch (IOException ex) {
//
//        }

        return cadena;
    }

    public String getIP() {
        List<InetAddress> addrs;
        String address = "";
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress() && addr instanceof Inet4Address) {
                        address = addr.getHostAddress().toUpperCase(new Locale("es", "MX"));
                    }
                }
            }
        } catch (Exception e) {
            Log.w(TAG, "Ex getting IP value " + e.getMessage());
        }
        return address;
    }

    /**
     * Metodo que permite obtener la ip del archivo de texto
     * @param cont
     * @return
     */
    public String urlBase(Context cont) {
        String ip = "";
        try {
            BufferedReader leer = new BufferedReader(new InputStreamReader(cont.openFileInput("ipconfig.txt")));
            ip = leer.readLine();
            leer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url_base = "http://" + ip + ":7000/";
        return url_base;
    }

}
