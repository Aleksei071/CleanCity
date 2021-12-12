package com.example.cleancity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cleancity.R;
import com.example.cleancity.api.SolicitudConexion;
import com.example.cleancity.api.UsuarioConexion;
import com.example.cleancity.interfaces.ICallBackUsuario;
import com.example.cleancity.modelos.SolicitudModelo;
import com.example.cleancity.modelos.UsuarioModelo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class SolicitudActivity extends AppCompatActivity {
    String rut, fecha;
    EditText fullName, correo, telefono, sector, dir, tipo, desc;
    Button Enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud);

        Objects.requireNonNull(getSupportActionBar()).hide();

        rut = getIntent().getStringExtra("Usuario");

        fullName = findViewById(R.id.fullnameTextBox);
        correo = findViewById(R.id.emailTextBox);
        telefono = findViewById(R.id.phoneTextBox);

        setUsuario(rut);

        Enviar = findViewById(R.id.sendBtn);
        Enviar.setOnClickListener(v -> {
            enviarSolicitud(rut);
        });
    }

    private void setUsuario(String Rut){
        UsuarioConexion conn = new UsuarioConexion();
        UsuarioModelo user = new UsuarioModelo();

        user.setRut(Rut);

        conn.buscar(user, new ICallBackUsuario() {
            @Override
            public void retornar(List<UsuarioModelo> resultado) {

            }

            @Override
            public void retorno(UsuarioModelo resultado) {
                Log.d("RESPONSE", resultado.getTelefono());
                String nombre = resultado.getNombre() + " " + resultado.getApellido();

                fullName.setText(nombre);
                correo.setText(resultado.getCorreo());
                telefono.setText(resultado.getTelefono());
            }
        });
    }

    private void enviarSolicitud(String rut){
        SolicitudConexion connSol = new SolicitudConexion();
        SolicitudModelo sol = new SolicitudModelo();

        sector = findViewById(R.id.sectorTxtBox);
        dir = findViewById(R.id.calleTextBox);
        tipo = findViewById(R.id.escombroeTxtBox);
        desc = findViewById(R.id.descRetiroTxtBox);

        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");

        fecha = date.format(Calendar.getInstance().getTime());

        sol.setSector(sector.getText().toString());
        sol.setDescripcion(desc.getText().toString());
        sol.setDireccion(dir.getText().toString());
        sol.setTipo(tipo.getText().toString());
        sol.setRutUser(rut);
        sol.setFecha(fecha);

        if (sector.length()==0){
            sector.requestFocus();
            sector.setError("Este campo no puede estar vacio");
        } else if (dir.length()==0){
            dir.requestFocus();
            dir.setError("Este campo no puede estar vacio");
        } else if (tipo.length()==0) {
            tipo.requestFocus();
            tipo.setError("Este campo no puede estar vacio");
        } else if (desc.length()==0) {
            correo.requestFocus();
            correo.setError("Este campo no puede estar vacio");
        } else {
            try {
                connSol.insertar(sol, funciono -> { });
                Toast.makeText(getApplicationContext(), "Se ha enviado la solicitud", Toast.LENGTH_LONG).show();
                Intent I = new Intent(getBaseContext(), MainActivity.class);
                I.putExtra("Usuario", rut);
                startActivity(I);
                finish();
            } catch (Exception e) { Log.d("RESPONSE", e.getMessage()); }
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent I = new Intent(getBaseContext(),MainActivity.class);
        I.putExtra("Usuario", rut);
        super.onBackPressed();
    }
}