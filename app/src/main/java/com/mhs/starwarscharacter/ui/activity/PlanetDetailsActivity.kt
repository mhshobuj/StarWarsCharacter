package com.mhs.starwarscharacter.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mhs.starwarscharacter.databinding.ActivityPlanetDetailsBinding
import com.mhs.starwarscharacter.db.StarWarDatabase
import com.mhs.starwarscharacter.entity.planet.PlanetDetailsDB
import com.mhs.starwarscharacter.response.planet.PlanetDetails
import com.mhs.starwarscharacter.utils.DataStatus
import com.mhs.starwarscharacter.utils.NetworkChecking
import com.mhs.starwarscharacter.utils.isVisible
import com.mhs.starwarscharacter.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlanetDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlanetDetailsBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var starWarDatabase: StarWarDatabase
    private var connectivityStatus: String? = null
    private var itemURL: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanetDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** Network checking **/
        val networkChecking = NetworkChecking
        connectivityStatus = networkChecking.getConnectivityStatusString(this)

        //initialize database
        starWarDatabase = StarWarDatabase.getDatabase(this)

        // Retrieve the string value from the Intent
        itemURL = intent.getStringExtra("itemURL")
        if (itemURL!!.isNotEmpty()){
            val planetID = extractCharacterIDFromUrl(itemURL!!)
            if (planetID != null) {
                getPlanetDetails(planetID)
            }
        }
    }

    private fun getPlanetDetails(id: Int) {
        if (connectivityStatus == "Wifi enabled" || connectivityStatus == "Mobile data enabled") {
            lifecycleScope.launch {
                binding.apply {
                    viewModel.getPlanetDetails(id)
                    viewModel.planetDetails.observe(this@PlanetDetailsActivity) {
                        when (it.status) {
                            DataStatus.Status.LOADING -> {
                                pBarLoading.isVisible(true, mainLayout)
                            }

                            DataStatus.Status.SUCCESS -> {
                                pBarLoading.isVisible(false, mainLayout)
                                it.data?.let { value -> setValue(value) }

                                GlobalScope.launch {
                                    it.data.let { planetDetails ->
                                        val planetDetail = PlanetDetailsDB().apply {
                                            name = planetDetails?.name.toString()
                                            rotation_period = planetDetails?.rotationPeriod.toString()
                                            orbital_period = planetDetails?.orbitalPeriod.toString()
                                            diameter = planetDetails?.diameter.toString()
                                            climate = planetDetails?.climate.toString()
                                            gravity = planetDetails?.gravity.toString()
                                            terrain = planetDetails?.terrain.toString()
                                            surface_water = planetDetails?.surfaceWater.toString()
                                            population = planetDetails?.population.toString()
                                            url = planetDetails?.url.toString()
                                        }
                                        starWarDatabase.starWarDao()
                                            .addPlanetDetails(planetDetail)
                                    }
                                }
                            }

                            DataStatus.Status.ERROR -> {
                                pBarLoading.isVisible(false, mainLayout)
                                Toast.makeText(
                                    this@PlanetDetailsActivity,
                                    "There is something wrong!!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
        } else{
            binding.pBarLoading.isVisible(false, binding.mainLayout)
            GlobalScope.launch {
                val planetDetails = starWarDatabase.starWarDao().getPlanetDetails(itemURL!!)
                val planetDetail = PlanetDetails().apply {
                    climate = planetDetails.climate
                    diameter = planetDetails.diameter
                    gravity = planetDetails.gravity
                    name = planetDetails.name
                    rotationPeriod = planetDetails.rotation_period
                    orbitalPeriod = planetDetails.orbital_period
                    terrain = planetDetails.terrain
                    surfaceWater = planetDetails.surface_water
                    population = planetDetails.population
                }
                setValue(planetDetail)
            }
        }
    }

    private fun setValue(data: PlanetDetails) {
        binding.apply {
            txtName.text = data.name
            txtRPeriod.text = data.rotationPeriod
            txtOPeriod.text = data.orbitalPeriod
            txtDiameter.text = data.diameter
            txtClimate.text = data.climate
            txtGravity.text = data.gravity
            txtTerrain.text = data.terrain
            txtSurface.text = data.surfaceWater
            txtPopulation.text = data.population
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