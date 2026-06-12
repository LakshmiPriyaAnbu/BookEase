import SwiftUI

struct HomeView: View {
    @State private var vm = HomeViewModel()

    private let categoryIconColors: [Color] = [.bePrimary, .beSuccess, .beInfo, .beWarning]
    private let categoryBgColors: [Color] = [.bePrimarySoft, .beSuccessBg, .beInfoBg, .beWarningBg]

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 0) {
                headerSection
                    .padding(.horizontal, Spacing.lg)
                    .padding(.top, Spacing.lg)
                    .padding(.bottom, Spacing.lg)

                searchBar
                    .padding(.horizontal, Spacing.lg)
                    .padding(.bottom, Spacing.xl)

                if let booking = vm.upcomingBooking {
                    upcomingCard(booking: booking)
                        .padding(.horizontal, Spacing.lg)
                        .padding(.bottom, Spacing.xl)
                }

                popularSection
                    .padding(.bottom, Spacing.xl)

                categoriesSection
                    .padding(.bottom, Spacing.xl)

                marketingLinksSection
                    .padding(.horizontal, Spacing.lg)
                    .padding(.bottom, Spacing.xxl)
            }
        }
        .background(Color.beAppBg)
        .navigationBarHidden(true)
    }

    // MARK: - Header

    private var headerSection: some View {
        HStack {
            VStack(alignment: .leading, spacing: 3) {
                Text(AppStrings.Home.greeting)
                    .font(.jakarta(14, weight: .medium))
                    .foregroundColor(.beMuted500)
                Text(MockData.currentUser.fullName)
                    .font(.jakarta(23, weight: .heavy))
                    .foregroundColor(.beInk800)
            }

            Spacer()

            HStack(spacing: Spacing.md) {
                Button {} label: {
                    Image(systemName: "bell")
                        .font(.system(size: 19, weight: .medium))
                        .foregroundColor(.beInk700)
                        .frame(width: 40, height: 40)
                        .background(Color.white)
                        .clipShape(Circle())
                        .overlay(Circle().stroke(Color.beBorder100, lineWidth: 1))
                }

                Circle()
                    .fill(Color.bePrimarySoft)
                    .frame(width: 40, height: 40)
                    .overlay(
                        Text(String(MockData.currentUser.fullName.prefix(1)))
                            .font(.jakarta(16, weight: .bold))
                            .foregroundColor(.bePrimary)
                    )
                    .overlay(Circle().stroke(Color.beBorder100, lineWidth: 1))
            }
        }
    }

    // MARK: - Search Bar

    private var searchBar: some View {
        HStack(spacing: 10) {
            Image(systemName: "magnifyingglass")
                .font(.system(size: 18))
                .foregroundColor(.beMuted500)
            Text(AppStrings.Home.searchPlaceholder)
                .font(.jakarta(15, weight: .regular))
                .foregroundColor(.beMuted500)
            Spacer()
        }
        .frame(height: 48)
        .padding(.horizontal, Spacing.base)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: Radius.lg))
        .overlay(RoundedRectangle(cornerRadius: Radius.lg).stroke(Color.beBorder100, lineWidth: 1))
    }

    // MARK: - Upcoming Card

    private func upcomingCard(booking: Booking) -> some View {
        VStack(alignment: .leading, spacing: 0) {
            HStack {
                Text(AppStrings.Home.upcomingSessionLabel)
                    .font(.jakarta(12, weight: .bold))
                    .foregroundColor(Color(hex: "D9D3FA"))
                    .tracking(1.2)
                Spacer()
                Text(AppStrings.Home.upcomingSessionBadge)
                    .font(.jakarta(11, weight: .bold))
                    .foregroundColor(.white)
                    .padding(.horizontal, 9)
                    .padding(.vertical, 4)
                    .background(Color.white.opacity(0.2))
                    .clipShape(Capsule())
            }
            .padding(.bottom, Spacing.md)

            Text(booking.serviceName)
                .font(.jakarta(19, weight: .heavy))
                .foregroundColor(.white)
                .padding(.bottom, 4)

            Text(booking.formattedDateTime)
                .font(.jakarta(13.5, weight: .medium))
                .foregroundColor(Color(hex: "D9D3FA"))
        }
        .padding(Spacing.lg)
        .background(
            LinearGradient(
                colors: [Color.bePrimary, Color.bePrimary700],
                startPoint: .topLeading,
                endPoint: .bottomTrailing
            )
        )
        .clipShape(RoundedRectangle(cornerRadius: Radius.xl))
        .shadow(color: Color.bePrimary.opacity(0.32), radius: 14, x: 0, y: 12)
    }

    // MARK: - Popular Services

    private var popularSection: some View {
        VStack(alignment: .leading, spacing: Spacing.md) {
            HStack {
                Text(AppStrings.Home.popularServicesTitle)
                    .font(.jakarta(17, weight: .heavy))
                    .foregroundColor(.beInk800)
                Spacer()
                NavigationLink(destination: ServiceListView()) {
                    Text(AppStrings.Common.seeAll)
                        .font(.jakarta(13.5, weight: .bold))
                        .foregroundColor(.bePrimary)
                }
            }
            .padding(.horizontal, Spacing.lg)

            HStack(spacing: Spacing.md) {
                ForEach(Array(vm.popularServices.prefix(2))) { service in
                    NavigationLink(destination: ServiceDetailView(service: service)) {
                        PopularServiceCard(service: service)
                    }
                    .buttonStyle(.plain)
                }
            }
            .padding(.horizontal, Spacing.lg)
        }
    }

    // MARK: - Marketing Links

    private var marketingLinksSection: some View {
        VStack(alignment: .leading, spacing: Spacing.md) {
            HStack(spacing: 0) {
                NavigationLink(destination: HowItWorksView()) {
                    Text(AppStrings.Home.howItWorksLink)
                        .font(.jakarta(14, weight: .semibold))
                        .foregroundColor(.bePrimary)
                }
                .buttonStyle(.plain)
                .accessibilityLabel(AppStrings.Home.howItWorksLink)

                Spacer()

                NavigationLink(destination: PricingView()) {
                    Text(AppStrings.Home.pricingLink)
                        .font(.jakarta(14, weight: .semibold))
                        .foregroundColor(.bePrimary)
                }
                .buttonStyle(.plain)
                .accessibilityLabel(AppStrings.Home.pricingLink)

                Spacer()

                NavigationLink(destination: ContactView()) {
                    Text(AppStrings.Home.contactLink)
                        .font(.jakarta(14, weight: .semibold))
                        .foregroundColor(.bePrimary)
                }
                .buttonStyle(.plain)
                .accessibilityLabel(AppStrings.Home.contactLink)

                Spacer()

                NavigationLink(destination: LoginView()) {
                    Text(AppStrings.Home.loginLink)
                        .font(.jakarta(14, weight: .semibold))
                        .foregroundColor(.bePrimary)
                }
                .buttonStyle(.plain)
                .accessibilityLabel(AppStrings.Home.loginLink)
            }
            .padding(Spacing.lg)
            .background(Color.white)
            .clipShape(RoundedRectangle(cornerRadius: Radius.xl))
            .overlay(RoundedRectangle(cornerRadius: Radius.xl).stroke(Color.beBorder100, lineWidth: 1))
        }
    }

    // MARK: - Categories

    private var categoriesSection: some View {
        VStack(alignment: .leading, spacing: Spacing.md) {
            Text(AppStrings.Home.categoriesTitle)
                .font(.jakarta(17, weight: .heavy))
                .foregroundColor(.beInk800)
                .padding(.horizontal, Spacing.lg)

            LazyVGrid(
                columns: [GridItem(.flexible(), spacing: Spacing.md), GridItem(.flexible(), spacing: Spacing.md)],
                spacing: Spacing.md
            ) {
                ForEach(Array(vm.categories.enumerated()), id: \.element.id) { index, category in
                    NavigationLink(destination: ServiceListView(filterCategory: category.name)) {
                        CategoryTile(
                            category: category,
                            iconColor: categoryIconColors[index % categoryIconColors.count],
                            bgColor: categoryBgColors[index % categoryBgColors.count]
                        )
                    }
                    .buttonStyle(.plain)
                }
            }
            .padding(.horizontal, Spacing.lg)
        }
    }
}

// MARK: - Popular Service Card

struct PopularServiceCard: View {
    let service: Service

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            ServiceThumbnail(imageUrl: service.imageUrl, size: 140)
                .frame(maxWidth: .infinity)
                .frame(height: 84)
                .clipped()

            VStack(alignment: .leading, spacing: 3) {
                Text(service.name)
                    .font(.jakarta(14, weight: .bold))
                    .foregroundColor(.beInk700)
                    .lineLimit(1)

                Text("\(service.formattedDuration) · \(service.formattedPrice)")
                    .font(.jakarta(12, weight: .medium))
                    .foregroundColor(.beMuted500)
            }
            .padding(.horizontal, 13)
            .padding(.vertical, 11)
        }
        .frame(maxWidth: .infinity)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: Radius.base))
        .overlay(RoundedRectangle(cornerRadius: Radius.base).stroke(Color.beBorder100, lineWidth: 1))
    }
}

// MARK: - Category Tile

struct CategoryTile: View {
    let category: BookCategory
    let iconColor: Color
    let bgColor: Color

    var body: some View {
        HStack(spacing: 11) {
            Image(systemName: category.iconSystemName)
                .font(.system(size: 18, weight: .semibold))
                .foregroundColor(iconColor)
                .frame(width: 36, height: 36)
                .background(bgColor)
                .clipShape(RoundedRectangle(cornerRadius: 10))

            Text(category.name)
                .font(.jakarta(14, weight: .bold))
                .foregroundColor(.beInk700)

            Spacer()
        }
        .padding(15)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: Radius.lg))
        .overlay(RoundedRectangle(cornerRadius: Radius.lg).stroke(Color.beBorder100, lineWidth: 1))
    }
}

#Preview {
    NavigationStack {
        HomeView()
    }
}
