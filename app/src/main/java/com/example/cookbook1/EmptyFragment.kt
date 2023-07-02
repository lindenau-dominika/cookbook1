package com.example.cookbook1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class EmptyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Tworzenie widoku dla pustego fragmentu
        return inflater.inflate(R.layout.fragment_empty, container, false)
    }
}
