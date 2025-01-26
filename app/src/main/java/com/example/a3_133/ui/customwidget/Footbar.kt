package com.example.a3_133.ui.customwidget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.a3_133.R

@Composable
fun Footbar(
    onHalamanProduk: () -> Unit,
    onHalamanPemasok: () -> Unit,
    onHalamanMerk: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFFFA500))
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onHalamanPemasok() }) {
            Image(
                painter = painterResource(id = R.drawable.pemasok),
                contentDescription = "Pemasok",
                modifier = Modifier.size(40.dp)
            )
        }

        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = { onHalamanProduk() }) {
                Image(
                    painter = painterResource(id = R.drawable.product),
                    contentDescription = "Produk",
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        IconButton(onClick = { onHalamanMerk() }) {
            Image(
                painter = painterResource(id = R.drawable.merk),
                contentDescription = "Merk",
                modifier = Modifier.size(40.dp)
            )
        }
    }
}