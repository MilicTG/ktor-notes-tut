package com.delminiusdevs.data.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class User(
    @BsonId
    val id: String = ObjectId().toString(),
    val userName: String,
    val email: String,
    val password: String,
)
