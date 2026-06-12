import SwiftUI

struct EmptyStateView: View {
    enum Variant {
        case bookings
        case generic(icon: String)
    }

    var variant: Variant = .generic(icon: "magnifyingglass")
    var title: String
    var message: String
    var actionTitle: String? = nil
    var onAction: (() -> Void)? = nil

    var body: some View {
        VStack(spacing: 0) {
            Spacer()

            iconContainer
                .padding(.bottom, Spacing.xl)

            Text(title)
                .font(.spaceGrotesk(24, weight: .bold))
                .foregroundColor(.beInk800)
                .multilineTextAlignment(.center)
                .padding(.bottom, Spacing.sm)

            Text(message)
                .font(.jakarta(15.5, weight: .medium))
                .foregroundColor(.beText600)
                .multilineTextAlignment(.center)
                .padding(.bottom, Spacing.xxl)

            if let title = actionTitle, let action = onAction {
                PrimaryButton(title: title, action: action)
            }

            Spacer()
        }
        .frame(maxWidth: .infinity)
        .padding(.horizontal, Spacing.xl)
    }

    @ViewBuilder
    private var iconContainer: some View {
        switch variant {
        case .bookings:
            ZStack {
                RoundedRectangle(cornerRadius: 24)
                    .fill(Color.bePrimarySoft)
                    .frame(width: 96, height: 96)

                Image(systemName: "calendar.badge.plus")
                    .font(.system(size: 44, weight: .medium))
                    .foregroundColor(.bePrimary)
                    .accessibilityHidden(true)
            }
            .accessibilityHidden(true)

        case .generic(let icon):
            ZStack {
                RoundedRectangle(cornerRadius: 24)
                    .fill(Color.beSurface100)
                    .frame(width: 96, height: 96)

                Image(systemName: icon)
                    .font(.system(size: 44, weight: .medium))
                    .foregroundColor(.beMuted500)
                    .accessibilityHidden(true)
            }
            .accessibilityHidden(true)
        }
    }
}

#Preview {
    VStack(spacing: 0) {
        EmptyStateView(
            variant: .bookings,
            title: "No bookings yet",
            message: "When you book a session it'll show up here. Find a coach and lock in your first one.",
            actionTitle: "Browse services",
            onAction: { print("browse") }
        )

        EmptyStateView(
            variant: .generic(icon: "magnifyingglass"),
            title: "No results found",
            message: "Try adjusting your search or filters to find what you're looking for."
        )
    }
    .background(Color.beAppBg)
}
