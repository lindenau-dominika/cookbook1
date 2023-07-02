package com.example.cookbook1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class RecipeListAdapter(
    context: Context,
    private val recipeList: List<Recipe>
) : ArrayAdapter<Recipe>(context, 0, recipeList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        val recipe = recipeList[position]
        val nameTextView = itemView!!.findViewById<TextView>(R.id.textView)
        nameTextView.text = recipe.name

        return itemView
    }
}