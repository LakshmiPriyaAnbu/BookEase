import SwiftUI

struct ConfirmationView: View {
    let booking: Booking
    @State private var showIcon = false
    @Environment(\.dismiss) private var dismiss

    var body: some View {
        ZStack(alignment: .bottom) {
            ScrollView {
                VStack(spacing: 0) {
                    Spacer(minLength: 30)

                    successIcon
                        .padding(.top, 24)
                        .padding(.bottom, 22)

                    Text("Booking confirmed")
                        .font(.jakarta(25, weight: .heavy))
                        .foregroundColor(.beInk800)
                        .padding(.bottom, 8)

                    Text("Your session is booked. We've sent the details to your email.")
                        .font(.jakarta(14.5, weight: .regular))
                        .foregroundColor(.beMuted500)
                        .multilineTextAlignment(.center)
                        .lineSpacing(3)
                        .padding(.horizontal, Spacing.xl)
                        .padding(.bottom, Spacing.xl)

                    detailCard
                        .padding(.horizontal, Spacing.lg)

                    Color.clear.frame(height: 120)
                }
                .frame(maxWidth: .infinity)
            }
            .background(Color.beAppBg)

            bottomButtons
        }
        .navigationBarHidden(true)
        .onAppear {
            withAnimation(.spring(response: 0.5, dampingFraction: 0.65).delay(0.2)) {
                showIcon = true
            }
        }
    }

    // MARK: - Success Icon

    private var successIcon: some View {
        ZStack {
            Circle()
                .fill(Color.beSuccessBg)
                .frame(width: 88, height: 88)

            Circle()
                .fill(Color.beSuccess)
                .frame(width: 60, height: 60)

            Image(systemName: "checkmark")
                .font(.system(size: 26, weight: .bold))
                .foregroundColor(.white)
        }
        .scaleEffect(showIcon ? 1 : 0.4)
        .opacity(showIcon ? 1 : 0)
    }

    // MARK: - Detail Card

    private var detailCard: some View {
        VStack(spacing: 0) {
            // Booking ID row
            HStack {
                Text("Booking ID")
                    .font(.jakarta(12.5, weight: .semibold))
                    .foregroundColor(.beMuted500)
                Spacer()
                Text("#\(booking.ref)")
                    .font(.jakarta(14, weight: .heavy))
                    .foregroundColor(.bePrimary)
            }
            .padding(.bottom, Spacing.md)

            dashedDivider
                .padding(.bottom, Spacing.md)

            detailRow(label: "Service",    value: booking.serviceName)
            detailRow(label: "Date & time", value: booking.formattedDateTime)
            detailRow(label: "Coach",       value: booking.coachName)
        }
        .padding(.horizontal, 22)
        .padding(.vertical, Spacing.lg)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: Radius.xl))
        .overlay(RoundedRectangle(cornerRadius: Radius.xl).stroke(Color.beBorder100, lineWidth: 1))
    }

    private func detailRow(label: String, value: String) -> some View {
        HStack {
            Text(label)
                .font(.jakarta(14, weight: .regular))
                .foregroundColor(.beMuted500)
            Spacer()
            Text(value)
                .font(.jakarta(14, weight: .bold))
                .foregroundColor(.beInk700)
                .multilineTextAlignment(.trailing)
        }
        .padding(.vertical, 6)
    }

    private var dashedDivider: some View {
        GeometryReader { geo in
            Path { path in
                path.move(to: CGPoint(x: 0, y: 0))
                path.addLine(to: CGPoint(x: geo.size.width, y: 0))
            }
            .stroke(style: StrokeStyle(lineWidth: 1, dash: [6, 4]))
            .foregroundColor(Color.beBorder200)
        }
        .frame(height: 1)
    }

    // MARK: - Bottom Buttons

    private var bottomButtons: some View {
        VStack(spacing: 10) {
            Button {} label: {
                Text("Add to Calendar")
                    .font(.jakarta(15, weight: .bold))
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .frame(height: 50)
                    .background(Color.bePrimary)
                    .clipShape(RoundedRectangle(cornerRadius: 13))
            }

            Button {
                dismiss()
            } label: {
                Text("Back to Home")
                    .font(.jakarta(15, weight: .bold))
                    .foregroundColor(.beInk700)
                    .frame(maxWidth: .infinity)
                    .frame(height: 50)
                    .background(Color(hex: "F2F2F5"))
                    .clipShape(RoundedRectangle(cornerRadius: 13))
            }
        }
        .padding(.horizontal, Spacing.lg)
        .padding(.vertical, Spacing.md)
        .padding(.bottom, 16)
        .background(
            Color.white
                .shadow(color: Color.black.opacity(0.08), radius: 12, x: 0, y: -4)
                .ignoresSafeArea()
        )
    }
}

#Preview {
    NavigationStack {
        ConfirmationView(booking: MockData.bookings[0])
    }
}
