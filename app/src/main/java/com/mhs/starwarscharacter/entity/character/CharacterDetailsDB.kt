package com.mhs.starwarscharacter.entity.character

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "character_details", indices = [Index(value = ["url"], unique = true)])
data class CharacterDetailsDB(
    var birthYear: String,
    var eyeColor: String,
    var gender: String,
    var hairColor: String,
    var height: String,
    var mass: String,
    var name: String,
    var skinColor: String,
    @PrimaryKey
    var url: String
){
    // Default constructor required by Room
    constructor() : this("", "", "", "", "", "", "", "", "")
}
