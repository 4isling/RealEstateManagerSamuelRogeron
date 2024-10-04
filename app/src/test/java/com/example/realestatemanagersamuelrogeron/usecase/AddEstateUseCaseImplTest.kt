package com.example.realestatemanagersamuelrogeron.usecase

import android.content.Context
import android.net.Uri
import com.example.realestatemanagersamuelrogeron.data.relations.EstateInterestPointCrossRef
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddEstateUseCaseImpl
import com.example.realestatemanagersamuelrogeron.utils.FileTypeHelper
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class AddEstateUseCaseImplTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var addEstateUseCase: AddEstateUseCaseImpl

    @Mock
    private lateinit var estateRepository: EstateRepository

    @Mock
    private lateinit var context: Context

    @Before
    fun setUp() {
        addEstateUseCase = AddEstateUseCaseImpl(estateRepository, context)
    }

    @Test
    fun `invoke should add estate and return estateId`() = runTest {
        // Arrange
        val estate = Estate(
            estateId = 0L,
            title = "La forge D'Entre Mont",
            typeOfOffer = "Price",
            typeOfEstate = "House",
            etage = "2",
            address = "24 Route De La Vallée",
            zipCode = "06910",
            region = "PACA",
            country = "France",
            city = "Pierrefeu",
            description = "",
            addDate = System.currentTimeMillis(),
            sellDate = null,
            agent = "Stephane",
            price = 250000,
            surface = 200,
            nbRooms = 4,
            status = true,
            lat = 43.5,
            lng = 6.7
        )
        val interestPoints = listOf(
            EstateInterestPoints(
                estateInterestPointId = 1,
                interestPointsName = "ipn1",
                iconCode = 1
            ),
            EstateInterestPoints(
                estateInterestPointId = 2,
                interestPointsName = "ipn2",
                iconCode = 2
            )
        )
        val pics = listOf(Uri.parse("file://dummy1"), Uri.parse("file://dummy2"))
        val mimeType = "image/jpeg"
        val expectedEstateId = 0L

        whenever(estateRepository.addEstate(estate)).thenReturn(expectedEstateId)
        whenever(FileTypeHelper.getMimeType(context, pics[0])).thenReturn(mimeType)
        whenever(FileTypeHelper.getMimeType(context, pics[1])).thenReturn(mimeType)

        // Act
        val result = addEstateUseCase.invoke(estate, interestPoints, pics).first()

        // Assert
        assertEquals(expectedEstateId, result)
        verify(estateRepository).addEstate(estate)
        interestPoints.forEach { point ->
            verify(estateRepository).addEstateInterestPointCrossRef(
                EstateInterestPointCrossRef(expectedEstateId, point.estateInterestPointId)
            )
        }
        pics.forEachIndexed { index, pic ->
            verify(estateRepository).addEstatePicture(
                EstateMedia(
                    estateId = expectedEstateId,
                    uri = pic.toString(),
                    mimeType = mimeType,
                    name = "e${expectedEstateId}p${index + 1}"
                )
            )
        }
    }

    @Test
    fun `invoke should return -1 when exception is thrown while adding estate`() = runTest {
        // Arrange
        val estate = Estate(
            estateId = 0,
            title = "La forge D'Entre Mont",
            typeOfOffer = "Price",
            typeOfEstate = "House",
            etage = "2",
            address = "24 Route De La Vallée",
            zipCode = "06910",
            region = "PACA",
            country = "France",
            city = "Pierrefeu",
            description = "",
            addDate = System.currentTimeMillis(),
            sellDate = null,
            agent = "Stephane",
            price = 250000,
            surface = 200,
            nbRooms = 4,
            status = true,
            lat = 43.5,
            lng = 6.7)
        val interestPoints = listOf<EstateInterestPoints>()
        val pics = listOf<Uri>()

        whenever(estateRepository.addEstate(estate)).thenThrow(RuntimeException("Error adding estate"))

        // Act
        val result = addEstateUseCase.invoke(estate, interestPoints, pics).first()

        // Assert
        assertEquals(-1L, result)
        verify(estateRepository).addEstate(estate)
    }

    @Test
    fun `invoke should throw exception when adding estate interest points fails`() = runTest {
        // Arrange
        val estate = Estate(
            estateId = 0,
            title = "La forge D'Entre Mont",
            typeOfOffer = "Price",
            typeOfEstate = "House",
            etage = "2",
            address = "24 Route De La Vallée",
            zipCode = "06910",
            region = "PACA",
            country = "France",
            city = "Pierrefeu",
            description = "",
            addDate = System.currentTimeMillis(),
            sellDate = null,
            agent = "Stephane",
            price = 250000,
            surface = 200,
            nbRooms = 4,
            status = true,
            lat = 43.5,
            lng = 6.7)
        val interestPoints = listOf(EstateInterestPoints(
            estateInterestPointId = 1,
            interestPointsName = "ipn1",
            iconCode = 1))
        val pics = listOf<Uri>()
        val estateId = 123L

        whenever(estateRepository.addEstate(estate)).thenReturn(estateId)
        whenever(estateRepository.addEstateInterestPointCrossRef(any())).thenThrow(
            RuntimeException(
                "Error adding interest point"
            )
        )

        // Act & Assert
        assertThrows<RuntimeException> {
            runTest(UnconfinedTestDispatcher()) {
                addEstateUseCase.invoke(estate, interestPoints, pics).first()
            }
        }
        verify(estateRepository).addEstate(estate)
        verify(estateRepository).addEstateInterestPointCrossRef(any())
    }

    @Test
    fun `invoke should throw exception when adding estate pictures fails`() = runTest {
        // Arrange
        val estate = Estate(
            estateId = 0,
            title = "La forge D'Entre Mont",
            typeOfOffer = "Price",
            typeOfEstate = "House",
            etage = "2",
            address = "24 Route De La Vallée",
            zipCode = "06910",
            region = "PACA",
            country = "France",
            city = "Pierrefeu",
            description = "",
            addDate = System.currentTimeMillis(),
            sellDate = null,
            agent = "Stephane",
            price = 250000,
            surface = 200,
            nbRooms = 4,
            status = true,
            lat = 43.5,
            lng = 6.7)
        val interestPoints = listOf<EstateInterestPoints>()
        val pics = listOf(Uri.parse("file://dummy1"))
        val estateId = 123L
        val mimeType = "image/jpeg"

        whenever(estateRepository.addEstate(estate)).thenReturn(estateId)
        whenever(FileTypeHelper.getMimeType(context, pics[0])).thenReturn(mimeType)
        whenever(estateRepository.addEstatePicture(any())).thenThrow(RuntimeException("Error adding picture"))

        // Act & Assert
        assertThrows<RuntimeException> {
            runTest(UnconfinedTestDispatcher()) {
                addEstateUseCase.invoke(estate, interestPoints, pics).first()
            }
        }
        verify(estateRepository).addEstate(estate)
        verify(estateRepository).addEstatePicture(any())
    }
}
