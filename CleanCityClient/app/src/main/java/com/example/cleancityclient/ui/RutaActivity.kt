package com.example.cleancityclient.ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.cleancityclient.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import java.util.*

class RutaActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: MapView
    private var _map: GoogleMap? = null
    private var isPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ruta)

        Objects.requireNonNull(supportActionBar)!!.hide()

        map = findViewById(R.id.mapRuta)
        map.onCreate(savedInstanceState)

        map.getMapAsync(this)
        checkPermission()
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
                    val builder = AlertDialog.Builder(this@RutaActivity)
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
        ruta()
    }

    override fun onResume() {
        super.onResume()
        val myPos = LatLng(-36.838338, -73.106981)
        _map?.animateCamera(CameraUpdateFactory.newLatLngZoom(myPos, 15f))
        map.onResume()
    }

    private fun ruta() {
        val start = _map!!.addMarker(
            MarkerOptions().position(LatLng(-36.8417, -73.1074)).title("INICIO RUTA")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        )
        val end = _map!!.addMarker(
            MarkerOptions().position(LatLng(-36.8386, -73.1127)).title("TERMINO RUTA")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        )
        val polyline1 = _map!!.addPolyline(
            PolylineOptions()
                .clickable(true)
                .color(Color.BLUE)
                .add(
                    LatLng(-36.8417, -73.1074),
                    LatLng(-36.8416, -73.1084),
                    LatLng(-36.8415, -73.1097),
                    LatLng(-36.8414, -73.1115),
                    LatLng(-36.8387, -73.1113),
                    LatLng(-36.8386, -73.1127)
                )
        )
        polyline1.tag = "A"
        polyline1.jointType = JointType.ROUND
        polyline1.endCap = SquareCap()
        polyline1.startCap = SquareCap()
    }
}