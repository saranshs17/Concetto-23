package com.iitism.concetto.ui.sponsors

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun UserWidget (sponsor: Sponsor) {

    Card (
        modifier = Modifier.padding(10.dp)
    ){
        Column(modifier = Modifier.fillMaxWidth()) {

            AsyncImage(
                model = sponsor.img,
                contentDescription = "Image of the Sponsor"
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(text =sponsor.cat)

        }

    }
}




