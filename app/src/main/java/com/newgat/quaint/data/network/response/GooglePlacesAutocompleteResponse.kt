package com.newgat.quaint.data.network.response

import com.newgat.quaint.data.db.entity.Prediction

data class GooglePlacesAutocompleteResponse(
    val predictions: List<Prediction>,
    val status: String
)