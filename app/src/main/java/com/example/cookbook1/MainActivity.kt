package com.example.cookbook1
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ListFragment.OnItemSelectedListener {

    private lateinit var recipeList: List<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize your recipe list here
        recipeList = listOf(
            Recipe(0, "Chana Masala", "Ciecierzyca, ryż, mleko kokosowe", R.drawable.chana_masala),
            Recipe(1, "Tikka Masala", "Ingredients 2", R.drawable.tikka_masala),
            Recipe(2, "Butter Chicken", "Ingredients 3", R.drawable.butter_chicken)
            // Add more recipes as needed
        )

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ListFragment.newInstance(recipeList))
                .commit()
        }
    }

    override fun onItemSelected(item: Recipe) {
        val recipePagerFragment = RecipePagerFragment.newInstance(recipeList, item.id)
        println("Clicked recipe: $item.id")
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, recipePagerFragment)
            .addToBackStack(null)
            .commit()
    }
}
