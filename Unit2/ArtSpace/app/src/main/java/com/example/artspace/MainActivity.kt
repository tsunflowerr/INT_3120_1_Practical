package com.example.artspace
import android.R.attr.maxHeight
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                ArtSpace()
            }
        }
    }
}

@Composable
fun ArtSpace() {
    var currentPage by remember { mutableStateOf(1) }

    val imageResource = when (currentPage) {
        1 -> R.drawable.ti_xung
        2 -> R.drawable.ti_xung__1_
        3 -> R.drawable.ti_xung__2_
        else -> R.drawable.ti_xung__3_
    }
    val titleResource = when (currentPage) {
        1 -> R.string.tit1
        2 -> R.string.tit2
        3 -> R.string.tit3
        else -> R.string.tit4
    }
    val artistResource = when (currentPage) {
        1 -> R.string.artist1
        2 -> R.string.artist2
        3 -> R.string.artist3
        else -> R.string.artist4
    }
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation

    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ImageFun(
                imageRes = imageResource,
                idImage = currentPage,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .padding(16.dp)
            )

            Artwork(
                title = titleResource,
                artist = artistResource,
            )

            Spacer(modifier = Modifier.height(16.dp))

            ButtonFun(
                currentPage = currentPage,
                onPageChange = { newPage -> currentPage = newPage }
            )
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ImageFun(
                imageRes = imageResource,
                idImage = currentPage,
                modifier = Modifier
                    .height(240.dp)
                    .width(400.dp)
                    .padding(16.dp)
            )

            Artwork(
                title = titleResource,
                artist = artistResource,
            )

            Spacer(modifier = Modifier.height(16.dp))

            ButtonFun(
                currentPage = currentPage,
                onPageChange = { newPage -> currentPage = newPage }
            )
        }
    }
}
@Composable
fun ImageFun(
    @DrawableRes imageRes: Int,
    idImage: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "$idImage",
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun Artwork(
    @StringRes title: Int,
    @StringRes artist: Int,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
        horizontalAlignment =  Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = title),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(6.dp)
        )
        Text(
            text = stringResource(id = artist),
            modifier = Modifier.padding(6.dp)
        )
    }
}

@Composable
fun ButtonFun(
    currentPage: Int,
    onPageChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Button(
            onClick = {onPageChange(if(currentPage <= 1) 4 else currentPage - 1)}
        ) {
            Text(text = "Previous")
        }
        Button(
            onClick = {onPageChange(if(currentPage >= 4) 1 else currentPage + 1)}
        ){
            Text(text = "Next")
        }
    }
}

