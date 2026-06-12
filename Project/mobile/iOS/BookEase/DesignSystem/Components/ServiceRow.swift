import SwiftUI

struct ServiceRow: View {
    let service: Service
    var onBook: () -> Void = {}

    var body: some View {
        HStack(spacing: Spacing.md) {
            // Thumbnail
            ServiceThumbnail(imageUrl: service.imageUrl, size: 64)

            // Info
            VStack(alignment: .leading, spacing: 4) {
                Text(service.name)
                    .font(.jakarta(15, weight: .bold))
                    .foregroundColor(.beInk700)
                    .lineLimit(2)

                Text("\(service.formattedDuration) · ★ \(String(format: "%.1f", service.rating))")
                    .font(.jakarta(12.5, weight: .medium))
                    .foregroundColor(.beMuted500)
            }

            Spacer()

            // Price + Book
            VStack(alignment: .trailing, spacing: 6) {
                Text(service.formattedPrice)
                    .font(.jakarta(16, weight: .heavy))
                    .foregroundColor(.bePrimary)

                Button(action: onBook) {
                    Text(AppStrings.Common.book)
                        .font(.jakarta(12.5, weight: .bold))
                        .foregroundColor(Color(hex: "4A3BD0"))
                        .padding(.horizontal, 14)
                        .padding(.vertical, 7)
                        .background(Color.bePrimarySoft)
                        .clipShape(RoundedRectangle(cornerRadius: 9))
                }
            }
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

struct ServiceThumbnail: View {
    let imageUrl: String?
    let size: CGFloat

    var body: some View {
        Group {
            if let url = imageUrl, let parsed = URL(string: url) {
                AsyncImage(url: parsed) { phase in
                    switch phase {
                    case .success(let img):
                        img.resizable().scaledToFill()
                    default:
                        placeholderGradient
                    }
                }
            } else {
                placeholderGradient
            }
        }
        .frame(width: size, height: size)
        .clipShape(RoundedRectangle(cornerRadius: Radius.lg))
    }

    private var placeholderGradient: some View {
        LinearGradient(
            colors: [Color.bePrimarySoft, Color.bePrimary.opacity(0.15)],
            startPoint: .topLeading,
            endPoint: .bottomTrailing
        )
        .overlay(
            Image(systemName: "figure.strengthtraining.traditional")
                .foregroundColor(.bePrimary.opacity(0.5))
                .font(.system(size: size * 0.4))
        )
    }
}

#Preview {
    ServiceRow(service: MockData.services[0])
        .padding()
}
