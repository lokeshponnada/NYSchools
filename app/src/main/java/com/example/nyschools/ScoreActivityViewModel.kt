package com.example.nyschools

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nyschools.model.Score
import com.example.nyschools.uistate.SchoolListLoadState
import com.example.nyschools.uistate.ScoreState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScoreActivityViewModel @Inject constructor(private val schoolRepository: SchoolRepository) :ViewModel() {

    private val _scoreStateMutableLiveData : MutableLiveData<ScoreState>  by lazy {
        MutableLiveData<ScoreState>(ScoreState(SchoolListLoadState.LOADING, Score()))
    }
    val scoreStateLiveData = _scoreStateMutableLiveData as LiveData<ScoreState>

    fun getSchoolScore(dbn: String){

        val scoreState = _scoreStateMutableLiveData.value
        scoreState?.loadState = SchoolListLoadState.LOADING

        viewModelScope.launch {
            val score = schoolRepository.getSchoolScore(dbn)
            scoreState?.loadState = if(score != null) SchoolListLoadState.SUCCESS else SchoolListLoadState.FAILURE
            scoreState?.score = score
            _scoreStateMutableLiveData.value = scoreState
        }
    }



}