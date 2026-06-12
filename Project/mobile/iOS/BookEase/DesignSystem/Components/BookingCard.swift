import SwiftUI

struct BookingCard: View {
    let booking: Booking

    var body: some View {
        HStack(spacing: Spacing.md) {
            DateChip(
                month: booking.formattedMonth,
                day: booking.formattedDay,
                status: booking.status
            )

            VStack(alignment: .leading, spacing: 4) {
                Text(booking.serviceName)
                    .font(.jakarta(15, weight: .bold))
                    .foregroundColor(.beInk700)
                    .lineLimit(2)

                HStack(spacing: Spacing.xs) {
                    Image(systemName: "clock")
                        .font(.system(size: 11))
                        .foregroundColor(.beMuted500)
                    Text(booking.formattedTime)
                        .font(.beCaption)
                        .foregroundColor(.beMuted500)

                    Text("·")
                        .foregroundColor(.beMuted500)

                    Image(systemName: "person.fill")
                        .font(.system(size: 11))
                        .foregroundColor(.beMuted500)
                    Text(booking.coachName)
                        .font(.beCaption)
                        .foregroundColor(.beMuted500)
                }
            }

            Spacer()

            StatusBadge(status: booking.status)
        }
        .padding(Spacing.base)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: Radius.lg))
        .overlay(
            RoundedRectangle(cornerRadius: Radius.lg)
                .stroke(Color.beBorder100, lineWidth: 1)
        )
    }
}

#Preview {
    VStack(spacing: 12) {
        BookingCard(booking: MockData.bookings[0])
        BookingCard(booking: MockData.bookings[1])
        BookingCard(booking: MockData.bookings[2])
    }
    .padding()
    .background(Color.beAppBg)
}
