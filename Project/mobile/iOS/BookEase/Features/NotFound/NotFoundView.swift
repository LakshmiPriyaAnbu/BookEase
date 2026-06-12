import SwiftUI

struct NotFoundView: View {
    var onBackToHome: () -> Void = {}
    var onBrowseServices: () -> Void = {}

    var body: some View {
        VStack(spacing: 0) {
            logoRow
                .padding(.top, 24)
                .padding(.horizontal, Spacing.xl)

            Spacer()

            centreContent
                .padding(.horizontal, Spacing.xl)

            Spacer()
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color.white)
        .toolbar(.hidden, for: .tabBar)
        .navigationBarHidden(true)
    }

    // MARK: - Logo Row

    private var logoRow: some View {
        HStack(spacing: 11) {
            ZStack {
                RoundedRectangle(cornerRadius: 11)
                    .fill(
                        LinearGradient(
                            colors: [Color(hex: "6B5BF0"), Color(hex: "4A3BD0")],
                            startPoint: .topLeading,
                            endPoint: .bottomTrailing
                        )
                    )
                    .frame(width: 38, height: 38)
                    .shadow(color: Color(hex: "4A3BD0").opacity(0.32), radius: 7, x: 0, y: 6)

                Image(systemName: "bolt.fill")
                    .font(.system(size: 18, weight: .bold))
                    .foregroundColor(.beAccentLime)
                    .accessibilityHidden(true)
            }

            Text(AppStrings.Common.appName)
                .font(.spaceGrotesk(20, weight: .bold))
                .foregroundColor(.beInk800)

            Spacer()
        }
        .accessibilityElement(children: .combine)
        .accessibilityLabel(AppStrings.Common.appName)
    }

    // MARK: - Centre Content

    private var centreContent: some View {
        VStack(spacing: 0) {
            Text(AppStrings.NotFound.errorCode)
                .font(.spaceGrotesk(96))
                .tracking(-0.04 * 96)
                .foregroundStyle(
                    LinearGradient(
                        colors: [Color(hex: "6B5BF0"), Color(hex: "4A3BD0")],
                        startPoint: .topLeading,
                        endPoint: .bottomTrailing
                    )
                )
                .accessibilityLabel(AppStrings.NotFound.errorCode)

            Text(AppStrings.NotFound.title)
                .font(.spaceGrotesk(24, weight: .bold))
                .foregroundColor(.beInk800)
                .multilineTextAlignment(.center)
                .padding(.top, Spacing.lg)

            Text(AppStrings.NotFound.message)
                .font(.jakarta(14.5, weight: .medium))
                .foregroundColor(.beMuted500)
                .multilineTextAlignment(.center)
                .padding(.top, Spacing.sm)
                .padding(.bottom, Spacing.xxl)

            PrimaryButton(title: AppStrings.NotFound.backToHomeButton, action: onBackToHome)

            Button(action: onBrowseServices) {
                Text(AppStrings.NotFound.browseServicesButton)
                    .font(.jakarta(16, weight: .bold))
                    .foregroundColor(.beInk700)
                    .frame(maxWidth: .infinity)
                    .frame(height: 54)
                    .background(Color.white)
                    .clipShape(RoundedRectangle(cornerRadius: Radius.md))
                    .overlay(
                        RoundedRectangle(cornerRadius: Radius.md)
                            .stroke(Color.beBorder200, lineWidth: 1)
                    )
            }
            .accessibilityLabel(AppStrings.NotFound.browseServicesButton)
            .padding(.top, Spacing.md)
        }
        .multilineTextAlignment(.center)
    }
}

#Preview {
    NavigationStack {
        NotFoundView()
    }
}
