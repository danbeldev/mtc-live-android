package ru.mtc.live.data.network.model

import com.google.android.gms.maps.model.LatLng

data class Venue(
    val id: Int,
    val name: String,
    val longitude: Double,
    val latitude: Double,
    val features: List<VenueFeature>
) {
    fun getLatLng(): LatLng {
        return LatLng(latitude, longitude)
    }
}

enum class VenueFeature {
    WHEELCHAIR,
    BLIND
}
