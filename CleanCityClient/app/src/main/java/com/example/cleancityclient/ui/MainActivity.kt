package com.example.cleancityclient.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.cleancityclient.R
import com.example.cleancityclient.api.CamionConexion
import com.example.cleancityclient.interfaces.ICallBackCamion
import com.example.cleancityclient.modelos.CamionModelo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: MapView
    private var _map: GoogleMap? = null
    private var lista: ArrayList<CamionModelo> = ArrayList()
    private lateinit var solBtn: Button
    private lateinit var rutaBtn: Button
    //private lateinit var fusedLocationClient: FusedLocationProviderClient
    //private var latitud: Double = 0.0
    //private var longitud: Double = 0.0
    private var isPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Objects.requireNonNull(supportActionBar)!!.hide()

        setWidgets()
        camiones()

        map = findViewById(R.id.mapView)
        map.onCreate(savedInstanceState)
        map.getMapAsync(this)
        checkPermission()

        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        //fusedLocationClient.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10,  )
    }

    private fun camiones() {
        val con = CamionConexion()
        con.seleccionar(object : ICallBackCamion {
            override fun retornar(resultado: List<CamionModelo>) {
                lista.addAll(resultado)
                this@MainActivity.runOnUiThread(java.lang.Runnable {
                    markers()
                })
            }
            override fun retorno(resultado: CamionModelo) {}
        })
    }

    private fun markers() {
        for (i in lista.indices) {
            Log.d("RESPONSE", lista[i].toString())
            _map!!.addMarker(
                MarkerOptions()
                    .position(LatLng(lista[i].getLatitud()!!, lista[i].getLongitud()!!))
                    .title(lista[i].getMatricula())
                    .snippet(lista[i].getLatitud().toString() + " " + lista[i].getLongitud().toString())
            )
        }
        _map!!.setOnMarkerClickListener { marker ->
            marker.showInfoWindow()
            false
        }
    }

    private fun checkPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse?) {
                    isPermissionGranted = true
                    //Toast.makeText(MainActivity.this, "PERMISION GRANTED", Toast.LENGTH_LONG).show();
                }

                override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse?) {
                    /*Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(),"" );
                intent.setData(uri);
                startActivity(intent);*/
                    val handler = Handler()
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setMessage("Para mostrar su ubicacion necesita activar el servicio de ubicacion en su celular")
                    val dialog = builder.create()
                    dialog.show()
                    handler.postDelayed({ dialog.dismiss() }, 3000)
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: com.karumi.dexter.listener.PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }
            }).check()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        _map = googleMap
        val myPos = LatLng(-36.838338, -73.106981)
        _map!!.animateCamera(CameraUpdateFactory.newLatLngZoom(myPos, 15f))
        _map!!.uiSettings.isZoomControlsEnabled = true
        _map!!.uiSettings.isCompassEnabled = true
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        _map!!.isMyLocationEnabled = true
    }

    override fun onResume() {
        super.onResume()
        val myPos = LatLng(-36.838338, -73.106981)
        _map?.animateCamera(CameraUpdateFactory.newLatLngZoom(myPos, 15f))
        map.onResume()
    }

    private fun setWidgets(){
        solBtn = findViewById(R.id.solicitudesBtn)
        solBtn.setOnClickListener { v ->
            val I = Intent(this, ListadoActivity::class.java)
            startActivity(I)
        }

        rutaBtn = findViewById(R.id.rutaBtn)
        rutaBtn.setOnClickListener{ v ->
            val I = Intent(this, RutaActivity::class.java)
            startActivity(I)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val I = Intent(this, MainActivity::class.java)
        startActivity(I)
    }

    /*override fun onLocationChanged(newlocation: Location){
        checkPermission()
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                latitud = location.latitude
                longitud = location.longitude

            }
        }
        cambiarCamionGPS(latitud, longitud)
    }*/

    /*private fun cambiarCamionGPS(latitud: Double, longitud: Double) {
        val con = CamionConexion()
        val camion = CamionModelo()

        camion.setMatricula("1")

        con.buscar(camion, object : ICallBackCamion {
            override fun retornar(resultado: List<CamionModelo>) {}
            override fun retorno(resultado: CamionModelo) {
                camion.setConductor(resultado.getConductor())
                Log.d("RESPONSE", resultado.toString())
            }
        })

        camion.setLatitud(latitud)
        camion.setLongitud(longitud)

        con.editar(camion, object : ICallBackFunciono{
            override fun retornar(funciono: Boolean) {
                Log.d("RESPONSE", camion.toString())
            }})
    }*/
}