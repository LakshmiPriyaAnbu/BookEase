import SwiftUI

struct HowItWorksView: View {
    var body: some View {
        ZStack {
            Color.beAppBg.ignoresSafeArea()

            ScrollView {
                VStack(alignment: .leading, spacing: 0) {
                    headerSection
                        .padding(.horizontal, Spacing.lg)
                        .padding(.top, Spacing.sm)
                        .padding(.bottom, 22)

                    stepsSection
                        .padding(.horizontal, Spacing.lg)
                        .padding(.bottom, Spacing.xxl)

                    Color.clear.frame(height: 80)
                }
            }
            .safeAreaInset(edge: .bottom) {
                ctaBar
            }
        }
        .navigationBarHidden(true)
    }

    // MARK: - Header

    private var headerSection: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text("HOW IT WORKS")
                .font(.jakarta(12, weight: .heavy))
                .foregroundColor(.bePrimary)
                .tracking(1.2)
                .padding(.bottom, 6)

            Text("Booked in three steps")
                .font(.jakarta(26, weight: .heavy))
                .foregroundColor(.beInk800)
                .lineSpacing(26 * 0.1)

            Text("From browsing to a confirmed session in under a minute — no calls, no waiting.")
                .font(.jakarta(14, weight: .medium))
                .foregroundColor(.beMuted500)
                .lineSpacing(14 * 0.55)
                .padding(.top, Spacing.sm)
        }
    }

    // MARK: - Steps

    private var stepsSection: some View {
        VStack(spacing: 14) {
            ForEach(HowItWorksStep.allSteps) { step in
                StepCard(step: step)
            }
        }
    }

    // MARK: - CTA Bar

    private var ctaBar: some View {
        VStack(spacing: 0) {
            Divider()
                .background(Color.beBorder100)
            Button {} label: {
                Text("Get started")
                    .font(.jakarta(15.5, weight: .bold))
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .frame(height: 52)
                    .background(Color.bePrimary)
                    .clipShape(RoundedRectangle(cornerRadius: Radius.lg))
                    .beShadowBtn()
            }
            .accessibilityLabel("Get started")
            .padding(.horizontal, Spacing.lg)
            .padding(.top, 14)
            .padding(.bottom, 30)
        }
        .background(Color.white)
    }
}

// MARK: - Step Card

private struct StepCard: View {
    let step: HowItWorksStep

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            HStack(spacing: 14) {
                Text(step.number)
                    .font(.jakarta(19, weight: .heavy))
                    .foregroundColor(.white)
                    .frame(width: 44, height: 44)
                    .background(
                        Color.bePrimary
                            .shadow(.drop(color: Color.bePrimary.opacity(0.3), radius: 7, x: 0, y: 6))
                    )
                    .clipShape(RoundedRectangle(cornerRadius: 13))
                    .accessibilityHidden(true)

                Text(step.title)
                    .font(.jakarta(17, weight: .heavy))
                    .foregroundColor(.beInk800)
            }
            .padding(.bottom, Spacing.md)

            Text(step.description)
                .font(.jakarta(14, weight: .medium))
                .foregroundColor(.beText600)
                .lineSpacing(14 * 0.6)
        }
        .padding(22)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: Radius.xl))
        .overlay(RoundedRectangle(cornerRadius: Radius.xl).stroke(Color.beBorder100, lineWidth: 1))
        .accessibilityElement(children: .combine)
        .accessibilityLabel("Step \(step.number): \(step.title). \(step.description)")
    }
}

// MARK: - Step Model

private struct HowItWorksStep: Identifiable {
    let id: Int
    let number: String
    let title: String
    let description: String

    static let allSteps: [HowItWorksStep] = [
        HowItWorksStep(
            id: 1,
            number: "1",
            title: "Choose a service",
            description: "Browse coaches and programs, filter by goal and schedule. Every coach is verified and reviewed."
        ),
        HowItWorksStep(
            id: 2,
            number: "2",
            title: "Pick a time slot",
            description: "See live availability and lock the slot that fits your week. Reschedule free up to 12 hours before."
        ),
        HowItWorksStep(
            id: 3,
            number: "3",
            title: "Confirm & go",
            description: "Pay securely in-app and get instant confirmation plus a calendar invite. Just show up and train."
        )
    ]
}

#Preview {
    NavigationStack {
        HowItWorksView()
    }
}
