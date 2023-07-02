package com.example.cookbook1

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class RecipePagerAdapter(fa: RecipePagerFragment, private val recipes: List<Recipe>) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = recipes.size

    override fun createFragment(position: Int): Fragment {
        val recipe = recipes[position]
        return DetailsFragment.newInstance(
            id = recipe.id,
            name = recipe.name,
            ingredients = recipe.ingredients,
            imageResourceId = recipe.imageResourceId
        )
    }
}
