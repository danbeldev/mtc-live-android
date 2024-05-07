package ru.mtc.live.data.network.model

import androidx.annotation.DrawableRes
import com.google.android.gms.maps.model.LatLng
import ru.mtc.live.R

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

enum class VenueFeature(@DrawableRes val icon: Int) {
    WHEELCHAIR(R.drawable.wheelchair),
    BLIND(R.drawable.eye)
}
