package com.example.realestatemanagersamuelrogeron.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap

object FileTypeHelper {
    // Function to get MIME type from URI
    fun getMimeType(context: Context, uri: Uri): String? {
        var mimeType: String? = null
        if (ContentResolver.SCHEME_CONTENT == uri.scheme) {
            val contentResolver = context.contentResolver
            mimeType = contentResolver.getType(uri)
        } else {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase())
        }
        return mimeType
    }

    // Function to check if the file is an image
    fun isImage(mimeType: String?): Boolean {
        return mimeType != null && mimeType.startsWith("image/")
    }

    // Function to check if the file is a video
    fun isVideo(mimeType: String?): Boolean {
        return mimeType != null && mimeType.startsWith("video/")
    }
}