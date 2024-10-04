package com.example.realestatemanagersamuelrogeron.domain.model

import android.content.ContentValues
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "estate_pictures",
    /*foreignKeys = [
        ForeignKey(
            entity = Estate::class,
            parentColumns = ["id"],
            childColumns = ["estateId"],
            onDelete = CASCADE
            )
    ]*/
)
data class EstateMedia(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val estateId: Long = 0,
    val uri: String,
    val mimeType: String?,
    val name: String,
){
    companion object {
        fun fromContentValues(values: ContentValues): EstateMedia {
            return EstateMedia(
                id = values.getAsLong("id") ?: 0L,
                estateId = values.getAsLong("estateId") ?: 0L,
                uri = values.getAsString("uri") ?: "",
                mimeType = values.getAsString("mimeType"),
                name = values.getAsString("name") ?: ""
            )
        }
    }
}