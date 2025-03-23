package com.vango.presentation.main.menu.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vango.R
import com.vango.presentation.theme.BlackGray
import com.vango.presentation.theme.TextColor


@Composable
fun AllNotificationsScreen(
    notifications: List<Notification> = emptyList(),
    alertas: List<Alertas> = emptyList()
) {
    // Si no hay notificaciones, muestra un mensaje centralizado
    if (notifications.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_notifications),
                    contentDescription = "Sin notificaciones",
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp),
                )
                Text(
                    text = "No tienes ningun mensaje.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextColor,
                    modifier = Modifier.padding(18.dp)
                )
                Text(
                    text = "Cuando recibas un mensaje nuevo,",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = TextColor
                )
                Text(
                    text = "se mostrará aqui.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = TextColor
                )
            }
        }
    } else {
        // Si hay notificaciones, muestra el listado
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(notifications) { notification ->
                NotificationItem(notification)
                if (notifications.indexOf(notification) < notifications.size - 1) {
                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun NotificationItem(notification: Notification) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Círculo con la inicial del remitente
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(BlackGray),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = notification.sender.firstOrNull()?.toString() ?: "",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Nombre del remitente y título del mensaje
        Column {
            Text(
                text = notification.sender,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor
            )
            Text(
                text = notification.title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
        }
    }
}

// Modelo de datos para las notificaciones
data class Notification(
    val sender: String,
    val title: String
)

data class Alertas(
    val sender: String,
    val title: String
)

// Vista previa para pruebas
@Preview(showBackground = true)
@Composable
fun NotificationsScreenPreview() {
    val notifications = listOf(
        Notification("Alice", "Nueva actualización disponible"),
        Notification("Bob", "Recordatorio: Reunión a las 3 PM"),
        Notification("Charlie", "Tu pedido ha sido enviado")
    )
    AllNotificationsScreen(notifications)
}

@Preview(showBackground = true)
@Composable
fun EmptyNotificationsScreenPreview() {
    AllNotificationsScreen()
}

