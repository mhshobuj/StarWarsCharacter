package com.mhs.starwarscharacter.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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

@AndroidEntryPoint
class CharacterFragment : Fragment() {

    private var _binding : FragmentCharacterBinding?=null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()
    private var page = 1
    private var status = false
    private var characterAdapter: CharacterAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterAdapter = CharacterAdapter(requireContext())
        setUpRecyclerView()
        getCharacterList(page)

        binding.nsScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val totalHeight = binding.nsScrollView.getChildAt(0).height
            val scrollViewHeight = v.height
            if (scrollY == totalHeight - scrollViewHeight) {
                page++
                Log.e("page", "$page")
                // Do something when scrolled to the bottom
                if (!status) {
                    getCharacterList(page)
                }
            }
        }
    }

    private fun getCharacterList(page: Int) {
        lifecycleScope.launch {
            binding.apply {
                viewModel.getCharacterList(page)
                viewModel.charactersList.observe(viewLifecycleOwner){
                    when(it.status){
                        DataStatus.Status.LOADING->{
                            pBarLoading.isVisible(true, rvCharacter)
                        }
                        DataStatus.Status.SUCCESS->{
                            pBarLoading.isVisible(false, rvCharacter)
                            if (it.data?.next != null) {
                                characterAdapter?.submitData(it.data?.results!!)
                            }
                            else{
                                status = true
                                Toast.makeText(requireContext(), "That's all the data..", Toast.LENGTH_LONG).show()
                            }
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
        binding.rvCharacter.initRecycler(LinearLayoutManager(requireContext()), characterAdapter!!)
    }
}