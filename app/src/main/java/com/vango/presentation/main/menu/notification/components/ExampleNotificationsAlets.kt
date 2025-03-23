package com.vango.presentation.main.menu.notification.components

import com.vango.presentation.main.menu.notification.Notification

val alerts = emptyList<Notification>() // Sin alertas
val notifications = listOf(
    Notification("Alice", true, "Nueva actualización disponible"),
    Notification("Bob", false,"Recordatorio: Reunión a las 3 PM"),
    Notification("Charlie", false, "Evento importante para ti"),
    Notification("David", true, "Solicitud de amistad recibida"),
    Notification("Eve", false, "Oferta de trabajo para ti")
)
