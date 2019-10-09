package com.newgat.quaint.data.network.response


import com.newgat.quaint.data.db.entity.Result

data class GoogleGeocodeResponse(
    val results: List<Result>,
    val status: String
)