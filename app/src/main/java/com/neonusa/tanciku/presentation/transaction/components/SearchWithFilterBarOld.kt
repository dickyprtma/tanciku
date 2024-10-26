package com.neonusa.tanciku.presentation.transaction.components

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neonusa.tanciku.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchWithFilterBar() {
    // State untuk menyimpan status selected dari setiap FilterChip
    var selectedChips by remember { mutableStateOf(List(3) { false }) }
    val categoryList = listOf("Kebutuhan", "Keinginan", "Menabung")

    var selectedTypeChips by remember { mutableStateOf(List(2) { false }) }
    val typeList = listOf("Pemasukan", "Pengeluaran")

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = Modifier.fillMaxWidth()) {
        SearchBar(text = "Cari...", onValueChange = {}) {

        }

//        LazyRow(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
//            items(selectedTypeChips.size) { index ->
//                FilterChip(
//                    title = typeList.get(index),
//                    selected = selectedTypeChips[index],
//                    onSelectedChange = { isSelected ->
//                        // Update status selected untuk chip pada index yang diklik
//                        selectedTypeChips = selectedTypeChips.toMutableList().apply {
//                            this[index] = isSelected
//                        }
//                    }
//                )
//            }
//        }

    }
}

@Composable
private fun FilterChip(title: String,selected: Boolean, onSelectedChange: (Boolean) -> Unit) {
    FilterChip(
        modifier = Modifier.padding(start = 4.dp),
        onClick = { onSelectedChange(!selected) },
        label = {
            Text(
                fontSize = 12.sp,
                text =  title)
        },
        selected = selected,
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
        shape = RoundedCornerShape(16.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isClicked = interactionSource.collectIsPressedAsState().value
    LaunchedEffect(key1 = isClicked){
        if(isClicked){
            onClick?.invoke()
        }
    }

    Box(modifier = modifier) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .searchBar(),
            value = text,
            onValueChange = onValueChange,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = colorResource(id = R.color.body)
                )
            },
            placeholder = {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.text_title_small)
                )
            },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(id = R.color.white),
//                textColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                cursorColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch()
                }
            ),
            textStyle = MaterialTheme.typography.bodySmall,
            interactionSource = interactionSource
        )
    }
}

fun Modifier.searchBar(): Modifier = composed {
    if (!isSystemInDarkTheme()) {
        border(
            width = 1.dp,
            color = Color.Black,
            shape = MaterialTheme.shapes.extraLarge
        )
    } else {
        this
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchWithFilterBar() {
    SearchWithFilterBar()
}