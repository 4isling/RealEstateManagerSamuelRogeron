package com.example.realestatemanagersamuelrogeron.data.provider

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.realestatemanagersamuelrogeron.data.AppDataBase
import com.example.realestatemanagersamuelrogeron.data.dao.EstateDao
import com.example.realestatemanagersamuelrogeron.utils.TestDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EstateContentProviderTest {

    private lateinit var context: Context
    private lateinit var testDatabase: AppDataBase
    private lateinit var testEstateDao: EstateDao
    private lateinit var contentProvider: EstateContentProvider

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext

        testDatabase = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
            .allowMainThreadQueries()
            .build()

        testEstateDao = testDatabase.estateDao()

        runBlocking {
            TestDatabase.getTestEstates().forEach { testEstateDao.createEstate(it) }
            TestDatabase.getTestInterestPoints().forEach { testEstateDao.insertEstateInterestPoints(it) }
            TestDatabase.getTestEstateInterestPointCrossRef().forEach { testEstateDao.insertCrossRef(it) }
            TestDatabase.getTestEstateMedia().forEach { testEstateDao.insertEstatePicture(it) }
        }

        contentProvider = EstateContentProvider()
        contentProvider.attachInfo(context, null)

        val field = EstateContentProvider::class.java.getDeclaredField("estateDao")
        field.isAccessible = true
        field.set(contentProvider, testEstateDao)
    }

    @After
    fun tearDown() {
        testDatabase.close()
    }

    @Test
    fun testQueryAllEstates() {
        val uri = Uri.parse("content://com.example.realestatemanagersamuelrogeron.provider/estates")
        val cursor = contentProvider.query(uri, null, null, null, null)

        assertNotNull("Cursor should not be null", cursor)
        assertEquals("Should have 3 estates", 3, cursor!!.count)

        cursor.moveToFirst()
        assertEquals("Cozy Apartment", cursor.getString(cursor.getColumnIndexOrThrow("title")))
        cursor.moveToNext()
        assertEquals("Spacious House", cursor.getString(cursor.getColumnIndexOrThrow("title")))
        cursor.moveToNext()
        assertEquals("Modern Loft", cursor.getString(cursor.getColumnIndexOrThrow("title")))

        cursor.close()
    }

    @Test
    fun testQueryEstateById() {
        val uri = Uri.parse("content://com.example.realestatemanagersamuelrogeron.provider/estates/2")
        val cursor = contentProvider.query(uri, null, null, null, null)

        assertNotNull("Cursor should not be null", cursor)
        assertEquals("Should have 1 estate", 1, cursor!!.count)

        cursor.moveToFirst()
        assertEquals("Spacious House", cursor.getString(cursor.getColumnIndexOrThrow("title")))
        assertEquals(350000, cursor.getInt(cursor.getColumnIndexOrThrow("price")))

        cursor.close()
    }

    @Test
    fun testQueryEstateInterestPoints() {
        val uri = Uri.parse("content://com.example.realestatemanagersamuelrogeron.provider/interest_points")
        val cursor = contentProvider.query(uri, null, null, null, null)

        assertNotNull("Cursor should not be null", cursor)
        assertEquals("Should have 3 interest points", 3, cursor!!.count)

        cursor.close()
    }

    @Test
    fun testQueryEstateMedia() {
        val uri = Uri.parse("content://com.example.realestatemanagersamuelrogeron.provider/media")
        val cursor = contentProvider.query(uri, null, null, null, null)

        assertNotNull("Cursor should not be null", cursor)
        assertEquals("Should have 5 media items", 5, cursor!!.count)

        cursor.close()
    }

    @Test
    fun testInsertEstate() {
        val uri = Uri.parse("content://com.example.realestatemanagersamuelrogeron.provider/estates")
        val values = ContentValues().apply {
            put("title", "New Test Estate")
            put("price", 200000)
            put("surface", 100)
            put("nbRooms", 3)
            put("address", "Test Address")
            put("city", "Test City")
            put("country", "Test Country")
            put("status", true)
            put("addDate", System.currentTimeMillis())
        }

        val insertUri = contentProvider.insert(uri, values)
        assertNotNull("Insert URI should not be null", insertUri)

        val cursor = contentProvider.query(uri, null, null, null, null)
        assertNotNull("Cursor should not be null", cursor)
        assertEquals("Should now have 4 estates", 4, cursor!!.count)

        cursor.close()
    }

    @Test
    fun testUpdateEstate() {
        val uri = Uri.parse("content://com.example.realestatemanagersamuelrogeron.provider/estates/1")
        val values = ContentValues().apply {
            put("price", 1600)
        }

        val updateCount = contentProvider.update(uri, values, null, null)
        assertEquals("Should update 1 row", 1, updateCount)

        val cursor = contentProvider.query(uri, null, null, null, null)
        assertNotNull("Cursor should not be null", cursor)
        cursor!!.moveToFirst()
        assertEquals(1600, cursor.getInt(cursor.getColumnIndexOrThrow("price")))

        cursor.close()
    }

    @Test
    fun testDeleteEstate() {
        val uri = Uri.parse("content://com.example.realestatemanagersamuelrogeron.provider/estates/3")

        val deleteCount = contentProvider.delete(uri, null, null)
        assertEquals("Should delete 1 row", 1, deleteCount)

        val cursor = contentProvider.query(Uri.parse("content://com.example.realestatemanagersamuelrogeron.provider/estates"), null, null, null, null)
        assertNotNull("Cursor should not be null", cursor)
        assertEquals("Should now have 2 estates", 2, cursor!!.count)

        cursor.close()
    }
}