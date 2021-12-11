package com.example.cleancity.modelos;

import com.example.cleancity.api.Parametro;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import kotlin.collections.ArrayDeque;

public class CamionModelo {
    private String matricula;
    private String conductor;
    private double lat;
    private double lng;

    public CamionModelo() {
    }

    public CamionModelo(String matricula, String conductor, double lat, double lng) {
        this.matricula = matricula;
        this.conductor = conductor;
        this.lat = lat;
        this.lng = lng;
    }

    public CamionModelo(JSONObject fromJSON) throws JSONException {
        this.matricula = fromJSON.getString("Matricula_Camion");
        this.conductor = fromJSON.getString("Conductor_Camion");
        this.lat = fromJSON.getDouble("Latitud_Camion");
        this.lng = fromJSON.getDouble("Longitud_Camion");
    }

    public List<Parametro> busqueda(){
        List<Parametro> retornar = new ArrayDeque<Parametro>();
        try{
            retornar.add(new Parametro("Matricula_Camion", getMatricula()));
        }catch (Exception e){}

        return retornar;
    }

    public List<Parametro> parametros(){
        List<Parametro> retornar = new ArrayDeque<Parametro>();
        try{
            retornar.add(new Parametro("Matricula_Camion", getMatricula()));
        }catch (Exception e){}
        retornar.add(new Parametro("Conductor_Camion", getConductor()));
        retornar.add(new Parametro("Latitud_Camion", getLat()));
        retornar.add(new Parametro("Longitud_Camion", getLng()));

        return retornar;
    }

    @Override
    public String toString() {
        return "CamionModelo{" +
                "matricula='" + matricula + '\'' +
                ", nombre='" + conductor + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
