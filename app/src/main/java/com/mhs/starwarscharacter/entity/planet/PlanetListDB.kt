package com.mhs.starwarscharacter.entity.planet

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "planets", indices = [Index(value = ["url"], unique = true)])
data class PlanetListDB(
    @PrimaryKey(autoGenerate = true)
    var characterId: Int? = null, // Nullable or remove default initialization
    var name: String,
    var diameter: String,
    var population: String,
    var url: String
)
