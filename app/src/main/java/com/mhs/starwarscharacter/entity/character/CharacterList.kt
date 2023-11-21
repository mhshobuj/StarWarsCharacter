package com.mhs.starwarscharacter.entity.character

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "characters", indices = [Index(value = ["url"], unique = true)])
data class CharacterList(
    @PrimaryKey(autoGenerate = true)
    var characterId: Int? = null, // Nullable or remove default initialization
    var gender: String,
    var height: String,
    var name: String,
    var url: String
)
