import Foundation

enum MockData {

    // MARK: - Current User

    static let currentUser = User(
        id: UUID(),
        fullName: "Alex Johnson",
        email: "alex.johnson@email.com",
        phone: "+1 (555) 012-3456",
        avatarUrl: nil
    )

    // MARK: - Categories

    static let categories: [BookCategory] = [
        BookCategory(id: UUID(), name: "Strength", iconSystemName: "dumbbell.fill",       colorHex: "EEEBFD"),
        BookCategory(id: UUID(), name: "HIIT",     iconSystemName: "bolt.fill",            colorHex: "FEF3D9"),
        BookCategory(id: UUID(), name: "Mobility", iconSystemName: "figure.flexibility",  colorHex: "E7F8EE"),
        BookCategory(id: UUID(), name: "Nutrition",iconSystemName: "leaf.fill",            colorHex: "EAEFFC"),
    ]

    // MARK: - Services

    static let services: [Service] = [
        Service(
            id: UUID(),
            name: "Power Strength Session",
            description: "A high-intensity strength training session focused on compound movements. Build muscle, increase metabolism, and improve overall athletic performance under expert guidance.",
            priceCents: 6500,
            durationMin: 60,
            categoryId: categories[0].id,
            imageUrl: nil,
            rating: 4.9,
            coachName: "Marcus Reid",
            included: ["Personalised warm-up routine", "Compound lift coaching", "Progress tracking", "Cool-down & mobility work"]
        ),
        Service(
            id: UUID(),
            name: "HIIT Cardio Blast",
            description: "Burn calories and boost cardiovascular fitness with this high-intensity interval training class. Perfect for all fitness levels with scalable intensity.",
            priceCents: 4500,
            durationMin: 45,
            categoryId: categories[1].id,
            imageUrl: nil,
            rating: 4.8,
            coachName: "Sofia Cheng",
            included: ["Dynamic warm-up", "Interval circuits", "Heart rate monitoring", "Recovery stretching"]
        ),
        Service(
            id: UUID(),
            name: "Mobility & Flexibility",
            description: "Improve your range of motion, reduce injury risk, and recover faster. This session targets joint health, muscle flexibility, and movement quality.",
            priceCents: 5500,
            durationMin: 50,
            categoryId: categories[2].id,
            imageUrl: nil,
            rating: 4.7,
            coachName: "Priya Nair",
            included: ["Joint mobility drills", "Fascial release techniques", "Yoga-inspired flows", "Breathing exercises"]
        ),
        Service(
            id: UUID(),
            name: "Nutrition Coaching",
            description: "1-on-1 nutrition consultation to create a personalised meal plan aligned with your fitness goals, dietary needs, and lifestyle.",
            priceCents: 9500,
            durationMin: 60,
            categoryId: categories[3].id,
            imageUrl: nil,
            rating: 5.0,
            coachName: "Dr. Lena Torres",
            included: ["Full dietary assessment", "Custom meal plan", "Supplement guidance", "Weekly check-in support"]
        ),
        Service(
            id: UUID(),
            name: "Olympic Lifting Intro",
            description: "Learn the fundamentals of snatch and clean & jerk from a certified coach. Safe, technical instruction for beginners and intermediate athletes.",
            priceCents: 7500,
            durationMin: 75,
            categoryId: categories[0].id,
            imageUrl: nil,
            rating: 4.9,
            coachName: "Marcus Reid",
            included: ["Technique video review", "Barbell skill drills", "Safety & injury prevention", "Programming advice"]
        ),
        Service(
            id: UUID(),
            name: "Recovery & Rehab",
            description: "Targeted recovery session using soft tissue work, corrective exercises, and breath work. Ideal post-competition or after intense training blocks.",
            priceCents: 6000,
            durationMin: 60,
            categoryId: categories[2].id,
            imageUrl: nil,
            rating: 4.6,
            coachName: "Priya Nair",
            included: ["Postural assessment", "Foam rolling & massage", "Corrective movements", "Sleep & recovery tips"]
        ),
    ]

    // MARK: - Bookings

    private static var cal: Calendar { Calendar.current }

    static let bookings: [Booking] = [
        Booking(
            id: UUID(),
            ref: "BK-20416",
            serviceId: services[0].id,
            serviceName: services[0].name,
            coachName: services[0].coachName,
            startsAt: cal.date(byAdding: .day, value: 1, to: Date())!,
            status: .confirmed,
            priceCents: 6500
        ),
        Booking(
            id: UUID(),
            ref: "BK-20389",
            serviceId: services[1].id,
            serviceName: services[1].name,
            coachName: services[1].coachName,
            startsAt: cal.date(byAdding: .day, value: 4, to: Date())!,
            status: .pending,
            priceCents: 4500
        ),
        Booking(
            id: UUID(),
            ref: "BK-20310",
            serviceId: services[2].id,
            serviceName: services[2].name,
            coachName: services[2].coachName,
            startsAt: cal.date(byAdding: .day, value: -7, to: Date())!,
            status: .completed,
            priceCents: 5500
        ),
        Booking(
            id: UUID(),
            ref: "BK-20290",
            serviceId: services[3].id,
            serviceName: services[3].name,
            coachName: services[3].coachName,
            startsAt: cal.date(byAdding: .day, value: -14, to: Date())!,
            status: .completed,
            priceCents: 9500
        ),
        Booking(
            id: UUID(),
            ref: "BK-20201",
            serviceId: services[4].id,
            serviceName: services[4].name,
            coachName: services[4].coachName,
            startsAt: cal.date(byAdding: .day, value: -21, to: Date())!,
            status: .cancelled,
            priceCents: 7500
        ),
    ]

    // MARK: - Time Slots

    static let timeSlots: [TimeSlot] = [
        TimeSlot(id: UUID(), label: "9:00 AM",  isAvailable: true),
        TimeSlot(id: UUID(), label: "10:30 AM", isAvailable: true),
        TimeSlot(id: UUID(), label: "12:00 PM", isAvailable: false),
        TimeSlot(id: UUID(), label: "1:30 PM",  isAvailable: true),
        TimeSlot(id: UUID(), label: "3:00 PM",  isAvailable: true),
        TimeSlot(id: UUID(), label: "4:30 PM",  isAvailable: true),
    ]

    // MARK: - Date Pills (next 7 days)

    static func datePills() -> [(weekday: String, day: String, date: Date)] {
        let cal = Calendar.current
        let formatter = DateFormatter()
        formatter.dateFormat = "EEE"
        let dayFormatter = DateFormatter()
        dayFormatter.dateFormat = "d"
        return (0..<7).compactMap { offset in
            guard let date = cal.date(byAdding: .day, value: offset, to: Date()) else { return nil }
            return (
                weekday: formatter.string(from: date).uppercased(),
                day: dayFormatter.string(from: date),
                date: date
            )
        }
    }
}
