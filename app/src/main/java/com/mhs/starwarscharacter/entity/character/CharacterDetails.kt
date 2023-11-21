package com.mhs.starwarscharacter.entity.character

import androidx.room.Entity

@Entity(tableName = "character_details")
data class CharacterDetails(
    val characterId: Int,
    var birthYear: String,
    var eyeColor: String,
    var gender: String,
    var hairColor: String,
    var height: String,
    var mass: String,
    var name: String,
    var skinColor: String,
)
