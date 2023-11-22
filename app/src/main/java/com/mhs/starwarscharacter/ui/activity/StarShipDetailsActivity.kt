package com.mhs.starwarscharacter.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mhs.starwarscharacter.databinding.ActivityStarShipDetailsBinding
import com.mhs.starwarscharacter.db.StarWarDatabase
import com.mhs.starwarscharacter.entity.character.CharacterDetailsDB
import com.mhs.starwarscharacter.response.character.CharacterDetails
import com.mhs.starwarscharacter.response.starShip.StarShipDetails
import com.mhs.starwarscharacter.utils.DataStatus
import com.mhs.starwarscharacter.utils.NetworkChecking
import com.mhs.starwarscharacter.utils.isVisible
import com.mhs.starwarscharacter.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StarShipDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStarShipDetailsBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var starWarDatabase: StarWarDatabase
    private var connectivityStatus: String? = null
    private var itemURL: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStarShipDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** Network checking **/
        val networkChecking = NetworkChecking
        connectivityStatus = networkChecking.getConnectivityStatusString(this)

        //initialize database
        starWarDatabase = StarWarDatabase.getDatabase(this)

        // Retrieve the string value from the Intent
        itemURL = intent.getStringExtra("itemURL")
        if (itemURL!!.isNotEmpty()){
            val starShipID = extractCharacterIDFromUrl(itemURL!!)
            if (starShipID != null) {
                getStarShipDetails(starShipID)
            }
        }
    }

    private fun getStarShipDetails(id: Int) {
        if (connectivityStatus == "Wifi enabled" || connectivityStatus == "Mobile data enabled") {
            lifecycleScope.launch {
                binding.apply {
                    viewModel.getStarShipDetails(id)
                    viewModel.starShipDetails.observe(this@StarShipDetailsActivity) {
                        when (it.status) {
                            DataStatus.Status.LOADING -> {
                                pBarLoading.isVisible(true, mainLayout)
                            }

                            DataStatus.Status.SUCCESS -> {
                                pBarLoading.isVisible(false, mainLayout)
                                it.data?.let { value -> setValue(value) }

                                /*GlobalScope.launch {
                                    it.data.let { characterDetails ->
                                        val characterDetail = CharacterDetailsDB().apply {
                                            birthYear = characterDetails?.birthYear.toString()
                                            eyeColor = characterDetails?.eyeColor.toString()
                                            gender = characterDetails?.gender.toString()
                                            hairColor = characterDetails?.hairColor.toString()
                                            height = characterDetails?.height.toString()
                                            mass = characterDetails?.mass.toString()
                                            name = characterDetails?.name.toString()
                                            skinColor = characterDetails?.skinColor.toString()
                                            url = characterDetails?.url.toString()
                                        }
                                        starWarDatabase.starWarDao()
                                            .addCharacterDetails(characterDetail)
                                    }
                                }*/
                            }

                            DataStatus.Status.ERROR -> {
                                pBarLoading.isVisible(false, mainLayout)
                                Toast.makeText(
                                    this@StarShipDetailsActivity,
                                    "There is something wrong!!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
        } else{
            /*binding.pBarLoading.isVisible(false, binding.mainLayout)
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
            }*/
        }
    }

    private fun setValue(data: StarShipDetails) {
        binding.apply {
            txtName.text = data.name
            txtModel.text = data.model
            txtManufacturer.text = data.manufacturer
            txtCost.text = data.costInCredits
            txtLength.text = data.length
            txtSpeed.text = data.maxAtmospheringSpeed
            txtCrew.text = data.crew
            txtPassengers.text = data.passengers
            txtCapacity.text = data.cargoCapacity
            txtConsumables.text = data.consumables
            txtRating.text = data.hyperdriveRating
            txtMGLT.text = data.mGLT
            txtClass.text = data.starshipClass
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