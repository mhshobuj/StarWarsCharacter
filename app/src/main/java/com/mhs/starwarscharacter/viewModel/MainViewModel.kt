package com.mhs.starwarscharacter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhs.starwarscharacter.repository.MainRepository
import com.mhs.starwarscharacter.response.character.CharacterDetails
import com.mhs.starwarscharacter.response.character.CharacterList
import com.mhs.starwarscharacter.response.planet.PlanetDetails
import com.mhs.starwarscharacter.response.planet.PlanetList
import com.mhs.starwarscharacter.response.starShip.StarShipDetails
import com.mhs.starwarscharacter.response.starShip.StarShipList
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

    //getStarShipList
    private val _starShipsList = MutableLiveData<DataStatus<StarShipList>>()
    val starShipList: LiveData<DataStatus<StarShipList>> get() = _starShipsList

    fun getStarShipList(page: Int) = viewModelScope.launch {
        mainRepository.getStarShipList(page).collect {
            _starShipsList.value = it
        }
    }

    //getStarShipDetails
    private val _starShipDetails = MutableLiveData<DataStatus<StarShipDetails>>()
    val starShipDetails: LiveData<DataStatus<StarShipDetails>> get() = _starShipDetails

    fun getStarShipDetails(id: Int) = viewModelScope.launch {
        mainRepository.getStarShipDetails(id).collect {
            _starShipDetails.value = it
        }
    }

    //getPlanetsList
    private val _planetsList = MutableLiveData<DataStatus<PlanetList>>()
    val planetsList: LiveData<DataStatus<PlanetList>> get() = _planetsList

    fun getPlanetsList(page: Int) = viewModelScope.launch {
        mainRepository.getPlanetsList(page).collect {
            _planetsList.value = it
        }
    }

    //getPlanetsDetails
    private val _planetsDetails = MutableLiveData<DataStatus<PlanetDetails>>()
    val planetDetails: LiveData<DataStatus<PlanetDetails>> get() = _planetsDetails

    fun getPlanetDetails(id: Int) = viewModelScope.launch {
        mainRepository.getPlanetDetails(id).collect {
            _planetsDetails.value = it
        }
    }
}