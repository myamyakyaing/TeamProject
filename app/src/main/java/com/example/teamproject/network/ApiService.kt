package com.example.teamproject.network

import com.example.teamproject.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("students")
    fun getAllStudent(): Call<List<StudentList>>
    @GET("students/{id}")
    fun getIndevidualStudent(@Path("id") id: Int): Call<StudentList>
    @POST("students")
    fun sendStudentData(@Body data: StudentData) : Call<StudentList>

    @DELETE("students/{id}")
    fun deleteIndevidualStudent(@Path("id") id: Int): Call<StudentList>

    @GET("trainers")
    fun getAllTrainer(): Call<List<Trainer>>

    @GET("trainers/{id}")
    fun getIndevidualTrainer(@Path("id") id: Int): Call<Trainer>

    @POST("trainers")
    fun sendTrainerData(@Body data: TrainerData): Call<Trainer>

    @GET("trainers/{id}")
    fun deleteIndevidualTrainer(@Path("id") id: Int): Call<Trainer>


    @GET("teams")
    fun getAllTeam(): Call<List<Team>>
    @GET("teams/{id}")
    fun deleteIndevidualTeam(@Path("id") id: Int): Call<Team>

    @GET("courses")
    fun getAllCourseDetail(): Call<List<CourseDetail>>
    @GET("courses/{id}")
    fun getIndevidualCourse(@Path("id") id: Int): Call<List<CourseDetail>>
    @GET("courses/{id}")
    fun deleteIndevidualCourse(@Path("id") id: Int): Call<CourseDetail>
    @POST("courses")
    fun sendCourseData(@Body data: CourseData): Call<CourseDetail>

    @GET("admins")
    fun getAllAdmin(): Call<List<Admin>>

    @GET("evaluations")
    fun getAllEvaluation(): Call<List<Evaluation>>

    @POST("evaluations")
    fun sendEvaluationData(@Body data: EvaluationData): Call<Evaluation>
}