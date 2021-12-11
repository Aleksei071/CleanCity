package com.example.cleancity.api;

import android.util.Log;

import com.example.cleancity.interfaces.ICallBackCamion;
import com.example.cleancity.interfaces.ICallBackFunciono;
import com.example.cleancity.interfaces.ICallBackRecorrido;
import com.example.cleancity.modelos.CamionModelo;
import com.example.cleancity.modelos.RecorridoModelo;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CamionConexion extends ConexionBase{
    public void seleccionar(ICallBackCamion callback){
        solicitar("https://cleancitysp.000webhostapp.com/api/solicitudes/camion/seleccionar.php", new ArrayList(), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("Respuesta", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String respuesta = response.body().string();
                try{
                    JSONArray lista = new JSONArray(respuesta);
                    List<CamionModelo> listaRetorno = new ArrayList<>();
                    for (int i = 0; i < lista.length(); i++){
                        CamionModelo cam = new CamionModelo(lista.getJSONObject(i));
                        listaRetorno.add(cam);
                    }
                    callback.retornar(listaRetorno);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void buscar(CamionModelo cam, ICallBackCamion callback){
        solicitar("https://cleancitysp.000webhostapp.com/api/solicitudes/camion/buscar.php", cam.busqueda(), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("Respuesta", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String respuesta = response.body().string();
                try{
                    JSONArray lista = new JSONArray(respuesta);

                    CamionModelo recorrido = new CamionModelo(lista.getJSONObject(0));

                    callback.retorno(recorrido);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void insertar(CamionModelo cam, ICallBackFunciono callback){
        solicitar("https://cleancitysp.000webhostapp.com/api/solicitudes/camion/insertar.php", cam.parametros(), new Callback() {
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

    public void editar(CamionModelo cam, ICallBackFunciono callback){
        solicitar("https://cleancitysp.000webhostapp.com/api/solicitudes/camion/editar.php", cam.parametros(), new Callback() {
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

    public void eliminar(CamionModelo cam, ICallBackFunciono callback){
        solicitar("https://cleancitysp.000webhostapp.com/api/solicitudes/camion/eliminar.php", cam.busqueda(), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("Respuesta", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try{
                    String funciono = response.body().string();
                    callback.retornar(Boolean.parseBoolean(funciono));
                } catch (Exception e){

                }
            }
        });
    }
}
