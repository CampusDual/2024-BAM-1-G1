package com.vango.presentation.main.menu.support


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.vango.R
import com.vango.presentation.theme.MainColor
import com.vango.presentation.theme.TextColor
import com.vango.presentation.theme.WarningColor

@Composable
fun HelpScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        // Título principal: Saludo al usuario
        Text(
            text = "Hola Lorena, ",
            fontSize = 20.sp,
            color = MainColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 18.dp)
        )
        Text(
            text = "¿En qué podemos ayudarte?",
            fontSize = 20.sp,
            color = MainColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Subtítulo: Recomendaciones para ti
        Text(
            text = "Recomendaciones para ti",
            fontSize = 16.sp,
            color = TextColor,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(3) { index ->
                RecommendationCard(
                    title = "Tú identidad no se ha verificado",
                    description = "Verificar tu identidad nos ayuda a confirmar que eres tú. Es por ello que es muy importante que vayas a tu cuenta y rellenes todos los datos.",
                    actionText1 = "Ir a ",
                    actionText2 = "Mi cuenta",
                    onActionClick = { navController.navigate("home") }
                )
            }
        }
        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Preguntas frecuentes",
            fontSize = 16.sp,
            color = TextColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val faqs = listOf(
            "¿Qué es esta aplicación y para qué sirve?" to "Esta aplicacion te salvara la vida cuando viajes en Camper, sera tu ilusion, te sentiras desnudo sin ella cuando salgas de casa y creeras que estan perdido en la selva sin ella.",
            "¿En qué dispositivos puedo usar la APP?" to "Desde un pisapapeles hasta esos preciosos HONOR Magic V3 , pero por ahora no para los sobrevalorados y supercaros Iphons.",
            "¿La aplicación es gratuita?" to "Desearias pagar por esta droga todo tu dinero, por ahora esta en modo de campaña y es gratis pero pronto pondremos la manera de sacasrte todo tu dinero",
            "Olvidé mi contraseña, ¿cómo la recupero?" to "Create otro email, y otra cuanta, pon tu targeta otra vez y hazte de la cuanta premiun otra vez, nosotros si sabemos usar cotrrectamente tu dinero..",
            "¿La app muestra información sobre normativas de estacionamiento?" to "Que va, si te multan paga, nosotros no nos hacemos responsables de que no sepas donde esta parado y te aparques al lado de una señal de prohivido parar y estacionar.",
            "¿Cómo reporto un lugar incorrecto o con información desactualizada?" to "Manda un correo a meimportaunpepinoloquedigas@miscojonesmaduros.orto."
        )

        faqs.forEach { (question, answer) ->
            ExpandableFAQItem(
                question = question,
                answer = answer
            )
        }
        Spacer(modifier = Modifier.height(85.dp))// para salvar la bottombar
    }
}

@Composable
fun ExpandableFAQItem(question: String, answer: String) {
    val isExpanded =
        remember { mutableStateOf(false) } // Estado para controlar la visibilidad de la respuesta

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Pregunta clickeable
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded.value = !isExpanded.value }
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = question,
                    fontSize = 14.sp,
                    color = TextColor,
                    fontWeight = FontWeight.Normal
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (isExpanded.value) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_edit_password),
                        contentDescription = "Atras",
                        modifier = Modifier
                            .width(10.dp)
                            .height(10.dp)
                            .rotate(-90f),
                        colorFilter =
                        androidx.compose.ui.graphics.ColorFilter.tint(com.vango.presentation.theme.TextColor)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_edit_password),
                        contentDescription = "Atras",
                        modifier = Modifier
                            .width(10.dp)
                            .height(10.dp)
                            .rotate(90f),
                        colorFilter =
                        androidx.compose.ui.graphics.ColorFilter.tint(com.vango.presentation.theme.TextColor)
                    )
                }

            }
        }

        // Respuesta (visible solo si isExpanded es true)
        if (isExpanded.value) {
            Text(
                text = answer,
                fontSize = 14.sp,
                color = TextColor.copy(alpha = 0.7f),
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )
        }

        // Divisor
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 0.dp),
            thickness = 1.dp,
            color = Color.Gray.copy(alpha = 0.2f)
        )
    }
}

@Composable
fun RecommendationCard(
    title: String,
    description: String,
    actionText1: String,
    actionText2: String,
    onActionClick: () -> Unit
) {
    val CardBackgroundColor = Color.White
    val BorderColor = Color.Gray.copy(alpha = 0.3f)

    Box(
        modifier = Modifier
            .width(240.dp)
            .height(220.dp)
            .background(CardBackgroundColor, shape = RoundedCornerShape(8.dp))
            .border(1.dp, BorderColor, shape = RoundedCornerShape(15.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_warning),
                    contentDescription = "Warning",
                    modifier = Modifier
                        .width(18.dp)
                        .height(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Accion Necesaria",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = WarningColor
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor
            )
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = description,
                fontSize = 14.sp,
                color = TextColor
            )
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.BottomStart
            ) {
                Column() {
                    HorizontalDivider(
                        modifier = Modifier.requiredWidth(240.dp),
                        thickness = 1.dp,
                        color = Color.Gray.copy(alpha = 0.2f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.8f),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row {
                                Text(
                                    text = actionText1,
                                    fontSize = 14.sp,
                                    color = TextColor,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.clickable(onClick = onActionClick)
                                )
                                Text(
                                    text = actionText2,
                                    fontSize = 14.sp,
                                    color = TextColor,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.clickable(onClick = onActionClick)
                                )

                            }

                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_edit_password),
                                contentDescription = "Atras",
                                modifier = Modifier
                                    .width(10.dp)
                                    .height(10.dp),
                                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(com.vango.presentation.theme.TextColor)
                            )
                        }

                    }

                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun SuggestionsScreenPreview() {
    val navController = rememberNavController()
    HelpScreen(navController = navController)
}
