package com.example.nyschools.uistate

import com.example.nyschools.model.Score
import javax.inject.Inject

/*Class used to track the state of Score Details UI */
data class ScoreState @Inject constructor(var loadState:SchoolListLoadState = SchoolListLoadState.LOADING, var score: Score?)
