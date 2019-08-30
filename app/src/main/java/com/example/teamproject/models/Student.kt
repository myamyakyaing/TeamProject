package com.example.teamproject.models

import java.util.*

data class Student(
    val address: String,
    val batchId: Int,
    val dob: Date,
    val email: String,
    val facebookAccount: String,
    val gender: String,
    val id: Int,
    val image: String,
    val name: String,
    val nrc: String,
    val phone: String,
    val qualification: String,
    val trackId: Int,
    val batch: Batch,
    val track: Track
)