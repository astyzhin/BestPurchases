package com.akmisoftware.bestpurchases.ui.eventDetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.akmisoftware.bestpurchases.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class EventDetailsFragment : Fragment(), OnMapReadyCallback {
    private var gMap: MapView? = null

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        gMap?.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_event_details, container, false)
        gMap = root?.findViewById(R.id.fragment_details_mapView) as? MapView
        gMap?.onCreate(savedInstanceState)
        gMap?.getMapAsync(this)
        return root
    }

    override fun onResume() {
        super.onResume()
        gMap?.onResume()
    }
    override fun onPause() {
        super.onPause()
        gMap?.onPause()
    }

    override fun onStart() {
        super.onStart()
        gMap?.onStart()
    }

    override fun onStop() {
        super.onStop()
        gMap?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        gMap?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        gMap?.onLowMemory()
    }

    override fun onMapReady(p0: GoogleMap?) {
        val moscow = LatLng(55.751244, 37.618423)
        p0?.apply {
            addMarker(MarkerOptions().position(moscow).title("Moscow"))
            moveCamera(CameraUpdateFactory.newLatLngZoom(moscow, 10.0f))
        }
    }

}
