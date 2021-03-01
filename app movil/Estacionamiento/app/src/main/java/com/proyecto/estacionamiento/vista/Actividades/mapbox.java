package com.proyecto.estacionamiento.vista.Actividades;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.proyecto.estacionamiento.MainActivity;
import com.proyecto.estacionamiento.R;
import com.proyecto.estacionamiento.controlador.ControladorEstacionamiento;
import com.proyecto.estacionamiento.modelo.Ticket;
import com.proyecto.estacionamiento.modelo.Vehiculo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mapbox extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, MapboxMap.OnMapClickListener, View.OnClickListener {

    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    private static final String TAG = "MyActivity";
    private MapView mapView;
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private Location originaLocation = null;
    private Point origenPosition;
    private com.mapbox.geojson.Point destinoPosition;
    private Marker destinationMatket;
    private LocationComponent locationComponent = null;
    private NavigationMapRoute navigationMapRoute;
    Intent intent;
    FloatingActionButton fabperfil, fabconfig, fabsalir, fabVehiculoM;
    FloatingActionButton fabVehiculo, fabEstacion, fabReserva;
    FloatingActionsMenu fabMenu;
    String dataPersona, nombre, apellido, cedula, correo, clave;
    EditText txtcedula, txtnombre, txtapellido, txtcorreo, txtclave;
    EditText txtfechaIni, txtfechFin, txthoraIni, txthorafin, txtcostoHora, txtcostoTotal;
    Button btnCancelarT, btnGuardarT;
    TextView txtUsuario;
    Ticket ticket = new Ticket();
    DatePickerDialog datePick;
    DatePickerDialog datePick1;
    //Variables para obtener la hora hora
    public final Calendar c = Calendar.getInstance();
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);
    Calendar cal;
    JSONObject datosUser = null;
    ControladorEstacionamiento controladorEstacionamiento = new ControladorEstacionamiento(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        dataPersona = getIntent().getExtras().getString("data");
        Log.i(TAG, "Data mapbox: " + dataPersona);
        Mapbox.getInstance(this, "pk.eyJ1IjoieXVrYXNtaW5hIiwiYSI6ImNrNzZyM3VhYTAwZmkzZW1rcWFjZDZybGIifQ.Y-DnJhWF-oBA6aPaZGVKOg");
        setContentView(R.layout.activity_mapbox);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        cargarComponentes();
    }

    public void cargarComponentes() {

        //para el floatbuton
        fabMenu = findViewById(R.id.idgrupoFloat);
        fabperfil = findViewById(R.id.idFloaPerfil);
        fabconfig = findViewById(R.id.idFloaTiket);
        fabsalir = findViewById(R.id.idFloaSalir);
        fabVehiculo = findViewById(R.id.btnVehiculoFloat);
        fabEstacion = findViewById(R.id.btnestacionamientosFloat);
        fabReserva = findViewById(R.id.btnReservasFloat);
        txtUsuario = findViewById(R.id.txtNombreUser);
        txtUsuario.setEnabled(true);
        fabperfil.setOnClickListener(this);
        fabconfig.setOnClickListener(this);
        fabsalir.setOnClickListener(this);
        fabVehiculo.setOnClickListener(this);
        fabEstacion.setOnClickListener(this);
        fabReserva.setOnClickListener(this);
        try {
            datosUser = new JSONObject(dataPersona);
            JSONObject jsonPersona = datosUser.getJSONObject("perosna");
            txtUsuario.setText("Bienvenido: " + jsonPersona.getString("nombres") + " " + jsonPersona.getString("apellidos"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.addOnMapClickListener(this);
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                //new LoadGeoJson(DrawGeojsonLineActivity.this).execute();
                enableLocationComponent(style);
//                LocalizationPlugin localizationPlugin = new LocalizationPlugin(mapView, mapboxMap, style);
//
//                try {
//                    localizationPlugin.matchMapLanguageWithDeviceDefault();
//                } catch (RuntimeException exception) {
//                    Log.d(TAG, exception.toString());
//                }
            }
        });
        mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                marker.getInfoWindow();
                destinoPosition = com.mapbox.geojson.Point.fromLngLat(marker.getPosition().getLongitude(), marker.getPosition().getLatitude());
                origenPosition = com.mapbox.geojson.Point.fromLngLat(originaLocation.getLongitude(), originaLocation.getLatitude());
                getRoute(origenPosition, destinoPosition);
                return true;
            }
        });

    }

    private void getRoute(Point origen, Point destino) {
        NavigationRoute.builder(this).accessToken("pk.eyJ1IjoieXVrYXNtaW5hIiwiYSI6ImNrNzZyM3VhYTAwZmkzZW1rcWFjZDZybGIifQ.Y-DnJhWF-oBA6aPaZGVKOg")
                .origin(origen)
                .destination(destino)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        if (response == null) {
                            Log.e(TAG, "No existe ruta revise el token: " + response);
                            return;
                        } else if (response.body().routes().size() == 0) {
                            Log.e(TAG, "No existe ruta: ");
                            return;
                        }
                        DirectionsRoute currentRoute = response.body().routes().get(0);
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                        Log.e(TAG, "Error: " + t.getMessage());
                    }
                });
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Get an instance of the component
            locationComponent = mapboxMap.getLocationComponent();

            // Activate with options
            locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(this, loadedMapStyle).build());

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
            // originaLocation=locationComponent.getLastKnownLocation();
            originaLocation = locationComponent.getLastKnownLocation();
//            Log.i(TAG, "Lng: " + originaLocation.getLongitude() + " Lat: " + originaLocation.getLatitude());

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
        //Log(TAG,"dato"+originaLocation);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        if (destinationMatket != null) {
            mapboxMap.removeMarker(destinationMatket);
        }
        destinationMatket = mapboxMap.addMarker(new MarkerOptions().position(point));
        destinoPosition = com.mapbox.geojson.Point.fromLngLat(point.getLongitude(), point.getLatitude());
        origenPosition = com.mapbox.geojson.Point.fromLngLat(originaLocation.getLongitude(), originaLocation.getLatitude());
        getRoute(origenPosition, destinoPosition);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.idFloaPerfil:
                fabMenu.collapse();
                JSONObject jsonParser = null;
                JSONObject jsonCuenta = null;
                JSONObject jsonPersona = null;
                try {
                    jsonParser = new JSONObject(dataPersona);
                    jsonCuenta = jsonParser.getJSONObject("cuenta");
                    jsonPersona = jsonParser.getJSONObject("perosna");
                    correo = jsonCuenta.getString("correo");
                    clave = jsonCuenta.getString("clave");
                    cedula = jsonPersona.getString("cedula");
                    nombre = jsonPersona.getString("nombres");
                    apellido = jsonPersona.getString("apellidos");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
                bottomSheetDialog.setContentView(R.layout.layout_perfil);
                bottomSheetDialog.setCanceledOnTouchOutside(false);
                Button btnCerrar = bottomSheetDialog.findViewById(R.id.idbuttomCancelar);

                txtcedula = bottomSheetDialog.findViewById(R.id.idtxtCedulaPer);
                txtnombre = bottomSheetDialog.findViewById(R.id.idtxtNombrePer);
                txtapellido = bottomSheetDialog.findViewById(R.id.idtxtApellidoPer);
                txtcorreo = bottomSheetDialog.findViewById(R.id.txtcorreoPer);
                txtclave = bottomSheetDialog.findViewById(R.id.txtPaswordPer);
                txtcedula.setText(cedula);
                txtnombre.setText(nombre);
                txtapellido.setText(apellido);
                txtcorreo.setText(correo);
                txtclave.setText(clave);
                txtcedula.setEnabled(false);
                txtcorreo.setEnabled(false);
                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
//                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//                        builder.setTitle("Perfil");
//                        builder.setMessage("Estodo tu perfil");
//                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                        AlertDialog alertDialog = builder.create();
//                        alertDialog.show();
                    }
                });
                bottomSheetDialog.show();
//                View bottomSheetView= LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_perfil,(LinearLayout)findViewById(R.id.bottomSheetContainer));
//                bottomSheetView.findViewById(R.id.idbuttomShare).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        bottomSheetDialog.dismiss();
//                    }
//                });
//                bottomSheetDialog.setContentView(bottomSheetView);
//                bottomSheetDialog.show();
                break;
            case R.id.idFloaTiket:
                fabMenu.collapse();
                Log.i(TAG, "precionno config: ");
                break;
            case R.id.idFloaSalir:
                Log.i(TAG, "llega");
                mensaje("Usuario Close");
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btnVehiculoFloat:
                JSONObject jsonPer = null;
                try {
                    jsonPer = new JSONObject(dataPersona);
                    JSONObject jsonCedula = jsonPer.getJSONObject("perosna");
                    cedula = jsonCedula.getString("cedula");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "cedula: " + cedula);
                Intent intent = new Intent(this, ActivityVehiculo.class);
                intent.putExtra("cedula", cedula);
                startActivity(intent);
                break;
            case R.id.btnestacionamientosFloat:
                // Create an Icon object for the marker to use

                controladorEstacionamiento.getAll(new ControladorEstacionamiento.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject json) {
                        IconFactory iconFactory = IconFactory.getInstance(mapbox.this);
                        Icon icon = iconFactory.fromResource(R.drawable.sitio);
                        try {
                            if (json.getBoolean("success")) {
                                JSONArray jsonObject = json.getJSONArray("data");
                                for (int i = 0; i < jsonObject.length(); i++) {
                                    double lat, lng;
                                    lat = jsonObject.getJSONObject(i).getDouble("latitud");
                                    lng = jsonObject.getJSONObject(i).getDouble("longitud");
                                    // Add the marker to the map
                                    Log.i(TAG, "lat: " + lat);
                                    Log.i(TAG, "lng: " + lng);
                                    mapboxMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(lng, lat))
                                            .icon(icon)
                                            .title(jsonObject.getJSONObject(i).getString("nombre")));
                                    destinoPosition = com.mapbox.geojson.Point.fromLngLat(lat, lng);
                                    origenPosition = com.mapbox.geojson.Point.fromLngLat(originaLocation.getLongitude(), originaLocation.getLatitude());
                                    getRoute(origenPosition, destinoPosition);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(VolleyError string) {
                        Log.i(TAG, string.toString());
                    }
                });
                break;
            case R.id.btnReservasFloat:

                JSONObject jsonPers = null;
                try {
                    jsonPers = new JSONObject(dataPersona);
                    JSONObject jsonCedula = jsonPers.getJSONObject("perosna");
                    cedula = jsonCedula.getString("persona_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final BottomSheetDialog bottomSheetDialogTicket = new BottomSheetDialog(this);
                bottomSheetDialogTicket.setContentView(R.layout.detalle_ticket);
                bottomSheetDialogTicket.setCanceledOnTouchOutside(false);
                txtfechaIni = bottomSheetDialogTicket.findViewById(R.id.txtfechaini);
                txtfechFin = bottomSheetDialogTicket.findViewById(R.id.txtfechafin);
                txthoraIni = bottomSheetDialogTicket.findViewById(R.id.txthoraIni);
                txthorafin = bottomSheetDialogTicket.findViewById(R.id.txthoraFin);
                txtcostoHora = bottomSheetDialogTicket.findViewById(R.id.txtCostohora);
                txtcostoTotal = bottomSheetDialogTicket.findViewById(R.id.txtCostoTotal);
                btnGuardarT = bottomSheetDialogTicket.findViewById(R.id.idbtnGuardarT);
                btnCancelarT = bottomSheetDialogTicket.findViewById(R.id.idbtnCencelarT);
                ticket.setFechaini(txtfechaIni.getText().toString());
                ticket.setFechafin(txtfechFin.getText().toString());
                ticket.setHoraini(txthoraIni.getText().toString());
                ticket.setHorafin(txthorafin.getText().toString());
                ticket.setCostohora(txtcostoHora.getText().toString());
                txtcostoHora.setText("1");
                txtcostoHora.setEnabled(true);
                txtcostoTotal.setText("1");
                txtcostoTotal.setEnabled(true);
                ticket.setCostototal(txtcostoTotal.getText().toString());
                ticket.setId_persona(cedula);
                ticket.setId_sitio("1");
                datePicker();
                datePicker1();
                txthoraIni.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        obtenerHora();
                    }
                });
                txthorafin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        obtenerHoraFin();
                    }
                });
                btnCancelarT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialogTicket.dismiss();
                    }
                });
                btnGuardarT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        controladorEstacionamiento.insert(ticket, new ControladorEstacionamiento.VolleyCallback() {
                            @Override
                            public void onSuccess(JSONObject string) {
                                Log.i(TAG, "Response: " + string);
                            }

                            @Override
                            public void onError(VolleyError string) {
                                Log.i(TAG, "Error: " + string);
                            }
                        });
                    }
                });
                bottomSheetDialogTicket.show();
                break;
            default:
        }
    }

    public void datePicker() {
        txtfechaIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int mes = cal.get(Calendar.MONTH);
                int anio = cal.get(Calendar.YEAR);
                datePick = new DatePickerDialog(mapbox.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtfechaIni.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                    }
                }, anio, mes, day);
                datePick.show();
            }
        });

    }

    public void datePicker1() {
        txtfechFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int mes = cal.get(Calendar.MONTH);
                int anio = cal.get(Calendar.YEAR);
                datePick1 = new DatePickerDialog(mapbox.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtfechFin.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                    }
                }, anio, mes, day);
                datePick1.show();
            }
        });
    }

    private void obtenerHora() {
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada = (hourOfDay < 10) ? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10) ? String.valueOf(CERO + minute) : String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if (hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                txthoraIni.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);

        recogerHora.show();
    }

    private void obtenerHoraFin() {
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada = (hourOfDay < 10) ? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10) ? String.valueOf(CERO + minute) : String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if (hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                txthorafin.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);

        recogerHora.show();
    }

    public void mensaje(String mensaje) {
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
