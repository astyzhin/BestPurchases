package com.akmisoftware.bestpurchases.ui.eventDetail


import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.akmisoftware.bestpurchases.NewOrEditEventActivity
import com.akmisoftware.bestpurchases.R
import com.akmisoftware.bestpurchases.db.AppConstants
import com.akmisoftware.bestpurchases.model.Event
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_event_details.*

class EventDetailsFragment : Fragment(), OnMapReadyCallback {
    private var gMap: MapView? = null
    private var event: Event? = null

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
        event = activity?.intent?.getParcelableExtra(AppConstants.EVENT_INFO)
        Log.d("NewOrEditEventActivity", "${event?.name}, ${event?.locationName}, ${event?.latlng}")
        gMap = root?.findViewById(R.id.fragment_details_mapView) as? MapView
        gMap?.onCreate(savedInstanceState)
        gMap?.getMapAsync(this)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (event!!.description.isNotEmpty()) {
            fragment_details_description.text = event?.description
        } else {
            fragment_details_description.text = "(No Description)"
        }
        if (event!!.locationName.isNotEmpty()) {
            fragment_details_location.text = event?.locationName
        } else {
            fragment_details_location.visibility = View.GONE
            fragment_details_location_label.visibility = View.GONE
            divider.visibility = View.GONE
        }
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
    private fun setUpMap(map: GoogleMap?) {
        if (ContextCompat.checkSelfPermission(this.context!!, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map?.isMyLocationEnabled = true
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        if (!event?.latlng.isNullOrEmpty()) {
            val latLng = event!!.latlng.split(",")
            try {
                val lat = latLng[0].toDouble()
                val lng = latLng[1].toDouble()
                val location = LatLng(lat, lng)
                p0?.apply {
                    addMarker(MarkerOptions().position(location).title(event?.locationName))
                    moveCamera(CameraUpdateFactory.newLatLngZoom(location, 11.0f))
                    setUpMap(this)
                }
            } catch (e: Exception) {
                Log.e(NewOrEditEventActivity::class.java.simpleName, "Maps fragment ${e.localizedMessage}")
                fragment_details_mapView.visibility = View.GONE
            }
        } else {
            fragment_details_mapView.visibility = View.GONE
        }
//        val moscow = LatLng(55.751244,37.618423)
    }

}
