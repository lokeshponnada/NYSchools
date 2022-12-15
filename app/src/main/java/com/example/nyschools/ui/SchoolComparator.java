package com.example.nyschools.ui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.nyschools.model.School;

import java.util.Objects;

/*  Comparator to minimize list view refresh when appropriate */
public class SchoolComparator extends DiffUtil.ItemCallback<School> {
    @Override
    public boolean areItemsTheSame(@NonNull School oldItem, @NonNull School newItem) {
        return Objects.equals(oldItem.component1(), newItem.component1());
    }

    @Override
    public boolean areContentsTheSame(@NonNull School oldItem, @NonNull School newItem) {
        return false;
    }
}
