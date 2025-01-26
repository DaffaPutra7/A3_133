package com.example.a3_133.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a3_133.R
import com.example.a3_133.ui.navigation.DestinasiNavigasi

object DestinasiHomeScreen : DestinasiNavigasi {
    override val route = "homescreen"
    override val titleRes = "Home Screen"
}

@Composable
fun HomeScreen(
    onHalamanStart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFFFA500), Color(0xFFFF4500))
                )
            )
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Amazon Shop",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Image(
            painter = painterResource(id = R.drawable.amazon), // Ganti dengan logo Amazon
            contentDescription = "Logo Amazon",
            modifier = Modifier
                .size(150.dp) // Ukuran logo lebih besar
                .padding(bottom = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onHalamanStart() },
            modifier = Modifier
                .width(350.dp) // Set a fixed width for the button
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White // Tombol putih
            ),
            shape = MaterialTheme.shapes.medium,
        ) {
            Text(
                text = "Mulai",
                fontSize = 18.sp,
                color = Color(0xFFFF9900), // Teks oranye sesuai logo
                fontWeight = FontWeight.Bold
            )
        }
    }
}