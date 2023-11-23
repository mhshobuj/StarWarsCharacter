package com.mhs.starwarscharacter.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mhs.starwarscharacter.adapter.StarShipAdapter
import com.mhs.starwarscharacter.databinding.FragmentStarShipBinding
import com.mhs.starwarscharacter.db.StarWarDatabase
import com.mhs.starwarscharacter.entity.starShip.StarShipListDB
import com.mhs.starwarscharacter.response.starShip.StarShipList
import com.mhs.starwarscharacter.utils.DataStatus
import com.mhs.starwarscharacter.utils.NetworkChecking
import com.mhs.starwarscharacter.utils.initRecycler
import com.mhs.starwarscharacter.utils.isVisible
import com.mhs.starwarscharacter.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StarShipFragment : Fragment() {

    private var _binding: FragmentStarShipBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()
    private var page = 1
    private var status = false
    private var starShipAdapter: StarShipAdapter? = null
    private var connectivityStatus: String? = null
    private lateinit var starWarDatabase: StarWarDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStarShipBinding.inflate(layoutInflater)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Network checking
        val networkChecking = NetworkChecking
        connectivityStatus = networkChecking.getConnectivityStatusString(requireContext())

        // Initialize the Room database
        starWarDatabase = StarWarDatabase.getDatabase(requireContext())

        starShipAdapter = StarShipAdapter(requireContext())
        setUpRecyclerView()
        GlobalScope.launch {
            getStarShipList(page)
        }

        // Set up scroll listener for infinite scrolling
        binding.nsScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val totalHeight = binding.nsScrollView.getChildAt(0).height
            val scrollViewHeight = v.height
            if (scrollY == totalHeight - scrollViewHeight) {
                page++
                Log.e("page", "$page")
                // Do something when scrolled to the bottom
                if (!status) {
                    GlobalScope.launch {
                        if (connectivityStatus == "Wifi enabled" || connectivityStatus == "Mobile data enabled") {
                            getStarShipList(page)
                        }
                    }
                }
            }
        }
    }

    // Coroutine function to fetch starship list
    private suspend fun getStarShipList(page: Int) {
        if (connectivityStatus == "Wifi enabled" || connectivityStatus == "Mobile data enabled") {
            lifecycleScope.launch {
                binding.apply {
                    viewModel.getStarShipList(page)
                    viewModel.starShipList.observe(viewLifecycleOwner) {
                        when (it.status) {
                            DataStatus.Status.LOADING -> {
                                pBarLoading.isVisible(true, rvStarShip)
                            }

                            DataStatus.Status.SUCCESS -> {
                                pBarLoading.isVisible(false, rvStarShip)
                                if (it.data?.next != null) {
                                    starShipAdapter?.submitData(it.data?.results!!)

                                    // Save starships to the local database
                                    GlobalScope.launch {
                                        it.data?.results?.let { starShipList ->
                                            val starShips = starShipList.map { starShipResult ->
                                                StarShipListDB(
                                                    name = starShipResult.name,
                                                    model = starShipResult.model,
                                                    costInCredits = starShipResult.costInCredits,
                                                    url = starShipResult.url
                                                )
                                            }
                                            starWarDatabase.starWarDao().addStarShip(starShips)
                                        }
                                    }
                                } else {
                                    status = true
                                    Toast.makeText(
                                        requireContext(),
                                        "That's all the data..",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }

                            DataStatus.Status.ERROR -> {
                                pBarLoading.isVisible(false, rvStarShip)
                                Toast.makeText(
                                    requireContext(),
                                    "There is something wrong!!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
        } else {
            // Fetch starships from the local database if there is no internet connection
            binding.pBarLoading.isVisible(false, binding.rvStarShip)
            val starShips = starWarDatabase.starWarDao().getStarWarList()
            val resultsList = starShips.map { starShip ->
                StarShipList.Result(
                    cargoCapacity = "",
                    consumables = "",
                    costInCredits = starShip.costInCredits,
                    created = "",
                    crew = "",
                    edited = "",
                    films = emptyList(),
                    hyperdriveRating = "",
                    length = "",
                    mGLT = "",
                    manufacturer = "",
                    maxAtmospheringSpeed = "",
                    model = starShip.model,
                    name = starShip.name,
                    passengers = "",
                    pilots = emptyList(),
                    starshipClass = "",
                    url = starShip.url
                )
            }
            val starShipList = StarShipList(1, "", "", resultsList)
            starShipAdapter?.submitData(starShipList.results)
        }
    }

    // Set up RecyclerView with the starship adapter
    private fun setUpRecyclerView() {
        binding.rvStarShip.initRecycler(LinearLayoutManager(requireContext()), starShipAdapter!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clean up the binding variable to avoid memory leaks
        _binding = null
    }
}