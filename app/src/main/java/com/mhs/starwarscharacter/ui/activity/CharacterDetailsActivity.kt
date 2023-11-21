package com.mhs.starwarscharacter.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mhs.starwarscharacter.databinding.ActivityCharacterDetailsBinding
import com.mhs.starwarscharacter.utils.DataStatus
import com.mhs.starwarscharacter.utils.isVisible
import com.mhs.starwarscharacter.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailsBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the string value from the Intent
        val itemURL = intent.getStringExtra("itemURL")
        if (itemURL!!.isNotEmpty()){
            val characterID = extractCharacterIDFromUrl(itemURL)
            if (characterID != null) {
                getCharacterDetails(characterID)
            }
        }
    }

    private fun getCharacterDetails(id: Int) {
        lifecycleScope.launch {
            binding.apply {
                viewModel.getCharacterDetails(id)
                viewModel.characterDetails.observe(this@CharacterDetailsActivity){
                    when(it.status){
                        DataStatus.Status.LOADING->{
                            pBarLoading.isVisible(true, mainLayout)
                        }
                        DataStatus.Status.SUCCESS->{
                            pBarLoading.isVisible(false, mainLayout)
                            txtName.text = it.data?.name
                            txtHeight.text = it.data?.height
                            txtMass.text = it.data?.mass
                            txtHairColor.text = it.data?.hairColor
                            txtSkinColor.text = it.data?.skinColor
                            txtEyeColor.text = it.data?.eyeColor
                            txtBirthDay.text = it.data?.birthYear
                            txtGender.text = it.data?.gender
                        }
                        DataStatus.Status.ERROR->{
                            pBarLoading.isVisible(false, mainLayout)
                            Toast.makeText(this@CharacterDetailsActivity, "There is something wrong!!", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
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