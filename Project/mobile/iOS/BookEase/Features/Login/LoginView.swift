import SwiftUI

struct LoginView: View {
    @State private var email = ""
    @State private var password = ""
    @State private var showPassword = false

    @State private var emailError: String? = nil
    @State private var passwordError: String? = nil
    @State private var showErrorBanner = false

    @FocusState private var focusedField: LoginField?

    @Environment(ToastManager.self) private var toastManager

    private enum LoginField {
        case email, password
    }

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 0) {
                logoRow
                    .padding(.top, 24)
                    .padding(.bottom, 40)

                titleBlock
                    .padding(.bottom, 0)

                if showErrorBanner {
                    errorBannerView
                        .padding(.bottom, Spacing.base)
                }

                emailField
                    .padding(.top, 0)
                    .padding(.bottom, Spacing.base)

                passwordField
                    .padding(.bottom, Spacing.xl)

                loginButton
                    .padding(.bottom, Spacing.lg)

                orDivider
                    .padding(.bottom, Spacing.lg)

                socialButtons
                    .padding(.bottom, Spacing.xl)

                signUpLink
            }
            .padding(.horizontal, Spacing.xl)
        }
        .background(Color.white)
        .navigationBarHidden(true)
    }

    // MARK: - Logo Row

    private var logoRow: some View {
        HStack(spacing: 11) {
            ZStack {
                RoundedRectangle(cornerRadius: 11)
                    .fill(
                        LinearGradient(
                            colors: [Color(hex: "6B5BF0"), Color(hex: "4A3BD0")],
                            startPoint: .topLeading,
                            endPoint: .bottomTrailing
                        )
                    )
                    .frame(width: 38, height: 38)
                    .shadow(color: Color(hex: "4A3BD0").opacity(0.32), radius: 7, x: 0, y: 6)

                Image(systemName: "bolt.fill")
                    .font(.system(size: 18, weight: .bold))
                    .foregroundColor(.beAccentLime)
                    .accessibilityHidden(true)
            }

            Text(AppStrings.Common.appName)
                .font(.spaceGrotesk(20, weight: .bold))
                .foregroundColor(.beInk800)
        }
        .accessibilityElement(children: .combine)
        .accessibilityLabel(AppStrings.Common.appName)
    }

    // MARK: - Error Banner

    private var errorBannerView: some View {
        HStack(alignment: .top, spacing: 12) {
            Image(systemName: "info.circle.fill")
                .font(.system(size: 18))
                .foregroundColor(.beDanger)
                .accessibilityHidden(true)

            Text(AppStrings.Auth.errorBannerMessage)
                .font(.jakarta(13.5, weight: .semibold))
                .foregroundColor(.beDangerText)
                .fixedSize(horizontal: false, vertical: true)

            Spacer(minLength: 0)
        }
        .padding(.horizontal, 15)
        .padding(.vertical, 13)
        .background(Color.beDangerBg)
        .clipShape(RoundedRectangle(cornerRadius: 12))
        .overlay(
            RoundedRectangle(cornerRadius: 12)
                .stroke(Color(hex: "F6C6D1"), lineWidth: 1)
        )
        .accessibilityElement(children: .combine)
        .accessibilityLabel(AppStrings.Auth.errorBannerMessage)
    }

    // MARK: - Title Block

    private var titleBlock: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text(AppStrings.Auth.welcomeBack)
                .font(.jakarta(28, weight: .heavy))
                .foregroundColor(.beInk800)
                .padding(.bottom, Spacing.sm)

            Text(AppStrings.Auth.loginSubtitle)
                .font(.jakarta(14.5, weight: .medium))
                .foregroundColor(.beMuted500)
                .padding(.bottom, 28)
        }
    }

    // MARK: - Email Field

    private var emailField: some View {
        VStack(alignment: .leading, spacing: 6) {
            Text(AppStrings.Auth.emailLabel)
                .font(.jakarta(12.5, weight: .bold))
                .foregroundColor(.beText600)

            TextField(AppStrings.Auth.emailPlaceholder, text: $email)
                .font(.jakarta(15, weight: .medium))
                .foregroundColor(.beInk700)
                .keyboardType(.emailAddress)
                .textInputAutocapitalization(.never)
                .autocorrectionDisabled()
                .focused($focusedField, equals: .email)
                .frame(height: 50)
                .padding(.horizontal, 15)
                .background(Color.beSurface150)
                .clipShape(RoundedRectangle(cornerRadius: 13))
                .overlay(
                    RoundedRectangle(cornerRadius: 13)
                        .stroke(
                            emailError != nil ? Color.beDanger : Color.beBorder100,
                            lineWidth: emailError != nil ? 1.5 : 1
                        )
                )
                .overlay(
                    RoundedRectangle(cornerRadius: 13)
                        .stroke(
                            focusedField == .email ? Color.bePrimary.opacity(0.25) : Color.clear,
                            lineWidth: 2
                        )
                )
                .accessibilityLabel(AppStrings.Auth.emailAccessibilityLabel)
                .onChange(of: email) { _, _ in
                    if emailError != nil { emailError = nil }
                }

            if let error = emailError {
                FieldErrorLabel(message: error)
            }
        }
    }

    // MARK: - Password Field

    private var passwordField: some View {
        VStack(alignment: .leading, spacing: 6) {
            HStack {
                Text(AppStrings.Auth.passwordLabel)
                    .font(.jakarta(12.5, weight: .bold))
                    .foregroundColor(.beText600)
                Spacer()
                Button {} label: {
                    Text(AppStrings.Auth.forgotPassword)
                        .font(.jakarta(12.5, weight: .bold))
                        .foregroundColor(.bePrimary)
                }
                .accessibilityLabel(AppStrings.Auth.forgotPasswordAccessibilityLabel)
            }

            HStack {
                Group {
                    if showPassword {
                        TextField(AppStrings.Auth.passwordPlaceholder, text: $password)
                            .focused($focusedField, equals: .password)
                    } else {
                        SecureField(AppStrings.Auth.passwordPlaceholder, text: $password)
                            .focused($focusedField, equals: .password)
                    }
                }
                .font(.jakarta(15, weight: .medium))
                .foregroundColor(.beInk700)
                .onChange(of: password) { _, _ in
                    if passwordError != nil { passwordError = nil }
                }

                Button {
                    showPassword.toggle()
                } label: {
                    Image(systemName: showPassword ? "eye" : "eye.slash")
                        .font(.system(size: 17, weight: .medium))
                        .foregroundColor(.beMuted500)
                }
                .accessibilityLabel(showPassword ? AppStrings.Auth.hidePasswordAccessibilityLabel : AppStrings.Auth.showPasswordAccessibilityLabel)
            }
            .frame(height: 50)
            .padding(.horizontal, 15)
            .background(Color.beSurface150)
            .clipShape(RoundedRectangle(cornerRadius: 13))
            .overlay(
                RoundedRectangle(cornerRadius: 13)
                    .stroke(
                        passwordError != nil ? Color.beDanger : Color.beBorder100,
                        lineWidth: passwordError != nil ? 1.5 : 1
                    )
            )
            .overlay(
                RoundedRectangle(cornerRadius: 13)
                    .stroke(
                        focusedField == .password ? Color.bePrimary.opacity(0.25) : Color.clear,
                        lineWidth: 2
                    )
            )
            .accessibilityLabel(AppStrings.Auth.passwordLabel)

            if let error = passwordError {
                FieldErrorLabel(message: error)
            }
        }
    }

    // MARK: - Log In Button

    private var loginButton: some View {
        Button {
            submitLogin()
        } label: {
            Text(AppStrings.Auth.loginButton)
                .font(.jakarta(15.5, weight: .bold))
                .foregroundColor(.white)
                .frame(maxWidth: .infinity)
                .frame(height: 52)
                .background(Color.bePrimary)
                .clipShape(RoundedRectangle(cornerRadius: Radius.lg))
                .beShadowBtn()
        }
        .accessibilityLabel(AppStrings.Auth.loginButton)
    }

    // MARK: - OR Divider

    private var orDivider: some View {
        HStack(spacing: Spacing.md) {
            Rectangle()
                .fill(Color.beBorder100)
                .frame(height: 1)
            Text(AppStrings.Common.orDivider)
                .font(.jakarta(12, weight: .semibold))
                .foregroundColor(.beMuted400)
            Rectangle()
                .fill(Color.beBorder100)
                .frame(height: 1)
        }
        .accessibilityHidden(true)
    }

    // MARK: - Social Buttons

    private var socialButtons: some View {
        VStack(spacing: 11) {
            socialButton(
                icon: AnyView(GoogleGIcon()),
                title: AppStrings.Auth.continueWithGoogle
            )
            socialButton(
                icon: AnyView(
                    Image(systemName: "apple.logo")
                        .font(.system(size: 17, weight: .semibold))
                        .foregroundColor(.beInk800)
                ),
                title: AppStrings.Auth.continueWithApple
            )
        }
    }

    private func socialButton(icon: AnyView, title: String) -> some View {
        Button {} label: {
            HStack(spacing: 9) {
                icon
                    .accessibilityHidden(true)
                Text(title)
                    .font(.jakarta(17, weight: .bold))
                    .foregroundColor(.beInk700)
            }
            .frame(maxWidth: .infinity)
            .frame(height: 50)
            .background(Color.white)
            .clipShape(RoundedRectangle(cornerRadius: 13))
            .overlay(RoundedRectangle(cornerRadius: 13).stroke(Color(hex: "DDDFE6"), lineWidth: 1))
        }
        .accessibilityLabel(title)
    }

    // MARK: - Sign Up Link

    private var signUpLink: some View {
        HStack(spacing: 0) {
            Spacer()
            Text(AppStrings.Auth.noAccountPrompt)
                .font(.jakarta(14, weight: .medium))
                .foregroundColor(.beText600)
            Button {} label: {
                Text(AppStrings.Auth.signUp)
                    .font(.jakarta(14, weight: .bold))
                    .foregroundColor(.bePrimary)
            }
            .accessibilityLabel(AppStrings.Auth.signUpAccessibilityLabel)
            Spacer()
        }
    }

    // MARK: - Validation

    private func submitLogin() {
        focusedField = nil
        var hasErrors = false

        let trimmedEmail = email.trimmingCharacters(in: .whitespaces)
        if trimmedEmail.isEmpty || !trimmedEmail.contains("@") {
            emailError = AppStrings.Errors.validationEmailInvalid
            hasErrors = true
        } else {
            emailError = nil
        }

        if password.count < 8 {
            passwordError = AppStrings.Errors.validationPasswordTooShort
            hasErrors = true
        } else {
            passwordError = nil
        }

        if hasErrors {
            withAnimation(.easeInOut(duration: 0.2)) { showErrorBanner = true }
            toastManager.show(AppStrings.Auth.toastCheckCredentials, type: .error)
            return
        }

        withAnimation(.easeInOut(duration: 0.2)) { showErrorBanner = false }
        toastManager.show(AppStrings.Auth.toastWelcomeBack, type: .success)
    }
}

// MARK: - Google G Icon

private struct GoogleGIcon: View {
    var body: some View {
        ZStack {
            Text("G")
                .font(.system(size: 18, weight: .bold))
                .foregroundStyle(
                    LinearGradient(
                        colors: [
                            Color(hex: "4285F4"),
                            Color(hex: "EA4335")
                        ],
                        startPoint: .topLeading,
                        endPoint: .bottomTrailing
                    )
                )
        }
        .frame(width: 20, height: 20)
    }
}

#Preview {
    NavigationStack {
        LoginView()
    }
    .environment(ToastManager())
}
