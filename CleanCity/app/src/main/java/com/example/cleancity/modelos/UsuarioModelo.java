package com.example.cleancity.modelos;

import com.example.cleancity.api.Parametro;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import kotlin.collections.ArrayDeque;

public class UsuarioModelo {
    private String rut;
    private String nombre;
    private String apellido;
    private String sexo;
    private String correo;
    private String sector;
    private String direccion;
    private String telefono;
    private String pass;

    public UsuarioModelo() {
    }

    public UsuarioModelo(String rut, String nombre, String apellido, String sexo, String correo, String sector, String direccion, String telefono, String pass) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.correo = correo;
        this.sector = sector;
        this.direccion = direccion;
        this.telefono = telefono;
        this.pass = pass;
    }

    public UsuarioModelo(JSONObject fromJSON) throws JSONException {
        this.rut = fromJSON.getString("Rut_User");
        this.nombre = fromJSON.getString("Nombre_User");
        this.apellido = fromJSON.getString("Apellido_User");
        this.sexo = fromJSON.getString("Sexo_User");
        this.correo = fromJSON.getString("Correo_User");
        this.sector = fromJSON.getString("Sector_User");
        this.direccion = fromJSON.getString("Direccion_User");
        this.telefono = fromJSON.getString("Telefono_User");
        this.pass = fromJSON.getString("Pass_User");
    }

    public List<Parametro> busqueda(){
        List<Parametro> retornar = new ArrayDeque<Parametro>();
        try{
            retornar.add(new Parametro("Rut_User", getRut()));
        }catch (Exception e){}

        return retornar;
    }

    public List<Parametro> parametros(){
        List<Parametro> retornar = new ArrayDeque<Parametro>();
        try{
            retornar.add(new Parametro("Rut_User", getRut()));
        }catch (Exception e){}
            retornar.add(new Parametro("Nombre_User", getNombre()));
            retornar.add(new Parametro("Apellido_User", getApellido()));
            retornar.add(new Parametro("Sexo_User", getSexo()));
            retornar.add(new Parametro("Correo_User", getCorreo()));
            retornar.add(new Parametro("Sector_User", getSector()));
            retornar.add(new Parametro("Direccion_User", getDireccion()));
            retornar.add(new Parametro("Telefono_User", getTelefono()));
            retornar.add(new Parametro("Pass_User", getPass()));

        return retornar;
    }

    @Override
    public String toString() {
        return "UsuarioModelo{" +
                "rut='" + rut + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", sexo='" + sexo + '\'' +
                ", correo='" + correo + '\'' +
                ", sector='" + sector + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
