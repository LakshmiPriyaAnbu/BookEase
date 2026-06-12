import SwiftUI

struct LoginView: View {
    @State private var email = ""
    @State private var password = ""
    @State private var showPassword = false

    @State private var emailError: String? = nil
    @State private var passwordError: String? = nil

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

            Text("BookEase")
                .font(.spaceGrotesk(20, weight: .bold))
                .foregroundColor(.beInk800)
        }
        .accessibilityElement(children: .combine)
        .accessibilityLabel("BookEase")
    }

    // MARK: - Title Block

    private var titleBlock: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text("Welcome back")
                .font(.jakarta(28, weight: .heavy))
                .foregroundColor(.beInk800)
                .padding(.bottom, Spacing.sm)

            Text("Log in to manage your bookings.")
                .font(.jakarta(14.5, weight: .medium))
                .foregroundColor(.beMuted500)
                .padding(.bottom, 28)
        }
    }

    // MARK: - Email Field

    private var emailField: some View {
        VStack(alignment: .leading, spacing: 6) {
            Text("Email")
                .font(.jakarta(12.5, weight: .bold))
                .foregroundColor(.beText600)

            TextField("your@email.com", text: $email)
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
                        .stroke(emailError != nil ? Color.beDanger : Color.beBorder100, lineWidth: 1)
                )
                .overlay(
                    RoundedRectangle(cornerRadius: 13)
                        .stroke(
                            focusedField == .email ? Color.bePrimary.opacity(0.25) : Color.clear,
                            lineWidth: 2
                        )
                )
                .accessibilityLabel("Email address")
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
                Text("Password")
                    .font(.jakarta(12.5, weight: .bold))
                    .foregroundColor(.beText600)
                Spacer()
                Button {} label: {
                    Text("Forgot?")
                        .font(.jakarta(12.5, weight: .bold))
                        .foregroundColor(.bePrimary)
                }
                .accessibilityLabel("Forgot password")
            }

            HStack {
                Group {
                    if showPassword {
                        TextField("Password", text: $password)
                            .focused($focusedField, equals: .password)
                    } else {
                        SecureField("Password", text: $password)
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
                .accessibilityLabel(showPassword ? "Hide password" : "Show password")
            }
            .frame(height: 50)
            .padding(.horizontal, 15)
            .background(Color.beSurface150)
            .clipShape(RoundedRectangle(cornerRadius: 13))
            .overlay(
                RoundedRectangle(cornerRadius: 13)
                    .stroke(passwordError != nil ? Color.beDanger : Color.beBorder100, lineWidth: 1)
            )
            .overlay(
                RoundedRectangle(cornerRadius: 13)
                    .stroke(
                        focusedField == .password ? Color.bePrimary.opacity(0.25) : Color.clear,
                        lineWidth: 2
                    )
            )
            .accessibilityLabel("Password")

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
            Text("Log in")
                .font(.jakarta(15.5, weight: .bold))
                .foregroundColor(.white)
                .frame(maxWidth: .infinity)
                .frame(height: 52)
                .background(Color.bePrimary)
                .clipShape(RoundedRectangle(cornerRadius: Radius.lg))
                .beShadowBtn()
        }
        .accessibilityLabel("Log in")
    }

    // MARK: - OR Divider

    private var orDivider: some View {
        HStack(spacing: Spacing.md) {
            Rectangle()
                .fill(Color.beBorder100)
                .frame(height: 1)
            Text("OR")
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
                title: "Continue with Google"
            )
            socialButton(
                icon: AnyView(
                    Image(systemName: "apple.logo")
                        .font(.system(size: 17, weight: .semibold))
                        .foregroundColor(.beInk800)
                ),
                title: "Continue with Apple"
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
            Text("Don't have an account? ")
                .font(.jakarta(14, weight: .medium))
                .foregroundColor(.beText600)
            Button {} label: {
                Text("Sign up")
                    .font(.jakarta(14, weight: .bold))
                    .foregroundColor(.bePrimary)
            }
            .accessibilityLabel("Sign up for an account")
            Spacer()
        }
    }

    // MARK: - Validation

    private func submitLogin() {
        focusedField = nil
        var hasErrors = false

        let trimmedEmail = email.trimmingCharacters(in: .whitespaces)
        if trimmedEmail.isEmpty || !trimmedEmail.contains("@") {
            emailError = "Enter a valid email address"
            hasErrors = true
        } else {
            emailError = nil
        }

        if password.count < 8 {
            passwordError = "Password must be at least 8 characters"
            hasErrors = true
        } else {
            passwordError = nil
        }

        if hasErrors {
            toastManager.show("Please check your email and password.", type: .error)
            return
        }

        toastManager.show("Welcome back!", type: .success)
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
