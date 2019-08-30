package com.example.teamproject.models

data class Team(
    val batch: Batch,
    val batchId: Int,
    val endDate: String,
    val id: Int,
    val projectName: String,
    val startDate: String,
    val student: Student,
    val studentId: Int,
    val teamName: String,
    val track: Track,
    val trackId: Int
)