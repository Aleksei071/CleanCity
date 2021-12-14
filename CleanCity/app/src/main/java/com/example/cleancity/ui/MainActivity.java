package com.example.cleancity.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.cleancity.R;
import com.example.cleancity.api.CamionConexion;
import com.example.cleancity.interfaces.ICallBackCamion;
import com.example.cleancity.modelos.CamionModelo;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.UrlTileProvider;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView map;
    private GoogleMap _map;
    private ArrayList<CamionModelo> lista = new ArrayList<>();
    private String rut;
    boolean isPermissionGranted;

    /** SISTEMA DE PUNTOS EN MAPA FUNCIONA - INFORMACION DEL PUNTO NO SE PUEDE EXTRAER SOLO CON GOOGLE MAPS SE NECESITA GEOCODING API PARA OBTENER DATOS DESDE MAPS BILLING REQUERIDO ??
     *  SE PUEDE SACAR LAT Y LONG - CREAR VENTANAS DE INFORMACION PERSONALIZADAR PARA RUTAS Y PUNTOS DE CAMIONES - NO SE NECESITA BILLING
     *  LAT Y LONG SE PUEDEN GUARDAR EN BASE DE DATOS Y COLOCAR LOS PUNTOS PARA QUE LOS VEAN LOS USUARIOS */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();

        rut = getIntent().getStringExtra("Usuario");
        setButtons();
        camiones();

        map = findViewById(R.id.mapView);
        map.onCreate(savedInstanceState);
        map.getMapAsync(this);

        checkPermission();
    }

    private void checkPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isPermissionGranted = true;
                //Toast.makeText(MainActivity.this, "PERMISION GRANTED", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                /*Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(),"" );
                intent.setData(uri);
                startActivity(intent);*/
                final Handler handler = new Handler();
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
        LatLng myPos = new LatLng(-36.838338, -73.106981);
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
        markers();
        _map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull @NotNull Marker marker) {
                marker.showInfoWindow();
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(_map != null){
            LatLng myPos = new LatLng(-36.838338,-73.106981);
            _map.animateCamera(CameraUpdateFactory.newLatLngZoom(myPos, 15));
        }
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /*private void addMarker(){
        _map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull @NotNull LatLng latLng) {
                removeMarker();
                marker = _map.addMarker(new MarkerOptions().position(latLng));
                Log.d("UBICACION", latLng.latitude + " " + latLng.longitude + " " + latLng.describeContents());
                if (marker != null) {
                    marker.hideInfoWindow();
                    marker.showInfoWindow();
                }
            }
        });
    }

    private void removeMarker(){
        if (marker != null){
            marker.remove();
            marker = null;
        }
    }*/

    private void setButtons(){
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

        Button calendar = findViewById(R.id.CalendarBTN);
        calendar.setOnClickListener(v -> {
            Intent I = new Intent(getBaseContext(), CalendarActivity.class);
            I.putExtra("Usuario", rut);
            startActivity(I);
        });

        Button ruta = findViewById(R.id.rutaBtn);
        ruta.setOnClickListener(v -> {
            Intent I = new Intent(getBaseContext(), CachureoActivity.class);
            I.putExtra("Usuario", rut);
            startActivity(I);
        });

        Button solicitud = findViewById(R.id.escombrosBtn);
        solicitud.setOnClickListener(v -> {
            Intent I = new Intent(getBaseContext(), SolicitudActivity.class);
            I.putExtra("Usuario", rut);
            startActivity(I);
        });
    }

    private void camiones(){
        CamionConexion con = new CamionConexion();

        con.seleccionar(new ICallBackCamion() {
            @Override
            public void retornar(List<CamionModelo> resultado) {
                lista.addAll(resultado);
            }

            @Override
            public void retorno(CamionModelo resultado) { }
        });
    }

    private void markers(){
        for (int i = 0; i < lista.size(); i++){
            Log.d("RESPONSE", lista.get(i).toString());
            Marker marker = _map.addMarker(new MarkerOptions()
                    .position(new LatLng(lista.get(i).getLat(), lista.get(i).getLng()))
                    .title(lista.get(i).getMatricula())
                    .snippet(lista.get(i).getLat() + " " + lista.get(i).getLng()));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent I = new Intent(getBaseContext(),LoginActivity.class);
        startActivity(I);
    }
}