import Foundation
import Observation

enum BookingSegment: String, CaseIterable {
    case upcoming  = "Upcoming"
    case completed = "Completed"
    case cancelled = "Cancelled"
}

@Observable
final class BookingsViewModel {
    var allBookings: [Booking] = MockData.bookings
    var selectedSegment: BookingSegment = .upcoming

    var filteredBookings: [Booking] {
        switch selectedSegment {
        case .upcoming:
            return allBookings.filter { $0.status == .confirmed || $0.status == .pending }
        case .completed:
            return allBookings.filter { $0.status == .completed }
        case .cancelled:
            return allBookings.filter { $0.status == .cancelled }
        }
    }

    func cancelBooking(id: UUID) {
        guard let idx = allBookings.firstIndex(where: { $0.id == id }) else { return }
        allBookings[idx].status = .cancelled
    }
}
