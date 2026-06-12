import SwiftUI

// MARK: - Toast Model

struct Toast: Identifiable, Equatable {
    let id = UUID()
    let title: String?
    let message: String
    let type: ToastType

    init(title: String? = nil, message: String, type: ToastType) {
        self.title = title
        self.message = message
        self.type = type
    }

    enum ToastType {
        case success, error, info, warning

        var accentColor: Color {
            switch self {
            case .success: return .beSuccess
            case .error:   return .beDanger
            case .info:    return .beInfo
            case .warning: return .beWarning
            }
        }

        var iconBgColor: Color {
            switch self {
            case .success: return .beSuccessBg
            case .error:   return .beDangerBg
            case .info:    return .beInfoBg
            case .warning: return .beWarningBg
            }
        }

        var textColor: Color {
            switch self {
            case .success: return .beSuccessText
            case .error:   return .beDangerText
            case .info:    return .beInfoText
            case .warning: return .beWarningText
            }
        }

        var icon: String {
            switch self {
            case .success: return "checkmark.circle.fill"
            case .error:   return "xmark.circle.fill"
            case .info:    return "info.circle.fill"
            case .warning: return "exclamationmark.triangle.fill"
            }
        }
    }
}

// MARK: - ToastManager

@Observable
final class ToastManager {
    var toasts: [Toast] = []

    func show(
        _ message: String,
        title: String? = nil,
        type: Toast.ToastType = .info,
        duration: Double = 3.5
    ) {
        let t = Toast(title: title, message: message, type: type)
        if toasts.count >= 3 { toasts.removeFirst() }
        withAnimation(.spring(response: 0.35)) { toasts.append(t) }
        DispatchQueue.main.asyncAfter(deadline: .now() + duration) { [weak self] in
            self?.dismiss(t)
        }
    }

    func dismiss(_ toast: Toast) {
        withAnimation(.spring(response: 0.3)) {
            toasts.removeAll { $0.id == toast.id }
        }
    }
}

// MARK: - Single Toast Item

private struct ToastItemView: View {
    let toast: Toast
    let onDismiss: () -> Void

    var body: some View {
        HStack(spacing: 0) {
            // Leading accent bar
            Rectangle()
                .fill(toast.type.accentColor)
                .frame(width: 4)
                .accessibilityHidden(true)

            HStack(spacing: 12) {
                // 32×32 icon circle
                ZStack {
                    Circle()
                        .fill(toast.type.iconBgColor)
                        .frame(width: 32, height: 32)

                    Image(systemName: toast.type.icon)
                        .font(.system(size: 16, weight: .semibold))
                        .foregroundColor(toast.type.accentColor)
                        .accessibilityHidden(true)
                }
                .accessibilityHidden(true)

                // Title + optional subtitle
                VStack(alignment: .leading, spacing: 2) {
                    if let title = toast.title {
                        Text(title)
                            .font(.jakarta(14, weight: .bold))
                            .foregroundColor(.beInk700)
                            .multilineTextAlignment(.leading)
                            .fixedSize(horizontal: false, vertical: true)

                        Text(toast.message)
                            .font(.jakarta(13, weight: .medium))
                            .foregroundColor(.beMuted500)
                            .multilineTextAlignment(.leading)
                            .fixedSize(horizontal: false, vertical: true)
                    } else {
                        Text(toast.message)
                            .font(.jakarta(14, weight: .bold))
                            .foregroundColor(.beInk700)
                            .multilineTextAlignment(.leading)
                            .fixedSize(horizontal: false, vertical: true)
                    }
                }

                Spacer(minLength: 0)

                Button(action: onDismiss) {
                    Image(systemName: "xmark")
                        .font(.system(size: 12, weight: .bold))
                        .foregroundColor(Color.beInk700.opacity(0.4))
                }
                .accessibilityLabel(AppStrings.Common.dismissNotification)
            }
            .padding(.horizontal, 14)
            .padding(.vertical, 14)
        }
        .frame(minWidth: 280, maxWidth: 360)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 12))
        .overlay(
            RoundedRectangle(cornerRadius: 12)
                .stroke(Color.beBorder100, lineWidth: 1)
        )
        .shadow(
            color: Color(red: 16 / 255, green: 17 / 255, blue: 22 / 255).opacity(0.10),
            radius: 15,
            x: 0,
            y: 6
        )
        .transition(.move(edge: .bottom).combined(with: .opacity))
        .accessibilityElement(children: .combine)
        .accessibilityLabel(toast.title.map { "\($0). \(toast.message)" } ?? toast.message)
    }
}

// MARK: - Toast Stack View

struct ToastStackView: View {
    let manager: ToastManager

    var body: some View {
        VStack(alignment: .trailing, spacing: 8) {
            ForEach(manager.toasts) { toast in
                ToastItemView(toast: toast) {
                    manager.dismiss(toast)
                }
            }
        }
        .padding(.bottom, 28)
        .padding(.trailing, 20)
    }
}

// MARK: - View Extension

extension View {
    func toastOverlay(_ manager: ToastManager) -> some View {
        self.overlay(alignment: .bottomTrailing) {
            ToastStackView(manager: manager)
                .zIndex(9000)
        }
    }
}
