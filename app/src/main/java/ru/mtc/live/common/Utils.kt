package ru.mtc.live.common

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.maps.model.LatLng

object Utils {

    @SuppressLint("MissingPermission")
    internal fun getGPS(
        context: Context
    ): LatLng {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers = lm.getProviders(true)
        var l: Location? = null
        for (i in providers.indices.reversed()) {
            l = lm.getLastKnownLocation(providers[i])
            if (l != null) break
        }
        l?.let {
            return LatLng(l.latitude, l.longitude)
        }
        return LatLng(0.1,0.1)
    }
}