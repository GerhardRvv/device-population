package com.example.devicepopulation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.devicepopulation.R
import com.example.devicepopulation.data.models.DeviceModel
import com.example.devicepopulation.ui.theme.DeviceAppTheme
import com.example.devicepopulation.ui.utils.mirroringBackIcon

enum class SearchDisplay {
     Results, NoResults
}

@Composable
fun rememberSearchState(
    query: TextFieldValue = TextFieldValue(""),
    focused: Boolean = false,
    searching: Boolean = false,
    searchResults: List<DeviceModel> = emptyList()
): SearchState {
    return remember {
        SearchState(
            query = query,
            focused = focused,
            searching = searching,
            searchResults = searchResults
        )
    }
}

@Stable
class SearchState(
    query: TextFieldValue,
    focused: Boolean,
    searching: Boolean,
    searchResults: List<DeviceModel>
) {
    var query by mutableStateOf(query)
    var focused by mutableStateOf(focused)
    var searching by mutableStateOf(searching)
    var searchResults by mutableStateOf(searchResults)
    val searchDisplay: SearchDisplay
        get() = when {
            searchResults.isEmpty() -> SearchDisplay.NoResults
            else -> SearchDisplay.Results
        }
}

@Composable
fun SearchBar(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    searchFocused: Boolean,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    searching: Boolean,
    modifier: Modifier = Modifier
) {

    val focusManager = LocalFocusManager.current

    DevicePopulationSurface(
        color = DeviceAppTheme.colors.uiFloated,
        contentColor = DeviceAppTheme.colors.textSecondary,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            if (query.text.isEmpty()) {
                SearchHint()
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight()
            ) {
                if (searchFocused || !query.text.isNullOrEmpty()) {
                    IconButton(onClick = onClearQuery) {
                        Icon(
                            imageVector = mirroringBackIcon(),
                            tint = DeviceAppTheme.colors.iconPrimary,
                            contentDescription = stringResource(R.string.label_back)
                        )
                    }
                }

                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .weight(1f)
                        .onFocusChanged { focusState ->
                            onSearchFocusChange(focusState.isFocused)
                        },
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Sentences,
                        autoCorrect = true,
                        keyboardType = KeyboardType.Ascii,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    })
                )
                if (searching) {
                    CircularProgressIndicator(
                        color = DeviceAppTheme.colors.textHelp,
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .size(36.dp)
                    )
                } else {
                    Spacer(Modifier.width(IconSize)) // balance arrow icon
                }
            }
        }
    }
}

private val IconSize = 48.dp

@Composable
private fun SearchHint() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            tint = DeviceAppTheme.colors.textHelp,
            contentDescription = stringResource(R.string.label_search)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.search_devices),
            color = DeviceAppTheme.colors.textHelp
        )
    }
}

@Composable
fun NoResults(
    query: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize()
            .padding(24.dp)
    ) {
        Image(
            painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.search_no_matches, query),
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.search_no_matches_retry),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    DeviceAppTheme {
        DevicePopulationSurface {
            SearchBar(
                query = TextFieldValue(""),
                onQueryChange = { },
                searchFocused = false,
                onSearchFocusChange = { },
                onClearQuery = { },
                searching = false
            )
        }
    }
}