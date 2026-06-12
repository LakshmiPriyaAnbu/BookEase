package com.bookease.app.ui.screens.explore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bookease.app.data.mock.MockData
import com.bookease.app.data.models.BookCategory
import com.bookease.app.data.models.Service
import java.util.UUID

class ExploreViewModel(filterCategoryId: UUID? = null) : ViewModel() {

    private val allServices = MockData.services
    val categories          = MockData.categories

    var searchText by mutableStateOf("")
        private set

    // index 0 = "All", 1..n = category index
    var selectedChipIndex by mutableIntStateOf(
        if (filterCategoryId == null) 0
        else categories.indexOfFirst { it.id == filterCategoryId } + 1
    )
        private set

    val chipLabels: List<String>
        get() = listOf("All") + categories.map { it.name }

    val filteredServices: List<Service>
        get() {
            val categoryFilter: BookCategory? =
                if (selectedChipIndex == 0) null
                else categories.getOrNull(selectedChipIndex - 1)

            return allServices.filter { svc ->
                val matchesSearch = searchText.isEmpty() ||
                        svc.name.contains(searchText, ignoreCase = true) ||
                        svc.coachName.contains(searchText, ignoreCase = true)
                val matchesCategory = categoryFilter == null || svc.categoryId == categoryFilter.id
                matchesSearch && matchesCategory
            }
        }

    fun onSearchChange(text: String) { searchText = text }
    fun onChipSelect(index: Int) { selectedChipIndex = index }
}
