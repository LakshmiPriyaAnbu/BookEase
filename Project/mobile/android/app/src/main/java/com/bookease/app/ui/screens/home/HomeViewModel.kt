package com.bookease.app.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bookease.app.data.mock.MockData
import com.bookease.app.data.models.BookCategory
import com.bookease.app.data.models.Booking
import com.bookease.app.data.models.BookingStatus
import com.bookease.app.data.models.Service

class HomeViewModel : ViewModel() {

    var searchText by mutableStateOf("")
        private set

    val user = MockData.currentUser

    val upcomingBooking: Booking? = MockData.bookings
        .filter { it.status == BookingStatus.CONFIRMED }
        .minByOrNull { it.startsAt }

    val popularServices: List<Service> = MockData.services.take(4)

    val categories: List<BookCategory> = MockData.categories

    fun onSearchChange(text: String) { searchText = text }
}
