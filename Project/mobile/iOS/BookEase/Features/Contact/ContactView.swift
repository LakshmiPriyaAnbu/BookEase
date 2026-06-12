import SwiftUI

struct ContactView: View {
    @State private var name = ""
    @State private var email = ""
    @State private var message = ""

    @State private var nameError: String? = nil
    @State private var emailError: String? = nil
    @State private var messageError: String? = nil

    @Environment(ToastManager.self) private var toastManager

    var body: some View {
        ZStack {
            Color.beAppBg.ignoresSafeArea()

            ScrollView {
                VStack(alignment: .leading, spacing: 0) {
                    headerSection
                        .padding(.horizontal, Spacing.lg)
                        .padding(.top, Spacing.lg)

                    infoCardsSection
                        .padding(.horizontal, Spacing.lg)
                        .padding(.top, Spacing.base)
                        .padding(.bottom, 22)

                    formSection
                        .padding(.horizontal, Spacing.lg)
                        .padding(.bottom, Spacing.xxl)

                    Color.clear.frame(height: 80)
                }
            }
            .safeAreaInset(edge: .bottom) {
                ctaBar
            }
        }
        .navigationBarHidden(true)
    }

    // MARK: - Header

    private var headerSection: some View {
        VStack(alignment: .leading, spacing: 3) {
            Text(AppStrings.Contact.title)
                .font(.jakarta(26, weight: .heavy))
                .foregroundColor(.beInk800)
            Text(AppStrings.Contact.subtitle)
                .font(.jakarta(13.5, weight: .medium))
                .foregroundColor(.beMuted500)
        }
    }

    // MARK: - Info Cards

    private var infoCardsSection: some View {
        VStack(spacing: 10) {
            ContactInfoCard(
                iconName: "envelope",
                iconBgColor: .bePrimarySoft,
                iconFgColor: .bePrimary,
                label: AppStrings.Contact.emailInfoLabel,
                value: AppStrings.Contact.emailInfoValue
            )
            ContactInfoCard(
                iconName: "phone",
                iconBgColor: .beSuccessBg,
                iconFgColor: .beSuccess,
                label: AppStrings.Contact.phoneInfoLabel,
                value: AppStrings.Contact.phoneInfoValue
            )
            ContactInfoCard(
                iconName: "mappin",
                iconBgColor: .beInfoBg,
                iconFgColor: .beInfo,
                label: AppStrings.Contact.studioInfoLabel,
                value: AppStrings.Contact.studioInfoValue
            )
        }
    }

    // MARK: - Form

    private var formSection: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text(AppStrings.Contact.sendMessageTitle)
                .font(.jakarta(16, weight: .heavy))
                .foregroundColor(.beInk800)
                .padding(.bottom, Spacing.md)

            VStack(spacing: 10) {
                // Name field
                VStack(alignment: .leading, spacing: 4) {
                    TextField(AppStrings.Contact.namePlaceholder, text: $name)
                        .font(.jakarta(14.5, weight: .medium))
                        .foregroundColor(.beInk700)
                        .frame(height: 46)
                        .padding(.horizontal, 14)
                        .background(Color.white)
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                        .overlay(
                            RoundedRectangle(cornerRadius: 12)
                                .stroke(nameError != nil ? Color.beDanger : Color.beBorder100, lineWidth: 1)
                        )
                        .accessibilityLabel(AppStrings.Contact.nameAccessibilityLabel)
                        .onChange(of: name) { _, _ in
                            if nameError != nil { nameError = nil }
                        }

                    if let error = nameError {
                        FieldErrorLabel(message: error)
                    }
                }

                // Email field
                VStack(alignment: .leading, spacing: 4) {
                    TextField(AppStrings.Contact.emailPlaceholder, text: $email)
                        .font(.jakarta(14.5, weight: .medium))
                        .foregroundColor(.beInk700)
                        .keyboardType(.emailAddress)
                        .textInputAutocapitalization(.never)
                        .frame(height: 46)
                        .padding(.horizontal, 14)
                        .background(Color.white)
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                        .overlay(
                            RoundedRectangle(cornerRadius: 12)
                                .stroke(emailError != nil ? Color.beDanger : Color.beBorder100, lineWidth: 1)
                        )
                        .accessibilityLabel(AppStrings.Contact.emailAccessibilityLabel)
                        .onChange(of: email) { _, _ in
                            if emailError != nil { emailError = nil }
                        }

                    if let error = emailError {
                        FieldErrorLabel(message: error)
                    }
                }

                // Message field
                VStack(alignment: .leading, spacing: 4) {
                    messageEditor

                    if let error = messageError {
                        FieldErrorLabel(message: error)
                    }
                }
            }
        }
    }

    private var messageEditor: some View {
        ZStack(alignment: .topLeading) {
            if message.isEmpty {
                Text(AppStrings.Contact.messagePlaceholder)
                    .font(.jakarta(14.5, weight: .medium))
                    .foregroundColor(.beMuted500)
                    .padding(.top, 12)
                    .padding(.leading, 4)
                    .allowsHitTesting(false)
            }
            TextEditor(text: $message)
                .font(.jakarta(14.5, weight: .medium))
                .foregroundColor(.beInk700)
                .frame(minHeight: 96)
                .scrollContentBackground(.hidden)
                .accessibilityLabel(AppStrings.Contact.messageAccessibilityLabel)
                .onChange(of: message) { _, _ in
                    if messageError != nil { messageError = nil }
                }
        }
        .padding(12)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 12))
        .overlay(
            RoundedRectangle(cornerRadius: 12)
                .stroke(messageError != nil ? Color.beDanger : Color.beBorder100, lineWidth: 1)
        )
    }

    // MARK: - CTA Bar

    private var ctaBar: some View {
        VStack(spacing: 0) {
            Divider()
                .background(Color.beBorder100)
            Button {
                submitForm()
            } label: {
                Text(AppStrings.Contact.sendMessageButton)
                    .font(.jakarta(15.5, weight: .bold))
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .frame(height: 52)
                    .background(Color.bePrimary)
                    .clipShape(RoundedRectangle(cornerRadius: Radius.lg))
                    .beShadowBtn()
            }
            .accessibilityLabel(AppStrings.Contact.sendMessageButton)
            .padding(.horizontal, Spacing.lg)
            .padding(.top, 14)
            .padding(.bottom, 30)
        }
        .background(Color.white)
    }

    // MARK: - Validation

    private func submitForm() {
        var hasErrors = false

        let trimmedName = name.trimmingCharacters(in: .whitespaces)
        if trimmedName.isEmpty {
            nameError = AppStrings.Contact.errorNameRequired
            hasErrors = true
        } else {
            nameError = nil
        }

        let trimmedEmail = email.trimmingCharacters(in: .whitespaces)
        if trimmedEmail.isEmpty || !trimmedEmail.contains("@") || !trimmedEmail.contains(".") {
            emailError = AppStrings.Contact.errorInvalidEmail
            hasErrors = true
        } else {
            emailError = nil
        }

        if message.count < 10 {
            messageError = AppStrings.Contact.errorMessageTooShort
            hasErrors = true
        } else {
            messageError = nil
        }

        if hasErrors {
            toastManager.show(AppStrings.Contact.toastFillFields, type: .error)
            return
        }

        toastManager.show(AppStrings.Contact.toastSuccess, type: .success)
        name = ""
        email = ""
        message = ""
        nameError = nil
        emailError = nil
        messageError = nil
    }
}

// MARK: - Contact Info Card

private struct ContactInfoCard: View {
    let iconName: String
    let iconBgColor: Color
    let iconFgColor: Color
    let label: String
    let value: String

    var body: some View {
        HStack(spacing: 13) {
            Image(systemName: iconName)
                .font(.system(size: 17, weight: .medium))
                .foregroundColor(iconFgColor)
                .frame(width: 40, height: 40)
                .background(iconBgColor)
                .clipShape(RoundedRectangle(cornerRadius: Radius.md))
                .accessibilityHidden(true)

            VStack(alignment: .leading, spacing: 2) {
                Text(label)
                    .font(.jakarta(12, weight: .semibold))
                    .foregroundColor(.beMuted500)
                Text(value)
                    .font(.jakarta(14.5, weight: .bold))
                    .foregroundColor(.beInk700)
            }

            Spacer()
        }
        .padding(.horizontal, Spacing.base)
        .padding(.vertical, 14)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 14))
        .overlay(RoundedRectangle(cornerRadius: 14).stroke(Color.beBorder100, lineWidth: 1))
        .accessibilityElement(children: .combine)
        .accessibilityLabel("\(label): \(value)")
    }
}

#Preview {
    NavigationStack {
        ContactView()
    }
    .environment(ToastManager())
}
