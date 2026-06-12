import SwiftUI

struct SettingsRow: View {
    let icon: String
    let iconBg: Color
    let iconColor: Color
    let title: String
    var hasChevron: Bool = true
    var action: () -> Void = {}

    var body: some View {
        Button(action: action) {
            HStack(spacing: Spacing.md) {
                // Icon circle
                Image(systemName: icon)
                    .font(.system(size: 16, weight: .semibold))
                    .foregroundColor(iconColor)
                    .frame(width: 36, height: 36)
                    .background(iconBg)
                    .clipShape(RoundedRectangle(cornerRadius: Radius.sm))

                Text(title)
                    .font(.jakarta(15, weight: .bold))
                    .foregroundColor(.beInk700)

                Spacer()

                if hasChevron {
                    Image(systemName: "chevron.right")
                        .font(.system(size: 13, weight: .semibold))
                        .foregroundColor(.beMuted400)
                }
            }
            .padding(.vertical, Spacing.sm + 2)
        }
    }
}

struct SettingsToggleRow: View {
    let icon: String
    let iconBg: Color
    let iconColor: Color
    let title: String
    @Binding var isOn: Bool

    var body: some View {
        HStack(spacing: Spacing.md) {
            Image(systemName: icon)
                .font(.system(size: 16, weight: .semibold))
                .foregroundColor(iconColor)
                .frame(width: 36, height: 36)
                .background(iconBg)
                .clipShape(RoundedRectangle(cornerRadius: Radius.sm))

            Text(title)
                .font(.jakarta(15, weight: .bold))
                .foregroundColor(.beInk700)

            Spacer()

            Toggle("", isOn: $isOn)
                .tint(.bePrimary)
                .labelsHidden()
        }
        .padding(.vertical, Spacing.sm + 2)
    }
}

#Preview {
    VStack {
        SettingsRow(icon: "calendar", iconBg: .bePrimarySoft, iconColor: .bePrimary, title: "My bookings")
        Divider()
        SettingsRow(icon: "creditcard.fill", iconBg: .beInfoBg, iconColor: .beInfo, title: "Payment methods")
        Divider()
        SettingsToggleRow(icon: "bell.fill", iconBg: .beWarningBg, iconColor: .beWarning, title: "Notifications", isOn: .constant(true))
    }
    .padding()
    .background(Color.white)
    .clipShape(RoundedRectangle(cornerRadius: Radius.xl))
    .padding()
}
