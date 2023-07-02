package com.example.cookbook1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

class RecipePagerFragment : Fragment() {

    private lateinit var viewPager: ViewPager2

    companion object {
        private const val ARG_RECIPES = "recipes"
        private const val ARG_INITIAL_POSITION = "initial_position"

        fun newInstance(recipes: List<Recipe>, initialPosition: Int): RecipePagerFragment {
            val fragment = RecipePagerFragment()
            val args = Bundle()
            args.putParcelableArrayList(ARG_RECIPES, ArrayList(recipes))
            args.putInt(ARG_INITIAL_POSITION, initialPosition)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_pager, container, false)

        // Retrieve the list of recipes and the initial position
        val recipes = arguments?.getParcelableArrayList<Recipe>(ARG_RECIPES)
        val initialPosition = arguments?.getInt(ARG_INITIAL_POSITION) ?: 0

        // Initialize the ViewPager
        viewPager = view.findViewById(R.id.viewPager)
        viewPager.adapter = recipes?.let { RecipePagerAdapter(this, it) }

        // Set the initial item
        viewPager.setCurrentItem(initialPosition, false)

        return view
    }
}
