package com.example.teamproject.models

import java.io.Serializable

class Evaluation:Serializable {
    val batch: Batch? = null
    val batchId: Int? = null
    val hardskill: String? = null
    val id: Int? = null
    val rule: String? = null
    val softskill: String? = null
    val student: Student? = null
    val studentId: Int? = null
    val track: Track? = null
    val trackId: Int? = null
}