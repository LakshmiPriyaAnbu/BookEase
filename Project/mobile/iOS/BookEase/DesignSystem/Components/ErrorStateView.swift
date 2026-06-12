import SwiftUI

struct ErrorStateView: View {
    var title: String = AppStrings.Errors.defaultTitle
    var message: String = AppStrings.Errors.defaultMessage
    var errorCode: String? = nil
    var onRetry: (() -> Void)? = nil
    var onContactSupport: (() -> Void)? = nil

    var body: some View {
        VStack(spacing: 0) {
            Spacer()

            iconCircle
                .padding(.bottom, Spacing.xl)

            Text(title)
                .font(.spaceGrotesk(28, weight: .bold))
                .foregroundColor(.beInk800)
                .multilineTextAlignment(.center)
                .padding(.bottom, Spacing.sm)

            Text(message)
                .font(.jakarta(16, weight: .medium))
                .foregroundColor(.beText600)
                .multilineTextAlignment(.center)
                .padding(.bottom, Spacing.xxl)

            if let retry = onRetry {
                retryButton(action: retry)
                    .padding(.bottom, Spacing.lg)
            }

            if let support = onContactSupport {
                contactSupportButton(action: support)
                    .padding(.bottom, Spacing.lg)
            }

            if let code = errorCode {
                Text(AppStrings.Errors.errorCodePrefix + code)
                    .font(.jakarta(12, weight: .regular))
                    .foregroundColor(.beMuted400)
            }

            Spacer()
        }
        .frame(maxWidth: .infinity)
        .padding(.horizontal, Spacing.xl)
    }

    private var iconCircle: some View {
        ZStack {
            Circle()
                .fill(Color(hex: "FCE4E9"))
                .frame(width: 84, height: 84)

            Image(systemName: "exclamationmark.triangle")
                .font(.system(size: 36, weight: .semibold))
                .foregroundColor(.beDanger)
                .accessibilityHidden(true)
        }
        .accessibilityHidden(true)
    }

    private func retryButton(action: @escaping () -> Void) -> some View {
        Button(action: action) {
            HStack(spacing: 8) {
                Image(systemName: "arrow.clockwise")
                    .font(.system(size: 15, weight: .bold))
                    .accessibilityHidden(true)
                Text(AppStrings.Common.tryAgain)
                    .font(.jakarta(16, weight: .bold))
            }
            .foregroundColor(.white)
            .frame(maxWidth: .infinity)
            .frame(height: 54)
            .background(Color.bePrimary)
            .clipShape(RoundedRectangle(cornerRadius: Radius.md))
            .beShadowBtn()
        }
        .accessibilityLabel(AppStrings.Common.tryAgain)
    }

    private func contactSupportButton(action: @escaping () -> Void) -> some View {
        Button(action: action) {
            Text(AppStrings.Common.contactSupport)
                .font(.jakarta(15, weight: .bold))
                .foregroundColor(.bePrimary)
        }
        .accessibilityLabel(AppStrings.Common.contactSupport)
    }
}

#Preview {
    VStack(spacing: 40) {
        ErrorStateView(
            title: "Something went wrong",
            message: "We hit a snag. Please try again in a moment.",
            errorCode: "ERR_503",
            onRetry: { print("retry") },
            onContactSupport: { print("support") }
        )

        ErrorStateView(
            title: "Network error",
            message: "Check your connection and try again.",
            onRetry: { print("retry") }
        )
    }
    .background(Color.beAppBg)
}
