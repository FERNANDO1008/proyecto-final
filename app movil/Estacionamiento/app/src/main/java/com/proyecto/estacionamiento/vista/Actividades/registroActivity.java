package com.proyecto.estacionamiento.vista.Actividades;

import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.proyecto.estacionamiento.R;
import com.proyecto.estacionamiento.modelo.Comunicador;
import com.proyecto.estacionamiento.vista.Actividades.ui.main.SectionsPagerAdapter;

public class registroActivity extends AppCompatActivity implements Comunicador {
    ViewPager viewPager;
    private LinearLayout linearPuntos;
    private TextView[] puntoSlider;
    String ced, nomb, ape, dir, celu, corr, clav, nro_p, mar, colo;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
//        TabLayout tabs = findViewById(R.id.tabs);
//        tabs.setupWithViewPager(viewPager);
        linearPuntos = findViewById(R.id.idPuntosPagina);
        viewPager.addOnPageChangeListener(viewListener);
        agregarIndicadorPuntos(0);
        //FloatingActionButton fab = findViewById(R.id.fab);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentManager fragmentManager = getSupportFragmentManager();
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//
//            }
//        });
    }


    private void agregarIndicadorPuntos(int pos) {
        puntoSlider = new TextView[3];
        linearPuntos.removeAllViews();
        for (int i = 0; i < puntoSlider.length; i++) {
            puntoSlider[i] = new TextView(this);
            puntoSlider[i].setText(Html.fromHtml("&#8226;"));
            puntoSlider[i].setTextSize(45);
            puntoSlider[i].setTextColor(getResources().getColor(R.color.backgraund_whit_transp));
            linearPuntos.addView(puntoSlider[i]);
        }
        if (puntoSlider.length > 0) {
            puntoSlider[pos].setTextColor(getResources().getColor(R.color.backgraund_whit_blanco));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            agregarIndicadorPuntos(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    public void perfil(String cedula, String nombres, String apellidos, String direccion, String celular) {
        this.ced = cedula;
        this.nomb = nombres;
        this.ape = apellidos;
        this.dir = direccion;
        this.celu = celular;
    }

    @Override
    public void cuenta(String correo, String clave) {
        this.corr = correo;
        this.clav = clave;
    }

    @Override
    public void vehiculo(String nro_placa, String color, String marca) {
        this.nro_p = nro_placa;
        this.mar = marca;
        this.colo = color;
    }
}