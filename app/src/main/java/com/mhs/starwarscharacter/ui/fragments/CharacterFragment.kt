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
import com.mhs.starwarscharacter.adapter.CharacterAdapter
import com.mhs.starwarscharacter.databinding.FragmentCharacterBinding
import com.mhs.starwarscharacter.utils.DataStatus
import com.mhs.starwarscharacter.utils.initRecycler
import com.mhs.starwarscharacter.utils.isVisible
import com.mhs.starwarscharacter.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CharacterFragment : Fragment() {

    private var _binding : FragmentCharacterBinding?=null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var characterAdapter: CharacterAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        lifecycleScope.launch {
            binding.apply {
                viewModel.getCharacterList()
                viewModel.charactersList.observe(viewLifecycleOwner){
                    when(it.status){
                        DataStatus.Status.LOADING->{
                            pBarLoading.isVisible(true, rvCharacter)
                        }
                        DataStatus.Status.SUCCESS->{
                            pBarLoading.isVisible(false, rvCharacter)
                            characterAdapter.differ.submitList(it.data?.results)
                            Log.e("data", "" + it.data?.results?.get(1)?.name)
                        }
                        DataStatus.Status.ERROR->{
                            pBarLoading.isVisible(false, rvCharacter)
                            Toast.makeText(requireContext(), "There is something wrong!!", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.rvCharacter.initRecycler(LinearLayoutManager(requireContext()), characterAdapter)
    }
}