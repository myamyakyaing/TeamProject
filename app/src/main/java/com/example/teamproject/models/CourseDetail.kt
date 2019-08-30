package com.example.teamproject.models

import java.io.Serializable
import java.util.*

class CourseDetail:Serializable {
     val id: Int? = null
     val batchId: Int? = null
     val trackId: Int? = null
     val courseName: String? = null
     val startDate: String? = null
     val endDate: String? = null
     val batch: Batch? = null
     val track: Track? = null
     val teachingDay: String? = null
}
