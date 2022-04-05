package ca.group6.meetmatcher

import android.hardware.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ca.group6.meetmatcher.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var placeName: String
    private lateinit var placeLatLng: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        placeName = intent.getStringExtra("placeName").toString()
        val lat = intent.getDoubleExtra("placeLat", 49.0)
        val long = intent.getDoubleExtra("placeLong", 120.0)
        placeLatLng = LatLng(lat, long)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera\
        // coo coo coffee 49.276140582185896, -123.123745
        //val location = LatLng(49.27574947559409, -123.12371080205423)
        mMap.addMarker(MarkerOptions().position(placeLatLng).title(placeName))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(placeLatLng))
    }
}