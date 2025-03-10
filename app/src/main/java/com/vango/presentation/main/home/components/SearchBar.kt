package com.vango.presentation.main.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vango.R

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    performSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current

    TextField(
        value = searchQuery,
        shape = RoundedCornerShape(100.dp),
        onValueChange = { onSearchQueryChange(it) },
        modifier = modifier
            .padding(top = 48.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth()
            .background(Color.Transparent, RoundedCornerShape(100.dp))
            .shadow(4.dp, RoundedCornerShape(100.dp)),
        placeholder = {
            if (!isFocused) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "Buscar",
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { performSearch(searchQuery) },
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Empieza a buscar",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
        singleLine = true,
        leadingIcon = {
            if (isFocused) {
                Icon(
                    painter = painterResource(id = R.drawable.ex),
                    contentDescription = "Borrar",
                    modifier = Modifier
                        .size(18.dp)
                        .padding(start = 8.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onSearchQueryChange("")
                            focusManager.clearFocus()
                        },
                    tint = Color.Black
                )
            }
        },
        trailingIcon = {
            if (isFocused) {
                Icon(
                    painter = painterResource(id = R.drawable.filter_search),
                    contentDescription = "Filtro",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 8.dp)
                        .clickable { },
                    tint = Color.Black
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                performSearch(searchQuery)
                focusManager.clearFocus()
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White
        ),
        interactionSource = interactionSource
    )
}