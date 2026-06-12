package com.bookease.app.data.models

import java.util.UUID

// ── User ────────────────────────────────────────────────────────────────────

data class User(
    val id: UUID = UUID.randomUUID(),
    val fullName: String,
    val email: String,
    val phone: String,
    val avatarUrl: String? = null
)

// ── Category ─────────────────────────────────────────────────────────────────

data class BookCategory(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val iconName: String,   // maps to Material Icon name string
    val colorHex: String
)

// ── Service ──────────────────────────────────────────────────────────────────

data class Service(
    val id: UUID = UUID.randomUUID(),
    val categoryId: UUID,
    val name: String,
    val description: String,
    val priceCents: Int,
    val durationMin: Int,
    val imageUrl: String? = null,
    val rating: Double,
    val coachName: String,
    val included: List<String> = emptyList()
) {
    val formattedPrice: String get() = "₹${priceCents / 100}"
    val formattedDuration: String get() = "$durationMin min"
}

// ── Booking ───────────────────────────────────────────────────────────────────

enum class BookingStatus {
    PENDING, CONFIRMED, COMPLETED, CANCELLED;

    val label: String get() = when (this) {
        PENDING -> "Pending"
        CONFIRMED -> "Confirmed"
        COMPLETED -> "Completed"
        CANCELLED -> "Cancelled"
    }
}

data class Booking(
    val id: UUID = UUID.randomUUID(),
    val ref: String,
    val serviceId: UUID,
    val serviceName: String,
    val coachName: String,
    val startsAt: Long,           // epoch millis
    val status: BookingStatus,
    val priceCents: Int
) {
    val formattedPrice: String get() = "₹${priceCents / 100}"

    val formattedDateTime: String get() {
        val date = java.util.Date(startsAt)
        val dateFmt = java.text.SimpleDateFormat("EEE, MMM d", java.util.Locale.getDefault())
        val timeFmt = java.text.SimpleDateFormat("h:mm a",    java.util.Locale.getDefault())
        return "${dateFmt.format(date)} · ${timeFmt.format(date)} · $coachName"
    }
}

// ── TimeSlot ──────────────────────────────────────────────────────────────────

data class TimeSlot(
    val id: UUID = UUID.randomUUID(),
    val label: String,
    val isAvailable: Boolean
)

// ── DatePill ──────────────────────────────────────────────────────────────────

data class DatePill(
    val epochMillis: Long,
    val weekdayShort: String,   // "Mon", "Tue"…
    val dayNumber: Int
)
