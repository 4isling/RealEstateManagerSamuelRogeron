package com.example.realestatemanagersamuelrogeron.data.relations

import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia

data class EstateWithPictures(val estate: Estate, val pictures: List<EstateMedia>) {
}