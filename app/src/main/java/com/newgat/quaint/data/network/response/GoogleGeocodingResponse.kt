package com.newgat.quaint.data.network.response


import com.newgat.quaint.data.db.entity.Result

data class GoogleGeocodingResponse(
    val results: List<Result>,
    val status: String
)