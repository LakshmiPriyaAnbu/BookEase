import Foundation

struct TimeSlot: Identifiable {
    let id: UUID
    let label: String
    var isAvailable: Bool
}
