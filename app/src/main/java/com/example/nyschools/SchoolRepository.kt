package com.example.nyschools

import com.example.nyschools.global.ScoreCache
import com.example.nyschools.model.Score
import com.example.nyschools.model.School
import com.example.nyschools.network.ApiService
import com.example.nyschools.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/* Data Layer - fetches data from network or local cache */
class SchoolRepository @Inject constructor() {

    private val schoolsApiService: ApiService = RetrofitClient.retrofit.create(ApiService::class.java)

     fun getBackendService() : ApiService{
        return schoolsApiService
    }

    suspend fun getSchoolScore(dbn:String): Score?{
        var score = ScoreCache.getInstance().getItem(dbn)
        if(score == null){
            kotlin.runCatching {
                val scoreResponse = schoolsApiService.getSchoolScore(dbn)
                if(scoreResponse.isNotEmpty() ){
                    score = scoreResponse[0]
                    ScoreCache.getInstance().putItem(dbn,score)
                }
            }

        }else{
           // Score already fetched from cache
        }
        return score
    }

    /*Prefetch scores and store them in  cache so that user will have seamless navigation
    * when they want to see the school details */
    suspend fun fetchScores(schools: List<School>){
        GlobalScope.launch(Dispatchers.IO) {
            repeat(schools.size){
                val school = schools[it]
                if(school.dbn != null && ScoreCache.getInstance().getItem(school.dbn) == null){
                    runCatching{
                        val schoolScore = getSchoolScore(school.dbn)
                        if(schoolScore != null){
                            ScoreCache.getInstance().putItem(school.dbn,schoolScore)
                        }
                    }
                }
            }
        }
    }

}