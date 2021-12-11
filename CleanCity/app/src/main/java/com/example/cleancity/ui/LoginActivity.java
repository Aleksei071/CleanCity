package com.example.cleancity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cleancity.R;
import com.example.cleancity.api.RecorridoConexion;
import com.example.cleancity.api.UsuarioConexion;
import com.example.cleancity.interfaces.ICallBackRecorrido;
import com.example.cleancity.interfaces.ICallBackUsuario;
import com.example.cleancity.modelos.RecorridoModelo;
import com.example.cleancity.modelos.UsuarioModelo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements Serializable {
    private Button Registro, Login;
    private EditText username, pass;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();

        username = findViewById(R.id.userRUTTextBox);
        pass = findViewById(R.id.passLoginTextBox);

        Registro = findViewById(R.id.registerLoginBtn);
        Registro.setOnClickListener(v -> {
            Intent I = new Intent(getBaseContext(), RegisterActivity.class);
            startActivity(I);
        });

        Login = findViewById(R.id.loginBtn);
        Login.setOnClickListener(v -> {
            UsuarioConexion conn = new UsuarioConexion();
            UsuarioModelo user = new UsuarioModelo();

            user.setRut(username.getText().toString());

            try {
                conn.buscar(user, new ICallBackUsuario() {
                    @Override
                    public void retornar(List<UsuarioModelo> resultado) {

                    }

                    @Override
                    public void retorno(UsuarioModelo resultado) {
                        Looper.prepare();
                        password = pass.getText().toString();
                        Log.d("RESPONSE", resultado.toString() + " PASS= "+password);
                        if(resultado.getPass().equals(password)){
                            Intent I = new Intent(getBaseContext(), MainActivity.class);
                            I.putExtra("Usuario", resultado.getRut());
                            startActivity(I);
                            username.setText("");
                            pass.setText("");
                        } else {
                            Toast.makeText(getApplicationContext(), "Contraseña Incorrecta", Toast.LENGTH_LONG).show();
                        }
                        Looper.loop();
                    }
                });
            } catch (Exception e) {
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "Rut No Existe", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        });
    }
}