import SwiftUI

// MARK: - Color Tokens

extension Color {
    init(hex: String) {
        let hex = hex.trimmingCharacters(in: CharacterSet.alphanumerics.inverted)
        var int: UInt64 = 0
        Scanner(string: hex).scanHexInt64(&int)
        let r, g, b: UInt64
        switch hex.count {
        case 6:
            (r, g, b) = ((int >> 16) & 0xFF, (int >> 8) & 0xFF, int & 0xFF)
        default:
            (r, g, b) = (0, 0, 0)
        }
        self.init(red: Double(r) / 255, green: Double(g) / 255, blue: Double(b) / 255)
    }

    // Primary
    static let bePrimary     = Color(hex: "5B4BE3")
    static let bePrimary700  = Color(hex: "4A3BD0")
    static let bePrimarySoft = Color(hex: "EEEBFD")
    static let beAccentLime  = Color(hex: "C8F25C")
    static let beAccentInk   = Color(hex: "1B2906")

    // Ink & Text
    static let beInk900  = Color(hex: "0E0F14")
    static let beInk800  = Color(hex: "0F1115")
    static let beInk700  = Color(hex: "16181D")
    static let beText600 = Color(hex: "545A66")
    static let beMuted500 = Color(hex: "8A909C")
    static let beMuted400 = Color(hex: "B6BBC4")

    // Borders & Surfaces
    static let beBorder200  = Color(hex: "E2E3E9")
    static let beBorder100  = Color(hex: "ECECEF")
    static let beDivider    = Color(hex: "F0F0F4")
    static let beSurface0   = Color.white
    static let beSurface100 = Color(hex: "F4F4F7")
    static let beSurface150 = Color(hex: "F7F7FA")
    static let beSurface200 = Color(hex: "F2F2F5")
    static let beAppBg      = Color(hex: "F5F5F8")

    // Status — Success
    static let beSuccess     = Color(hex: "16A34A")
    static let beSuccessText = Color(hex: "15803D")
    static let beSuccessBg   = Color(hex: "E7F8EE")

    // Status — Warning
    static let beWarning     = Color(hex: "D97706")
    static let beWarningText = Color(hex: "B45309")
    static let beWarningBg   = Color(hex: "FEF3D9")

    // Status — Danger
    static let beDanger     = Color(hex: "E11D48")
    static let beDangerText = Color(hex: "BE123C")
    static let beDangerBg   = Color(hex: "FCE4E9")

    // Status — Info
    static let beInfo     = Color(hex: "2563EB")
    static let beInfoText = Color(hex: "1D4ED8")
    static let beInfoBg   = Color(hex: "EAEFFC")
}

// MARK: - Typography

extension Font {
    // Space Grotesk — display/headings/numerics
    static func spaceGrotesk(_ size: CGFloat, weight: Font.Weight = .bold) -> Font {
        .custom("SpaceGrotesk-Bold", size: size).weight(weight)
    }

    // Plus Jakarta Sans — UI body text
    static func jakarta(_ size: CGFloat, weight: Font.Weight = .medium) -> Font {
        switch weight {
        case .bold:       return .custom("PlusJakartaSans-Bold", size: size)
        case .semibold:   return .custom("PlusJakartaSans-SemiBold", size: size)
        case .heavy:      return .custom("PlusJakartaSans-ExtraBold", size: size)
        case .regular:    return .custom("PlusJakartaSans-Regular", size: size)
        default:          return .custom("PlusJakartaSans-Medium", size: size)
        }
    }

    // Named scale shortcuts
    static let beH1:      Font = .spaceGrotesk(34, weight: .bold)
    static let beH2:      Font = .spaceGrotesk(30, weight: .bold)
    static let beH3:      Font = .jakarta(22, weight: .heavy)
    static let beTitle:   Font = .jakarta(26, weight: .heavy)
    static let beBodyLg:  Font = .jakarta(18, weight: .medium)
    static let beBody:    Font = .jakarta(16, weight: .medium)
    static let beBodySm:  Font = .jakarta(14.5, weight: .medium)
    static let beLabel:   Font = .jakarta(13.5, weight: .bold)
    static let beCaption: Font = .jakarta(12.5, weight: .semibold)
    static let beNumeric: Font = .spaceGrotesk(16, weight: .bold)
}

// MARK: - Spacing

enum Spacing {
    static let xs: CGFloat    = 4
    static let sm: CGFloat    = 8
    static let md: CGFloat    = 12
    static let base: CGFloat  = 16
    static let lg: CGFloat    = 20
    static let xl: CGFloat    = 24
    static let xxl: CGFloat   = 32
    static let xxxl: CGFloat  = 48
}

// MARK: - Radius

enum Radius {
    static let sm: CGFloat   = 8
    static let md: CGFloat   = 11
    static let lg: CGFloat   = 14
    static let xl: CGFloat   = 18
    static let xxl: CGFloat  = 22
    static let pill: CGFloat = 999
}

// MARK: - Shadow Helpers

extension View {
    func beShadowCard() -> some View {
        self.shadow(color: Color(hex: "10111600").opacity(0.13), radius: 30, x: 0, y: 24)
    }
    func beShadowBtn() -> some View {
        self.shadow(color: Color.bePrimary.opacity(0.30), radius: 10, x: 0, y: 8)
    }
    func beShadowBtnSm() -> some View {
        self.shadow(color: Color.bePrimary.opacity(0.28), radius: 6, x: 0, y: 4)
    }
}
