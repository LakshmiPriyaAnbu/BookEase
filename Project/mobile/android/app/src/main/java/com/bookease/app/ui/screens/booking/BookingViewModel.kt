package com.bookease.app.ui.screens.booking

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bookease.app.data.mock.MockData
import com.bookease.app.data.models.*
import java.util.Calendar
import java.util.UUID

class BookingViewModel(val service: Service) : ViewModel() {

    val datePills = MockData.datePills()
    val timeSlots = MockData.timeSlots

    var selectedDateIndex by mutableIntStateOf(0)
        private set

    var selectedSlot: TimeSlot? by mutableStateOf(null)
        private set

    var fullName by mutableStateOf(MockData.currentUser.fullName)
    var phone    by mutableStateOf(MockData.currentUser.phone)
    var notes    by mutableStateOf("")

    val isValid: Boolean
        get() = fullName.isNotBlank() && phone.isNotBlank() && selectedSlot != null

    fun selectDate(index: Int) { selectedDateIndex = index }
    fun selectSlot(slot: TimeSlot) { if (slot.isAvailable) selectedSlot = slot }

    fun buildConfirmedBooking(): Booking {
        val pill = datePills[selectedDateIndex]
        val cal  = Calendar.getInstance()
        cal.timeInMillis = pill.epochMillis
        cal.set(Calendar.HOUR_OF_DAY, 10)

        val ref = "BK-${(10000..99999).random()}"
        return Booking(
            ref         = ref,
            serviceId   = service.id,
            serviceName = service.name,
            coachName   = service.coachName,
            startsAt    = cal.timeInMillis,
            status      = BookingStatus.CONFIRMED,
            priceCents  = service.priceCents
        )
    }
}
