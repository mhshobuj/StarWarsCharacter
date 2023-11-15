package com.mhs.starwarscharacter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mhs.starwarscharacter.R
import com.mhs.starwarscharacter.databinding.ActivityMainBinding
import com.mhs.starwarscharacter.ui.fragments.CharacterFragment
import com.mhs.starwarscharacter.ui.fragments.PlanetFragment
import com.mhs.starwarscharacter.ui.fragments.StarShipFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(CharacterFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.character -> replaceFragment(CharacterFragment())
                R.id.starship -> replaceFragment(StarShipFragment())
                R.id.planet -> replaceFragment(PlanetFragment())
                else ->{
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}