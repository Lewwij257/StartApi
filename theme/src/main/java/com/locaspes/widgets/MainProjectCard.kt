package com.locaspes.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.locaspes.theme.*

@Composable
fun MainProjectCard(title: String,
                difficulty: String,
                technologies: List<String>,
                shortDescription: String,
                modifier: Modifier = Modifier){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)

    ) {
        Column(modifier = Modifier
            .padding(8.dp)
            .background(Color.Red)) {

            Row(modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                )


                Image(
                    painter = painterResource(R.drawable.img_briefcase_selected),
                    contentDescription = "type of project",
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .sizeIn(
                            minHeight = 24.dp,
                            minWidth = 24.dp,
                            maxHeight = 32.dp,
                            maxWidth = 32.dp
                        )
                        .aspectRatio(1f)
                )

            }

            Text(text = technologies.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)

            Text(text = difficulty,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)

            Text(text = shortDescription,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis)

        }
    }
}

@Composable
@Preview
fun CardPreview(){
    MainProjectCard("Sample Project", "Hard", listOf("2d","unity"), "dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd")
}