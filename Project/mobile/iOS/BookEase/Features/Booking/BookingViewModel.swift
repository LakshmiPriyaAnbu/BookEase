import Foundation
import Observation

@Observable
final class BookingViewModel {
    let service: Service
    var preselectedSlot: TimeSlot?

    var datePills: [(weekday: String, day: String, date: Date)] = MockData.datePills()
    var selectedDateIndex: Int = 0
    var timeSlots: [TimeSlot] = MockData.timeSlots
    var selectedSlot: TimeSlot?

    var fullName: String = ""
    var phone: String = ""
    var notes: String = ""

    var isValid: Bool {
        !fullName.trimmingCharacters(in: .whitespaces).isEmpty &&
        !phone.trimmingCharacters(in: .whitespaces).isEmpty &&
        selectedSlot != nil
    }

    init(service: Service, preselectedSlot: TimeSlot? = nil) {
        self.service = service
        self.preselectedSlot = preselectedSlot
        self.selectedSlot = preselectedSlot
    }

    func buildConfirmedBooking() -> Booking {
        let base = datePills[selectedDateIndex].date
        let cal = Calendar.current
        let startsAt = cal.date(byAdding: .hour, value: 10, to: base) ?? base

        return Booking(
            id: UUID(),
            ref: "BK-\(Int.random(in: 10000...99999))",
            serviceId: service.id,
            serviceName: service.name,
            coachName: service.coachName,
            startsAt: startsAt,
            status: .confirmed,
            priceCents: service.priceCents
        )
    }
}
