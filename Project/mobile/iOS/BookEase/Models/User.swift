import Foundation

struct User: Codable, Identifiable {
    let id: UUID
    let fullName: String
    let email: String
    let phone: String
    let avatarUrl: String?
}
