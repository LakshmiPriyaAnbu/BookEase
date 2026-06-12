import SwiftUI

struct PricingView: View {
    @State private var isYearly = false

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 0) {
                headerSection
                    .padding(.horizontal, Spacing.lg)
                    .padding(.top, Spacing.lg)

                billingToggle
                    .padding(.horizontal, Spacing.lg)
                    .padding(.top, Spacing.base)
                    .padding(.bottom, Spacing.lg)

                plansSection
                    .padding(.horizontal, Spacing.lg)
                    .padding(.bottom, Spacing.xxl)
            }
        }
        .background(Color.beAppBg)
        .navigationBarHidden(true)
    }

    // MARK: - Header

    private var headerSection: some View {
        VStack(alignment: .leading, spacing: 3) {
            Text(AppStrings.Pricing.title)
                .font(.jakarta(26, weight: .heavy))
                .foregroundColor(.beInk800)
            Text(AppStrings.Pricing.subtitle)
                .font(.jakarta(13.5, weight: .medium))
                .foregroundColor(.beMuted500)
        }
    }

    // MARK: - Billing Toggle

    private var billingToggle: some View {
        HStack(spacing: 4) {
            toggleTab(title: AppStrings.Pricing.monthly, isActive: !isYearly) {
                isYearly = false
            }
            toggleTab(title: AppStrings.Pricing.yearlyWithDiscount, isActive: isYearly) {
                isYearly = true
            }
        }
        .padding(4)
        .background(Color(hex: "ECECEF"))
        .clipShape(RoundedRectangle(cornerRadius: 12))
        .accessibilityElement(children: .contain)
    }

    private func toggleTab(title: String, isActive: Bool, action: @escaping () -> Void) -> some View {
        Button(action: action) {
            Text(title)
                .font(.jakarta(13.5, weight: isActive ? .bold : .semibold))
                .foregroundColor(isActive ? .beInk800 : .beMuted500)
                .frame(maxWidth: .infinity)
                .frame(height: 36)
                .background(
                    isActive
                    ? Color.white.shadow(.drop(color: Color.black.opacity(0.06), radius: 4, x: 0, y: 2))
                    : Color.clear
                )
                .clipShape(RoundedRectangle(cornerRadius: 9))
        }
        .accessibilityLabel(title)
        .accessibilityAddTraits(isActive ? .isSelected : [])
    }

    // MARK: - Plans

    private var plansSection: some View {
        VStack(spacing: 14) {
            StarterPlanCard()
            ProPlanCard(isYearly: isYearly)
            ElitePlanCard(isYearly: isYearly)
        }
    }
}

// MARK: - Starter Plan Card

private struct StarterPlanCard: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text(AppStrings.Pricing.starterPlanName)
                .font(.jakarta(14, weight: .bold))
                .foregroundColor(.beMuted500)
                .padding(.bottom, Spacing.sm)

            Text(AppStrings.Pricing.starterPlanPrice)
                .font(.jakarta(34, weight: .heavy))
                .foregroundColor(.beInk800)
                .tracking(-0.02 * 34)

            Text(AppStrings.Pricing.starterPlanBillingCycle)
                .font(.jakarta(12.5, weight: .medium))
                .foregroundColor(.beMuted500)
                .padding(.top, 2)
                .padding(.bottom, Spacing.base)

            PlanFeatureList(features: [
                AppStrings.Pricing.starterFeature1,
                AppStrings.Pricing.starterFeature2,
                AppStrings.Pricing.starterFeature3
            ])
            .padding(.bottom, Spacing.base)

            Button {} label: {
                Text(AppStrings.Pricing.starterCTAButton)
                    .font(.jakarta(14.5, weight: .bold))
                    .foregroundColor(.beInk800)
                    .frame(maxWidth: .infinity)
                    .frame(height: 48)
                    .background(Color.beSurface200)
                    .clipShape(RoundedRectangle(cornerRadius: 12))
            }
            .accessibilityLabel(AppStrings.Pricing.starterCTAAccessibilityLabel)
        }
        .padding(22)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: Radius.xl))
        .overlay(RoundedRectangle(cornerRadius: Radius.xl).stroke(Color.beBorder100, lineWidth: 1))
    }
}

// MARK: - Pro Plan Card

private struct ProPlanCard: View {
    let isYearly: Bool

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text(AppStrings.Pricing.proPlanName)
                .font(.jakarta(14, weight: .bold))
                .foregroundColor(.bePrimary)
                .padding(.bottom, Spacing.sm)

            HStack(alignment: .lastTextBaseline, spacing: 3) {
                Text(isYearly ? AppStrings.Pricing.proPlanPricingYearly : AppStrings.Pricing.proPlanPricingMonthly)
                    .font(.jakarta(34, weight: .heavy))
                    .foregroundColor(.beInk800)
                    .tracking(-0.02 * 34)
                Text(AppStrings.Pricing.proPlanPerMonth)
                    .font(.jakarta(15, weight: .medium))
                    .foregroundColor(.beMuted500)
            }

            Text(AppStrings.Pricing.proPlanBillingCycle)
                .font(.jakarta(12.5, weight: .medium))
                .foregroundColor(.beMuted500)
                .padding(.top, 2)
                .padding(.bottom, Spacing.base)

            PlanFeatureList(features: [
                AppStrings.Pricing.proFeature1,
                AppStrings.Pricing.proFeature2,
                AppStrings.Pricing.proFeature3,
                AppStrings.Pricing.proFeature4
            ])
            .padding(.bottom, Spacing.base)

            Button {} label: {
                Text(AppStrings.Pricing.proCTAButton)
                    .font(.jakarta(14.5, weight: .bold))
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .frame(height: 48)
                    .background(Color.bePrimary)
                    .clipShape(RoundedRectangle(cornerRadius: 12))
                    .beShadowBtn()
            }
            .accessibilityLabel("\(AppStrings.Pricing.proCTAButton) plan, \(isYearly ? AppStrings.Pricing.proPlanPricingYearly : AppStrings.Pricing.proPlanPricingMonthly) per month")
        }
        .padding(.horizontal, 22)
        .padding(.top, 34)
        .padding(.bottom, 22)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: Radius.xl))
        .overlay(RoundedRectangle(cornerRadius: Radius.xl).stroke(Color.bePrimary, lineWidth: 2))
        .shadow(color: Color.bePrimary.opacity(0.16), radius: 16, x: 0, y: 14)
        .overlay(alignment: .topLeading) {
            Text(AppStrings.Pricing.mostPopularBadge)
                .font(.jakarta(11, weight: .bold))
                .foregroundColor(.white)
                .padding(.horizontal, 11)
                .padding(.vertical, 5)
                .background(Color.bePrimary)
                .clipShape(Capsule())
                .offset(x: 22, y: -12)
        }
    }
}

// MARK: - Elite Plan Card

private struct ElitePlanCard: View {
    let isYearly: Bool

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text(AppStrings.Pricing.elitePlanName)
                .font(.jakarta(14, weight: .bold))
                .foregroundColor(.beMuted500)
                .padding(.bottom, Spacing.sm)

            HStack(alignment: .lastTextBaseline, spacing: 3) {
                Text(isYearly ? AppStrings.Pricing.elitePlanPricingYearly : AppStrings.Pricing.elitePlanPricingMonthly)
                    .font(.jakarta(34, weight: .heavy))
                    .foregroundColor(.beInk800)
                    .tracking(-0.02 * 34)
                Text(AppStrings.Pricing.proPlanPerMonth)
                    .font(.jakarta(15, weight: .medium))
                    .foregroundColor(.beMuted500)
            }

            Text(AppStrings.Pricing.elitePlanBillingCycle)
                .font(.jakarta(12.5, weight: .medium))
                .foregroundColor(.beMuted500)
                .padding(.top, 2)
                .padding(.bottom, Spacing.base)

            PlanFeatureList(features: [
                AppStrings.Pricing.eliteFeature1,
                AppStrings.Pricing.eliteFeature2,
                AppStrings.Pricing.eliteFeature3
            ])
            .padding(.bottom, Spacing.base)

            Button {} label: {
                Text(AppStrings.Pricing.eliteCTAButton)
                    .font(.jakarta(14.5, weight: .bold))
                    .foregroundColor(.beInk800)
                    .frame(maxWidth: .infinity)
                    .frame(height: 48)
                    .background(Color.beSurface200)
                    .clipShape(RoundedRectangle(cornerRadius: 12))
            }
            .accessibilityLabel("\(AppStrings.Pricing.eliteCTAButton) plan, \(isYearly ? AppStrings.Pricing.elitePlanPricingYearly : AppStrings.Pricing.elitePlanPricingMonthly) per month")
        }
        .padding(22)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: Radius.xl))
        .overlay(RoundedRectangle(cornerRadius: Radius.xl).stroke(Color.beBorder100, lineWidth: 1))
    }
}

// MARK: - Plan Feature List

private struct PlanFeatureList: View {
    let features: [String]

    var body: some View {
        VStack(alignment: .leading, spacing: 9) {
            ForEach(features, id: \.self) { feature in
                HStack(spacing: Spacing.sm) {
                    Image(systemName: "checkmark")
                        .font(.system(size: 12, weight: .bold))
                        .foregroundColor(.beSuccess)
                        .accessibilityHidden(true)
                    Text(feature)
                        .font(.jakarta(13.5, weight: .medium))
                        .foregroundColor(.beText600)
                }
            }
        }
    }
}

#Preview {
    NavigationStack {
        PricingView()
    }
}
