package com.vango.presentation.main.menu.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vango.R
import com.vango.presentation.main.menu.notification.components.Notification
import com.vango.presentation.theme.TextColor

@Composable
fun AlertsScreen(
    notifications: List<Notification> = emptyList()
) {
    // Filtrar solo las notificaciones con alert == true
    val filteredNotifications = notifications.filter { it.alert }

    // Si no hay notificaciones con alert == true, muestra un mensaje centralizado
    if (filteredNotifications.isEmpty()) {
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
                    text = "No tienes ninguna alerta activa.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextColor,
                    modifier = Modifier.padding(18.dp)
                )
                Text(
                    text = "Cuando recibas una nueva alerta,",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = TextColor
                )
                Text(
                    text = "se mostrará aquí.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = TextColor
                )
            }
        }
    } else {
        // Si hay notificaciones con alert == true, muestra el listado
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredNotifications) { notification ->
                NotificationItem(notification)
                if (filteredNotifications.indexOf(notification) < filteredNotifications.size - 1) {
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
@Preview(showBackground = true)
@Composable
fun AlertsScreenPreview() {
    val notifications = listOf(
        Notification("Alice", true,"Nueva actualización disponible"),
        Notification("Bob", false,"Recordatorio: Reunión a las 3 PM"),
        Notification("Charlie", true,"Tu pedido ha sido enviado")
    )
    AlertsScreen(notifications)
}

@Preview(showBackground = true)
@Composable
fun EntryAlertsSPreview() {
    val notifications = emptyList<Notification>()
    AlertsScreen(
        notifications
    )
}

