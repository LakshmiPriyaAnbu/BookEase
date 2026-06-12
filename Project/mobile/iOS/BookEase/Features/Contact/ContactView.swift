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
            Text("Contact")
                .font(.jakarta(26, weight: .heavy))
                .foregroundColor(.beInk800)
            Text("We reply within a day.")
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
                label: "Email",
                value: "hello@bookease.app"
            )
            ContactInfoCard(
                iconName: "phone",
                iconBgColor: .beSuccessBg,
                iconFgColor: .beSuccess,
                label: "Phone",
                value: "+1 (415) 555-0100"
            )
            ContactInfoCard(
                iconName: "mappin",
                iconBgColor: .beInfoBg,
                iconFgColor: .beInfo,
                label: "Studio",
                value: "540 Market St, SF"
            )
        }
    }

    // MARK: - Form

    private var formSection: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text("Send a message")
                .font(.jakarta(16, weight: .heavy))
                .foregroundColor(.beInk800)
                .padding(.bottom, Spacing.md)

            VStack(spacing: 10) {
                // Name field
                VStack(alignment: .leading, spacing: 4) {
                    TextField("Jordan Hayes", text: $name)
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
                        .accessibilityLabel("Name")
                        .onChange(of: name) { _, _ in
                            if nameError != nil { nameError = nil }
                        }

                    if let error = nameError {
                        FieldErrorLabel(message: error)
                    }
                }

                // Email field
                VStack(alignment: .leading, spacing: 4) {
                    TextField("jordan@email.com", text: $email)
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
                        .accessibilityLabel("Email")
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
                Text("Your message…")
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
                .accessibilityLabel("Message")
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
                Text("Send message")
                    .font(.jakarta(15.5, weight: .bold))
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .frame(height: 52)
                    .background(Color.bePrimary)
                    .clipShape(RoundedRectangle(cornerRadius: Radius.lg))
                    .beShadowBtn()
            }
            .accessibilityLabel("Send message")
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
            nameError = "Name is required"
            hasErrors = true
        } else {
            nameError = nil
        }

        let trimmedEmail = email.trimmingCharacters(in: .whitespaces)
        if trimmedEmail.isEmpty || !trimmedEmail.contains("@") || !trimmedEmail.contains(".") {
            emailError = "Enter a valid email address"
            hasErrors = true
        } else {
            emailError = nil
        }

        if message.count < 10 {
            messageError = "Message must be at least 10 characters"
            hasErrors = true
        } else {
            messageError = nil
        }

        if hasErrors {
            toastManager.show("Please fill in all required fields.", type: .error)
            return
        }

        toastManager.show("Message sent! We'll reply within a day.", type: .success)
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
