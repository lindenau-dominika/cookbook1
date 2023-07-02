package com.example.cookbook1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailsFragment : Fragment() {

    companion object {
        private const val ARG_ID = "id"
        private const val ARG_NAME = "name"
        private const val ARG_INGREDIENTS = "ingredients"
        private const val ARG_IMAGE_RESOURCE_ID = "imageResourceId"

        fun newInstance(
            id: Int,
            name: String,
            ingredients: String,
            imageResourceId: Int
        ): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putInt(ARG_ID, id)
            args.putString(ARG_NAME, name)
            args.putString(ARG_INGREDIENTS, ingredients)
            args.putInt(ARG_IMAGE_RESOURCE_ID, imageResourceId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)

        val id = arguments?.getInt(ARG_ID)
        val name = arguments?.getString(ARG_NAME)
        val ingredients = arguments?.getString(ARG_INGREDIENTS)
        val imageResourceId = arguments?.getInt(ARG_IMAGE_RESOURCE_ID)

        val imageView = view.findViewById<ImageView>(R.id.imageView)
        imageResourceId?.let {
            imageView.setImageResource(it)
        }

        val fab: FloatingActionButton = view.findViewById(R.id.shareFab)
        fab.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, ingredients)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inicjalizacja TimerFragment
        val timerFragment = TimerFragment.newInstance()
        // Dodaj TimerFragment do timerContainer
        childFragmentManager.beginTransaction()
            .replace(R.id.timerContainer, timerFragment)
            .commit()
    }

}
