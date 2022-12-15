package com.example.nyschools

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.nyschools.network.SchoolPagingSource
import com.example.nyschools.ui.SchoolListPagingAdapter
import com.example.nyschools.uistate.SchoolListLoadState
import com.example.nyschools.uistate.SchoolListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SchoolListViewModel @Inject constructor(private val schoolPagingSource: SchoolPagingSource)  : ViewModel(){

     val schoolListStateLiveData : MutableLiveData<SchoolListState>  by lazy {
        MutableLiveData<SchoolListState>(SchoolListState(SchoolListLoadState.LOADING,""))
    }

    val flow = Pager(PagingConfig(pageSize = 10)){
        schoolPagingSource
    }.flow.cachedIn(viewModelScope)


    fun retryLoadingList(){
        // todo -  access adapter and retry
    }

    fun loadingStateChanged(loadState: CombinedLoadStates,schoolListPagingAdapter: SchoolListPagingAdapter) {

        val schoolListState = schoolListStateLiveData.value

        if(schoolListPagingAdapter.itemCount < 1 && loadState.source.refresh is LoadState.Loading){
            // waiting for initial load , show loading
            schoolListState?.loadState = SchoolListLoadState.LOADING
        }else if(loadState.source.refresh is LoadState.Error && schoolListPagingAdapter.itemCount < 1) {
            // show error
            schoolListState?.loadState = SchoolListLoadState.FAILURE
        }else{
            // show list
            schoolListState?.loadState = SchoolListLoadState.SUCCESS
        }

        schoolListStateLiveData.value = schoolListState
    }

}