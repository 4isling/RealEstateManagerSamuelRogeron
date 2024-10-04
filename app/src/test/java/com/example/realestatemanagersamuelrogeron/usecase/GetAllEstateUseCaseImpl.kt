package com.example.realestatemanagersamuelrogeron.usecase

import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import com.example.realestatemanagersamuelrogeron.domain.usecases.EstateFilter
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetAllEstatesWithDetailsUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetAllEstatesWithDetailsUseCaseImpl
import com.example.realestatemanagersamuelrogeron.utils.predefinedInterestPoints
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class GetAllEstatesWithDetailsUseCaseTest {

    private lateinit var estateRepository: EstateRepository
    private lateinit var getAllEstatesWithDetailsUseCase: GetAllEstatesWithDetailsUseCase

    @Before
    fun setUp() {
        estateRepository = mockk()
        getAllEstatesWithDetailsUseCase = GetAllEstatesWithDetailsUseCaseImpl(estateRepository)
    }

    @Test
    fun `execute should return list of estates when repository returns data`() = runTest {
        // Arrange
        val filter = EstateFilter()

        // Define the mock data
        val predefinedInterestPoints = predefinedInterestPoints
        val estateWithDetails = EstateWithDetails(
            estate = Estate(
                estateId = 1L,
                title = "La forge D'Entre Mont",
                typeOfOffer = "Rent",
                typeOfEstate = "House",
                etage = "2",
                address = "address",
                zipCode = "",
                city = "Pierre-Feu",
                description = "oé c'est une description",
                addDate = 0,
                sellDate = 0,
                agent = "",
                price = 2500,
                surface = 200,
                nbRooms = 4,
                status = true
            ),
            estatePictures = listOf(EstateMedia(1L, 1L, "HURI", "", "")),
            estateInterestPoints = predefinedInterestPoints
        )

        // Mock the repository to return the desired flow
        every { estateRepository.getFilteredEstates(any()) } returns flowOf(listOf(estateWithDetails))

        // Act
        val result = getAllEstatesWithDetailsUseCase.execute(filter).toList()

        // Assert
        assertEquals(listOf(estateWithDetails), result.first())
    }

    @Test
    fun `execute should return empty list when repository throws exception`() = runTest {
        // Arrange
        val filter = EstateFilter()
        coEvery { estateRepository.getFilteredEstates(filter) } throws Exception()

        // Act
        val result = getAllEstatesWithDetailsUseCase.execute(filter).toList()

        // Assert
        assertEquals(emptyList<EstateWithDetails>(), result.first())
    }
}