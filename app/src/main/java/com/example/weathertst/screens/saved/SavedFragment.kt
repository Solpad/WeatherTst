package com.example.weathertst.screens.saved

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.models.geocoding.LocationResponseItem
import com.example.weathertst.MainActivity
import com.example.weathertst.R
import com.example.weathertst.adapters.SavedAdapter
import com.example.weathertst.adapters.SearchAdapter
import com.example.weathertst.databinding.FragmentSavedBinding
import com.example.weathertst.databinding.FragmentSearchBinding
import com.example.weathertst.databinding.FragmentWeekBinding
import com.example.weathertst.screens.search.SearchFragmentViewModel
import kotlinx.android.synthetic.main.fragment_saved.*
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectOutputStream

class SavedFragment : Fragment() {

    private var _binding: FragmentSavedBinding? = null
    private val mBinding get() = _binding!!
    lateinit var savedAdapter: SavedAdapter
    private lateinit var mViewModel: SavedFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedBinding.inflate(layoutInflater,container,false)
        mViewModel = ViewModelProvider(this)[SavedFragmentViewModel::class.java]

        mViewModel.printRepo()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = ViewModelProvider(this)[SavedFragmentViewModel::class.java]
        setupRecyclerView()

        go_search_locations_in_search_fragment.setOnClickListener {
            findNavController().navigate(R.id.action_savedFragment_to_searchFragment)
        }

        back_to_current_weather.setOnClickListener {
            findNavController().navigate(R.id.action_savedFragment_to_mainFragment)
        }

        savedAdapter.setOnItemClickListener { location ->
            mViewModel.selectedLocation = MutableLiveData(location)
            saveToFile(activity as MainActivity, location)
            findNavController().navigate(R.id.action_savedFragment_to_mainFragment)
        }

    }

    private fun setupRecyclerView() {
        savedAdapter = SavedAdapter()
        rv_saved_locations.apply {
            adapter = savedAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    fun saveToFile(context: Context, location: LocationResponseItem) {
        try {
            val fileOutputStream: FileOutputStream =
                context.openFileOutput("fileName", Context.MODE_PRIVATE)
            val objectOutputStream = ObjectOutputStream(fileOutputStream)
            objectOutputStream.writeObject(location)
            objectOutputStream.close()
            fileOutputStream.close()
        } catch (e: IOException) {
            Log.d("qwert", "file not save")
            e.printStackTrace()
        }
    }


}