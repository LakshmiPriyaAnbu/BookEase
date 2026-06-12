package com.bookease.app.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bookease.app.R
import com.bookease.app.ui.components.ToastType
import com.bookease.app.ui.theme.*

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    showToast: (String, ToastType) -> Unit = { _, _ -> },
    vm: LoginViewModel = viewModel()
) {
    val welcomeToastMessage = stringResource(R.string.auth_welcome_toast)

    LaunchedEffect(Unit) {
        vm.loginEvent.collect {
            showToast(welcomeToastMessage, ToastType.SUCCESS)
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BeColor.surfaceWhite)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(24.dp))

        // ── Logo row ─────────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .padding(bottom = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(11.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(BeColor.primary, BeColor.primaryDark)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint               = Color.White,
                    modifier           = Modifier.size(20.dp)
                )
            }
            Text(
                text  = stringResource(R.string.common_app_name),
                style = MaterialTheme.typography.headlineSmall.copy(
                    color      = BeColor.ink900,
                    fontSize   = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        // ── Welcome heading ──────────────────────────────────────────────────
        Text(
            text  = stringResource(R.string.auth_welcome_back),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize   = 28.sp,
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text  = stringResource(R.string.auth_login_subtitle),
            style = MaterialTheme.typography.bodyMedium.copy(
                color      = BeColor.ink300,
                fontSize   = 14.5.sp,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // ── Error summary banner ─────────────────────────────────────────────
        if (vm.submitted && (vm.emailError != null || vm.passwordError != null)) {
            ErrorBannerRow()
            Spacer(Modifier.height(16.dp))
        }

        // ── Email field ──────────────────────────────────────────────────────
        FieldLabel(text = stringResource(R.string.auth_label_email))
        Spacer(Modifier.height(6.dp))
        LoginTextField(
            value           = vm.email,
            onValueChange   = vm::onEmailChange,
            placeholder     = stringResource(R.string.auth_placeholder_email),
            isError         = vm.submitted && vm.emailError != null,
            hasErrorStyle   = vm.submitted && vm.emailError != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            contentDesc     = stringResource(R.string.auth_email_input_content_desc)
        )
        FieldError(error = vm.emailError)

        Spacer(Modifier.height(16.dp))

        // ── Password field label + forgot ────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            FieldLabel(text = stringResource(R.string.auth_label_password))
            Text(
                text  = stringResource(R.string.auth_forgot_password),
                style = MaterialTheme.typography.labelMedium.copy(
                    color      = BeColor.primary,
                    fontSize   = 12.5.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.clickable { }
            )
        }
        LoginTextField(
            value           = vm.password,
            onValueChange   = vm::onPasswordChange,
            placeholder     = stringResource(R.string.auth_placeholder_password),
            isError         = vm.submitted && vm.passwordError != null,
            hasErrorStyle   = vm.submitted && vm.passwordError != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (vm.passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = vm::togglePasswordVisibility) {
                    Icon(
                        imageVector        = if (vm.passwordVisible)
                            Icons.Rounded.Visibility
                        else
                            Icons.Rounded.VisibilityOff,
                        contentDescription = if (vm.passwordVisible)
                            stringResource(R.string.auth_password_hide)
                        else
                            stringResource(R.string.auth_password_show),
                        tint               = BeColor.ink300,
                        modifier           = Modifier.size(20.dp)
                    )
                }
            },
            contentDesc = stringResource(R.string.auth_password_input_content_desc)
        )
        FieldError(error = vm.passwordError)

        Spacer(Modifier.height(16.dp))

        // ── Keep me logged in ────────────────────────────────────────────────
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Checkbox(
                checked         = vm.keepLoggedIn,
                onCheckedChange = vm::onKeepLoggedInChange,
                colors          = CheckboxDefaults.colors(
                    checkedColor   = BeColor.primary,
                    checkmarkColor = Color.White
                )
            )
            Text(
                text  = stringResource(R.string.auth_keep_logged_in),
                style = MaterialTheme.typography.bodySmall.copy(
                    color      = Color(0xFF545A66),
                    fontSize   = 13.5.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }

        Spacer(Modifier.height(20.dp))

        // ── Log in button ────────────────────────────────────────────────────
        val loginButtonContentDesc = stringResource(R.string.auth_login_button_content_desc)
        Button(
            onClick  = vm::onLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .semantics { contentDescription = loginButtonContentDesc },
            shape    = RoundedCornerShape(14.dp),
            colors   = ButtonDefaults.buttonColors(
                containerColor = BeColor.primary,
                contentColor   = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
        ) {
            Text(
                text  = stringResource(R.string.auth_login_button),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize   = 15.5.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Spacer(Modifier.height(20.dp))

        // ── OR divider ───────────────────────────────────────────────────────
        Row(
            modifier              = Modifier.fillMaxWidth(),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(BeSp.md)
        ) {
            HorizontalDivider(
                modifier  = Modifier.weight(1f),
                color     = BeColor.border,
                thickness = 1.dp
            )
            Text(
                text  = stringResource(R.string.common_or_divider),
                style = MaterialTheme.typography.labelMedium.copy(
                    color      = Color(0xFFB6BBC4),
                    fontSize   = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            HorizontalDivider(
                modifier  = Modifier.weight(1f),
                color     = BeColor.border,
                thickness = 1.dp
            )
        }

        Spacer(Modifier.height(20.dp))

        // ── Google button ────────────────────────────────────────────────────
        SocialButton(
            label       = stringResource(R.string.auth_continue_google),
            icon        = Icons.Default.Email,
            contentDesc = stringResource(R.string.auth_continue_google),
            onClick     = {}
        )

        Spacer(Modifier.height(BeSp.md))

        // ── Apple button ─────────────────────────────────────────────────────
        SocialButton(
            label       = stringResource(R.string.auth_continue_apple),
            icon        = Icons.Default.PhoneIphone,
            contentDesc = stringResource(R.string.auth_continue_apple),
            onClick     = {}
        )

        Spacer(Modifier.height(BeSp.xl))

        // ── Sign up text ─────────────────────────────────────────────────────
        val noAccountPrefix = stringResource(R.string.auth_no_account_prefix)
        val signUpLabel = stringResource(R.string.auth_sign_up)
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = Color(0xFF545A66), fontSize = 14.sp)) {
                    append(noAccountPrefix)
                }
                withStyle(
                    SpanStyle(
                        color      = BeColor.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize   = 14.sp
                    )
                ) {
                    append(signUpLabel)
                }
            },
            modifier  = Modifier
                .fillMaxWidth()
                .clickable { },
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(BeSp.xxxl))
    }
}

// ── Field Label ───────────────────────────────────────────────────────────────

@Composable
private fun FieldLabel(text: String) {
    Text(
        text  = text,
        style = MaterialTheme.typography.labelMedium.copy(
            color      = Color(0xFF545A66),
            fontSize   = 12.5.sp,
            fontWeight = FontWeight.Bold
        )
    )
}

// ── Field Error ───────────────────────────────────────────────────────────────

@Composable
private fun FieldError(error: String?) {
    if (error != null) {
        Text(
            text  = error,
            style = MaterialTheme.typography.labelSmall.copy(
                color      = Color(0xFFBE123C),
                fontSize   = 12.sp,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

// ── Login Text Field ──────────────────────────────────────────────────────────

@Composable
private fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isError: Boolean,
    keyboardOptions: KeyboardOptions,
    contentDesc: String,
    hasErrorStyle: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value                = value,
        onValueChange        = onValueChange,
        placeholder          = {
            Text(
                text  = placeholder,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color    = BeColor.ink300,
                    fontSize = 14.5.sp
                )
            )
        },
        singleLine           = true,
        isError              = isError,
        visualTransformation = visualTransformation,
        trailingIcon         = trailingIcon,
        keyboardOptions      = keyboardOptions,
        shape                = RoundedCornerShape(13.dp),
        colors               = OutlinedTextFieldDefaults.colors(
            focusedContainerColor   = if (hasErrorStyle) BeColor.dangerBg else BeColor.surface100,
            unfocusedContainerColor = if (hasErrorStyle) BeColor.dangerBg else BeColor.surface100,
            errorContainerColor     = BeColor.dangerBg,
            focusedBorderColor      = BeColor.primary,
            unfocusedBorderColor    = if (hasErrorStyle) BeColor.dangerText else BeColor.border,
            errorBorderColor        = BeColor.dangerText
        ),
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color    = BeColor.ink700,
            fontSize = 14.5.sp
        ),
        modifier  = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .semantics { contentDescription = contentDesc }
    )
}

// ── Social Button ─────────────────────────────────────────────────────────────

@Composable
private fun SocialButton(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDesc: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(13.dp))
            .background(BeColor.surfaceWhite)
            .border(1.dp, Color(0xFFDDDFE6), RoundedCornerShape(13.dp))
            .clickable { onClick() }
            .semantics { contentDescription = contentDesc },
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector        = icon,
            contentDescription = null,
            tint               = BeColor.ink700,
            modifier           = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(BeSp.sm))
        Text(
            text  = label,
            style = MaterialTheme.typography.labelLarge.copy(
                color      = BeColor.ink700,
                fontSize   = 14.5.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

// ── Error Banner Row ──────────────────────────────────────────────────────────

@Composable
private fun ErrorBannerRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(BeColor.dangerBg)
            .border(1.dp, Color(0xFFF6C6D1), RoundedCornerShape(12.dp))
            .padding(horizontal = 15.dp, vertical = 13.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector        = Icons.Default.Info,
            contentDescription = null,
            tint               = BeColor.dangerText,
            modifier           = Modifier.size(18.dp)
        )
        Text(
            text  = stringResource(R.string.auth_error_banner),
            style = MaterialTheme.typography.bodySmall.copy(
                color      = Color(0xFFBE123C),
                fontWeight = FontWeight.SemiBold,
                fontSize   = 13.5.sp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    com.bookease.app.ui.theme.BookEaseTheme {
        LoginScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorBannerRowPreview() {
    com.bookease.app.ui.theme.BookEaseTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            ErrorBannerRow()
        }
    }
}
