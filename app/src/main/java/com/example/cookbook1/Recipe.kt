package com.example.cookbook1
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: Int,
    val name: String,
    val ingredients: String,
    val imageResourceId: Int
) :Parcelable