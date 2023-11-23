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

    // LiveData for character list
    private val _charactersList = MutableLiveData<DataStatus<CharacterList>>()
    val charactersList: LiveData<DataStatus<CharacterList>> get() = _charactersList

    /**
     * Fetches a list of characters from the repository.
     *
     * @param page The page number for pagination.
     */
    fun getCharacterList(page: Int) = viewModelScope.launch {
        mainRepository.getCharacterList(page).collect {
            _charactersList.value = it
        }
    }

    // LiveData for character details
    private val _characterDetails = MutableLiveData<DataStatus<CharacterDetails>>()
    val characterDetails: LiveData<DataStatus<CharacterDetails>> get() = _characterDetails

    /**
     * Fetches details of a character from the repository.
     *
     * @param id The ID of the character.
     */
    fun getCharacterDetails(id: Int) = viewModelScope.launch {
        mainRepository.getCharacterDetails(id).collect {
            _characterDetails.value = it
        }
    }

    // LiveData for starship list
    private val _starShipsList = MutableLiveData<DataStatus<StarShipList>>()
    val starShipList: LiveData<DataStatus<StarShipList>> get() = _starShipsList

    /**
     * Fetches a list of starships from the repository.
     *
     * @param page The page number for pagination.
     */
    fun getStarShipList(page: Int) = viewModelScope.launch {
        mainRepository.getStarShipList(page).collect {
            _starShipsList.value = it
        }
    }

    // LiveData for starship details
    private val _starShipDetails = MutableLiveData<DataStatus<StarShipDetails>>()
    val starShipDetails: LiveData<DataStatus<StarShipDetails>> get() = _starShipDetails

    /**
     * Fetches details of a starship from the repository.
     *
     * @param id The ID of the starship.
     */
    fun getStarShipDetails(id: Int) = viewModelScope.launch {
        mainRepository.getStarShipDetails(id).collect {
            _starShipDetails.value = it
        }
    }

    // LiveData for planets list
    private val _planetsList = MutableLiveData<DataStatus<PlanetList>>()
    val planetsList: LiveData<DataStatus<PlanetList>> get() = _planetsList

    /**
     * Fetches a list of planets from the repository.
     *
     * @param page The page number for pagination.
     */
    fun getPlanetsList(page: Int) = viewModelScope.launch {
        mainRepository.getPlanetsList(page).collect {
            _planetsList.value = it
        }
    }

    // LiveData for planet details
    private val _planetsDetails = MutableLiveData<DataStatus<PlanetDetails>>()
    val planetDetails: LiveData<DataStatus<PlanetDetails>> get() = _planetsDetails

    /**
     * Fetches details of a planet from the repository.
     *
     * @param id The ID of the planet.
     */
    fun getPlanetDetails(id: Int) = viewModelScope.launch {
        mainRepository.getPlanetDetails(id).collect {
            _planetsDetails.value = it
        }
    }
}