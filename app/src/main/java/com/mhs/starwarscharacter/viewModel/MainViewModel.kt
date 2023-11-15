package com.mhs.starwarscharacter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhs.starwarscharacter.repository.MainRepository
import com.mhs.starwarscharacter.response.character.CharacterList
import com.mhs.starwarscharacter.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    //getCharacterList
    private val _characterList = MutableLiveData<DataStatus<CharacterList>>()
    val charactersList: LiveData<DataStatus<CharacterList>> get() = _characterList

    fun getCharacterList() = viewModelScope.launch {
        mainRepository.getCharacterList().collect {
            _characterList.value = it
        }
    }
}