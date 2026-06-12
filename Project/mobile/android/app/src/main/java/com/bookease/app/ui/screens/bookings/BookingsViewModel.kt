package com.bookease.app.ui.screens.bookings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bookease.app.data.mock.MockData
import com.bookease.app.data.models.Booking
import com.bookease.app.data.models.BookingStatus
import java.util.UUID

class BookingsViewModel : ViewModel() {

    private val _bookings = mutableStateListOf<Booking>().also {
        it.addAll(MockData.bookings)
    }

    var selectedSegment by mutableIntStateOf(0)   // 0=Upcoming, 1=Completed, 2=Cancelled
        private set

    val filteredBookings: List<Booking>
        get() = when (selectedSegment) {
            0    -> _bookings.filter { it.status == BookingStatus.CONFIRMED || it.status == BookingStatus.PENDING }
            1    -> _bookings.filter { it.status == BookingStatus.COMPLETED }
            2    -> _bookings.filter { it.status == BookingStatus.CANCELLED }
            else -> emptyList()
        }.sortedBy { it.startsAt }

    fun selectSegment(index: Int) { selectedSegment = index }

    fun cancelBooking(id: UUID) {
        val idx = _bookings.indexOfFirst { it.id == id }
        if (idx >= 0) {
            _bookings[idx] = _bookings[idx].copy(status = BookingStatus.CANCELLED)
        }
    }
}
