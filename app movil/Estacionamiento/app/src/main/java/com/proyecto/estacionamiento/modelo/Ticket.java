package com.proyecto.estacionamiento.modelo;

public class Ticket {
    private String fechaini;
    private String fechafin;
    private String horaini;
    private String horafin;
    private String costohora;
    private String costototal;
    private String id_persona;
    private String id_sitio;

    public Ticket() {
    }

    public String getFechaini() {
        return fechaini;
    }

    public void setFechaini(String fechaini) {
        this.fechaini = fechaini;
    }

    public String getFechafin() {
        return fechafin;
    }

    public void setFechafin(String fechafin) {
        this.fechafin = fechafin;
    }

    public String getHoraini() {
        return horaini;
    }

    public void setHoraini(String horaini) {
        this.horaini = horaini;
    }

    public String getHorafin() {
        return horafin;
    }

    public void setHorafin(String horafin) {
        this.horafin = horafin;
    }

    public String getCostohora() {
        return costohora;
    }

    public void setCostohora(String costohora) {
        this.costohora = costohora;
    }

    public String getCostototal() {
        return costototal;
    }

    public void setCostototal(String costototal) {
        this.costototal = costototal;
    }

    public String getId_persona() {
        return id_persona;
    }

    public void setId_persona(String id_persona) {
        this.id_persona = id_persona;
    }

    public String getId_sitio() {
        return id_sitio;
    }

    public void setId_sitio(String id_sitio) {
        this.id_sitio = id_sitio;
    }
}
