package com.mhs.starwarscharacter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhs.starwarscharacter.repository.MainRepository
import com.mhs.starwarscharacter.response.character.CharacterDetails
import com.mhs.starwarscharacter.response.character.CharacterList
import com.mhs.starwarscharacter.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    //getCharacterList
    private val _charactersList = MutableLiveData<DataStatus<CharacterList>>()
    val charactersList: LiveData<DataStatus<CharacterList>> get() = _charactersList

    fun getCharacterList(page: Int) = viewModelScope.launch {
        mainRepository.getCharacterList(page).collect {
            _charactersList.value = it
        }
    }

    //getCharacterDetails
    private val _characterDetails = MutableLiveData<DataStatus<CharacterDetails>>()
    val characterDetails: LiveData<DataStatus<CharacterDetails>> get() = _characterDetails

    fun getCharacterDetails(id: Int) = viewModelScope.launch {
        mainRepository.getCharacterDetails(id).collect {
            _characterDetails.value = it
        }
    }
}