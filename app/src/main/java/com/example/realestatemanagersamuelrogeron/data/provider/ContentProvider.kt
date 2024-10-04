package com.example.realestatemanagersamuelrogeron.data.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.realestatemanagersamuelrogeron.data.AppDataBase
import com.example.realestatemanagersamuelrogeron.data.dao.EstateDao
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia


object EstateContract {
    const val AUTHORITY = "com.example.myapp.provider"
    val BASE_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY")

    object EstateEntry {
        const val TABLE_NAME = "estates"
        val CONTENT_URI: Uri = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME)
    }

    object EstateInterestPointsEntry {
        const val TABLE_NAME = "estate_interest_points"
        val CONTENT_URI: Uri = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME)
    }

    object EstateMediaEntry {
        const val TABLE_NAME = "estate_pictures"
        val CONTENT_URI: Uri = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME)
    }
}

class EstateContentProvider : ContentProvider() {

    companion object {
        private const val ESTATES = 100
        private const val ESTATE_WITH_ID = 101
        private const val ESTATE_INTEREST_POINTS = 200
        private const val ESTATE_INTEREST_POINTS_WITH_ID = 201
        private const val ESTATE_MEDIA = 300
        private const val ESTATE_MEDIA_WITH_ID = 301

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(EstateContract.AUTHORITY, EstateContract.EstateEntry.TABLE_NAME, ESTATES)
            addURI(EstateContract.AUTHORITY, "${EstateContract.EstateEntry.TABLE_NAME}/#", ESTATE_WITH_ID)
            addURI(EstateContract.AUTHORITY, EstateContract.EstateInterestPointsEntry.TABLE_NAME, ESTATE_INTEREST_POINTS)
            addURI(EstateContract.AUTHORITY, "${EstateContract.EstateInterestPointsEntry.TABLE_NAME}/#", ESTATE_INTEREST_POINTS_WITH_ID)
            addURI(EstateContract.AUTHORITY, EstateContract.EstateMediaEntry.TABLE_NAME, ESTATE_MEDIA)
            addURI(EstateContract.AUTHORITY, "${EstateContract.EstateMediaEntry.TABLE_NAME}/#", ESTATE_MEDIA_WITH_ID)
        }

        lateinit var estateDao: EstateDao
    }

    override fun onCreate(): Boolean {
        estateDao = AppDataBase.getDatabase(context!!).estateDao()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val cursor: Cursor? = when (uriMatcher.match(uri)) {
            ESTATES -> estateDao.getAllEstatesCursor()
            ESTATE_WITH_ID -> {
                val id = ContentUris.parseId(uri)
                estateDao.getEstateByIdCursor(id)
            }
            ESTATE_INTEREST_POINTS -> estateDao.getAllInterestPointsCursor()
            ESTATE_INTEREST_POINTS_WITH_ID -> {
                val id = ContentUris.parseId(uri)
                estateDao.getInterestPointByIdCursor(id)
            }
            ESTATE_MEDIA -> estateDao.getAllEstateMediaCursor()
            ESTATE_MEDIA_WITH_ID -> {
                val id = ContentUris.parseId(uri)
                estateDao.getEstateMediaByIdCursor(id)
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
        cursor?.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val id: Any = when (uriMatcher.match(uri)) {
            ESTATES -> estateDao.createEstate(Estate.fromContentValues(values!!))
            ESTATE_INTEREST_POINTS -> estateDao.insertEstateInterestPoints(EstateInterestPoints.fromContentValues(values!!))
            ESTATE_MEDIA -> estateDao.insertEstatePicture(EstateMedia.fromContentValues(values!!))
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
        context?.contentResolver?.notifyChange(uri, null)
        return ContentUris.withAppendedId(uri, id as Long)
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val count: Int = when (uriMatcher.match(uri)) {
            ESTATE_WITH_ID -> {
                val id = ContentUris.parseId(uri)
                val estate = Estate.fromContentValues(values!!).copy(estateId = id)
                estateDao.updateEstate(estate)
            }
            ESTATE_INTEREST_POINTS_WITH_ID -> {
                val id = ContentUris.parseId(uri)
                val interestPoint = EstateInterestPoints.fromContentValues(values!!).copy(estateInterestPointId = id)
                estateDao.updateInterestPoint(interestPoint)
            }
            ESTATE_MEDIA_WITH_ID -> {
                val id = ContentUris.parseId(uri)
                val estateMedia = EstateMedia.fromContentValues(values!!).copy(id = id)
                estateDao.updatePicture(estateMedia)
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
        context?.contentResolver?.notifyChange(uri, null)
        return count
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val count: Int = when (uriMatcher.match(uri)) {
            ESTATE_WITH_ID -> {
                val id = ContentUris.parseId(uri)
                estateDao.deleteEstateById(id)
            }
            ESTATE_INTEREST_POINTS_WITH_ID -> {
                val id = ContentUris.parseId(uri)
                estateDao.deleteInterestPointById(id)
            }
            ESTATE_MEDIA_WITH_ID -> {
                val id = ContentUris.parseId(uri)
                estateDao.deleteAllPicturesWithEstate(id)
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
        context?.contentResolver?.notifyChange(uri, null)
        return count
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            ESTATES -> "vnd.android.cursor.dir/${EstateContract.AUTHORITY}.${EstateContract.EstateEntry.TABLE_NAME}"
            ESTATE_WITH_ID -> "vnd.android.cursor.item/${EstateContract.AUTHORITY}.${EstateContract.EstateEntry.TABLE_NAME}"
            ESTATE_INTEREST_POINTS -> "vnd.android.cursor.dir/${EstateContract.AUTHORITY}.${EstateContract.EstateInterestPointsEntry.TABLE_NAME}"
            ESTATE_INTEREST_POINTS_WITH_ID -> "vnd.android.cursor.item/${EstateContract.AUTHORITY}.${EstateContract.EstateInterestPointsEntry.TABLE_NAME}"
            ESTATE_MEDIA -> "vnd.android.cursor.dir/${EstateContract.AUTHORITY}.${EstateContract.EstateMediaEntry.TABLE_NAME}"
            ESTATE_MEDIA_WITH_ID -> "vnd.android.cursor.item/${EstateContract.AUTHORITY}.${EstateContract.EstateMediaEntry.TABLE_NAME}"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }
}