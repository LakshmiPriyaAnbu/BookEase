import SwiftUI

struct FieldErrorLabel: View {
    let message: String

    var body: some View {
        HStack(spacing: 4) {
            Image(systemName: "exclamationmark.triangle.fill")
                .font(.system(size: 11, weight: .semibold))
                .foregroundColor(.beDangerText)
                .accessibilityHidden(true)
            Text(message)
                .font(.jakarta(12, weight: .medium))
                .foregroundColor(.beDangerText)
        }
        .accessibilityElement(children: .combine)
        .accessibilityLabel(message)
    }
}
