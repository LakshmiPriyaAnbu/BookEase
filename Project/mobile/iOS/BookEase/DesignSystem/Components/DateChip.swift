import SwiftUI

struct DateChip: View {
    let month: String
    let day: String
    let status: BookingStatus

    var body: some View {
        VStack(spacing: 2) {
            Text(month)
                .font(.beCaption)
                .foregroundColor(status.dateBadgeFg)
            Text(day)
                .font(.jakarta(18, weight: .heavy))
                .foregroundColor(status.dateBadgeFg)
        }
        .frame(width: 50, height: 50)
        .background(status.dateBadgeBg)
        .clipShape(RoundedRectangle(cornerRadius: Radius.lg))
    }
}

#Preview {
    HStack(spacing: 12) {
        DateChip(month: "JUN", day: "14", status: .confirmed)
        DateChip(month: "JUN", day: "18", status: .pending)
        DateChip(month: "MAY", day: "30", status: .completed)
        DateChip(month: "MAY", day: "12", status: .cancelled)
    }
    .padding()
}
