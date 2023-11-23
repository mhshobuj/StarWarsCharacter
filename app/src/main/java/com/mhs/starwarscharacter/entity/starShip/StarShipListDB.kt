package com.mhs.starwarscharacter.entity.starShip

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "star_ship", indices = [Index(value = ["url"], unique = true)])
data class StarShipListDB(
    @PrimaryKey(autoGenerate = true)
    var characterId: Int? = null, // Nullable or remove default initialization
    var name: String,
    var model: String,
    var costInCredits: String,
    var url: String
)
