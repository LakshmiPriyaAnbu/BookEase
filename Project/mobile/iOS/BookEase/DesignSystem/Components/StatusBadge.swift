import SwiftUI

struct StatusBadge: View {
    let status: BookingStatus

    var body: some View {
        Text(status.label)
            .font(.beCaption)
            .foregroundColor(status.foregroundColor)
            .padding(.horizontal, Spacing.md)
            .padding(.vertical, 5)
            .background(status.backgroundColor)
            .clipShape(Capsule())
    }
}

#Preview {
    HStack(spacing: 8) {
        StatusBadge(status: .confirmed)
        StatusBadge(status: .pending)
        StatusBadge(status: .completed)
        StatusBadge(status: .cancelled)
    }
    .padding()
}
