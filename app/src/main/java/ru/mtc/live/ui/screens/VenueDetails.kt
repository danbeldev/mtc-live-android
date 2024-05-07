package ru.mtc.live.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.mtc.live.data.network.MainNetwork
import ru.mtc.live.data.network.model.Event
import ru.mtc.live.data.network.model.Venue

@Composable
fun VenueDetails(
    venue: Venue,
    network: MainNetwork,
    onBack: () -> Unit = {}
) {
    var events by remember { mutableStateOf(emptyList<Event>()) }
    var event by remember { mutableStateOf<Event?>(null) }

    LaunchedEffect(key1 = Unit) {
        events = network.getAllEvents(venueId = venue.id)
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        modifier = Modifier.padding(5.dp),
                        onClick = {
                            if(event != null)
                                event = null
                            else
                                onBack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }

                    Text(
                        text = if(event != null) event!!.name else venue.name,
                        fontWeight = FontWeight.W900,
                        fontSize = 20.sp
                    )
                }

                if(event == null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        venue.features.forEach { feature ->
                            Card(
                                modifier = Modifier.padding(3.dp),
                                border = BorderStroke(2.dp, Color.Red)
                            ) {
                                Icon(
                                    painter = painterResource(id = feature.icon),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(35.dp)
                                        .padding(5.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        if(events.isNotEmpty() && event == null) {
            eventsList(
                events = events,
                onClick = { event = it }
            )
        }else if(event != null) item {
            EventDetails(
                eventId = event!!.id,
                network = network
            )
        }
    }
}

private fun LazyListScope.eventsList(
    events: List<Event>,
    onClick: (Event) -> Unit = {}
) {
    items(events) { event ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 5.dp),
            onClick = { onClick(event) }
        ) {
            event.image?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = null
                )
            }
            Text(
                text = event.name,
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