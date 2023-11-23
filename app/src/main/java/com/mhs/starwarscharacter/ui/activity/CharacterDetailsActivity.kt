package com.mhs.starwarscharacter.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.mhs.starwarscharacter.databinding.ActivityCharacterDetailsBinding
import com.mhs.starwarscharacter.db.StarWarDatabase
import com.mhs.starwarscharacter.entity.character.CharacterDetailsDB
import com.mhs.starwarscharacter.response.character.CharacterDetails
import com.mhs.starwarscharacter.utils.DataStatus
import com.mhs.starwarscharacter.utils.NetworkChecking
import com.mhs.starwarscharacter.utils.isVisible
import com.mhs.starwarscharacter.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailsBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var starWarDatabase: StarWarDatabase
    private var connectivityStatus: String? = null
    private var itemURL: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize view binding
        binding = ActivityCharacterDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check network connectivity
        val networkChecking = NetworkChecking
        connectivityStatus = networkChecking.getConnectivityStatusString(this)

        // Initialize the Room database
        starWarDatabase = StarWarDatabase.getDatabase(this)

        // Retrieve the character's URL from the Intent
        itemURL = intent.getStringExtra("itemURL")
        if (!itemURL.isNullOrEmpty()) {
            // Extract character ID from the URL and fetch details
            val characterID = extractCharacterIDFromUrl(itemURL!!)
            if (characterID != null) {
                getCharacterDetails(characterID)
            }
        }
    }

    private fun getCharacterDetails(id: Int) {
        if (connectivityStatus == "Wifi enabled" || connectivityStatus == "Mobile data enabled") {
            // Fetch character details from the network if there is an internet connection
            lifecycleScope.launch {
                binding.apply {
                    viewModel.getCharacterDetails(id)
                    viewModel.characterDetails.observe(this@CharacterDetailsActivity) {
                        when (it.status) {
                            DataStatus.Status.LOADING -> {
                                pBarLoading.isVisible(true, mainLayout)
                            }

                            DataStatus.Status.SUCCESS -> {
                                pBarLoading.isVisible(false, mainLayout)
                                it.data?.let { value -> setValue(value) }

                                // Save character details to the local database
                                GlobalScope.launch {
                                    it.data?.let { characterDetails ->
                                        val characterDetail = CharacterDetailsDB().apply {
                                            birthYear = characterDetails.birthYear.toString()
                                            eyeColor = characterDetails.eyeColor.toString()
                                            gender = characterDetails.gender.toString()
                                            hairColor = characterDetails.hairColor.toString()
                                            height = characterDetails.height.toString()
                                            mass = characterDetails.mass.toString()
                                            name = characterDetails.name.toString()
                                            skinColor = characterDetails.skinColor.toString()
                                            url = characterDetails.url.toString()
                                        }
                                        starWarDatabase.starWarDao()
                                            .addCharacterDetails(characterDetail)
                                    }
                                }
                            }

                            DataStatus.Status.ERROR -> {
                                pBarLoading.isVisible(false, mainLayout)
                                Toast.makeText(
                                    this@CharacterDetailsActivity,
                                    "There is something wrong!!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
        } else {
            // Fetch character details from the local database if there is no internet connection
            binding.pBarLoading.isVisible(false, binding.mainLayout)
            GlobalScope.launch {
                val characterDetails = starWarDatabase.starWarDao().getCharacterDetails(itemURL!!)
                val characterDetail = CharacterDetails().apply {
                    birthYear = characterDetails.birthYear
                    created = ""
                    edited = ""
                    eyeColor = characterDetails.eyeColor
                    films = emptyList()
                    gender = characterDetails.gender
                    hairColor = characterDetails.hairColor
                    height = characterDetails.height
                    homeworld = ""
                    mass = characterDetails.mass
                    name = characterDetails.name
                    skinColor = characterDetails.skinColor
                    species = emptyList()
                    starships = emptyList()
                    url = characterDetails.url
                    vehicles = emptyList()
                }
                setValue(characterDetail)
            }
        }
    }

    private fun setValue(data: CharacterDetails) {
        // Set values to the views
        binding.apply {
            txtName.text = data.name
            txtHeight.text = data.height
            txtMass.text = data.mass
            txtHairColor.text = data.hairColor
            txtSkinColor.text = data.skinColor
            txtEyeColor.text = data.eyeColor
            txtBirthDay.text = data.birthYear
            txtGender.text = data.gender
        }
    }

    private fun extractCharacterIDFromUrl(url: String): Int? {
        // Define a regular expression to match numeric values
        val regex = "\\d+".toRegex()

        // Find the first match in the URL
        val matchResult = regex.find(url)

        // Extract the matched value and convert it to Int
        return matchResult?.value?.toIntOrNull()
    }

}