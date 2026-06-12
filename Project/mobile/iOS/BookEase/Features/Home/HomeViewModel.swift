import Foundation
import Observation

@Observable
final class HomeViewModel {
    var upcomingBooking: Booking? = MockData.bookings.first { $0.status == .confirmed }
    var popularServices: [Service] = Array(MockData.services.prefix(4))
    var categories: [BookCategory] = MockData.categories
    var searchText: String = ""
}
