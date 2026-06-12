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
            Text("Pricing")
                .font(.jakarta(26, weight: .heavy))
                .foregroundColor(.beInk800)
            Text("Start free or save with a plan.")
                .font(.jakarta(13.5, weight: .medium))
                .foregroundColor(.beMuted500)
        }
    }

    // MARK: - Billing Toggle

    private var billingToggle: some View {
        HStack(spacing: 4) {
            toggleTab(title: "Monthly", isActive: !isYearly) {
                isYearly = false
            }
            toggleTab(title: "Yearly · -20%", isActive: isYearly) {
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
            Text("Starter")
                .font(.jakarta(14, weight: .bold))
                .foregroundColor(.beMuted500)
                .padding(.bottom, Spacing.sm)

            Text("$0")
                .font(.jakarta(34, weight: .heavy))
                .foregroundColor(.beInk800)
                .tracking(-0.02 * 34)

            Text("Pay per session")
                .font(.jakarta(12.5, weight: .medium))
                .foregroundColor(.beMuted500)
                .padding(.top, 2)
                .padding(.bottom, Spacing.base)

            PlanFeatureList(features: ["Book any service", "Reminders & calendar sync", "In-app payments"])
                .padding(.bottom, Spacing.base)

            Button {} label: {
                Text("Get started")
                    .font(.jakarta(14.5, weight: .bold))
                    .foregroundColor(.beInk800)
                    .frame(maxWidth: .infinity)
                    .frame(height: 48)
                    .background(Color.beSurface200)
                    .clipShape(RoundedRectangle(cornerRadius: 12))
            }
            .accessibilityLabel("Get started with Starter plan")
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
            Text("Pro")
                .font(.jakarta(14, weight: .bold))
                .foregroundColor(.bePrimary)
                .padding(.bottom, Spacing.sm)

            HStack(alignment: .lastTextBaseline, spacing: 3) {
                Text(isYearly ? "$39" : "$49")
                    .font(.jakarta(34, weight: .heavy))
                    .foregroundColor(.beInk800)
                    .tracking(-0.02 * 34)
                Text("/mo")
                    .font(.jakarta(15, weight: .medium))
                    .foregroundColor(.beMuted500)
            }

            Text("8 sessions / month")
                .font(.jakarta(12.5, weight: .medium))
                .foregroundColor(.beMuted500)
                .padding(.top, 2)
                .padding(.bottom, Spacing.base)

            PlanFeatureList(features: [
                "Everything in Starter",
                "Priority slots",
                "Free rescheduling",
                "Progress tracking"
            ])
            .padding(.bottom, Spacing.base)

            Button {} label: {
                Text("Choose Pro")
                    .font(.jakarta(14.5, weight: .bold))
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .frame(height: 48)
                    .background(Color.bePrimary)
                    .clipShape(RoundedRectangle(cornerRadius: 12))
                    .beShadowBtn()
            }
            .accessibilityLabel("Choose Pro plan, \(isYearly ? "$39" : "$49") per month")
        }
        .padding(.horizontal, 22)
        .padding(.top, 34)
        .padding(.bottom, 22)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: Radius.xl))
        .overlay(RoundedRectangle(cornerRadius: Radius.xl).stroke(Color.bePrimary, lineWidth: 2))
        .shadow(color: Color.bePrimary.opacity(0.16), radius: 16, x: 0, y: 14)
        .overlay(alignment: .topLeading) {
            Text("MOST POPULAR")
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
            Text("Elite")
                .font(.jakarta(14, weight: .bold))
                .foregroundColor(.beMuted500)
                .padding(.bottom, Spacing.sm)

            HStack(alignment: .lastTextBaseline, spacing: 3) {
                Text(isYearly ? "$79" : "$99")
                    .font(.jakarta(34, weight: .heavy))
                    .foregroundColor(.beInk800)
                    .tracking(-0.02 * 34)
                Text("/mo")
                    .font(.jakarta(15, weight: .medium))
                    .foregroundColor(.beMuted500)
            }

            Text("Unlimited sessions")
                .font(.jakarta(12.5, weight: .medium))
                .foregroundColor(.beMuted500)
                .padding(.top, 2)
                .padding(.bottom, Spacing.base)

            PlanFeatureList(features: [
                "Everything in Pro",
                "Dedicated coach",
                "Custom nutrition plan"
            ])
            .padding(.bottom, Spacing.base)

            Button {} label: {
                Text("Choose Elite")
                    .font(.jakarta(14.5, weight: .bold))
                    .foregroundColor(.beInk800)
                    .frame(maxWidth: .infinity)
                    .frame(height: 48)
                    .background(Color.beSurface200)
                    .clipShape(RoundedRectangle(cornerRadius: 12))
            }
            .accessibilityLabel("Choose Elite plan, \(isYearly ? "$79" : "$99") per month")
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
