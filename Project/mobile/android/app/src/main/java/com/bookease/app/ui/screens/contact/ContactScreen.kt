package com.bookease.app.ui.screens.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bookease.app.R
import com.bookease.app.ui.components.ToastType
import com.bookease.app.ui.theme.*

@Composable
fun ContactScreen(
    showToast: (String, ToastType) -> Unit = { _, _ -> },
    vm: ContactViewModel = viewModel()
) {
    val successToastMessage = stringResource(R.string.contact_success_toast)

    LaunchedEffect(Unit) {
        vm.submitEvent.collect {
            showToast(successToastMessage, ToastType.SUCCESS)
        }
    }

    Scaffold(
        containerColor = BeColor.surface100,
        bottomBar = {
            ContactBottomBar(onSend = vm::onSubmit)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(
                horizontal = BeSp.lg,
                vertical   = BeSp.lg
            )
        ) {
            // ── Title block ──────────────────────────────────────────────────
            item {
                Text(
                    text  = stringResource(R.string.contact_title),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize   = 26.sp,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text  = stringResource(R.string.contact_subtitle),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color      = BeColor.ink300,
                        fontSize   = 13.5.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(top = 4.dp, bottom = BeSp.lg)
                )
            }

            // ── Info cards ───────────────────────────────────────────────────
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    ContactInfoCard(
                        iconBg    = BeColor.primarySoft,
                        icon      = Icons.Default.Email,
                        iconTint  = BeColor.primary,
                        label     = stringResource(R.string.contact_info_email_label),
                        value     = stringResource(R.string.contact_info_email_value)
                    )
                    ContactInfoCard(
                        iconBg    = BeColor.successBg,
                        icon      = Icons.Default.Phone,
                        iconTint  = BeColor.successText,
                        label     = stringResource(R.string.contact_info_phone_label),
                        value     = stringResource(R.string.contact_info_phone_value)
                    )
                    ContactInfoCard(
                        iconBg    = BeColor.infoBg,
                        icon      = Icons.Default.LocationOn,
                        iconTint  = BeColor.infoText,
                        label     = stringResource(R.string.contact_info_studio_label),
                        value     = stringResource(R.string.contact_info_studio_value)
                    )
                }
                Spacer(Modifier.height(BeSp.xl))
            }

            // ── Form section title ───────────────────────────────────────────
            item {
                Text(
                    text  = stringResource(R.string.contact_send_message_title),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize   = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    modifier = Modifier.padding(bottom = BeSp.md)
                )
            }

            // ── Form fields ──────────────────────────────────────────────────
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    // Name
                    ContactField(
                        value            = vm.name,
                        onValueChange    = vm::onNameChange,
                        placeholder      = stringResource(R.string.contact_placeholder_name),
                        label            = stringResource(R.string.contact_field_name_label),
                        error            = vm.nameError,
                        keyboardOptions  = KeyboardOptions.Default,
                        contentDesc      = stringResource(R.string.contact_name_input_content_desc)
                    )

                    // Email
                    ContactField(
                        value            = vm.email,
                        onValueChange    = vm::onEmailChange,
                        placeholder      = stringResource(R.string.contact_placeholder_email),
                        label            = stringResource(R.string.contact_field_email_label),
                        error            = vm.emailError,
                        keyboardOptions  = KeyboardOptions(keyboardType = KeyboardType.Email),
                        contentDesc      = stringResource(R.string.contact_email_input_content_desc)
                    )

                    // Message
                    ContactField(
                        value            = vm.message,
                        onValueChange    = vm::onMessageChange,
                        placeholder      = stringResource(R.string.contact_placeholder_message),
                        label            = stringResource(R.string.contact_field_message_label),
                        error            = vm.messageError,
                        minLines         = 3,
                        singleLine       = false,
                        keyboardOptions  = KeyboardOptions.Default,
                        contentDesc      = stringResource(R.string.contact_message_input_content_desc)
                    )
                }
                Spacer(Modifier.height(BeSp.xxxl))
            }
        }
    }
}

// ── Contact Info Card ─────────────────────────────────────────────────────────

@Composable
private fun ContactInfoCard(
    iconBg: Color,
    icon: ImageVector,
    iconTint: Color,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(BeColor.surfaceWhite)
            .border(1.dp, BeColor.border, RoundedCornerShape(14.dp))
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = icon,
                contentDescription = null,
                tint               = iconTint,
                modifier           = Modifier.size(20.dp)
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text  = label,
                style = MaterialTheme.typography.labelMedium.copy(
                    color      = BeColor.ink300,
                    fontSize   = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text  = value,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color      = BeColor.ink700,
                    fontSize   = 14.5.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

// ── Contact Form Field ────────────────────────────────────────────────────────

@Composable
private fun ContactField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    label: String,
    error: String?,
    keyboardOptions: KeyboardOptions,
    contentDesc: String,
    singleLine: Boolean = true,
    minLines: Int = 1
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        OutlinedTextField(
            value            = value,
            onValueChange    = onValueChange,
            placeholder      = {
                Text(
                    text  = placeholder,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color    = BeColor.ink300,
                        fontSize = 14.5.sp
                    )
                )
            },
            singleLine       = singleLine,
            minLines         = minLines,
            keyboardOptions  = keyboardOptions,
            isError          = error != null,
            shape            = RoundedCornerShape(12.dp),
            colors           = OutlinedTextFieldDefaults.colors(
                focusedContainerColor   = BeColor.surfaceWhite,
                unfocusedContainerColor = BeColor.surfaceWhite,
                errorContainerColor     = BeColor.surfaceWhite,
                focusedBorderColor      = BeColor.primary,
                unfocusedBorderColor    = BeColor.border,
                errorBorderColor        = Color(0xFFE11D48)
            ),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color    = BeColor.ink700,
                fontSize = 14.5.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .then(if (singleLine) Modifier.height(46.dp) else Modifier)
                .semantics { contentDescription = contentDesc }
        )
        if (error != null) {
            Text(
                text  = error,
                style = MaterialTheme.typography.labelSmall.copy(
                    color      = Color(0xFFBE123C),
                    fontSize   = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

// ── Bottom Bar ────────────────────────────────────────────────────────────────

@Composable
private fun ContactBottomBar(onSend: () -> Unit) {
    val sendContentDesc = stringResource(R.string.contact_send_content_desc)
    val sendMessageLabel = stringResource(R.string.common_send_message)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BeColor.surfaceWhite)
            .border(
                width  = 1.dp,
                color  = BeColor.border,
                shape  = RoundedCornerShape(topStart = BeRadius.xxl, topEnd = BeRadius.xxl)
            )
            .padding(horizontal = BeSp.lg, vertical = 14.dp)
            .padding(bottom = 8.dp)
    ) {
        Button(
            onClick  = onSend,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .semantics { contentDescription = sendContentDesc },
            shape  = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BeColor.primary,
                contentColor   = Color.White
            )
        ) {
            Text(
                text  = sendMessageLabel,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize   = 15.5.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F8)
@Composable
private fun ContactScreenPreview() {
    com.bookease.app.ui.theme.BookEaseTheme {
        ContactScreen()
    }
}
