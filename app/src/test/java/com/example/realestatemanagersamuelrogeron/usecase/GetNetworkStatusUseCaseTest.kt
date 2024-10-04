package com.example.realestatemanagersamuelrogeron.usecase

import android.content.Context
import app.cash.turbine.test
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetNetworkStatusUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetNetworkStatusUseCaseImpl
import com.example.realestatemanagersamuelrogeron.utils.NetworkUtil.getNetworkStatusFlow
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetNetworkStatusUseCaseTest {

    private lateinit var getNetworkStatusUseCase: GetNetworkStatusUseCase
    private val testDispatcher = StandardTestDispatcher()

    private val context = mockk<Context>(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getNetworkStatusUseCase = GetNetworkStatusUseCaseImpl(context)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke should emit network status changes`() = runTest {
        // Mock the network status changes, assuming getNetworkStatusFlow is a function that provides Flow<Boolean> based on network status
        every { getNetworkStatusFlow(context) } returns flowOf(true, false).flowOn(testDispatcher)

        // Test the flow emissions
        getNetworkStatusUseCase.invoke().test {
            assert(awaitItem()) // First item should be true
            assert(!awaitItem()) // Second item should be false
            cancelAndIgnoreRemainingEvents()
        }
    }
}
