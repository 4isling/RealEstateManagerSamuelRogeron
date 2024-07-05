package com.example.realestatemanagersamuelrogeron.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UriPermissionHelper @Inject constructor(@ApplicationContext private val context: Context) {
    fun takePersistableUriPermission(uri: Uri){
        val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
        try {
            context.contentResolver.takePersistableUriPermission(uri, flag)
        } catch (e: Exception) {
            Log.e("UriPermissionHelper", "Error taking persistable URI permission for $uri: $e")
        }
    }
}