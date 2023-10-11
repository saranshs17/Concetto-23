package com.iitism.concetto.ui.Maps

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.ktx.Firebase
import com.iitism.concetto.R
import com.iitism.concetto.databinding.FragmentMapsBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener


class MapsFragment(
    private val destLat : Double,
    private val destLng : Double
) : Fragment() , OnMapReadyCallback {


    private var binding:FragmentMapsBinding? = null
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mMap: GoogleMap
    private lateinit var destinationLatLng: LatLng
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(layoutInflater)

        mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val intent = Intent()

        destinationLatLng = LatLng(destLat ,destLng)
        init()
            return binding?.root
    }

    @SuppressLint("MissingPermission")
    private fun init() {
        val locationRequest = com.google.android.gms.location.LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 5000
        ).build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                val newPos = LatLng(
                    locationResult.lastLocation?.latitude!!,
                    locationResult.lastLocation?.longitude!!
                )
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPos, 10f))

            }
        }
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //Request Permissions
        var userLatLng : LatLng = LatLng(0.0,0.0)
        Dexter.withContext(context)
            .withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                @SuppressLint("MissingPermission")
                override fun onPermissionGranted(permissions: PermissionGrantedResponse?) {
                    mMap.isMyLocationEnabled = true
                    mMap.uiSettings.isMyLocationButtonEnabled = true
                    mMap.setOnMyLocationButtonClickListener(){
                        fusedLocationProviderClient.lastLocation
                            .addOnFailureListener {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                            }.addOnSuccessListener { location ->
                                userLatLng = LatLng(location.latitude, location.longitude)
//                                mMap.animateCamera(
//                                    CameraUpdateFactory.newLatLngZoom(
//                                        userLatLng,
//                                        15F
//                                    )
//                                )
                                val markerOptions = MarkerOptions()
                                    .position(LatLng(destLat, destLng)) // Set the marker position (latitude and longitude)
                                    .title("Your Destination")
                                googleMap.addMarker(markerOptions)
                                // Set a title for the marker (optional)
                                val startPoint = LatLng(userLatLng.latitude, userLatLng.longitude)
                                val endPoint = LatLng(destinationLatLng.latitude, destinationLatLng.longitude)
                                val polylineOptions = PolylineOptions()
                                    .add(startPoint, endPoint)
                                    .color(Color.BLUE)  // Set the color of the polyline
                                    .width(20f)         // Set the width of the polyline

                                val polyline = googleMap.addPolyline(polylineOptions)
                                val boundsBuilder = LatLngBounds.builder()
                                    .include(startPoint)
                                    .include(endPoint)

                                val bounds = boundsBuilder.build()
                                val padding = 100 // Adjust padding as needed

                                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
                            }
                        true
                    }


                    val view = mapFragment.view?.findViewById<View>("1".toInt())?.parent as View
                    val locationButton = view.findViewById<View>("2".toInt())
                    val params = locationButton.layoutParams as RelativeLayout.LayoutParams
                    params.addRule(RelativeLayout.ALIGN_TOP, 0)
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
                    params.bottomMargin = 250
                }


                override fun onPermissionDenied(permissions: PermissionDeniedResponse?) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {

                }
            }).check()

        mMap.uiSettings.isZoomControlsEnabled = true

    }

}