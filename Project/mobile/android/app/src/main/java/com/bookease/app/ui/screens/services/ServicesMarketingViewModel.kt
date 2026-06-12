package com.bookease.app.ui.screens.services

import androidx.lifecycle.ViewModel
import com.bookease.app.data.mock.MockData
import com.bookease.app.data.models.Service

data class ServiceCategory(
    val name: String,
    val count: Int
)

class ServicesMarketingViewModel : ViewModel() {

    val categories: List<ServiceCategory> = listOf(
        ServiceCategory("Strength",  MockData.services.count { it.categoryId == MockData.categories[0].id }),
        ServiceCategory("HIIT",      MockData.services.count { it.categoryId == MockData.categories[1].id }),
        ServiceCategory("Mobility",  MockData.services.count { it.categoryId == MockData.categories[2].id }),
        ServiceCategory("Nutrition", MockData.services.count { it.categoryId == MockData.categories[3].id })
    )

    val featuredServices: List<Service> = MockData.services.take(2)
}
