package com.example.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BusinessCardTheme {
                BusinessCard()
            }
        }
    }
}
@Composable
fun BusinessCard() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFDCEFE1)),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopScreen(modifier = Modifier.weight(1f).fillMaxWidth())
        Column(
            modifier = Modifier.padding(bottom = 40.dp),
            horizontalAlignment = Alignment.Start
        ) {
            BotScreen(content = stringResource(R.string.contact), icon = Icons.Default.Call)
            BotScreen(content = stringResource(R.string.info), icon = Icons.Default.Share)
            BotScreen(content = stringResource(R.string.mail), icon = Icons.Default.Email)
        }
    }
}
@Composable
fun TopScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement =Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.android_logo),
            contentDescription = null,
            modifier = Modifier.size(100.dp).background(Color(0xFF073042)),
        )
        Text(
            text = stringResource(R.string.name),
            fontSize = 36.sp,
        )
        Text(
            text = stringResource(R.string.title),
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E7D32),
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Composable
fun BotScreen(content: String, icon: ImageVector) {
    Row(modifier = Modifier.padding(bottom = 4.dp, top = 4.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF2E7D32),
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = content,
            fontSize = 16.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BusinessCardTheme {
        BusinessCard()
    }
}