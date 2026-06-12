import SwiftUI

struct SearchBar: View {
    @Binding var text: String
    var placeholder: String = "Search services…"

    var body: some View {
        HStack(spacing: Spacing.sm) {
            Image(systemName: "magnifyingglass")
                .foregroundColor(.beMuted500)
                .font(.system(size: 16, weight: .medium))

            TextField(placeholder, text: $text)
                .font(.beBodySm)
                .foregroundColor(.beInk700)
        }
        .padding(.horizontal, Spacing.base)
        .frame(height: 46)
        .background(Color.beSurface150)
        .clipShape(RoundedRectangle(cornerRadius: Radius.md))
    }
}

#Preview {
    SearchBar(text: .constant(""))
        .padding()
}
