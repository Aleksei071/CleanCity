package com.example.cleancity.api;

import android.util.Log;

import com.example.cleancity.interfaces.ICallBackFunciono;
import com.example.cleancity.interfaces.ICallBackSolicitud;
import com.example.cleancity.modelos.SolicitudModelo;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SolicitudConexion extends ConexionBase {
    public void seleccionar(ICallBackSolicitud callback){
        solicitar("https://cleancitysp.000webhostapp.com/api/solicitudes/solicitud_retiro/seleccionar.php", new ArrayList<>(), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("Respuesta", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String respuesta = response.body().string();
                try{
                    JSONArray lista = new JSONArray(respuesta);
                    List<SolicitudModelo> listaRetorno = new ArrayList<>();
                    for (int i = 0; i < lista.length(); i++){
                        SolicitudModelo sol = new SolicitudModelo(lista.getJSONObject(i));
                        listaRetorno.add(sol);
                    }
                    callback.retornar(listaRetorno);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void insertar(SolicitudModelo sol, ICallBackFunciono callback){
        solicitar("https://cleancitysp.000webhostapp.com/api/solicitudes/solicitud_retiro/insertar.php", sol.parametros(), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("Respuesta", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try{
                    String funciono = response.body().string();
                    callback.retornar(Boolean.parseBoolean(funciono));
                } catch (Exception e){}
            }
        });
    }

    public void editar(SolicitudModelo sol, ICallBackFunciono callback){
        solicitar("https://cleancitysp.000webhostapp.com/api/solicitudes/solicitud_retiro/editar.php", sol.parametros(), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("Respuesta", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try{
                    String funciono = response.body().string();
                    callback.retornar(Boolean.parseBoolean(funciono));
                } catch (Exception e){}
            }
        });
    }

    public void eliminar(SolicitudModelo sol, ICallBackFunciono callback){
        solicitar("https://cleancitysp.000webhostapp.com/api/solicitudes/solicitud_retiro/eliminar.php", sol.busqueda(), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("Respuesta", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try{
                    String funciono = response.body().string();
                    callback.retornar(Boolean.parseBoolean(funciono));
                } catch (Exception e){}
            }
        });
    }

    public void buscar(SolicitudModelo sol, ICallBackFunciono callback){
        solicitar("https://cleancitysp.000webhostapp.com/api/solicitudes/solicitud_retiro/insertar.php", sol.busqueda(), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("Respuesta", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try{
                    String funciono = response.body().string();
                    callback.retornar(Boolean.parseBoolean(funciono));
                } catch (Exception e){}
            }
        });
    }
}