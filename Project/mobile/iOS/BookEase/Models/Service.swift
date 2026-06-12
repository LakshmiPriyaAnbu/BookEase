import Foundation

struct Service: Codable, Identifiable {
    let id: UUID
    let name: String
    let description: String
    let priceCents: Int
    let durationMin: Int
    let categoryId: UUID
    let imageUrl: String?
    let rating: Double
    let coachName: String
    let included: [String]

    var formattedPrice: String {
        let dollars = priceCents / 100
        return "$\(dollars)"
    }

    var formattedDuration: String {
        "\(durationMin) min"
    }
}
