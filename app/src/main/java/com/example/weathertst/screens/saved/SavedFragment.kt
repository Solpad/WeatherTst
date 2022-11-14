package com.example.weathertst.screens.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.weathertst.R
import com.example.weathertst.databinding.FragmentSavedBinding
import com.example.weathertst.databinding.FragmentWeekBinding
import kotlinx.android.synthetic.main.fragment_saved.*

class SavedFragment : Fragment() {

    private var _binding: FragmentSavedBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedBinding.inflate(layoutInflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        go_search_locations_in_search_fragment.setOnClickListener {
            findNavController().navigate(R.id.action_savedFragment_to_searchFragment)
        }

        back_to_current_weather.setOnClickListener {
            findNavController().navigate(R.id.action_savedFragment_to_mainFragment)
        }
    }

}