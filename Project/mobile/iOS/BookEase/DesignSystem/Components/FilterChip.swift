import SwiftUI

struct FilterChip: View {
    let title: String
    let isSelected: Bool
    var action: () -> Void = {}

    var body: some View {
        Button(action: action) {
            Text(title)
                .font(.beLabel)
                .foregroundColor(isSelected ? .bePrimary : .beText600)
                .padding(.horizontal, Spacing.base)
                .padding(.vertical, Spacing.sm)
                .background(isSelected ? Color.bePrimarySoft : Color.beSurface200)
                .clipShape(Capsule())
                .overlay(
                    Capsule()
                        .stroke(isSelected ? Color.bePrimary : Color.clear, lineWidth: 1.5)
                )
        }
    }
}

struct FilterChipBar: View {
    let categories: [String]
    @Binding var selected: String

    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(spacing: Spacing.sm) {
                ForEach(categories, id: \.self) { cat in
                    FilterChip(title: cat, isSelected: selected == cat) {
                        selected = cat
                    }
                }
            }
            .padding(.horizontal, Spacing.lg)
        }
    }
}

#Preview {
    FilterChipBar(categories: ["All", "Strength", "HIIT", "Mobility"], selected: .constant("All"))
        .padding(.vertical)
}
