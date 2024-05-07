package ru.mtc.live.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import ru.mtc.live.common.extensions.toDate
import ru.mtc.live.common.extensions.toBaseFormat
import ru.mtc.live.data.network.MainNetwork
import ru.mtc.live.data.network.model.EventDetails

@Composable
fun EventDetails(
    eventId: Int,
    network: MainNetwork
) {
    var details by remember { mutableStateOf<EventDetails?>(null) }

    LaunchedEffect(key1 = Unit) {
        details = network.getEventById(eventId)
    }

    if(details == null) {
        Spacer(modifier = Modifier.height(10.dp))
        CircularProgressIndicator()
    }else {

        details?.images?.firstOrNull()?.let { url ->
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(8))
                    .padding(horizontal = 5.dp, vertical = 3.dp)
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        LazyRow {
            items(details!!.categories) { category ->
                Card(
                    modifier = Modifier.padding(horizontal = 5.dp)
                ) {
                    Text(text = category.name, modifier = Modifier.padding(8.dp))
                }
            }
        }

        if(details!!.schedules.isNotEmpty()) {
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Расписание (${details!!.schedules.size})",
                fontWeight = FontWeight.W900,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 15.dp,
                        bottom = 5.dp,
                        top = 5.dp
                    ),
                textAlign = TextAlign.Start
            )

            LazyRow {
                items(details!!.schedules) { schedule ->
                    Card(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Text(
                            text = schedule.dateTime.toDate()?.toBaseFormat() ?: "-",
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }

        details?.description?.let { description ->
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Описание",
                fontWeight = FontWeight.W900,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 15.dp,
                        bottom = 5.dp,
                        top = 5.dp
                    ),
                textAlign = TextAlign.Start
            )

            Text(
                text = description,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 15.dp,
                        vertical = 5.dp
                    )
            )
        }

        if(details!!.persons.isNotEmpty()) {
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Персоны (${details!!.persons.size})",
                fontWeight = FontWeight.W900,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 15.dp,
                        bottom = 5.dp,
                        top = 5.dp
                    ),
                textAlign = TextAlign.Start
            )

            LazyRow {
                items(details!!.persons) { person ->
                    Card(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Column(
                            modifier = Modifier.width(120.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            person.person.photo?.let { photo ->
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(photo)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = null
                                )
                            }

                            Text(
                                text = person.person.getName(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.W900,
                                modifier = Modifier.padding(3.dp),
                                fontSize = 14.sp
                            )

                            Text(
                                text = person.post.name,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(3.dp),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}