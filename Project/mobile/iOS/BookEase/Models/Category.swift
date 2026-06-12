import Foundation

struct BookCategory: Codable, Identifiable {
    let id: UUID
    let name: String
    let iconSystemName: String
    let colorHex: String
}
