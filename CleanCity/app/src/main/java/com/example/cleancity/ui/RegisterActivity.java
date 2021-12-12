package com.example.cleancity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cleancity.R;
import com.example.cleancity.api.UsuarioConexion;
import com.example.cleancity.interfaces.ICallBackFunciono;
import com.example.cleancity.interfaces.ICallBackUsuario;
import com.example.cleancity.modelos.UsuarioModelo;

import java.util.List;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    Button Register;
    RadioGroup genero;
    CheckBox tos;
    EditText rut, nombre, apellido, correo, sector, direccion, telefono, pass, pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Objects.requireNonNull(getSupportActionBar()).hide();

        setButtons();
        Register.setOnClickListener(v -> {
            String _rut = rut.getText().toString();
            String _nombre = nombre.getText().toString();
            String _apellido = apellido.getText().toString();
            String _correo = correo.getText().toString();
            String _sector = sector.getText().toString();
            String _direccion = direccion.getText().toString();
            String _telefono = telefono.getText().toString();
            String _pass = pass.getText().toString();
            String _pass2 = pass2.getText().toString();

            int id = genero.getCheckedRadioButtonId();
            RadioButton sexo = genero.findViewById(id);
            String _sexo = sexo.getText().toString();

            validacionRegistro(_rut, _nombre, _apellido, _sexo, _correo, _sector, _direccion, _telefono, _pass, _pass2);
        });
    }

    private void registro(String _rut, String _nombre, String _apellido, String _sexo, String _correo, String _sector, String _direccion, String _telefono, String _pass, String _pass2){
        UsuarioConexion conn = new UsuarioConexion();
        UsuarioModelo user = new UsuarioModelo();

        user.setRut(_rut);

        conn.buscar(user, new ICallBackUsuario() {
            @Override
            public void retornar(List<UsuarioModelo> resultado) {  }

            @Override
            public void retorno(UsuarioModelo resultado) {
                Looper.prepare();
                if (resultado.getRut()!=null){
                    Toast.makeText(getApplicationContext(), "Rut ya existe", Toast.LENGTH_LONG).show();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            user.setNombre(_nombre);
                            user.setApellido(_apellido);
                            user.setSexo(_sexo);
                            user.setCorreo(_correo);
                            user.setSector(_sector);
                            user.setDireccion(_direccion);
                            user.setTelefono(_telefono);
                            user.setPass(_pass);
                            if (!tos.isChecked()) {
                                Toast.makeText(getApplicationContext(), "Debe Aceptar Los Términos y Condiciones", Toast.LENGTH_LONG).show();
                            } else {
                                if (_pass.equals(_pass2)) {
                                    conn.insertar(user, funciono -> {
                                    });
                                    Toast.makeText(getApplicationContext(), "Registro Exitoso", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getBaseContext(), LoginActivity.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Las Contraseñas no Coiciden", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                }
                Looper.loop();
            }
        });
    }

    private void setButtons(){
        Register = findViewById(R.id.registerBtn);

        tos = findViewById(R.id.tosCheck);
        rut = findViewById(R.id.rutTextBox);
        nombre = findViewById(R.id.nameTextBox);
        apellido = findViewById(R.id.lastnameTextBox);
        genero = findViewById(R.id.radio);
        correo = findViewById(R.id.emailTextBox);
        sector = findViewById(R.id.sectorTextBox);
        direccion = findViewById(R.id.calleTextBox);
        telefono = findViewById(R.id.phoneTextBox);
        pass = findViewById(R.id.passTextBox);
        pass2 = findViewById(R.id.pass2TextBox);
    }

    private void validacionRegistro(String _rut, String _nombre, String _apellido, String _sexo, String _correo, String _sector, String _direccion, String _telefono, String _pass, String _pass2){
        if (rut.length()==0){
            rut.requestFocus();
            rut.setError("Este campo no puede estar vacio");
        } else if (nombre.length()==0){
            nombre.requestFocus();
            nombre.setError("Este campo no puede estar vacio");
        } else if (apellido.length()==0) {
            apellido.requestFocus();
            apellido.setError("Este campo no puede estar vacio");
        } else if (correo.length()==0) {
            correo.requestFocus();
            correo.setError("Este campo no puede estar vacio");
        } else if (sector.length()==0) {
            sector.requestFocus();
            sector.setError("Este campo no puede estar vacio");
        } else if (direccion.length()==0) {
            direccion.requestFocus();
            direccion.setError("Este campo no puede estar vacio");
        } else if (telefono.length()==0) {
            telefono.requestFocus();
            telefono.setError("Este campo no puede estar vacio");
        } else if (pass.length()==0) {
            pass.requestFocus();
            pass.setError("Este campo no puede estar vacio");
        } else if (pass2.length()==0) {
            pass2.requestFocus();
            pass2.setError("Este campo no puede estar vacio");
        } else {
            registro(_rut, _nombre, _apellido, _sexo, _correo, _sector, _direccion, _telefono, _pass, _pass2);
        }
    }
}