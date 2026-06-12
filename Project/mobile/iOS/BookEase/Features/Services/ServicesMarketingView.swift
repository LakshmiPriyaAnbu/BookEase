import SwiftUI

struct ServicesMarketingView: View {
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 0) {
                headerSection
                    .padding(.horizontal, Spacing.lg)
                    .padding(.top, Spacing.lg)

                searchBarRow
                    .padding(.horizontal, Spacing.lg)
                    .padding(.top, Spacing.md)
                    .padding(.bottom, 22)

                browseByCategorySection
                    .padding(.bottom, Spacing.xl)

                featuredSection
                    .padding(.bottom, Spacing.xxl)
            }
        }
        .background(Color.beAppBg)
        .navigationBarHidden(true)
    }

    // MARK: - Header

    private var headerSection: some View {
        VStack(alignment: .leading, spacing: 3) {
            Text(AppStrings.Services.title)
                .font(.jakarta(26, weight: .heavy))
                .foregroundColor(.beInk800)
            Text(AppStrings.Services.marketingSubtitle)
                .font(.jakarta(13.5, weight: .medium))
                .foregroundColor(.beMuted500)
        }
    }

    // MARK: - Search Bar

    private var searchBarRow: some View {
        HStack(spacing: 10) {
            Image(systemName: "magnifyingglass")
                .font(.system(size: 17))
                .foregroundColor(.beMuted500)
                .accessibilityHidden(true)
            Text(AppStrings.Services.searchServicesOrCoaches)
                .font(.jakarta(14.5, weight: .medium))
                .foregroundColor(.beMuted500)
            Spacer()
        }
        .frame(height: 46)
        .padding(.horizontal, Spacing.base)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 13))
        .overlay(RoundedRectangle(cornerRadius: 13).stroke(Color.beBorder100, lineWidth: 1))
        .accessibilityLabel(AppStrings.Services.searchServicesOrCoaches)
    }

    // MARK: - Browse By Category

    private var browseByCategorySection: some View {
        VStack(alignment: .leading, spacing: Spacing.md) {
            Text(AppStrings.Services.browseByCategoryTitle)
                .font(.jakarta(16, weight: .heavy))
                .foregroundColor(.beInk800)
                .padding(.horizontal, Spacing.lg)

            LazyVGrid(
                columns: [GridItem(.flexible()), GridItem(.flexible())],
                spacing: 12
            ) {
                ForEach(MarketingCategory.allCases) { category in
                    MarketingCategoryCard(category: category)
                }
            }
            .padding(.horizontal, Spacing.lg)
        }
    }

    // MARK: - Featured

    private var featuredSection: some View {
        VStack(alignment: .leading, spacing: Spacing.md) {
            Text(AppStrings.Services.featuredTitle)
                .font(.jakarta(16, weight: .heavy))
                .foregroundColor(.beInk800)
                .padding(.horizontal, Spacing.lg)

            VStack(spacing: 13) {
                FeaturedServiceRow(
                    title: AppStrings.FeaturedService.strengthCoachingTitle,
                    duration: AppStrings.FeaturedService.strengthCoachingDuration,
                    rating: AppStrings.FeaturedService.strengthCoachingRating,
                    price: AppStrings.FeaturedService.strengthCoachingPrice
                )
                FeaturedServiceRow(
                    title: AppStrings.FeaturedService.nutritionConsultTitle,
                    duration: AppStrings.FeaturedService.nutritionConsultDuration,
                    rating: AppStrings.FeaturedService.nutritionConsultRating,
                    price: AppStrings.FeaturedService.nutritionConsultPrice
                )
            }
            .padding(.horizontal, Spacing.lg)
        }
    }
}

// MARK: - Marketing Category Card

private struct MarketingCategoryCard: View {
    let category: MarketingCategory

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            HStack(spacing: 0) {
                ZStack {
                    RoundedRectangle(cornerRadius: Radius.md)
                        .fill(category.iconBgColor)
                        .frame(width: 40, height: 40)
                    category.icon
                }
                Spacer()
            }
            .padding(.bottom, 10)

            Text(category.title)
                .font(.jakarta(15, weight: .bold))
                .foregroundColor(.beInk700)
            Text(category.countLabel)
                .font(.jakarta(12.5, weight: .medium))
                .foregroundColor(.beMuted500)
                .padding(.top, 2)
        }
        .padding(Spacing.base)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 16))
        .overlay(RoundedRectangle(cornerRadius: 16).stroke(Color.beBorder100, lineWidth: 1))
        .accessibilityElement(children: .combine)
        .accessibilityLabel("\(category.title), \(category.countLabel)")
    }
}

// MARK: - Featured Service Row

private struct FeaturedServiceRow: View {
    let title: String
    let duration: String
    let rating: String
    let price: String

    var body: some View {
        HStack(spacing: 13) {
            RoundedRectangle(cornerRadius: 12)
                .fill(Color.beSurface200)
                .frame(width: 64, height: 64)
                .accessibilityHidden(true)

            VStack(alignment: .leading, spacing: 0) {
                Text(title)
                    .font(.jakarta(15, weight: .bold))
                    .foregroundColor(.beInk700)
                Text("\(duration) · \u{2605} \(rating)")
                    .font(.jakarta(12.5, weight: .medium))
                    .foregroundColor(.beMuted500)
                    .padding(.top, 3)
                    .padding(.bottom, Spacing.sm)

                HStack {
                    Text(price)
                        .font(.jakarta(16, weight: .heavy))
                        .foregroundColor(.bePrimary)
                    Spacer()
                    Button {} label: {
                        Text(AppStrings.Common.book)
                            .font(.jakarta(12.5, weight: .bold))
                            .foregroundColor(.bePrimary700)
                            .padding(.horizontal, 14)
                            .padding(.vertical, 7)
                            .background(Color.bePrimarySoft)
                            .clipShape(RoundedRectangle(cornerRadius: 9))
                    }
                    .accessibilityLabel("\(AppStrings.Common.book) \(title)")
                }
            }
        }
        .padding(Spacing.md)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 16))
        .overlay(RoundedRectangle(cornerRadius: 16).stroke(Color.beBorder100, lineWidth: 1))
    }
}

// MARK: - Marketing Category Model

private enum MarketingCategory: String, CaseIterable, Identifiable {
    case strength, hiit, mobility, nutrition

    var id: String { rawValue }

    var title: String {
        switch self {
        case .strength:  return AppStrings.MarketingCategory.strengthTitle
        case .hiit:      return AppStrings.MarketingCategory.hiitTitle
        case .mobility:  return AppStrings.MarketingCategory.mobilityTitle
        case .nutrition: return AppStrings.MarketingCategory.nutritionTitle
        }
    }

    var countLabel: String {
        switch self {
        case .strength:  return AppStrings.MarketingCategory.strengthCount
        case .hiit:      return AppStrings.MarketingCategory.hiitCount
        case .mobility:  return AppStrings.MarketingCategory.mobilityCount
        case .nutrition: return AppStrings.MarketingCategory.nutritionCount
        }
    }

    var iconBgColor: Color {
        switch self {
        case .strength:  return .bePrimarySoft
        case .hiit:      return .beSuccessBg
        case .mobility:  return .beInfoBg
        case .nutrition: return .beWarningBg
        }
    }

    var iconColor: Color {
        switch self {
        case .strength:  return .bePrimary
        case .hiit:      return .beSuccess
        case .mobility:  return .beInfo
        case .nutrition: return .beWarning
        }
    }

    var icon: some View {
        Group {
            switch self {
            case .strength:
                Image(systemName: "bolt.fill")
                    .font(.system(size: 18, weight: .semibold))
                    .foregroundColor(iconColor)
            case .hiit:
                Image(systemName: "waveform.path.ecg")
                    .font(.system(size: 18, weight: .semibold))
                    .foregroundColor(iconColor)
            case .mobility:
                Image(systemName: "figure.mind.and.body")
                    .font(.system(size: 18, weight: .semibold))
                    .foregroundColor(iconColor)
            case .nutrition:
                Image(systemName: "flame")
                    .font(.system(size: 18, weight: .semibold))
                    .foregroundColor(iconColor)
            }
        }
    }
}

#Preview {
    NavigationStack {
        ServicesMarketingView()
    }
}
