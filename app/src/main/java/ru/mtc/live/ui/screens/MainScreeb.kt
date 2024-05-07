import android.Manifest
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch
import ru.mtc.live.common.Utils.getGPS
import ru.mtc.live.data.network.MainNetwork
import ru.mtc.live.data.network.model.Venue
import ru.mtc.live.ui.screens.VenueDetails

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(network: MainNetwork) {

    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = true)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
    val cameraPosition = rememberCameraPositionState()
    val locationPermission = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)

    var venues by remember { mutableStateOf(emptyList<Venue>()) }
    var venue by remember { mutableStateOf<Venue?>(null) }

    LaunchedEffect(key1 = Unit) {
        venues = network.getAllVenues()
        locationPermission.launchPermissionRequest()
    }

    LaunchedEffect(key1 = locationPermission.hasPermission) {
        if(locationPermission.hasPermission) {
            cameraPosition.position = CameraPosition.fromLatLngZoom(getGPS(context = context), 17f)
        }
    }

    LaunchedEffect(key1 = venue) {
        if (venue != null) {
            bottomSheetState.expand()
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            if(venue == null) {
                VenuesList(
                    venues = venues,
                    onClick = { item ->
                        scope.launch {
                            bottomSheetState.show()
                            cameraPosition.position = CameraPosition.fromLatLngZoom(item.getLatLng(), 17f)
                            venue = item
                        }
                    }
                )
            }else {
                VenueDetails(
                    venue = venue!!,
                    network = network,
                    onBack = { venue = null }
                )
            }
        }
    ) { p ->
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(p),
            cameraPositionState = cameraPosition,
            properties = MapProperties(
                isMyLocationEnabled = locationPermission.hasPermission
            ),
        ) {
            venues.forEach { item ->
                Marker(
                    state = rememberMarkerState(position = LatLng(item.latitude, item.longitude)),
                    title = item.name,
                    onClick = {
                        venue = item
                        true
                    }
                )
            }
        }
    }
}

@Composable
private fun VenuesList(
    venues: List<Venue>,
    onClick: (Venue) -> Unit = {}
) {
    LazyColumn {
        items(venues) { venue ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 5.dp),
                onClick = { onClick(venue) }
            ) {
                Text(
                    text = venue.name,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W900,
                    fontSize = 22.sp
                )
            }
        }
    }
}