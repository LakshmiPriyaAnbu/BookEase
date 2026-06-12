import SwiftUI

struct ProfileView: View {
    @State private var notificationsEnabled = true
    private let user = MockData.currentUser

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 0) {
                // Title
                Text(AppStrings.Profile.title)
                    .font(.jakarta(26, weight: .heavy))
                    .foregroundColor(.beInk800)
                    .padding(.horizontal, Spacing.lg)
                    .padding(.top, Spacing.sm)
                    .padding(.bottom, Spacing.lg)

                // Profile card
                profileCard
                    .padding(.horizontal, Spacing.lg)
                    .padding(.bottom, Spacing.xl)

                // Settings card
                settingsCard
                    .padding(.horizontal, Spacing.lg)
                    .padding(.bottom, Spacing.base)

                // Log out
                logOutButton
                    .padding(.horizontal, Spacing.lg)
                    .padding(.bottom, Spacing.xxl)
            }
        }
        .background(Color.beAppBg)
        .navigationBarHidden(true)
    }

    // MARK: - Profile Card

    private var profileCard: some View {
        HStack(spacing: 15) {
            Circle()
                .fill(Color.bePrimarySoft)
                .frame(width: 60, height: 60)
                .overlay(
                    Text(String(user.fullName.prefix(1)))
                        .font(.jakarta(24, weight: .bold))
                        .foregroundColor(.bePrimary)
                )

            VStack(alignment: .leading, spacing: 2) {
                Text(user.fullName)
                    .font(.jakarta(18, weight: .heavy))
                    .foregroundColor(.beInk800)
                Text(user.email)
                    .font(.jakarta(13, weight: .regular))
                    .foregroundColor(.beMuted500)
                Text(user.phone)
                    .font(.jakarta(13, weight: .regular))
                    .foregroundColor(.beMuted500)
            }

            Spacer()
        }
        .padding(18)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: Radius.xl))
        .overlay(RoundedRectangle(cornerRadius: Radius.xl).stroke(Color.beBorder100, lineWidth: 1))
    }

    // MARK: - Settings Card

    private var settingsCard: some View {
        VStack(spacing: 0) {
            NavigationLink(destination: BookingsView()) {
                settingsRow(
                    iconName: "calendar",
                    iconBg: Color.bePrimarySoft,
                    iconColor: Color.bePrimary,
                    title: AppStrings.Profile.myBookings
                )
            }
            .buttonStyle(.plain)

            Divider().background(Color(hex: "F4F4F6"))

            settingsRow(
                iconName: "creditcard.fill",
                iconBg: Color.beInfoBg,
                iconColor: Color.beInfo,
                title: AppStrings.Profile.paymentMethods
            )

            Divider().background(Color(hex: "F4F4F6"))

            notificationsRow
        }
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: Radius.base))
        .overlay(RoundedRectangle(cornerRadius: Radius.base).stroke(Color.beBorder100, lineWidth: 1))
    }

    private func settingsRow(iconName: String, iconBg: Color, iconColor: Color, title: String) -> some View {
        HStack(spacing: 13) {
            Image(systemName: iconName)
                .font(.system(size: 17, weight: .medium))
                .foregroundColor(iconColor)
                .frame(width: 34, height: 34)
                .background(iconBg)
                .clipShape(RoundedRectangle(cornerRadius: 9))

            Text(title)
                .font(.jakarta(15, weight: .semibold))
                .foregroundColor(.beInk700)

            Spacer()

            Image(systemName: "chevron.right")
                .font(.system(size: 14, weight: .medium))
                .foregroundColor(Color(hex: "C4C4CC"))
        }
        .padding(.horizontal, 17)
        .padding(.vertical, 15)
    }

    private var notificationsRow: some View {
        HStack(spacing: 13) {
            Image(systemName: "bell.fill")
                .font(.system(size: 17, weight: .medium))
                .foregroundColor(Color.beWarning)
                .frame(width: 34, height: 34)
                .background(Color.beWarningBg)
                .clipShape(RoundedRectangle(cornerRadius: 9))

            Text(AppStrings.Profile.notifications)
                .font(.jakarta(15, weight: .semibold))
                .foregroundColor(.beInk700)

            Spacer()

            Toggle("", isOn: $notificationsEnabled)
                .tint(Color.bePrimary)
                .labelsHidden()
        }
        .padding(.horizontal, 17)
        .padding(.vertical, 15)
    }

    // MARK: - Log Out

    private var logOutButton: some View {
        Button {} label: {
            Text(AppStrings.Profile.logOut)
                .font(.jakarta(15, weight: .bold))
                .foregroundColor(Color.beDanger)
                .frame(maxWidth: .infinity)
                .frame(height: 52)
                .background(Color.white)
                .clipShape(RoundedRectangle(cornerRadius: Radius.lg))
                .overlay(RoundedRectangle(cornerRadius: Radius.lg).stroke(Color.beBorder100, lineWidth: 1))
        }
    }
}

#Preview {
    NavigationStack {
        ProfileView()
    }
}
