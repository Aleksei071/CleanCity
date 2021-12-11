package com.example.cleancity.modelos;

import com.example.cleancity.api.Parametro;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import kotlin.collections.ArrayDeque;

public class SolicitudModelo {
    private int id;
    private String fecha;
    private String descripcion;
    private String sector;
    private String tipo;
    private String direccion;
    private String rutUser;

    public SolicitudModelo() {
    }

    public SolicitudModelo(String fecha, String descripcion, String sector, String tipo, String direccion, String rutUser) {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.sector = sector;
        this.tipo = tipo;
        this.direccion = direccion;
        this.rutUser = rutUser;
    }

    public SolicitudModelo(int id, String fecha, String descripcion, String sector, String tipo, String direccion, String rutUser) {
        this.id = id;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.sector = sector;
        this.tipo = tipo;
        this.direccion = direccion;
        this.rutUser = rutUser;
    }

    public SolicitudModelo(JSONObject fromJSON) throws JSONException {
        this.id = fromJSON.getInt("Id_Retiro");
        this.fecha = fromJSON.getString("Fecha_Retiro");
        this.descripcion = fromJSON.getString("Descripcion_Retiro");
        this.sector = fromJSON.getString("Sector_Retiro");
        this.tipo = fromJSON.getString("Tipo_Escombro");
        this.direccion = fromJSON.getString("Direccion_Retiro");
        this.rutUser = fromJSON.getString("Rut_User");
    }

    public List<Parametro> busqueda(){
        List<Parametro> retornar = new ArrayDeque<Parametro>();
        try{
            retornar.add(new Parametro("Id_Retiro", getId()));
        }catch (Exception e){}

        return retornar;
    }

    public List<Parametro> parametros(){
        List<Parametro> retornar = new ArrayDeque<Parametro>();
        retornar.add(new Parametro("Fecha_Retiro", getFecha()));
        retornar.add(new Parametro("Descripcion_Retiro", getDescripcion()));
        retornar.add(new Parametro("Sector_Retiro", getSector()));
        retornar.add(new Parametro("Tipo_Escombro", getTipo()));
        retornar.add(new Parametro("Direccion_Retiro", getDireccion()));
        retornar.add(new Parametro("Rut_User", getRutUser()));

        return retornar;
    }

    @Override
    public String toString() {
        return "SolicitudModelo{" +
                "id=" + id +
                ", fecha='" + fecha + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", sector='" + sector + '\'' +
                ", tipo='" + tipo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", rutUser='" + rutUser + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRutUser() {
        return rutUser;
    }

    public void setRutUser(String rutUser) {
        this.rutUser = rutUser;
    }
}
