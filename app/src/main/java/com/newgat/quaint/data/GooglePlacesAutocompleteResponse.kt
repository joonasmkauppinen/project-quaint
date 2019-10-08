package com.newgat.quaint.data

data class GooglePlacesAutocompleteResponse(
    val predictions: List<Prediction>,
    val status: String
)