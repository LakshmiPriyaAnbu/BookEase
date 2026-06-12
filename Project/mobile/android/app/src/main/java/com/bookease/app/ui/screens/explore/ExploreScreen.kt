package com.bookease.app.ui.screens.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bookease.app.R
import com.bookease.app.data.models.Service
import com.bookease.app.ui.components.FilterChipBar
import com.bookease.app.ui.components.ServiceRow
import com.bookease.app.ui.theme.*

@Composable
fun ExploreScreen(
    onServiceClick: (Service) -> Unit,
    vm: ExploreViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BeColor.surface100)
    ) {
        // Top bar
        Column(
            modifier = Modifier
                .background(BeColor.surface100)
                .padding(horizontal = BeSp.lg)
                .padding(top = BeSp.sm),
        ) {
            // Title + filter icon
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = BeSp.base),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.explore_title),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(BeRadius.md))
                        .background(BeColor.surfaceWhite)
                        .border(1.dp, BeColor.border, RoundedCornerShape(BeRadius.md)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = stringResource(R.string.explore_filter_content_desc),
                        tint = BeColor.ink700,
                        modifier = Modifier.size(19.dp)
                    )
                }
            }

            // Search bar
            val searchPlaceholder = stringResource(R.string.explore_search_placeholder)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .clip(RoundedCornerShape(BeRadius.md))
                    .background(BeColor.surfaceWhite)
                    .border(1.dp, BeColor.border, RoundedCornerShape(BeRadius.md))
                    .padding(horizontal = BeSp.base),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(BeSp.sm)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = BeColor.ink300,
                    modifier = Modifier.size(17.dp)
                )
                androidx.compose.foundation.text.BasicTextField(
                    value = vm.searchText,
                    onValueChange = vm::onSearchChange,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = BeColor.ink700,
                        fontSize = 14.5.sp
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    decorationBox = { inner ->
                        Box {
                            if (vm.searchText.isEmpty()) {
                                Text(
                                    text = searchPlaceholder,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = BeColor.ink300,
                                        fontSize = 14.5.sp
                                    )
                                )
                            }
                            inner()
                        }
                    }
                )
            }

            Spacer(Modifier.height(BeSp.base))

            // Filter chips
            FilterChipBar(
                labels = vm.chipLabels,
                selectedIndex = vm.selectedChipIndex,
                onSelect = vm::onChipSelect
            )

            Spacer(Modifier.height(BeSp.lg))
        }

        // List
        if (vm.filteredServices.isEmpty()) {
            EmptyState(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = BeSp.lg,
                    vertical = BeSp.xs
                ),
                verticalArrangement = Arrangement.spacedBy(13.dp)
            ) {
                items(vm.filteredServices, key = { it.id.toString() }) { service ->
                    ServiceRow(
                        service = service,
                        onClick = { onServiceClick(service) }
                    )
                }
                item { Spacer(Modifier.height(BeSp.xxl)) }
            }
        }
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.SearchOff,
            contentDescription = null,
            tint = BeColor.ink300,
            modifier = Modifier.size(56.dp)
        )
        Spacer(Modifier.height(BeSp.md))
        Text(
            text = stringResource(R.string.explore_empty_title),
            style = MaterialTheme.typography.headlineSmall.copy(color = BeColor.ink500)
        )
        Text(
            text = stringResource(R.string.explore_empty_message),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
