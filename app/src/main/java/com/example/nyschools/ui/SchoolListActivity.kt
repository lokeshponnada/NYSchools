package com.example.nyschools.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nyschools.SchoolListViewModel
import com.example.nyschools.global.Constants
import com.example.schoolsactivity.R
import com.example.schoolsactivity.databinding.ActivitySchoolListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SchoolListActivity : AppCompatActivity() {

    private lateinit var viewModel: SchoolListViewModel
    private val clickListener = SchoolListPagingAdapter.SchoolClickListener {
        navigateToDetailActivity(it)
    }
    var schoolListPagingAdapter: SchoolListPagingAdapter = SchoolListPagingAdapter(clickListener,SchoolComparator())
    private lateinit var schoolRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_list)

        viewModel = ViewModelProvider(this)[SchoolListViewModel::class.java]

        val binding: ActivitySchoolListBinding = DataBindingUtil.setContentView(this, R.layout.activity_school_list)
        binding.lifecycleOwner = this
        binding.schoolListViewModel = viewModel

        initRecyclerView()

        listentoLoadStateUpdates()

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                schoolListPagingAdapter.submitData(pagingData)
            }
        }


    }

    private fun listentoLoadStateUpdates() {
        schoolListPagingAdapter.addLoadStateListener {
            viewModel.loadingStateChanged(it,schoolListPagingAdapter)
        }
    }


    private fun initRecyclerView(){
        schoolRecyclerView = findViewById(R.id.schoolsListView)
        val layoutManager = LinearLayoutManager(this)
        schoolRecyclerView.layoutManager = layoutManager

        with(schoolListPagingAdapter){
            schoolRecyclerView.adapter = withLoadStateHeaderAndFooter(
                header = SchoolLoadingStateAdapter(this),
                footer = SchoolLoadingStateAdapter(this)
            )
        }

    }

    private fun navigateToDetailActivity(schoolDbn: String){
        val detailAct = Intent(this,ScoreActivity::class.java)
        detailAct.putExtra(Constants.DBN,schoolDbn)
        startActivity(detailAct)
    }
}