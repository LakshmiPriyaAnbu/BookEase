import SwiftUI

enum BookingStatus: String, Codable, CaseIterable {
    case pending
    case confirmed
    case completed
    case cancelled

    var label: String {
        switch self {
        case .pending:   return "Pending"
        case .confirmed: return "Confirmed"
        case .completed: return "Completed"
        case .cancelled: return "Cancelled"
        }
    }

    var foregroundColor: Color {
        switch self {
        case .pending:   return .beWarningText
        case .confirmed: return .beSuccessText
        case .completed: return .beText600
        case .cancelled: return .beDangerText
        }
    }

    var backgroundColor: Color {
        switch self {
        case .pending:   return .beWarningBg
        case .confirmed: return .beSuccessBg
        case .completed: return .beSurface200
        case .cancelled: return .beDangerBg
        }
    }

    var dateBadgeFg: Color {
        switch self {
        case .pending:   return .beWarningText
        case .confirmed: return .bePrimary
        case .completed: return .beText600
        case .cancelled: return .beDangerText
        }
    }

    var dateBadgeBg: Color {
        switch self {
        case .pending:   return .beWarningBg
        case .confirmed: return .bePrimarySoft
        case .completed: return .beSurface200
        case .cancelled: return .beDangerBg
        }
    }
}

struct Booking: Codable, Identifiable {
    let id: UUID
    let ref: String
    let serviceId: UUID
    let serviceName: String
    let coachName: String
    let startsAt: Date
    var status: BookingStatus
    let priceCents: Int

    var formattedDate: String {
        let f = DateFormatter()
        f.dateFormat = "MMM d"
        return f.string(from: startsAt)
    }

    var formattedMonth: String {
        let f = DateFormatter()
        f.dateFormat = "MMM"
        return f.string(from: startsAt).uppercased()
    }

    var formattedDay: String {
        let f = DateFormatter()
        f.dateFormat = "d"
        return f.string(from: startsAt)
    }

    var formattedTime: String {
        let f = DateFormatter()
        f.dateFormat = "h:mm a"
        return f.string(from: startsAt)
    }

    var formattedDateTime: String {
        let f = DateFormatter()
        f.dateFormat = "MMM d, yyyy • h:mm a"
        return f.string(from: startsAt)
    }

    var formattedPrice: String {
        "$\(priceCents / 100)"
    }
}
