package com.example.nyschools.network

import com.example.nyschools.model.School
import com.example.nyschools.model.Score
import retrofit2.http.GET
import retrofit2.http.Query


// schoolsUrl : https://data.cityofchicago.org/resource/f7f2-ggz5.json?$limit=100&$offset=100
// scoreurl: https://data.cityofnewyork.us/resource/f9bf-2cp4.json?dbn=11X253


interface ApiService {

    @GET("/resource/s3k6-pzi2.json")
    suspend fun getSchools(@Query("\$limit", encoded = true) limit:Int,  @Query("\$offset", encoded = true) nextPageStartingItemNumber: Int) : List<School>

    @GET("/resource/f9bf-2cp4.json")
    suspend fun getSchoolScore(@Query("dbn") dbn:String): List<Score>


}