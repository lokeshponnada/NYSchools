package com.example.nyschools.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nyschools.SchoolRepository
import com.example.nyschools.model.School
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SchoolPagingSource @Inject constructor(private val dataService: SchoolRepository) : PagingSource<Int, School>() {

    private val PAGE_LIMIT = 5

    override fun getRefreshKey(state: PagingState<Int, School>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(PAGE_LIMIT)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(PAGE_LIMIT)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, School> {
        try{
            val pageStartingItemNumber = params.key ?: 0
            val response = dataService.getBackendService().getSchools(PAGE_LIMIT,pageStartingItemNumber)
            val nextKey = if(response.isEmpty()){
                null
            }else{
                pageStartingItemNumber+PAGE_LIMIT
            }
            // The below line is executed async on another thread
            dataService.fetchScores(response)
            return LoadResult.Page(
                data = response ,
                prevKey = null ,
                nextKey = nextKey)
        }catch (e: IOException) {
            // IOException for network failures. , needs to be logged
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes., needs to be logged
            return LoadResult.Error(e)
        }

    }
}