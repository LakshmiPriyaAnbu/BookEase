import Foundation
import Observation

@Observable
final class ServiceDetailViewModel {
    let service: Service
    var timeSlots: [TimeSlot] = MockData.timeSlots
    var selectedSlot: TimeSlot? = nil

    init(service: Service) {
        self.service = service
    }

    var canProceed: Bool { selectedSlot != nil }
}
