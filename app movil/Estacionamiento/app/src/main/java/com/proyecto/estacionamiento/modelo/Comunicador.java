package com.proyecto.estacionamiento.modelo;

public interface Comunicador {
    public void perfil(String cedula, String nombres,String apellidos,String direccion,String celular);
    public void cuenta(String correo, String clave);
    public void vehiculo(String nro_placa, String color,String marca);

}
