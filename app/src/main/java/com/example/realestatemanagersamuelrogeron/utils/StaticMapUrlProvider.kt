package com.example.realestatemanagersamuelrogeron.utils

import com.example.realestatemanagersamuelrogeron.BuildConfig

class StaticMapUrlProvider {
    companion object {
        private const val BASE_URL = "https://maps.googleapis.com/maps/api/staticmap"
        private const val API_KEY = BuildConfig.MAPS_API_KEY //@TODO get api key

        fun getStaticMapUrl(latitude: Double, longitude: Double): String {
            val marker = "markers=color:red%7C$latitude,$longitude"
            val size = "size=600x400"
            val zoom = "zoom=15"
            val apiKey = "key=$API_KEY"

            return "$BASE_URL?$marker&$size&$zoom&$apiKey"
        }
    }
}