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
import com.mhs.starwarscharacter.adapter.PlanetAdapter
import com.mhs.starwarscharacter.databinding.FragmentPlanetBinding
import com.mhs.starwarscharacter.db.StarWarDatabase
import com.mhs.starwarscharacter.utils.DataStatus
import com.mhs.starwarscharacter.utils.NetworkChecking
import com.mhs.starwarscharacter.utils.initRecycler
import com.mhs.starwarscharacter.utils.isVisible
import com.mhs.starwarscharacter.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlanetFragment : Fragment() {

    private var _binding : FragmentPlanetBinding?=null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()
    private var page = 1
    private var status = false
    private var planetAdapter: PlanetAdapter? = null
    private var connectivityStatus: String? = null
    private lateinit var starWarDatabase: StarWarDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPlanetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Network checking */
        val networkChecking = NetworkChecking
        connectivityStatus = networkChecking.getConnectivityStatusString(requireContext())

        //initialize database
        starWarDatabase = StarWarDatabase.getDatabase(requireContext())

        planetAdapter = PlanetAdapter(requireContext())
        setUpRecyclerView()
        GlobalScope.launch {
            getPlanetList(page)
        }

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
                            getPlanetList(page)
                        }
                    }
                }
            }
        }
    }

    private fun getPlanetList(page: Int) {
        if (connectivityStatus == "Wifi enabled" || connectivityStatus == "Mobile data enabled") {
            lifecycleScope.launch {
                binding.apply {
                    viewModel.getPlanetsList(page)
                    viewModel.planetsList.observe(viewLifecycleOwner) {
                        when (it.status) {
                            DataStatus.Status.LOADING -> {
                                pBarLoading.isVisible(true, rvPlanet)
                            }

                            DataStatus.Status.SUCCESS -> {
                                pBarLoading.isVisible(false, rvPlanet)
                                if (it.data?.next != null) {
                                    planetAdapter?.submitData(it.data?.results!!)

                                    /*GlobalScope.launch {
                                        it.data?.results?.let { characterList ->
                                            val characters = characterList.map { characterResult ->
                                                CharacterListDB(
                                                    gender = characterResult.gender,
                                                    height = characterResult.height,
                                                    name = characterResult.name,
                                                    url = characterResult.url
                                                )
                                            }
                                            starWarDatabase.starWarDao().addCharacter(characters)
                                        }
                                    }*/
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
                                pBarLoading.isVisible(false, rvPlanet)
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
        } else{
            /*binding.pBarLoading.isVisible(false, binding.rvCharacter)
            val characters = starWarDatabase.starWarDao().getCharacterList()
            val resultsList = characters.map { character ->
                CharacterList.Result(
                    birthYear = "",
                    created = "",
                    edited = "",
                    eyeColor = "",
                    films = emptyList(),
                    gender = character.gender,
                    hairColor = "",
                    height = character.height,
                    homeworld = "",
                    mass = "",
                    name = character.name,
                    skinColor = "",
                    species = emptyList(),
                    starships = emptyList(),
                    url = character.url,
                    vehicles = emptyList()
                )
            }
            val characterList = CharacterList(1,"", "", resultsList)
            characterAdapter?.submitData(characterList.results)*/
        }

    }

    private fun setUpRecyclerView() {
        binding.rvPlanet.initRecycler(LinearLayoutManager(requireContext()), planetAdapter!!)
    }
}