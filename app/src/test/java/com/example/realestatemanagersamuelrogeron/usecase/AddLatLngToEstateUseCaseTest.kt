package com.example.realestatemanagersamuelrogeron.usecase

import android.location.Geocoder
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddLatLngToEstatesUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddLatLngToEstatesUseCaseImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class AddLatLngToEstateUseCaseTest {
    // Test Coroutine Dispatcher
    private val testDispatcher = UnconfinedTestDispatcher()

    // Mocks for Geocoder and EstateRepository
    private val geocoder: Geocoder = mock()
    private val estateRepository: EstateRepository = mock()

    // System Under Test (SUT)
    private lateinit var addLatLngToEstatesUseCase: AddLatLngToEstatesUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)  // Set the main dispatcher to the test dispatcher
        addLatLngToEstatesUseCase = AddLatLngToEstatesUseCaseImpl(geocoder, estateRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()  // Reset the main dispatcher to the original Main dispatcher
    }

    @Test
    fun `invoke should return true when estates are updated successfully`() = runTest {
        // Mock data
        val estate = mock<Estate> {
            on { address } doReturn "123 Main St"
            on { zipCode } doReturn "12345"
            on { city } doReturn "Anytown"
            on { region } doReturn "State"
            on { country } doReturn "Country"
            on { estateId } doReturn 1
        }

        // Mock behavior for estateRepository
        whenever(estateRepository.getEstateWithoutLatLng()).thenReturn(flowOf(listOf(estate)))
        whenever(estateRepository.updateEstateLatLng(any(), any(), any())).thenReturn(Unit)

        // Mock behavior for geocoder
        whenever(geocoder.getFromLocationName(any(), any())).thenReturn(listOf(mock {
            on { latitude } doReturn 40.7128
            on { longitude } doReturn -74.0060
        }))

        // Call the use case
        val result = addLatLngToEstatesUseCase.invoke()

        // Assertions
        assertTrue(result)

        // Verify interactions
        verify(estateRepository).updateEstateLatLng(eq(estate.estateId), eq(40.7128), eq(-74.0060))
    }

    @Test
    fun `invoke should return false when an exception is thrown`() = runTest {
        // Mock data
        val estate = mock<Estate>()

        // Mock behavior for estateRepository
        whenever(estateRepository.getEstateWithoutLatLng()).thenReturn(flowOf(listOf(estate)))
        whenever(estateRepository.updateEstateLatLng(any(), any(), any())).thenThrow(RuntimeException("Update failed"))

        // Call the use case
        val result = addLatLngToEstatesUseCase.invoke()

        // Assertions
        assertFalse(result)

        // Verify interactions
        verify(estateRepository).updateEstateLatLng(any(), any(), any())
    }
}