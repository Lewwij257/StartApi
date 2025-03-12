package com.locaspes.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.locaspes.stellaristheme.StellarisAppTheme

@Composable
fun MainProjectCard(title: String,
                technologies: List<String>,
                shortDescription: String,
                modifier: Modifier = Modifier){

    Column

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)

    ) {
        Column(modifier = Modifier
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.onSurface)) {

            Row (modifier = Modifier,
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
    StellarisAppTheme {
        MainProjectCard("Sample Project",  listOf("2d","unity"), "dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd")
    }
}