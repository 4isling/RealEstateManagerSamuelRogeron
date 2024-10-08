package com.example.realestatemanagersamuelrogeron.utils

import com.example.realestatemanagersamuelrogeron.data.relations.EstateInterestPointCrossRef
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia

import java.time.LocalDateTime
import java.time.ZoneOffset

class TestDatabase {
    companion object {
        fun getTestEstates(): List<Estate> {
            val now = LocalDateTime.now()
            return listOf(
                Estate(
                    estateId = 1,
                    title = "Cozy Apartment",
                    typeOfEstate = "Apartment",
                    typeOfOffer = "Rent",
                    price = 1500,
                    surface = 75,
                    nbRooms = 2,
                    address = "123 Test St",
                    city = "TestCity",
                    country = "TestCountry",
                    status = true,
                    addDate = now.minusDays(30).toEpochSecond(ZoneOffset.UTC),
                    sellDate = null,
                    agent = "Agent Smith",
                    description = "A cozy apartment in the heart of TestCity"
                ),
                Estate(
                    estateId = 2,
                    title = "Spacious House",
                    typeOfEstate = "House",
                    typeOfOffer = "Sell",
                    price = 350000,
                    surface = 200,
                    nbRooms = 4,
                    address = "456 Sample Ave",
                    city = "SampleTown",
                    country = "SampleCountry",
                    status = true,
                    addDate = now.minusDays(60).toEpochSecond(ZoneOffset.UTC),
                    sellDate = now.minusDays(10).toEpochSecond(ZoneOffset.UTC),
                    agent = "Agent Johnson",
                    description = "A spacious house with a large garden"
                ),
                Estate(
                    estateId = 3,
                    title = "Modern Loft",
                    typeOfEstate = "Apartment",
                    typeOfOffer = "Sell",
                    price = 250000,
                    surface = 100,
                    nbRooms = 1,
                    address = "789 Urban St",
                    city = "Metropolis",
                    country = "UrbanCountry",
                    status = true,
                    addDate = now.minusDays(15).toEpochSecond(ZoneOffset.UTC),
                    sellDate = null,
                    agent = "Agent Brown",
                    description = "A stylish loft in a trendy neighborhood"
                )
            )
        }

        fun getTestInterestPoints(): List<EstateInterestPoints> = listOf(
            EstateInterestPoints(estateInterestPointId = 1, interestPointsName = "School", iconCode = 12),
            EstateInterestPoints(estateInterestPointId = 2, interestPointsName = "Park", iconCode = 8),
            EstateInterestPoints(estateInterestPointId = 3, interestPointsName = "Subway", iconCode = 7)
        )

        fun getTestEstateInterestPointCrossRef(): List<EstateInterestPointCrossRef> = listOf(
            EstateInterestPointCrossRef(estateId = 1, estateInterestPointId = 1),
            EstateInterestPointCrossRef(estateId = 1, estateInterestPointId = 2),
            EstateInterestPointCrossRef(estateId = 2, estateInterestPointId = 1),
            EstateInterestPointCrossRef(estateId = 2, estateInterestPointId = 3),
            EstateInterestPointCrossRef(estateId = 3, estateInterestPointId = 2),
            EstateInterestPointCrossRef(estateId = 3, estateInterestPointId = 3),
        )

        fun getTestEstateMedia(): List<EstateMedia> = listOf(
            EstateMedia(id = 1, estateId = 1, uri = "content://test/image1.jpg", mimeType = "image/jpeg", name = "Cozy Apartment Living Room"),
            EstateMedia(id = 2, estateId = 1, uri = "content://test/image2.jpg", mimeType = "image/jpeg", name = "Cozy Apartment Kitchen"),
            EstateMedia(id = 3, estateId = 2, uri = "content://test/image3.jpg", mimeType = "image/jpeg", name = "Spacious House Exterior"),
            EstateMedia(id = 4, estateId = 2, uri = "content://test/image4.jpg", mimeType = "image/jpeg", name = "Spacious House Garden"),
            EstateMedia(id = 5, estateId = 3, uri = "content://test/image5.jpg", mimeType = "image/jpeg", name = "Modern Loft Interior")
        )
    }
}