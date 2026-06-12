import SwiftUI

enum AlertVariant {
    case success, error, info, warning
}

struct InlineAlertView: View {
    let variant: AlertVariant
    let title: String
    var message: String? = nil

    var body: some View {
        HStack(alignment: .top, spacing: 12) {
            Image(systemName: iconName)
                .font(.system(size: 19, weight: .semibold))
                .foregroundColor(iconColor)
                .accessibilityHidden(true)

            VStack(alignment: .leading, spacing: 3) {
                Text(title)
                    .font(.jakarta(14, weight: .bold))
                    .foregroundColor(titleColor)
                    .fixedSize(horizontal: false, vertical: true)

                if let message {
                    Text(message)
                        .font(.jakarta(13, weight: .regular))
                        .foregroundColor(titleColor.opacity(0.75))
                        .fixedSize(horizontal: false, vertical: true)
                }
            }

            Spacer(minLength: 0)
        }
        .padding(.horizontal, 15)
        .padding(.vertical, 17)
        .background(bgColor)
        .clipShape(RoundedRectangle(cornerRadius: 12))
        .overlay(
            RoundedRectangle(cornerRadius: 12)
                .stroke(borderColor, lineWidth: 1)
        )
        .accessibilityElement(children: .combine)
        .accessibilityLabel(message.map { "\(title). \($0)" } ?? title)
    }

    private var iconName: String {
        switch variant {
        case .success: return "checkmark.circle.fill"
        case .error:   return "info.circle.fill"
        case .info:    return "info.circle.fill"
        case .warning: return "exclamationmark.triangle.fill"
        }
    }

    private var iconColor: Color {
        switch variant {
        case .success: return .beSuccess
        case .error:   return .beDanger
        case .info:    return .beInfo
        case .warning: return .beWarning
        }
    }

    private var titleColor: Color {
        switch variant {
        case .success: return .beSuccessText
        case .error:   return .beDangerText
        case .info:    return .beInfoText
        case .warning: return .beWarningText
        }
    }

    private var bgColor: Color {
        switch variant {
        case .success: return .beSuccessBg
        case .error:   return .beDangerBg
        case .info:    return .beInfoBg
        case .warning: return .beWarningBg
        }
    }

    private var borderColor: Color {
        switch variant {
        case .success: return Color(hex: "BBE9CD")
        case .error:   return Color(hex: "F6C6D1")
        case .info:    return Color(hex: "C5D6F7")
        case .warning: return Color(hex: "F5DCA0")
        }
    }
}

#Preview {
    VStack(spacing: 12) {
        InlineAlertView(
            variant: .success,
            title: "Booking confirmed",
            message: "Your session has been successfully booked for tomorrow at 10:00 AM."
        )
        InlineAlertView(
            variant: .error,
            title: "Payment failed",
            message: "We couldn't process your payment. Please update your card details."
        )
        InlineAlertView(
            variant: .info,
            title: "Reminder",
            message: "Your session starts in 30 minutes."
        )
        InlineAlertView(
            variant: .warning,
            title: "Availability limited",
            message: "Only 2 slots remaining at this time."
        )
    }
    .padding()
    .background(Color.beAppBg)
}
