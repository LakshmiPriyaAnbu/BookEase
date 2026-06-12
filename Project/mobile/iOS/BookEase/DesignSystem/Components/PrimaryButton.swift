import SwiftUI

struct PrimaryButton: View {
    let title: String
    var action: () -> Void = {}
    var isEnabled: Bool = true

    var body: some View {
        Button(action: action) {
            Text(title)
                .font(.jakarta(16, weight: .bold))
                .foregroundColor(.white)
                .frame(maxWidth: .infinity)
                .frame(height: 54)
                .background(isEnabled ? Color.bePrimary : Color.beMuted400)
                .clipShape(RoundedRectangle(cornerRadius: Radius.md))
                .beShadowBtn()
        }
        .disabled(!isEnabled)
    }
}

struct SecondaryButton: View {
    let title: String
    var action: () -> Void = {}

    var body: some View {
        Button(action: action) {
            Text(title)
                .font(.jakarta(16, weight: .bold))
                .foregroundColor(.beInk700)
                .frame(maxWidth: .infinity)
                .frame(height: 54)
                .background(Color.beSurface200)
                .clipShape(RoundedRectangle(cornerRadius: Radius.md))
                .overlay(
                    RoundedRectangle(cornerRadius: Radius.md)
                        .stroke(Color.beBorder200, lineWidth: 1)
                )
        }
    }
}

struct DestructiveButton: View {
    let title: String
    var action: () -> Void = {}

    var body: some View {
        Button(action: action) {
            Text(title)
                .font(.jakarta(16, weight: .bold))
                .foregroundColor(.beDanger)
                .frame(maxWidth: .infinity)
                .frame(height: 54)
                .background(Color.white)
                .clipShape(RoundedRectangle(cornerRadius: Radius.md))
                .overlay(
                    RoundedRectangle(cornerRadius: Radius.md)
                        .stroke(Color.beBorder200, lineWidth: 1)
                )
        }
    }
}

#Preview {
    VStack(spacing: 16) {
        PrimaryButton(title: "Book Now")
        SecondaryButton(title: "Back to Home")
        DestructiveButton(title: "Log Out")
    }
    .padding()
}
