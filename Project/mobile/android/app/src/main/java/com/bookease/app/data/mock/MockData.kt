package com.bookease.app.data.mock

import com.bookease.app.data.models.*
import java.util.Calendar
import java.util.UUID

object MockData {

    val currentUser = User(
        fullName = "Alex Johnson",
        email = "alex.johnson@email.com",
        phone = "+1 (555) 012-3456"
    )

    // ── Categories ────────────────────────────────────────────────────────────

    private val strengthId = UUID.fromString("00000000-0000-0000-0000-000000000001")
    private val hiitId     = UUID.fromString("00000000-0000-0000-0000-000000000002")
    private val mobilityId = UUID.fromString("00000000-0000-0000-0000-000000000003")
    private val nutritionId = UUID.fromString("00000000-0000-0000-0000-000000000004")

    val categories = listOf(
        BookCategory(id = strengthId,  name = "Strength",   iconName = "FitnessCenter", colorHex = "#5B4BE3"),
        BookCategory(id = hiitId,      name = "HIIT",       iconName = "Bolt",          colorHex = "#E11D48"),
        BookCategory(id = mobilityId,  name = "Mobility",   iconName = "SelfImprovement",colorHex = "#16A34A"),
        BookCategory(id = nutritionId, name = "Nutrition",  iconName = "Restaurant",    colorHex = "#D97706")
    )

    // ── Services ──────────────────────────────────────────────────────────────

    val services = listOf(
        Service(
            categoryId  = strengthId,
            name        = "Power Lifting Foundations",
            description = "Build raw strength through compound movements. This session covers the squat, bench press, and deadlift with a focus on technique and progressive overload.",
            priceCents  = 7500,
            durationMin = 60,
            rating      = 4.9,
            coachName   = "Marcus Reid",
            included    = listOf("Technique assessment", "Personalised programme", "Post-session notes")
        ),
        Service(
            categoryId  = hiitId,
            name        = "HIIT Metabolic Blast",
            description = "Push your limits with high-intensity intervals designed to torch calories and boost your metabolic rate long after the session ends.",
            priceCents  = 5500,
            durationMin = 45,
            rating      = 4.8,
            coachName   = "Sofia Cheng",
            included    = listOf("Heart-rate tracking", "Custom intervals", "Cool-down routine")
        ),
        Service(
            categoryId  = mobilityId,
            name        = "Mobility & Flexibility Flow",
            description = "Improve range of motion and reduce injury risk with guided stretching and soft-tissue work tailored to your body's restrictions.",
            priceCents  = 6000,
            durationMin = 50,
            rating      = 4.7,
            coachName   = "Priya Nair",
            included    = listOf("Movement screening", "Targeted stretching", "Home exercise plan")
        ),
        Service(
            categoryId  = nutritionId,
            name        = "Nutrition Strategy Session",
            description = "Work one-on-one with a registered dietitian to build a sustainable eating plan aligned with your performance and body-composition goals.",
            priceCents  = 9500,
            durationMin = 60,
            rating      = 4.9,
            coachName   = "Dr. Lena Torres",
            included    = listOf("Dietary analysis", "Meal plan template", "Supplement review", "30-day check-in")
        ),
        Service(
            categoryId  = strengthId,
            name        = "Olympic Weightlifting Intro",
            description = "Learn the snatch and clean & jerk from scratch. Perfect for athletes looking to add explosive power to their training.",
            priceCents  = 8500,
            durationMin = 75,
            rating      = 4.8,
            coachName   = "Marcus Reid",
            included    = listOf("Technique drills", "Video analysis", "Progression roadmap")
        ),
        Service(
            categoryId  = hiitId,
            name        = "Cardio Kickboxing",
            description = "Combine martial-arts movements with cardio conditioning for a fun, full-body workout that improves coordination and burns fat.",
            priceCents  = 4500,
            durationMin = 45,
            rating      = 4.6,
            coachName   = "Sofia Cheng",
            included    = listOf("Gloves provided", "Combo sequences", "Endurance finisher")
        )
    )

    // ── Bookings ──────────────────────────────────────────────────────────────

    private fun daysFromNow(days: Int): Long {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, days)
        cal.set(Calendar.HOUR_OF_DAY, 10)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        return cal.timeInMillis
    }

    val bookings = mutableListOf(
        Booking(
            ref         = "BK-20416",
            serviceId   = services[0].id,
            serviceName = services[0].name,
            coachName   = services[0].coachName,
            startsAt    = daysFromNow(2),
            status      = BookingStatus.CONFIRMED,
            priceCents  = services[0].priceCents
        ),
        Booking(
            ref         = "BK-20390",
            serviceId   = services[1].id,
            serviceName = services[1].name,
            coachName   = services[1].coachName,
            startsAt    = daysFromNow(5),
            status      = BookingStatus.PENDING,
            priceCents  = services[1].priceCents
        ),
        Booking(
            ref         = "BK-20301",
            serviceId   = services[2].id,
            serviceName = services[2].name,
            coachName   = services[2].coachName,
            startsAt    = daysFromNow(-7),
            status      = BookingStatus.COMPLETED,
            priceCents  = services[2].priceCents
        ),
        Booking(
            ref         = "BK-20285",
            serviceId   = services[3].id,
            serviceName = services[3].name,
            coachName   = services[3].coachName,
            startsAt    = daysFromNow(-14),
            status      = BookingStatus.COMPLETED,
            priceCents  = services[3].priceCents
        ),
        Booking(
            ref         = "BK-20200",
            serviceId   = services[4].id,
            serviceName = services[4].name,
            coachName   = services[4].coachName,
            startsAt    = daysFromNow(-3),
            status      = BookingStatus.CANCELLED,
            priceCents  = services[4].priceCents
        )
    )

    // ── Time Slots ────────────────────────────────────────────────────────────

    val timeSlots = listOf(
        TimeSlot(label = "9:00 AM",  isAvailable = true),
        TimeSlot(label = "10:00 AM", isAvailable = true),
        TimeSlot(label = "11:00 AM", isAvailable = true),
        TimeSlot(label = "12:00 PM", isAvailable = false),
        TimeSlot(label = "1:00 PM",  isAvailable = true),
        TimeSlot(label = "2:00 PM",  isAvailable = true),
        TimeSlot(label = "3:00 PM",  isAvailable = true),
        TimeSlot(label = "4:30 PM",  isAvailable = true)
    )

    // ── Date Pills (next 7 days) ───────────────────────────────────────────────

    fun datePills(): List<DatePill> {
        val result = mutableListOf<DatePill>()
        val cal = Calendar.getInstance()
        val days = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        for (i in 0 until 7) {
            result.add(
                DatePill(
                    epochMillis   = cal.timeInMillis,
                    weekdayShort  = days[cal.get(Calendar.DAY_OF_WEEK) - 1],
                    dayNumber     = cal.get(Calendar.DAY_OF_MONTH)
                )
            )
            cal.add(Calendar.DAY_OF_YEAR, 1)
        }
        return result
    }
}
