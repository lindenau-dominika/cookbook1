package com.example.cookbook1

//import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import android.widget.Button
//import android.widget.EditText
import android.widget.ImageView
//import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment() {

    private var listener: OnItemSelectedListener? = null
    private lateinit var recipeList: List<Recipe>
    private lateinit var filteredRecipeList: List<Recipe>

    private var selectedFilters: MutableList<Filter> = mutableListOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter

    companion object {
        private const val ARG_RECIPES = "recipes"

        fun newInstance(recipes: List<Recipe>): ListFragment {
            val fragment = ListFragment()
            val args = Bundle()
            args.putParcelableArrayList(ARG_RECIPES, ArrayList(recipes))
            fragment.arguments = args
            return fragment
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnItemSelectedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnItemSelectedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val recipeArray = arguments?.getParcelableArrayList<Recipe>(ARG_RECIPES)
        recipeList = recipeArray ?: listOf()

        filteredRecipeList = recipeList // Initialize filteredRecipeList with all recipes initially

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2) // 2 columns in the grid
        adapter = RecipeAdapter(requireContext(), filteredRecipeList)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener { recipe ->
            listener?.onItemSelected(recipe)
        }

        return view
    }

    interface OnItemSelectedListener {
        fun onItemSelected(item: Recipe)
    }
}

class RecipeAdapter(
    private val context: Context,
    private var recipes: List<Recipe>
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var onItemClickListener: ((Recipe) -> Unit)? = null

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)

        fun bind(recipe: Recipe) {
            imageView.setImageResource(recipe.imageResourceId)
            nameTextView.text = recipe.name

            itemView.setOnClickListener {
                onItemClickListener?.invoke(recipe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int = recipes.size

    fun setOnItemClickListener(listener: (Recipe) -> Unit) {
        onItemClickListener = listener
    }

    fun updateRecipes(updatedRecipes: List<Recipe>) {
        recipes = updatedRecipes
        notifyDataSetChanged()
    }
}

data class Filter(val filterType: String, val filterValue: String)
