package com.example.cleancity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cleancity.R;
import com.example.cleancity.api.UsuarioConexion;
import com.example.cleancity.interfaces.ICallBackUsuario;
import com.example.cleancity.modelos.UsuarioModelo;

import java.util.List;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    Button Delete, Edit;
    RadioGroup genero;
    EditText nombre, apellido, correo, sector, direccion, telefono, pass, pass2;
    RadioButton mRB, fRB;
    String rut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Objects.requireNonNull(getSupportActionBar()).hide();

        nombre = findViewById(R.id.nameTextBox);
        apellido = findViewById(R.id.lastnameTextBox);
        genero = findViewById(R.id.radio);
        mRB = findViewById(R.id.maleRadioBtn);
        fRB = findViewById(R.id.femaleRadioBtn);
        correo = findViewById(R.id.emailTextBox);
        sector = findViewById(R.id.sectorTextBox);
        direccion = findViewById(R.id.calleTextBox);
        telefono = findViewById(R.id.phoneTextBox);
        pass = findViewById(R.id.passTextBox);
        pass2 = findViewById(R.id.pass2TextBox);

        rut = getIntent().getStringExtra("Usuario");

        setPerfil(rut);

        Delete = findViewById(R.id.DeleteBtn);
        Delete.setOnClickListener(v->{
            eliminarPerfil(rut);
        });

        Edit = findViewById(R.id.EditBtn);
        Edit.setOnClickListener(v-> {
            editarPerfil(rut);
        });
    }

    private void setPerfil(String Rut){
        UsuarioConexion conn = new UsuarioConexion();
        UsuarioModelo user = new UsuarioModelo();

        user.setRut(Rut);

        conn.buscar(user, new ICallBackUsuario() {
            @Override
            public void retornar(List<UsuarioModelo> resultado) {

            }

            @Override
            public void retorno(UsuarioModelo resultado) {
                //Log.d("RESPONSE", resultado.toString());
                Looper.prepare();
                if (resultado.getSexo().equals("Masculino")) {
                        mRB.setChecked(true);
                } else {
                    fRB.setChecked(true);
                }

                nombre.setText(resultado.getNombre());
                apellido.setText(resultado.getApellido());
                correo.setText(resultado.getCorreo());
                sector.setText(resultado.getSector());
                direccion.setText(resultado.getDireccion());
                telefono.setText(resultado.getTelefono());
                pass.setText(resultado.getPass());
                pass2.setText(resultado.getPass());
                Looper.loop();
            }
        });
    }

    private void editarPerfil(String rut){
        UsuarioConexion conn = new UsuarioConexion();
        UsuarioModelo user = new UsuarioModelo();

        if (pass.getText().toString().equals(pass2.getText().toString())) {
            int id = genero.getCheckedRadioButtonId();
            RadioButton sexo = genero.findViewById(id);
            String _sexo = sexo.getText().toString();

            user.setRut(rut);
            user.setNombre(nombre.getText().toString());
            user.setApellido(apellido.getText().toString());
            user.setCorreo(correo.getText().toString());
            user.setSector(sector.getText().toString());
            user.setDireccion(direccion.getText().toString());
            user.setTelefono(telefono.getText().toString());
            user.setPass(pass.getText().toString());
            user.setSexo(_sexo);

            conn.editar(user, funciono -> { });
            Toast.makeText(getApplicationContext(), "Actualizacion Exitosa", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getBaseContext(), MainActivity.class);
            i.putExtra("Usuario", rut);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), "Las Contraseñas no Coiciden", Toast.LENGTH_LONG).show();
        }
    }

    private void eliminarPerfil(String rut){
        UsuarioConexion conn = new UsuarioConexion();
        UsuarioModelo user = new UsuarioModelo();

        user.setRut(rut);

        conn.eliminar(user, funciono -> { });

        Toast.makeText(getApplicationContext(), "Perfil Eliminado", Toast.LENGTH_LONG).show();
        Intent i = new Intent(getBaseContext(), LoginActivity.class);
        i.putExtra("Usuario", rut);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        Intent I = new Intent(getBaseContext(),MainActivity.class);
        I.putExtra("Usuario", rut);
        super.onBackPressed();
    }
}