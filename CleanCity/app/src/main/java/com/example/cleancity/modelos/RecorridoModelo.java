package com.example.cleancity.modelos;

import com.example.cleancity.api.Parametro;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import kotlin.collections.ArrayDeque;

public class RecorridoModelo {
    private int id;
    private String sector;
    private String horario;
    private int calendario;
    private String ruta;
    private String retiro;
    private String matricula;

    public RecorridoModelo() {
    }

    public RecorridoModelo(int id, String sector, String horario, int calendario, String ruta, String retiro, String matricula) {
        this.id = id;
        this.sector = sector;
        this.horario = horario;
        this.calendario = calendario;
        this.ruta = ruta;
        this.retiro = retiro;
        this.matricula = matricula;
    }

    public RecorridoModelo(String sector, String horario, int calendario, String ruta, String retiro, String matricula) {
        this.sector = sector;
        this.horario = horario;
        this.calendario = calendario;
        this.ruta = ruta;
        this.retiro = retiro;
        this.matricula = matricula;
    }

    public RecorridoModelo(JSONObject fromJSON) throws JSONException {
        this.id = fromJSON.getInt("Id_Recorrido");
        this.sector = fromJSON.getString("Sector_Recorrido");
        this.horario = fromJSON.getString("Horario_Recorrido");
        this.calendario = fromJSON.getInt("Calendario_Recorrido");
        this.ruta = fromJSON.getString("Ruta_Cachureo");
        this.retiro = fromJSON.getString("Retiro_Escombro");
        this.matricula = fromJSON.getString("Matricula_Camion");
    }

    public List<Parametro> busqueda(){
        List<Parametro> retornar = new ArrayDeque<Parametro>();
        try{
            retornar.add(new Parametro("Id_Recorrido", getId()));
        }catch (Exception e){}

        return retornar;
    }

    public List<Parametro> parametros(){
        List<Parametro> retornar = new ArrayDeque<Parametro>();
        retornar.add(new Parametro("Id_Recorrido", getId()));
        retornar.add(new Parametro("Sector_Recorrido", getSector()));
        retornar.add(new Parametro("Horario_Recorrido", getHorario()));
        retornar.add(new Parametro("Calendario_Recorrido", getCalendario()));
        retornar.add(new Parametro("Ruta_Cachureo", getRuta()));
        retornar.add(new Parametro("Retiro_Escombro", getRetiro()));
        retornar.add(new Parametro("Matricula_Camion", getMatricula()));

        return retornar;
    }

    @Override
    public String toString() {
        return "RecorridoModelo{" +
                "id=" + id +
                ", sector='" + sector + '\'' +
                ", horario='" + horario + '\'' +
                ", calendario=" + calendario +
                ", ruta='" + ruta + '\'' +
                ", retiro='" + retiro + '\'' +
                ", matricula='" + matricula + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public int getCalendario() {
        return calendario;
    }

    public void setCalendario(int calendario) {
        this.calendario = calendario;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getRetiro() {
        return retiro;
    }

    public void setRetiro(String retiro) {
        this.retiro = retiro;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
