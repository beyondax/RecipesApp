package com.example.recipesapp.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipesapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    OnRecipeListener mOnRecipeListener;
    CircleImageView mCategoryCircleImageView;
    TextView mCategoryTitleTextView;


    public CategoryViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener) {
        super(itemView);

        mOnRecipeListener = onRecipeListener;
        mCategoryCircleImageView = itemView.findViewById(R.id.category_image);
        mCategoryTitleTextView = itemView.findViewById(R.id.category_title);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mOnRecipeListener.onCategoryClick(mCategoryTitleTextView.getText().toString());
    }
}
