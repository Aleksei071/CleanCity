package com.example.cleancity.api;

import android.util.Log;

import com.example.cleancity.interfaces.ICallBackFunciono;
import com.example.cleancity.interfaces.ICallBackRecorrido;
import com.example.cleancity.interfaces.ICallBackUsuario;
import com.example.cleancity.modelos.RecorridoModelo;
import com.example.cleancity.modelos.UsuarioModelo;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RecorridoConexion extends ConexionBase{
    public void seleccionar(ICallBackRecorrido callback){
        solicitar("https://cleancitysp.000webhostapp.com/api/solicitudes/recorrido/seleccionar.php", new ArrayList(), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("Respuesta", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String respuesta = response.body().string();
                try{
                    JSONArray lista = new JSONArray(respuesta);
                    List<RecorridoModelo> listaRetorno = new ArrayList<>();
                    for (int i = 0; i < lista.length(); i++){
                        RecorridoModelo rec = new RecorridoModelo(lista.getJSONObject(i));
                        listaRetorno.add(rec);
                    }
                    callback.retornar(listaRetorno);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void buscar(RecorridoModelo rec, ICallBackRecorrido callback){
        solicitar("https://cleancitysp.000webhostapp.com/api/solicitudes/recorrido/buscar.php", rec.busqueda(), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("Respuesta", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String respuesta = response.body().string();
                try{
                    JSONArray lista = new JSONArray(respuesta);

                    RecorridoModelo recorrido = new RecorridoModelo(lista.getJSONObject(0));

                    callback.retorno(recorrido);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void insertar(RecorridoModelo rec, ICallBackFunciono callback){
        solicitar("https://cleancitysp.000webhostapp.com/api/solicitudes/recorrido/insertar.php", rec.parametros(), new Callback() {
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

    public void editar(RecorridoModelo rec, ICallBackFunciono callback){
        solicitar("https://cleancitysp.000webhostapp.com/api/solicitudes/recorrido/editar.php", rec.parametros(), new Callback() {
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

    public void eliminar(RecorridoModelo rec, ICallBackFunciono callback){
        solicitar("https://cleancitysp.000webhostapp.com/api/solicitudes/recorrido/eliminar.php", rec.busqueda(), new Callback() {
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
