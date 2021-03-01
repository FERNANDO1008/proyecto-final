package com.proyecto.estacionamiento.vista.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.estacionamiento.R;
import com.proyecto.estacionamiento.modelo.Vehiculo;

import java.util.List;

public class AdapterVehiculo extends RecyclerView.Adapter<AdapterVehiculo.ViewHolderVehiculo> implements View.OnClickListener {


    List<Vehiculo> lista;

    public AdapterVehiculo(List<Vehiculo> lista) {
        this.lista = lista;
    }

    private View.OnClickListener click;

    @Override
    public ViewHolderVehiculo onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehiculo, null);//permite cargar la vista
        view.setOnClickListener(this);
        return new ViewHolderVehiculo((view));
    }

    @Override
    public void onBindViewHolder(ViewHolderVehiculo holder, int position) {
        holder.nro_placa.setText("Placa: " + lista.get(position).getNro_placa());
        holder.marca.setText("Modelo: " + lista.get(position).getMarca());
        holder.color.setText("Año: " + lista.get(position).getColor());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    public static class ViewHolderVehiculo extends RecyclerView.ViewHolder {
        TextView nro_placa,marca,color;
        public ViewHolderVehiculo(View itemView) {
            super(itemView);
            nro_placa = itemView.findViewById(R.id.ItemNro_placa);
            marca = itemView.findViewById(R.id.ItemMarca);
            color = itemView.findViewById(R.id.ItemColor);
        }
    }

    @Override
    public void onClick(View v) {
        if (click != null) {
            click.onClick(v);
        }
    }

    //metodo que se encarga de escuchar el evento OnClickListener
    public void setOnClickListener(View.OnClickListener listener) {
        this.click = listener;
    }

}
