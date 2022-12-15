package com.example.nyschools.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nyschools.model.School
import com.example.schoolsactivity.databinding.ItemSchoolBinding
import javax.inject.Inject

class SchoolListPagingAdapter @Inject constructor(private val clickListener: SchoolClickListener, diffCallback: DiffUtil.ItemCallback<School>)
    : PagingDataAdapter<School, SchoolListPagingAdapter.SchoolViewHolder>(diffCallback) {

    private lateinit var binding: ItemSchoolBinding

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        val school = getItem(position)!!
        holder.bind(school,clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        binding =  ItemSchoolBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SchoolViewHolder(binding)
    }

    class SchoolViewHolder(private val binding: ItemSchoolBinding) : RecyclerView.ViewHolder(binding.root){

       fun bind(school: School, clickListener: SchoolClickListener){
           binding.school = school
           binding.clickListener = clickListener
       }

    }

    class SchoolClickListener @Inject constructor(val  clickListener: (schoolDbn:String) -> Unit){
        fun onClick(school: School) = clickListener(school.dbn!!)
    }

}