package com.example.playlist_maker_android_levchenkosergey

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistBlue
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistChevron
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistHint
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistSearchField
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistTextPrimary
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistWhite

/**
 * Экран настроек.
 *
 * Свёрстан по макету. Три пункта выполняют внешние действия через Intent:
 * «Поделиться приложением», «Написать в поддержку» и «Пользовательское соглашение».
 * Переключение тёмной темы появится позже как дополнительная задача —
 * сейчас сверстан только сам переключатель.
 */
@Composable
fun SettingsScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current

    // Все тексты — из strings.xml; в Intent они передаются отсюда через stringResource()
    val shareText = stringResource(R.string.share_app_text)
    val supportEmail = stringResource(R.string.student_email)
    val supportSubject = stringResource(R.string.email_subject)
    val supportBody = stringResource(R.string.email_body)
    val termsUrl = stringResource(R.string.terms_url)
    val nothingFound = stringResource(R.string.no_app_to_handle)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PlaylistWhite)
    ) {
        ScreenHeader(
            title = stringResource(R.string.menu_settings),
            onBackClick = onBackClick
        )

        // Пункт «Тёмная тема»: вёрстка есть, логика переключения — в следующих спринтах
        SettingsRow(
            title = stringResource(R.string.setting_theme),
            trailing = {
                Switch(
                    checked = false,
                    onCheckedChange = { /* логика тёмной темы появится позже */ },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = PlaylistBlue,
                        uncheckedTrackColor = PlaylistSearchField
                    )
                )
            }
        )

        SettingsRow(
            title = stringResource(R.string.setting_share),
            icon = Icons.Default.Share,
            onClick = { shareApp(context, shareText, nothingFound) }
        )

        SettingsRow(
            title = stringResource(R.string.setting_write_support),
            icon = Icons.Default.SupportAgent,
            onClick = {
                writeToSupport(context, supportEmail, supportSubject, supportBody, nothingFound)
            }
        )

        SettingsRow(
            title = stringResource(R.string.setting_terms),
            icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            onClick = { openTerms(context, termsUrl, nothingFound) }
        )
    }
}

/**
 * Строка настроек: название и элемент справа (иконка или переключатель).
 */
@Composable
private fun SettingsRow(
    title: String,
    icon: ImageVector? = null,
    trailing: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(61.dp)
            .let { if (onClick != null) it.clickable { onClick() } else it }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = PlaylistTextPrimary,
            modifier = Modifier.weight(1f)
        )

        when {
            trailing != null -> trailing()
            icon != null -> Icon(
                imageVector = icon,
                contentDescription = null,
                tint = PlaylistHint,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

/** «Поделиться приложением»: системный диалог выбора мессенджера. */
private fun shareApp(context: Context, text: String, errorText: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show()
    }
}

/** «Написать в поддержку»: почтовый клиент с заполненными полями. */
private fun writeToSupport(
    context: Context,
    email: String,
    subject: String,
    body: String,
    errorText: String
) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }
    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show()
    }
}

/** «Пользовательское соглашение»: открывает ссылку в браузере. */
private fun openTerms(context: Context, url: String, errorText: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show()
    }
}
