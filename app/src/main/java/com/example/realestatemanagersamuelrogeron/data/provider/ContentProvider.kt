package com.example.realestatemanagersamuelrogeron.data.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.room.Transaction
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.realestatemanagersamuelrogeron.data.AppDataBase
import com.example.realestatemanagersamuelrogeron.data.dao.EstateDao
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class EstateContentProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "com.example.realestatemanagersamuelrogeron.provider"
        private const val ESTATES = 100
        private const val ESTATE_ID = 101
        private const val INTEREST_POINTS = 200
        private const val INTEREST_POINT_ID = 201
        private const val MEDIA = 300
        private const val MEDIA_ID = 301

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "estates", ESTATES)
            addURI(AUTHORITY, "estates/#", ESTATE_ID)
            addURI(AUTHORITY, "interest_points", INTEREST_POINTS)
            addURI(AUTHORITY, "interest_points/#", INTEREST_POINT_ID)
            addURI(AUTHORITY, "media", MEDIA)
            addURI(AUTHORITY, "media/#", MEDIA_ID)
        }

        private const val TAG = "EstateContentProvider"
    }

    private lateinit var estateDao: EstateDao

    override fun onCreate(): Boolean {
        estateDao = AppDataBase.getDatabase(context!!).estateDao()
        return true
    }

    @Transaction
    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val context = context ?: return null
        val cursor: Cursor = when (uriMatcher.match(uri)) {
            ESTATES -> {
                val query = buildQuery("estates", projection, selection, selectionArgs, sortOrder)
                estateDao.rawQuery(query)
            }
            ESTATE_ID -> {
                val id = ContentUris.parseId(uri)
                estateDao.getEstateByIdCursor(id)
            }
            INTEREST_POINTS -> estateDao.getAllInterestPointsCursor()
            INTEREST_POINT_ID -> {
                val id = ContentUris.parseId(uri)
                estateDao.getInterestPointByIdCursor(id)
            }
            MEDIA -> estateDao.getAllEstateMediaCursor()
            MEDIA_ID -> {
                val id = ContentUris.parseId(uri)
                estateDao.getEstateMediaByIdCursor(id)
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
        cursor?.setNotificationUri(context.contentResolver, uri)
        return cursor
    }

    override fun getType(uri: Uri): String? = when (uriMatcher.match(uri)) {
        ESTATES -> "vnd.android.cursor.dir/$AUTHORITY.estates"
        ESTATE_ID -> "vnd.android.cursor.item/$AUTHORITY.estates"
        INTEREST_POINTS -> "vnd.android.cursor.dir/$AUTHORITY.interest_points"
        INTEREST_POINT_ID -> "vnd.android.cursor.item/$AUTHORITY.interest_points"
        MEDIA -> "vnd.android.cursor.dir/$AUTHORITY.media"
        MEDIA_ID -> "vnd.android.cursor.item/$AUTHORITY.media"
        else -> null
    }

    @Transaction
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (values == null) return null
        val id = runBlocking(Dispatchers.IO) {
            when (uriMatcher.match(uri)) {
                ESTATES -> estateDao.createEstate(Estate.fromContentValues(values))
                INTEREST_POINTS -> estateDao.insertEstateInterestPoints(EstateInterestPoints.fromContentValues(values))
                MEDIA -> estateDao.insertEstatePicture(EstateMedia.fromContentValues(values))
                else -> throw IllegalArgumentException("Unknown URI: $uri")
            }
        }
        context?.contentResolver?.notifyChange(uri, null)
        return ContentUris.withAppendedId(uri, id)
    }

    @Transaction
    override fun bulkInsert(uri: Uri, values: Array<out ContentValues>): Int {
        var numInserted = 0
        runBlocking(Dispatchers.IO) {
            when (uriMatcher.match(uri)) {
                ESTATES -> {
                    values.forEach {
                        estateDao.createEstate(Estate.fromContentValues(it))
                        numInserted++
                    }
                }
                INTEREST_POINTS -> {
                    values.forEach {
                        estateDao.insertEstateInterestPoints(EstateInterestPoints.fromContentValues(it))
                        numInserted++
                    }
                }
                MEDIA -> {
                    values.forEach {
                        estateDao.insertEstatePicture(EstateMedia.fromContentValues(it))
                        numInserted++
                    }
                }
                else -> throw IllegalArgumentException("Unknown URI: $uri")
            }
        }
        context?.contentResolver?.notifyChange(uri, null)
        return numInserted
    }

    @Transaction
    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        if (values == null) return 0
        val count = runBlocking(Dispatchers.IO) {
            when (uriMatcher.match(uri)) {
                ESTATE_ID -> {
                    val id = ContentUris.parseId(uri)
                    val estate = Estate.fromContentValues(values).copy(estateId = id)
                    estateDao.updateEstate(estate)
                }
                INTEREST_POINT_ID -> {
                    val id = ContentUris.parseId(uri)
                    val interestPoint = EstateInterestPoints.fromContentValues(values).copy(estateInterestPointId = id)
                    estateDao.updateInterestPoint(interestPoint)
                }
                MEDIA_ID -> {
                    val id = ContentUris.parseId(uri)
                    val media = EstateMedia.fromContentValues(values).copy(id = id)
                    estateDao.updatePicture(media)
                }
                else -> throw IllegalArgumentException("Unknown URI: $uri")
            }
        }
        if (count > 0) {
            context?.contentResolver?.notifyChange(uri, null)
        }
        return count
    }

    @Transaction
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val count = runBlocking(Dispatchers.IO) {
            when (uriMatcher.match(uri)) {
                ESTATE_ID -> {
                    val id = ContentUris.parseId(uri)
                    estateDao.deleteEstateById(id)
                }
                INTEREST_POINT_ID -> {
                    val id = ContentUris.parseId(uri)
                    estateDao.deleteInterestPointById(id)
                }
                MEDIA_ID -> {
                    val id = ContentUris.parseId(uri)
                    estateDao.deleteEstateMediaById(id)
                }
                else -> throw IllegalArgumentException("Unknown URI: $uri")
            }
        }
        if (count > 0) {
            context?.contentResolver?.notifyChange(uri, null)
        }
        return count
    }

    private fun buildQuery(
        table: String,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): SupportSQLiteQuery {
        val queryBuilder = StringBuilder("SELECT ")

        if (projection == null) {
            queryBuilder.append("* ")
        } else {
            queryBuilder.append(projection.joinToString(", "))
        }

        queryBuilder.append(" FROM $table")

        if (selection != null) {
            queryBuilder.append(" WHERE $selection")
        }

        if (sortOrder != null) {
            queryBuilder.append(" ORDER BY $sortOrder")
        }

        return SimpleSQLiteQuery(queryBuilder.toString(), selectionArgs)
    }
}