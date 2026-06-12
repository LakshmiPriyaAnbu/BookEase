package com.bookease.app.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bookease.app.data.mock.MockData
import com.bookease.app.data.models.Service
import com.bookease.app.data.models.TimeSlot

class ServiceDetailViewModel(val service: Service) : ViewModel() {

    val timeSlots: List<TimeSlot> = MockData.timeSlots

    var selectedSlot: TimeSlot? by mutableStateOf(null)
        private set

    val canProceed: Boolean
        get() = selectedSlot != null

    fun selectSlot(slot: TimeSlot) {
        if (slot.isAvailable) selectedSlot = slot
    }
}
