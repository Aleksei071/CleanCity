package com.example.cleancity.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.cleancity.R;
import com.example.cleancity.api.RecorridoConexion;
import com.example.cleancity.interfaces.ICallBackRecorrido;
import com.example.cleancity.modelos.DirectionsJSONParse;
import com.example.cleancity.modelos.RecorridoModelo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.maps.model.SquareCap;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CachureoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView map;
    private GoogleMap _map;
    private String rut;
    private TextView sector, ruta, horario;
    boolean isPermissionGranted;

    /** SISTEMA DE RUTA USANDO DATOS REALES NO FUNCIONA SE NECESITA HABILITAR EL BILLING PARA PODER CREAR UNA RUTA CON GOOGLE DIRECTIONS API PARA QUE SIGUA EL CAMINO EN EL MAPA SIN SER UNA LINEA RECTA
     *  POSIBLE SOLUCION COLOCAR CAMINO MANUAL ATRAVES DE MULTIPLES PUNTOS EN EL MAPA PARA CREAR LINEA QUE SIGUA EL CAMINON - USAR ARRAY DE MARKER PARA OBTENER LAT Y LONG Y UNIRLOS AL POLYLINE
     *  CODIGO PARA SISTEMA AUTOMATIZADO EN Ruta.java*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cachureo);

        Objects.requireNonNull(getSupportActionBar()).hide();

        rut = getIntent().getStringExtra("Usuario");

        setButtons();

        map.onCreate(savedInstanceState);

        map.getMapAsync(this);

        checkPermission();
    }

    private void checkPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isPermissionGranted = true;
                //Toast.makeText(CachureoActivity.this, "PERMISION GRANTED", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                /*Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(),"" );
                intent.setData(uri);
                startActivity(intent);*/
                final Handler handler = new Handler();
                final AlertDialog.Builder builder = new AlertDialog.Builder(CachureoActivity.this);
                builder.setMessage("Para mostrar su ubicacion necesita activar el servicio de ubicacion en su celular");
                final AlertDialog dialog = builder.create();
                dialog.show();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                    }
                }, 3000);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        _map = googleMap;
        LatLng myPos = new LatLng(-36.8417, -73.1074);
        _map.animateCamera(CameraUpdateFactory.newLatLngZoom(myPos, 15));

        _map.getUiSettings().setZoomControlsEnabled(true);
        _map.getUiSettings().setCompassEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        _map.setMyLocationEnabled(true);

        ruta();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(_map != null){
            LatLng myPos = new LatLng(-36.8417,-73.1074);
            _map.animateCamera(CameraUpdateFactory.newLatLngZoom(myPos, 12));
        }
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void ruta(){
        Marker start = _map.addMarker(new MarkerOptions().position(new LatLng(-36.8417, -73.1074)).title("INICIO RUTA").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        Marker end = _map.addMarker(new MarkerOptions().position(new LatLng(-36.8386, -73.1127)).title("TERMINO RUTA").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        Polyline polyline1 = _map.addPolyline(new PolylineOptions()
                .clickable(true)
                .color(Color.BLUE)
                .add(
                        new LatLng(-36.8417, -73.1074),
                        new LatLng(-36.8416, -73.1084),
                        new LatLng(-36.8415, -73.1097),
                        new LatLng(-36.8414, -73.1115),
                        new LatLng(-36.8387, -73.1113),
                        new LatLng(-36.8386, -73.1127)));

        polyline1.setTag("A");
        polyline1.setJointType(JointType.ROUND);
        polyline1.setEndCap(new SquareCap());
        polyline1.setStartCap(new SquareCap());
    }

    private void setButtons(){
        map = findViewById(R.id.rutaMap);
        sector = findViewById(R.id.sectorTxtBox);
        ruta = findViewById(R.id.rutaTxtBox);
        horario = findViewById(R.id.horarioTxtBox);

        showRecorrido();

        ImageView profileImg = findViewById(R.id.UserImg);
        profileImg.setOnClickListener(v -> {
            Intent I = new Intent(getBaseContext(), ProfileActivity.class);
            I.putExtra("Usuario", rut);
            startActivity(I);
        });

        ImageView helpIMG = findViewById(R.id.HelpImg);
        helpIMG.setOnClickListener(v -> {
            Intent I = new Intent(getBaseContext(), HelpActivity.class);
            I.putExtra("Usuario", rut);
            startActivity(I);
        });
    }

    private void showRecorrido(){
        RecorridoConexion conn = new RecorridoConexion();
        RecorridoModelo rec = new RecorridoModelo();
        rec.setId(1);

        conn.buscar(rec, new ICallBackRecorrido() {
            @Override
            public void retornar(List<RecorridoModelo> resultado) {

            }

            @Override
            public void retorno(RecorridoModelo resultado) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        sector.setText(resultado.getSector());
                        ruta.setText(resultado.getRuta());
                        horario.setText(resultado.getHorario());
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent I = new Intent(getBaseContext(),MainActivity.class);
        I.putExtra("Usuario", rut);
        super.onBackPressed();
    }
}