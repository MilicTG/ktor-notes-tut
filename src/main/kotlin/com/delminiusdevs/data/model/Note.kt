package com.delminiusdevs.data.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Note(
    @BsonId
    val id: String = ObjectId().toString(),
    val title: String,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val owner: String = "",
)
