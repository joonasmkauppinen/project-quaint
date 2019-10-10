package com.newgat.quaint.data.db.entity

import androidx.room.Embedded

data class Prediction(
    val description: String,
    val place_id: String,
    @Embedded
    val structured_formatting: StructuredFormatting
)