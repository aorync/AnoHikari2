package com.syntxr.anohikari3.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent

object AppGlobalActions {

    fun copyAction(
        context: Context,
        ayaNo: Int,
        soraEn: String,
        ayaText: String,
        translation: String,
    ) {
        val copiedLabel = "$soraEn - $ayaNo"
        val copiedContent = "$ayaText \n$translation"
        val clip = ClipData.newPlainText(copiedLabel, copiedContent)
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(clip)
    }

    fun shareAction(
        context: Context,
        ayaNo: Int,
        soraEn: String,
        ayaText: String,
        translation: String,
    ) {
        val sharedSubject = "$soraEn - $ayaNo"
        val sharedContent = "$ayaText \n$translation"

        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TITLE, sharedSubject)
            putExtra(Intent.EXTRA_TEXT, sharedContent)
            type = "text/plain"
        }

        context.startActivity(Intent.createChooser(intent, "Share with..."))
    }

}