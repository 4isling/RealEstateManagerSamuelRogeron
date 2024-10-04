package com.example.realestatemanagersamuelrogeron

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.realestatemanagersamuelrogeron.utils.DataStorePreference
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class DataStorePreferenceTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val testCoroutineDispatcher = StandardTestDispatcher()
    private val testCoroutineScope = TestScope(testCoroutineDispatcher)


    private lateinit var dataStorePreference: DataStorePreference


    @Before
    fun setUp() {
        hiltRule.inject()
        // Use ApplicationProvider to get the context
        val appContext = ApplicationProvider.getApplicationContext<HiltTestApplication>()
        dataStorePreference = DataStorePreference(appContext)
    }

    @After
    fun tearDown() {
        // Clear preferences after each test
        runBlocking {
            dataStorePreference.clearPreferences()
        }
    }

    @Test
    fun testSaveAndRead() = testCoroutineScope.runTest {
        val key = "test_key"
        val value = true

        dataStorePreference.save(key, value)
        val result = dataStorePreference.read(key)

        assertEquals(value, result)
    }

    @Test
    fun testDisplayEuroBooleanFlow() = testCoroutineScope.runTest {
        val initialValue = dataStorePreference.displayEuroBooleanFlow.first()
        assertFalse(initialValue)

        dataStorePreference.save(DataStorePreference.PreferencesKeys.display_euro.name, true)
        val updatedValue = dataStorePreference.displayEuroBooleanFlow.first()
        assertTrue(updatedValue)
    }

    @Test
    fun testSaveAndReadMultipleValues() = testCoroutineScope.runTest {
        val keys = listOf("key1", "key2", "key3")
        val values = listOf(true, false, true)

        keys.zip(values).forEach { (key, value) ->
            dataStorePreference.save(key, value)
        }

        keys.zip(values).forEach { (key, expectedValue) ->
            val result = dataStorePreference.read(key)
            assertEquals(expectedValue, result)
        }
    }

    @Test
    fun testUpdateExistingValue() = testCoroutineScope.runTest {
        val key = "update_key"
        dataStorePreference.save(key, false)

        dataStorePreference.save(key, true)
        val result = dataStorePreference.read(key)

        assertTrue(result == true)
    }

    @Test
    fun testReadNonExistentKey() = testCoroutineScope.runTest {
        val result = dataStorePreference.read("non_existent_key")
        assertNull(result)
    }

    @Test
    fun testDisplayEuroBooleanFlowUpdates() = testCoroutineScope.runTest {
        val flow = dataStorePreference.displayEuroBooleanFlow

        val initialValue = flow.first()
        assertFalse(initialValue)

        dataStorePreference.save(DataStorePreference.PreferencesKeys.display_euro.name, true)
        val updatedValue = flow.first()
        assertTrue(updatedValue)

        dataStorePreference.save(DataStorePreference.PreferencesKeys.display_euro.name, false)
        val finalValue = flow.first()
        assertFalse(finalValue)
    }
}