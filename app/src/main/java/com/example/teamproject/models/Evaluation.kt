package com.example.teamproject.models

data class Evaluation(
    val batch: Batch,
    val batchId: Int,
    val hardskill: String,
    val id: Int,
    val rule: String,
    val softskill: String,
    val student: Student,
    val studentId: Int,
    val track: Track,
    val trackId: Int
)