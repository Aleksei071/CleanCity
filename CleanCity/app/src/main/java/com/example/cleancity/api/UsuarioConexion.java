package com.example.cleancity.api;

import android.util.Log;

import com.example.cleancity.interfaces.ICallBackFunciono;
import com.example.cleancity.interfaces.ICallBackUsuario;
import com.example.cleancity.modelos.UsuarioModelo;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UsuarioConexion extends ConexionBase{
    public void seleccionar(ICallBackUsuario callback){
        solicitar("https://cleancitysp.000webhostapp.com/api/solicitudes/usuario/seleccionar.php", new ArrayList(), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("Respuesta", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String respuesta = response.body().string();
                try{
                    JSONArray lista = new JSONArray(respuesta);
                    List<UsuarioModelo> listaRetorno = new ArrayList<>();
                    for (int i = 0; i < lista.length(); i++){
                        UsuarioModelo user = new UsuarioModelo(lista.getJSONObject(i));
                        listaRetorno.add(user);
                    }
                    callback.retornar(listaRetorno);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void buscar(UsuarioModelo user, ICallBackUsuario callback){
        solicitar("https://cleancitysp.000webhostapp.com/api/solicitudes/usuario/buscar.php", user.busqueda(), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("Respuesta", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String respuesta = response.body().string();
                try{
                    JSONArray lista = new JSONArray(respuesta);

                    UsuarioModelo usuario = new UsuarioModelo(lista.getJSONObject(0));

                    callback.retorno(usuario);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void insertar(UsuarioModelo user, ICallBackFunciono callback){
        solicitar("https://cleancitysp.000webhostapp.com/api/solicitudes/usuario/insertar.php", user.parametros(), new Callback() {
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

    public void editar(UsuarioModelo user, ICallBackFunciono callback){
        solicitar("https://cleancitysp.000webhostapp.com/api/solicitudes/usuario/editar.php", user.parametros(), new Callback() {
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

    public void eliminar(UsuarioModelo user, ICallBackFunciono callback){
        solicitar("https://cleancitysp.000webhostapp.com/api/solicitudes/usuario/eliminar.php", user.busqueda(), new Callback() {
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
