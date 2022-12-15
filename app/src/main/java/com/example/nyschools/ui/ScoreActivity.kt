package com.example.nyschools.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.nyschools.ScoreActivityViewModel
import com.example.nyschools.global.Constants
import com.example.schoolsactivity.R
import com.example.schoolsactivity.databinding.ActivitySchoolScoreBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ScoreActivity : AppCompatActivity() {

    private lateinit var viewModel: ScoreActivityViewModel
    private lateinit var schoolDbn:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_score)

        val dbn  = intent.getStringExtra(Constants.DBN)
        if(dbn == null){
            Toast.makeText(this,"Error Occured",Toast.LENGTH_LONG).show()
            // Log this error to server
            Log.d("nycschools","Invalid ID")
            finish()
        }else{
            schoolDbn = dbn
        }

        viewModel = ViewModelProvider(this)[ScoreActivityViewModel::class.java]

        val binding: ActivitySchoolScoreBinding = DataBindingUtil.setContentView(this, R.layout.activity_school_score)
        binding.lifecycleOwner = this
        binding.scoreViewModel = viewModel


        lifecycleScope.launch {
            viewModel.getSchoolScore(schoolDbn)
        }

    }

}